package com.example.demo.meetingroom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeetingroomController {
    @GetMapping("/meeting/room")
    public String room() {
        return "/meetingroom/room";
    }
}
