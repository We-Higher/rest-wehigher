package com.example.demo.chat;

import com.example.demo.employee.EmployeeDto;
import com.example.demo.employee.EmployeeService;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final com.example.demo.chat.ChatRoomRepository chatRoomRepository;
    private final EmployeeService eService;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Principal principal, Model map) {
        MemberDto memberDto = memberService.getMember(principal.getName());
        ArrayList<EmployeeDto> list = eService.getAll();
        map.addAttribute("list", list);
        map.addAttribute("loginId", memberDto.getId());

        return "chat/room";
    }

    // 참여한 채팅방 반환
    @GetMapping("/rooms/{participantId}")
    @ResponseBody
    public List<ChatRoom> getByParticipantId(@PathVariable("participantId") long participantId) {
        System.out.println("participantId = " + participantId);
        List<ChatRoom> result = chatRoomService.getByParticipantId(participantId);
        result.forEach(room -> System.out.println("room = " + room));
        return result;
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
    public ChatRoom createRoom(Principal principal, @RequestParam("name") String roomName) {
        System.out.println(111);
        Set<Member> participants = new HashSet<>();
        MemberDto memberDto = memberService.getMember(principal.getName());
        Member member = new Member().toEntity(memberDto);
        participants.add(member);

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .participants(participants)
                .build();

        return chatRoomService.create(chatRoom);
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