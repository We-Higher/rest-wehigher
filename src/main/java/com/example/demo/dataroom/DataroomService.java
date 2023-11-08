package com.example.demo.dataroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DataroomService {
    private final DataroomDao dao;

    public DataroomDto save(DataroomDto dto){
        Dataroom d = dao.save(new Dataroom(dto.getNum(),dto.getWriter(),dto.getWdate(),dto.getTitle(),
                dto.getContent(),dto.getFname(),dto.getCnt()));
        return new DataroomDto(d.getNum(),d.getWriter(),d.getWdate(),d.getTitle(),d.getContent(),
                d.getFname(),d.getCnt(),null);
    }

    public DataroomDto getDataroom(int num){
        Dataroom d = dao.findById(String.valueOf(num)).orElse(null);
        return new DataroomDto(d.getNum(),d.getWriter(),d.getWdate(),d.getTitle(),d.getContent(),
                d.getFname(),d.getCnt(),null);
    }

    public ArrayList<DataroomDto> getAll(){
        List<Dataroom> l = dao.findAll();
        ArrayList<DataroomDto> list = new ArrayList<DataroomDto>();
        for(Dataroom d:l){
            list.add(new DataroomDto(d.getNum(),d.getWriter(),d.getWdate(),d.getTitle(),d.getContent(),
                    d.getFname(),d.getCnt(),null));
        }
        return list;
    }

    public void delDataroom(int num){
        dao.deleteById(String.valueOf(num));
    }

    //다운로드 카운트
    public void editCnt(int num) {
        dao.updateCnt(num);
    }
}
