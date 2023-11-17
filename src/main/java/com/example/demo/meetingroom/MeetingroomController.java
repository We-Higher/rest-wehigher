package com.example.demo.meetingroom;


import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@Controller
@RequestMapping("/meetingroom")
public class MeetingroomController {
    private final MeetingroomService service;

    @RequestMapping("")
    public String schedule() {
        return "meetingroom/meetingroom";
    }


    //목록
    @RequestMapping("/list")
    @ResponseBody
    public ArrayList<Map<String, Object>> list(ModelMap map) {//map은 자동으로 뷰페이지로 전달됨
        ArrayList<MeetingroomDto> listAll = service.getAll();

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        for (int i = 0; i < listAll.size(); i++) {
            hash.put("cal_Id", listAll.get(i).getId());
            hash.put("title", listAll.get(i).getTitle());
            hash.put("start", listAll.get(i).getStartDate());
            hash.put("end", listAll.get(i).getEndDate());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        return jsonArr;
    }

    //일정추가
    @PostMapping("/add/{roomId}")
    @ResponseBody
    public Map addEvent(@RequestBody List<Map<String, Object>> param, @PathVariable("roomId") int roomId) throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        String title = (String) param.get(0).get("title");
        String startDateString = (String) param.get(0).get("start");
        String endDateString = (String) param.get(0).get("end");


        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

        MeetingroomDto dto = MeetingroomDto.builder()
                .title(title).startDate(startDate).endDate(endDate).roomId(roomId)
                .build();
        MeetingroomDto s = service.save(dto);

        Map<String, String> map = new HashMap<>();
        map.put("id", s.getId() + "");
        return map;

    }

    //일정삭제
    @GetMapping("/del/{id}")
    public String del(@PathVariable("id") int id) {
        service.del(id);
        return "redirect:/meetingroom";
    }

    //수정
    @PatchMapping("/edit/{id}")
    @ResponseBody
    public Map modifyEvent(@PathVariable("id") int id, @RequestBody List<Map<String, Object>> param) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        MeetingroomDto origin = service.get(id);

        String startDateString = (String) param.get(0).get("start");
        String endDateString = (String) param.get(0).get("end");

        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

        MeetingroomDto dto = MeetingroomDto.builder()
                .id(id).title(origin.getTitle()).startDate(startDate).endDate(endDate)
                .build();
        MeetingroomDto s = service.save(dto);

        return null;
    }

    //방 아이디
    @GetMapping("/list/{roomId}")
    @ResponseBody
    public ArrayList<Map<String, Object>> findbyroomId(ModelMap map, @PathVariable("roomId") int roomId) {
        ArrayList<MeetingroomDto> list = service.getByRoomId(roomId);

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            hash.put("cal_Id", list.get(i).getId());
            hash.put("title", list.get(i).getTitle());
            hash.put("start", list.get(i).getStartDate());
            hash.put("end", list.get(i).getEndDate());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        return jsonArr;
    }
}
