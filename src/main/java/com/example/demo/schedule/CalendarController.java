//package com.example.demo.schedule;
//
//import lombok.RequiredArgsConstructor;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.logging.Logger;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/full-calendar")
//public class CalendarController {
//
//    private static final Logger log = (Logger) LoggerFactory.getLogger(CalendarController.class);
//
//    private final ScheduleService scheduleService;
//
//    @GetMapping("/calendar-admin")
//    @ResponseBody
//    public List<Map<String, Object>> monthPlan() {
//        List<Schedule> listAll = scheduleService.findAll();
//
//        JSONObject jsonObj = new JSONObject();
//        JSONArray jsonArr = new JSONArray();
//
//        HashMap<String, Object> hash = new HashMap<>();
//
//        for (int i = 0; i < listAll.size(); i++) {
//            hash.put("title", listAll.get(i).getId());
//            hash.put("start", listAll.get(i).getScheduleDate());
////            hash.put("time", listAll.get(i).getScheduleTime());
//
//            jsonObj = new JSONObject(hash);
//            jsonArr.add(jsonObj);
//        }
//        log.info("jsonArrCheck: {}", jsonArr);
//        return jsonArr;
//    }
//}
