package com.example.demo.dataroom;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DataroomService {

	@Autowired
    private DataroomDao dao;

    public DataroomDto save(DataroomDto dto) {
    	
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedTime1 = LocalDateTime.now().format(formatter1);
    	dto.setWdate(formattedTime1);
    	dto.setTitle(dto.getFname());
        Dataroom d = dao.save(new Dataroom(dto.getNum(), dto.getMember(), dto.getWdate(), dto.getTitle(),
                dto.getContent(), dto.getFname(), dto.getCnt()));
        return new DataroomDto(d.getNum(), d.getMember(), d.getWdate(), d.getTitle(), d.getContent(),
                d.getFname(), d.getCnt(), null);
    }

    public DataroomDto getDataroom(int num) {
        Dataroom d = dao.findById(num).orElse(null);
        if(d==null) {
        	return null;
        }
        else {
        	return new DataroomDto(d.getNum(), d.getMember(), d.getWdate(), d.getTitle(), d.getContent(),
                    d.getFname(), d.getCnt(), null);
        }
    }

    public ArrayList<DataroomDto> getAll() {
        List<Dataroom> l = dao.findAll();
        ArrayList<DataroomDto> list = new ArrayList<DataroomDto>();
        for (Dataroom d : l) {
            list.add(new DataroomDto(d.getNum(), d.getMember(), d.getWdate(), d.getTitle(), d.getContent(),
                    d.getFname(), d.getCnt(), null));
        }
        return list;
    }

    public void delDataroom(int num) {
        dao.deleteById(num);
    }

    //다운로드 카운트
    public void editCnt(int num) {
        dao.updateCnt(num);
    }

    //수정
    public DataroomDto edit(DataroomDto dto) {
        Dataroom d = dao.save(new Dataroom(dto.getNum(), dto.getMember(), dto.getWdate(), dto.getTitle(),
                dto.getContent(), dto.getFname(), dto.getCnt()));
        return new DataroomDto(d.getNum(), d.getMember(), d.getWdate(), d.getTitle(), d.getContent(),
                d.getFname(), d.getCnt(), null);
    }

    //select로 검색
    public List<DataroomDto> getSearchReference(ReferenceSearch referenceSearch) {
        List<Dataroom> list = new ArrayList<>();
        if (Objects.equals("writer", referenceSearch.getSelect())) {
            list = dao.findByMemberNameContains(referenceSearch.getSearch());
        }
        if (Objects.equals("title", referenceSearch.getSelect())) {
            list = dao.findByTitleContains(referenceSearch.getSearch());
        }
        return list.stream()
                .map(DataroomDto::of)
                .toList();
    }

}