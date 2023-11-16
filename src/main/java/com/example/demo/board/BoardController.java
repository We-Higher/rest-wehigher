package com.example.demo.board;

import com.example.demo.employee.EmployeeDto;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;

@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/board")
public class BoardController {
	
    @Autowired
    private BoardService bservice;
    @Autowired
    private MemberService mservice;

    //글목록
    @GetMapping("/list")
    public void list(ModelMap map) {
        ArrayList<BoardDto> list = bservice.getAll();
        map.addAttribute("list", list);
    }
    
    //공지사항 목록
    @GetMapping("/notify")
    public void Notifylist(ModelMap map) {
        ArrayList<NotifyDto> list = bservice.getAllnotify();
        map.addAttribute("list", list);
    }
    
	//옵션으로 검색
	@PostMapping("/list")
	public String getbyOption(String type, Model map, String option) {
		System.out.println(type);
		System.out.println(option);
		ArrayList<BoardDto> list = bservice.getByOption(type, option);
		map.addAttribute("list", list);
		return "/board/list";
	}
	
	//옵션으로 검색
	/*@PostMapping("/notifylist")
	public String getbyOption2(String type, Model map, String option) {
		System.out.println(type);
		System.out.println(option);
		ArrayList<NotifyDto> list = bservice.getByOption(type, option);
		map.addAttribute("list", list);
		return "/board/list";
	}*/

	//작성폼
    @GetMapping("/add")
    public String addForm() {
        return "/board/add";
    }
    
	//공지사항 작성폼
    @GetMapping("/notifyadd")
    public String notifyaddForm() {
        return "/board/notifyadd";
    }

    //게시글 작성
    @PostMapping("/add")
    public String add(BoardDto b, Principal principal) {
    	int check = 0;
        MemberDto mdto = mservice.getMember(principal.getName());
        b.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain()));
        bservice.saveBoard(b, check);
        return "redirect:/board/list";
    }
    
    //공지사항 작성
    @PostMapping("/notifyadd")
    public String notifyadd(NotifyDto b, Principal principal) {
    	int check = 0;
        MemberDto mdto = mservice.getMember(principal.getName());
        b.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain()));
        bservice.saveNotify(b, check);
        return "redirect:/board/notify";
    }

    //게시글 수정폼
    @GetMapping("/edit")
    public void editForm(int num, ModelMap map) {
        BoardDto b = bservice.getBoard(num);
        map.addAttribute("b", b);
    }
    
    //공지사항 수정폼
    @GetMapping("/notifyedit")
    public void notifyeditForm(int num, ModelMap map) {
    	NotifyDto b = bservice.getNotify(num);
        map.addAttribute("b", b);
    }

    //게시글 수정완료
    @PostMapping("/edit")
    public String edit(BoardDto b) {
    	int check = 1;
        BoardDto b2 = bservice.getBoard(b.getNum());
        b2.setTitle(b.getTitle());
        b2.setContent(b.getContent());
        bservice.saveBoard(b2, check);
        return "redirect:/board/list";
    }
    
    //공지사항 수정완료
    @PostMapping("/notifyedit")
    public String notifyedit(NotifyDto b) {
    	int check = 1;
    	NotifyDto b2 = bservice.getNotify(b.getNum());
        b2.setTitle(b.getTitle());
        b2.setContent(b.getContent());
        bservice.saveNotify(b2, check);
        return "redirect:/board/notify";
    }

    //게시글 삭제
    @RequestMapping("/del")
    public String del(int num) {
        bservice.delBoard(num);
        return "redirect:/board/list";
    }
    
    //공지사항 삭제
    @RequestMapping("/notifydel")
    public String notifydel(int num) {
        bservice.delNotify(num);
        return "redirect:/board/notify";
    }
}







