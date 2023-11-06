package com.example.demo.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleDao dao;

    //추가,수정
    public ScheduleDto save(ScheduleDto dto) {
        Schedule entity = dao.save(new Schedule(dto.getId(), dto.getTitle(), dto.getStartDate(), dto.getEndDate()));
        return new ScheduleDto(entity.getId(), entity.getTitle(), entity.getStartDate(), entity.getEndDate());
    }

    public ScheduleDto get(int id) {
        Schedule entity = dao.findById(id).orElse(null);
        return new ScheduleDto(entity.getId(), entity.getTitle(), entity.getStartDate(), entity.getEndDate());
    }
    public ArrayList<ScheduleDto> getAll(){
        List<Schedule> s = dao.findAll();
        ArrayList<ScheduleDto> list = new ArrayList<>();
        for(Schedule entity:s) {
            list.add(new ScheduleDto(entity.getId(), entity.getTitle(), entity.getStartDate(), entity.getEndDate()));
        }
        return list;
    }

    //삭제
    public void del(int id) {
        dao.deleteById(id);
    }
}
