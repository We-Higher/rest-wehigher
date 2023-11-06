package com.example.demo.schedule;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService service;

    //글목록
    @RequestMapping("/list")
    @ResponseBody
    public ArrayList<Map<String, Object>> list(ModelMap map) {//map은 자동으로 뷰페이지로 전달됨
        ArrayList<ScheduleDto> listAll = service.getAll();
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();



        for (int i = 0; i < listAll.size(); i++) {

            hash.put("title", listAll.get(i).getTitle());
            hash.put("start", listAll.get(i).getStartDate());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        return jsonArr;
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

    //글번호로검색
    @GetMapping("/get/{id}")
    public ModelMap editForm(@PathVariable("id") int id, ModelMap map) {
        ScheduleDto s = service.get(id);
        map.addAttribute("s", s);
        return map;
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
