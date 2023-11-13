package com.example.demo.dataroom;

import com.example.demo.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DataroomService {
    private final DataroomDao dao;

    public DataroomDto save(DataroomDto dto) {
        Dataroom d = dao.save(new Dataroom(dto.getNum(), dto.getWriter(), dto.getWdate(), dto.getTitle(),
                dto.getContent(), dto.getFname(), dto.getCnt()));
        return new DataroomDto(d.getNum(), d.getWriter(), d.getWdate(), d.getTitle(), d.getContent(),
                d.getFname(), d.getCnt(), null);
    }

    public DataroomDto getDataroom(int num) {
        Dataroom d = dao.findById(num).orElse(null);
        return new DataroomDto(d.getNum(), d.getWriter(), d.getWdate(), d.getTitle(), d.getContent(),
                d.getFname(), d.getCnt(), null);
    }

    public ArrayList<DataroomDto> getAll() {
        List<Dataroom> l = dao.findAll();
        ArrayList<DataroomDto> list = new ArrayList<DataroomDto>();
        for (Dataroom d : l) {
            list.add(new DataroomDto(d.getNum(), d.getWriter(), d.getWdate(), d.getTitle(), d.getContent(),
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
        Dataroom d = dao.save(new Dataroom(dto.getNum(), dto.getWriter(), dto.getWdate(), dto.getTitle(),
                dto.getContent(), dto.getFname(), dto.getCnt()));
        return new DataroomDto(d.getNum(), d.getWriter(), d.getWdate(), d.getTitle(), d.getContent(),
                d.getFname(), d.getCnt(), null);
    }

    //select로 검색
    public List<DataroomDto> getSearchReference(ReferenceSearch referenceSearch) {
        List<Dataroom> list = new ArrayList<>();
        if (Objects.equals("writer", referenceSearch.getSelect())) {
            list = dao.findByWriterNameContains(referenceSearch.getSearch());
        }
        if (Objects.equals("title", referenceSearch.getSelect())) {
            list = dao.findByTitleContains(referenceSearch.getSearch());
        }
        return list.stream()
                .map(DataroomDto::of)
                .toList();
    }

}