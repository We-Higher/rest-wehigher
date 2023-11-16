package com.example.demo.chat;

import com.example.demo.employee.EmployeeDto;
import com.example.demo.employee.EmployeeService;
import com.example.demo.member.Member;
import com.example.demo.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final com.example.demo.chat.ChatRoomRepository chatRoomRepository;
    private final EmployeeService eService;
    private final MemberService memberService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Principal principal, Model map) {
        Member member = memberService.getMemberBy(principal.getName())
        ArrayList<EmployeeDto> list = eService.getAll();
        map.addAttribute("list", list);

        return "/chat/room";
    }
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam int memberId) {
        return chatRoomRepository.createChatRoom(memberId);
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}