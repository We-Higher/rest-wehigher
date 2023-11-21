package com.example.demo.schedule;

import com.example.demo.member.Member;
import com.example.demo.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.demo.member.Member;

import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService service;
    private final MemberService memberService;

    @RequestMapping("")
    public String schedule() {
        return "schedule/calendar";
    }


    //목록
    @RequestMapping("/list")
    @ResponseBody
    public ArrayList<Map<String, Object>> list(ModelMap map) {//map은 자동으로 뷰페이지로 전달됨
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        ArrayList<ScheduleDto> list = service.getByMember(member);

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();


        for (int i = 0; i < listAll.size(); i++) {
            hash.put("cal_Id",listAll.get(i).getId());
            hash.put("title", listAll.get(i).getTitle());
            hash.put("start", listAll.get(i).getStartDate());
            hash.put("end", listAll.get(i).getEndDate());
            hash.put("check", listAll.get(i).getCnt());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }

        return jsonArr;
    }

    //일정추가
    @PostMapping("/add")
    @ResponseBody
    public Map addEvent(@RequestBody List<Map<String, Object>> param) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        String title = (String) param.get(0).get("title");
        String startDateString = (String) param.get(0).get("start");
        String endDateString = (String) param.get(0).get("end");

        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        ScheduleDto dto = ScheduleDto.builder()
                .member(member).title(title).startDate(startDate).endDate(endDate)
                .build();
        ScheduleDto s = service.save(dto);

        Map<String, String> map = new HashMap<>();
        map.put("id", s.getId() + "");
        return map;

    }

    //일정삭제
    @GetMapping("/del/{id}")
    public String del(@PathVariable("id") int id) {
        service.del(id);
        return "redirect:/schedule";
    }

    //수정
    @PatchMapping("/edit/{id}")
    @ResponseBody
    public Map modifyEvent(@PathVariable("id") int id, @RequestBody List<Map<String, Object>> param) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        ScheduleDto origin = service.get(id);

        String startDateString = (String) param.get(0).get("start");
        String endDateString = (String) param.get(0).get("end");

        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

        ScheduleDto dto = ScheduleDto.builder()
                .id(id).title(origin.getTitle()).startDate(startDate).endDate(endDate)
                .build();
        
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member loginMember = (Member) authentication.getPrincipal();
    	if(origin.getCnt()==0) {
    		ScheduleDto s = service.save(dto);
    	}
    	else {
    		
    		if(loginMember.getIsMaster()==0) {
    			
                /*PrintWriter out = response.getWriter();
                out.write("<script>alert('"+"관리자만 삭제 가능합니다."+"');location.href='"+"/schedule"+"';</script>");
                out.flush();*/
    			System.out.println("관리자만 변경 가능합니다.");
    		}
    		else {
    			
    			ScheduleDto s = service.save(dto);
    		}
    		
    	}

        return null;
    }
}
