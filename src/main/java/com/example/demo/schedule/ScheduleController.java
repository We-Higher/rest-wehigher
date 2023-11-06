package com.example.demo.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService service;

    //글목록
    @RequestMapping("/list")
    public void list(ModelMap map) {//map은 자동으로 뷰페이지로 전달됨
        ArrayList<ScheduleDto> list = service.getAll();
        map.addAttribute("list", list);
        //뷰 페이지 경로: /board/list.jsp
    }

    //글작성폼
    @GetMapping("/add")
    public void addForm() {}

    //글작성
    @PostMapping("/add")
    public String add(ScheduleDto s) {
        service.save(s);
        return "redirect:/schedule/canlendar";
    }

//    //글번호로검색(수정폼)
//    @GetMapping("/edit")
//    public void editForm(int id, ModelMap map) {
//        ScheduleDto s = service.get(id);
//        map.addAttribute("s", s);
//    }
//
//    //수정완료
//    @PostMapping("/edit")
//    public String edit(ScheduleDto s) {
//        ScheduleDto s2 = service.get(s.getId());
//        s2.setTitle(s.getTitle());
//        s2.setStart(s.getStart());
//        s2.setEnd(s.getEnd());
//        service.save(s2);
//        return "redirect:/schedule/canlendar";
//    }
//
//    //삭제
//    @RequestMapping("/del")
//    public String del(int id) {
//        service.del(id);
//        return "redirect:/schedule/canlendar";
//    }
}
