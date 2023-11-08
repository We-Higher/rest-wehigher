package com.example.demo.schedule;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static junit.runner.Version.id;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService service;

    @RequestMapping("")
    public String schedule() {
        return "schedule/calendar";
    }


    //목록
    @RequestMapping("/list")
    @ResponseBody
    public ArrayList<Map<String, Object>> list(ModelMap map) {//map은 자동으로 뷰페이지로 전달됨
        ArrayList<ScheduleDto> listAll = service.getAll();

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        for (int i = 0; i < listAll.size(); i++) {
            hash.put("cal_Id",listAll.get(i).getId());
            hash.put("title", listAll.get(i).getTitle());
            hash.put("start", listAll.get(i).getStartDate());
            hash.put("end", listAll.get(i).getEndDate());
            hash.put("className", listAll.get(i).getClassName());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        return jsonArr;
    }

    //일정추가폼
    @GetMapping("/add")
    public void addForm() {
    }


    //일정추가
    @PostMapping("/add")
    @ResponseBody
    public Map addEvent(@RequestBody List<Map<String, Object>> param) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        System.out.println("param = " + param);
        System.out.println(param.get(0));
        System.out.println(param.get(0).get("title"));
        System.out.println(param.get(0).get("start"));
        System.out.println(param.get(0).get("end"));

        String title = (String) param.get(0).get("title"); // 이름 받아오기
        String startDateString = (String) param.get(0).get("start");
        String endDateString = (String) param.get(0).get("end");


        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

        ScheduleDto dto = ScheduleDto.builder()
                .title(title).startDate(startDate).endDate(endDate)
                .build();
        ScheduleDto s = service.save(dto);

        Map<String, String> map = new HashMap<>();
        map.put("id" , s.getId()+"");
        return map;

    }

    //일정삭제
    @DeleteMapping("/del/{id}")
    public String del(@PathVariable("id") int id) {
        service.del(id);
        return "redirect:/schedule";
    }

    //글번호로검색(수정폼)
    @GetMapping("/edit/{id}")
    public void editForm(int id, ModelMap map) {
        ScheduleDto s = service.get(id);
        map.addAttribute("s", s);
    }

    //수정
    @PatchMapping("/edit/{id}")
    @ResponseBody
    public Map modifyEvent(@PathVariable("id") int id, @RequestBody List<Map<String, Object>> param){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        ScheduleDto origin = service.get(id);
        String startDateString = (String) param.get(0).get("start");
        String endDateString = (String) param.get(0).get("end");

        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

        ScheduleDto dto = ScheduleDto.builder()
                .id(id).title(origin.getTitle()).startDate(startDate).endDate(endDate)
                .build();
        ScheduleDto s = service.save(dto);

        return null;
    }



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

}
