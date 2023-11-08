package com.example.demo.board;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService service;

    //글목록
    @GetMapping("/list")
    public void list(ModelMap map) {
        ArrayList<BoardDto> list = service.getAll();
        map.addAttribute("list", list);
    }

    @GetMapping("/add")
    public String addForm() {
        return "/board/add";
    }

    //글작성
    @PostMapping("/add")
    public String add(BoardDto b, HttpSession session) {
        MemberDto mdto = (MemberDto) session.getAttribute("username");
        b.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getCompanyRank(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain()));
        service.saveBoard(b);
        return "redirect:/board/list";
    }

    //글번호로검색(수정폼)
    @GetMapping("/edit")
    public void editForm(int num, ModelMap map) {
        BoardDto b = service.getBoard(num);
        map.addAttribute("b", b);
    }

    //수정완료
    @PostMapping("/edit")
    public String edit(BoardDto b) {
        BoardDto b2 = service.getBoard(b.getNum());
        b2.setTitle(b.getTitle());
        b2.setContent(b.getContent());
        service.saveBoard(b2);
        return "redirect:/board/list";
    }

    //삭제
    @RequestMapping("/del")
    public String del(int num) {
        service.delBoard(num);
        return "redirect:/board/list";
    }
	
	/*
	@RequestMapping("/getbyusername")
	public String getbywriter(String writer, Model map) {
		ArrayList<BoardDto> list = service.getByUserName(writer);
		map.addAttribute("list", list);
		return "board/list";
	}
	
	//제목으로 검색
	@RequestMapping("/getbytitle")
	public String getbytitle(String title, Model map) {
		ArrayList<BoardDto> list = service.getByTitle(title);
		map.addAttribute("list", list);
		return "board/list";
	}*/
}







