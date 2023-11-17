package com.example.demo.commute;

import com.example.demo.member.MemberDao;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if(c!=null) return new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate());
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

    public ArrayList<CommuteDto> getByOption(String type, String option) {
        ArrayList<CommuteDto> list2 = new ArrayList<>();
        if (type.equals("basicDate")) {
            List<Commute> list = cdao.findByBasicDateLike(option);
            for (Commute c : list) {
                list2.add(new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate()));
            }
            return list2;
        } else if (type.equals("name")) {
            List<Commute> list = cdao.findByMemberNameContaining(option);
            for (Commute c : list) {
                list2.add(new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate()));
            }
            return list2;
        } else if (type.equals("deptName")) {
            List<Commute> list = cdao.findByMemberDeptNameContaining(option);
            for (Commute c : list) {
                list2.add(new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate()));
            }
            return list2;
        } else {
            List<Commute> list = cdao.findAll();
            for (Commute c : list) {
                list2.add(new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate()));
            }
            return list2;
        }
    }

    public ArrayList<CommuteDto> getByMemberId(Long id) {
        ArrayList<CommuteDto> list2 = new ArrayList<>();
        List<Commute> list = cdao.findByMemberId(id);
        for (Commute c : list) {
            list2.add(new CommuteDto(c.getCommuteNum(), c.getMember(), c.getBasicDate(), c.getStartTime(), c.getEndTime(), c.getReason(), c.getEditStartTime(), c.getEditEndTime(), c.getEditBasicDate()));
        }
        return list2;
    }
}
