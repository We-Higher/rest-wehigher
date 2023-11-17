package com.example.demo.approval.vacation;

import com.example.demo.approval.expense.Expense;
import com.example.demo.approval.expense.ExpenseDto;
import com.example.demo.approval.report.Report;
import com.example.demo.approval.report.ReportDto;
import com.example.demo.member.MemberDto;
import com.example.demo.schedule.ScheduleService;
import com.example.demo.schedule.Schedule;
import com.example.demo.schedule.ScheduleDao;

import lombok.RequiredArgsConstructor;

import com.example.demo.schedule.ScheduleDao;
import com.example.demo.schedule.ScheduleDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class VacationService {
    @Autowired
    private VactaionDao dao;
    
    @Autowired
    private ScheduleDao sdao;
    
    @Autowired
    private ScheduleService sservice;

    public VacationDto saveVacation(VacationDto dto) {
        Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApproval1rank(), dto.getApproval2rank(), dto.getApp1username(), dto.getApp2username()));
        return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApproval1rank(), v.getApproval2rank(), v.getApp1username(), v.getApp2username());
    }

    public ArrayList<VacationDto> getAll() {
        ArrayList<Vacation> list = (ArrayList<Vacation>) dao.findAll();
        ArrayList<VacationDto> list2 = new ArrayList<>();
        for (Vacation v : list) {
            list2.add(new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApproval1rank(), v.getApproval2rank(), v.getApp1username(), v.getApp2username()));
        }
        return list2;
    }

    public VacationDto getById(int num) {
        Vacation v = dao.getById(num);
        return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApproval1rank(), v.getApproval2rank(), v.getApp1username(), v.getApp2username());
    }
    
    public VacationDto approveVacation(VacationDto dto, MemberDto mdto) {
    	
    	if(dto.getRstatus()==0 && dto.getStatus()==0 && dto.getApp1username().equals(mdto.getUsername())){
    		dto.setStatus(1);
        	Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApproval1rank(), dto.getApproval2rank(), dto.getApp1username(), dto.getApp2username()));
            return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApproval1rank(), v.getApproval2rank(), v.getApp1username(), v.getApp2username());
    	}
    	else if(dto.getRstatus()==0 &&dto.getStatus()==1 && dto.getApp2username().equals(mdto.getUsername())){
    		dto.setStatus(2);
    		
    		Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApproval1rank(), dto.getApproval2rank(), dto.getApp1username(), dto.getApp2username()));
    		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
    		//DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
            String startDateString = v.getStartDate() + "T09:00:00.000Z";
            String endDateString = v.getEndDate() + "T18:00:00.000Z";
    		LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
            ScheduleDto sdto = ScheduleDto.builder()
                    .title(dto.getMember().getName() + "" + dto.getMember().getCompanyRankName() + "휴가").startDate(startDate).endDate(endDate)
                    .build();
            sdao.save(new Schedule(sdto.getId(), sdto.getTitle(), sdto.getStartDate(), sdto.getEndDate(), sdto.getClassName()));
            return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApproval1rank(), v.getApproval2rank(), v.getApp1username(), v.getApp2username());
    	}
    	else {
    		System.out.println("결제할 수 없습니다.");
    		return null;
    	}
    }
    
    public VacationDto refuseVacation(VacationDto dto, MemberDto mdto) {
    	
    	if(dto.getRstatus()==0 && dto.getStatus()==0 && dto.getApp1username().equals(mdto.getUsername())){
    		
    		dto.setRstatus(-1);
    		Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApproval1rank(), dto.getApproval2rank(), dto.getApp1username(), dto.getApp2username()));
            return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApproval1rank(), v.getApproval2rank(), v.getApp1username(), v.getApp2username());
    	}
    	else if(dto.getRstatus()==0 && dto.getStatus()==1 && dto.getApp2username().equals(mdto.getUsername())){
    		dto.setRstatus(-1);		
    		Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApproval1rank(), dto.getApproval2rank(), dto.getApp1username(), dto.getApp2username()));
    		return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApproval1rank(), v.getApproval2rank(), v.getApp1username(), v.getApp2username());
    	}
    	else {
    		
    		System.out.println("반려할 수 	없습니다.");
    		return null;
    	}
    	
    }
}
