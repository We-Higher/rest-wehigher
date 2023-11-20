package com.example.demo.schedule;

import com.example.demo.member.Member;
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
        Schedule entity = dao.save(new Schedule(dto.getId(), dto.getMember(), dto.getTitle(), dto.getStartDate(), dto.getEndDate()));
        return new ScheduleDto(entity.getId(), entity.getMember(), entity.getTitle(), entity.getStartDate(), entity.getEndDate());
    }

    public ScheduleDto get(int id) {
        Schedule entity = dao.findById(id).orElse(null);
        return new ScheduleDto(entity.getId(), entity.getMember(), entity.getTitle(), entity.getStartDate(), entity.getEndDate());
    }

    // 전체 리스트
    public ArrayList<ScheduleDto> getAll(){
        List<Schedule> s = dao.findAll();
        ArrayList<ScheduleDto> list = new ArrayList<>();
        for(Schedule entity:s) {
            list.add(new ScheduleDto(entity.getId(), entity.getMember(), entity.getTitle(), entity.getStartDate(), entity.getEndDate()));
        }
        return list;
    }

    //member 리스트
    public ArrayList<ScheduleDto> getByMember(Member member){
        List<Schedule> ms=dao.findByMemberOrMemberId(member, 1L);
        ArrayList<ScheduleDto> list =new ArrayList<>();
        for(Schedule entity:ms) {
            list.add(new ScheduleDto(entity.getId(), entity.getMember(), entity.getTitle(), entity.getStartDate(), entity.getEndDate()));
        }
        return list;
    }

    //삭제
    public void del(int id) {
        dao.deleteById(id);
    }
}
