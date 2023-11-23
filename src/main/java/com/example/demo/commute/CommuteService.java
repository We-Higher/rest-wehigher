package com.example.demo.commute;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommuteService {
    @Autowired
    private CommuteDao cdao;

    public CommuteDto get(int commuteNum) {
        Commute c = cdao.findById(commuteNum).orElse(null);
        return new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate());
    }

    public CommuteDto getByDateAndUserName(String BasicDate, String Username) {
        Commute c = cdao.findByBasicDateAndMemberUsername(BasicDate, Username);
        if (c != null)
            return new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate());
        else return null;

    }


    public ArrayList<CommuteDto> getAll() {
        ArrayList<Commute> list = (ArrayList<Commute>) cdao.findAll();
        ArrayList<CommuteDto> list2 = new ArrayList<>();
        for (Commute c : list) {
            list2.add(new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate()));
        }
        return list2;
    }

    public CommuteDto save(CommuteDto dto) {
        Commute c = cdao.save(new Commute(dto.getCommuteNum(), dto.getMember(), dto.getBasicDate(), dto.getStartTime(), dto.getEndTime(), dto.getReason(), dto.getEditStartTime(), dto.getEditEndTime(), dto.getEditBasicDate()));
        return new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate());
    }

    public Page<CommuteDto> getByOption(String type, String option, Pageable pageable) {
        Page<Commute> list;
        if (type.equals("basicDate")) {
            list = cdao.findByBasicDateLike(option, pageable);
        } else if (type.equals("name")) {
            list = cdao.findByMemberNameContaining(option, pageable);
        } else if (type.equals("deptName")) {
            list = cdao.findByMemberDeptNameContaining(option, pageable);
        } else {
            list = cdao.findAll(pageable);
        }
        return list.map(CommuteDto::of);
    }

    public Page<CommuteDto> getByMemberId(Long id, Pageable pageable) {
        Page<Commute> list = cdao.findByMemberId(id, pageable);
        return list.map(CommuteDto::of);
    }

    public Page<Commute> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.cdao.findAll(pageable);
    }
}
