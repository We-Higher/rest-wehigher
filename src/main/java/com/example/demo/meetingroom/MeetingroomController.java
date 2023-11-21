package com.example.demo.meetingroom;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@Controller
@RequestMapping("/meetingroom")
public class MeetingroomController {
    private final MeetingroomService service;
    private final MemberService memberService;

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
            hash.put("title", listAll.get(i).getRoomId()+"회의실-"+listAll.get(i).getTitle());
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        MeetingroomDto dto = MeetingroomDto.builder()
                .member(member).title(title).startDate(startDate).endDate(endDate).roomId(roomId)
                .build();
        MeetingroomDto s = service.save(dto);

        Map<String, String> map = new HashMap<>();
        map.put("id", s.getId() + "");
        return map;

    }

    //일정삭제
    @GetMapping("/del/{id}")
    @ResponseBody
    public ModelMap del(@PathVariable("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        MeetingroomDto origin = service.get(id);

        ModelMap map = new ModelMap();
        boolean flag = true;

        if(member.getId().equals(origin.getMember().getId())){
            service.del(id);
        }
        else if(member.getIsMaster()==1){
            service.del(id);
        }
        else{
            String msg = "본인 예약만 삭제할 수 있습니다.";
            map.put("msg",msg);
            flag = false;
        }
        map.put("flag",flag);

        return map;
    }

    //수정
    @PatchMapping("/edit/{id}")
    @ResponseBody
    public ModelMap modifyEvent(@PathVariable("id") int id, @RequestBody List<Map<String, Object>> param) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        MeetingroomDto origin = service.get(id);

        String startDateString = (String) param.get(0).get("start");
        String endDateString = (String) param.get(0).get("end");

        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        MeetingroomDto dto = MeetingroomDto.builder()
                .id(id).member(member).title(origin.getTitle()).startDate(startDate).endDate(endDate)
                .build();

        ModelMap map = new ModelMap();
        boolean flag = true;

        if(member.getId().equals(origin.getMember().getId())){
            MeetingroomDto s = service.save(dto);
        }
        else if(member.getIsMaster()==1){
            MeetingroomDto s = service.save(dto);
        }
        else{
            String msg = "본인 예약만 수정할 수 있습니다.";
            map.put("msg",msg);
            flag = false;
        }
        map.put("flag",flag);

        return map;
    }

    //방 아이디
    @GetMapping("/list/{roomId}")
    @ResponseBody
    public ArrayList<Map<String, Object>> findbyroomId(ModelMap map, @PathVariable("roomId") int roomId) {
        ArrayList<MeetingroomDto> list=null;
        if(roomId == 0){
            list = service.getAll();
        } else{
            list = service.getByRoomId(roomId);
        }

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            hash.put("cal_Id", list.get(i).getId());
            hash.put("title", list.get(i).getRoomId()+"회의실-"+list.get(i).getTitle());
            hash.put("start", list.get(i).getStartDate());
            hash.put("end", list.get(i).getEndDate());
            hash.put("roomId", list.get(i).getRoomId());
            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        return jsonArr;
    }

    //화상회의 접속
    @GetMapping("/meeting")
    public String room() {
        return "/meetingroom/meeting";
    }
}