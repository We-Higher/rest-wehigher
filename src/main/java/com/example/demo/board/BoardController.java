package com.example.demo.board;

import com.example.demo.commute.Commute;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@PreAuthorize("hasRole(\"ADMIN\")")
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService bservice;
    @Autowired
    private MemberService mservice;

    //글목록
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public void list(Model map, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Board> paging = this.bservice.getBoardList(page - 1);
        map.addAttribute("paging", paging);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter1);
        map.addAttribute("date", date);
        ArrayList<BoardDto> list = bservice.getAll();
        map.addAttribute("list", list);
    }

    //공지사항 목록
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/notify")
    public void Notifylist(Model map, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Notify> paging = this.bservice.getNotifyList(page - 1);
        map.addAttribute("paging", paging);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter1);
        map.addAttribute("date", date);
        ArrayList<NotifyDto> list = bservice.getAllnotify();
        map.addAttribute("list", list);
    }

    //옵션으로 검색
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/list")
    public String getbyOption(String type, Model map, String option, Pageable pageable) {
        System.out.println(type);
        System.out.println(option);
        Page<BoardDto> list = bservice.getByOption(type, option, pageable);
        map.addAttribute("list", list);
        return "/board/list";
    }

    //공지사항 옵션으로 검색
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/notifylist")
    public String getbyOption2(String type, Model map, String option, Pageable pageable) {
        System.out.println(type);
        System.out.println(option);
        Page<NotifyDto> list = bservice.getByOption2(type, option, pageable);
        map.addAttribute("list", list);
        return "/board/notify";
    }

    //작성폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add")
    public String addForm(Principal principal, ModelMap map) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter1);
        MemberDto mdto = mservice.getMember(principal.getName());
        map.addAttribute("date", date);
        map.addAttribute("name", mdto.getName());
        return "/board/add";
    }

    //공지사항 작성폼
    @GetMapping("/notifyadd")
    public String notifyaddForm(ModelMap map) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter1);
        map.addAttribute("date", date);
        return "/board/notifyadd";
    }

    //게시글 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public String add(BoardDto b, Principal principal) {
        int check = 0;
        MemberDto mdto = mservice.getMember(principal.getName());
        b.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getCstatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain(),mdto.getMonthMember()));
        bservice.saveBoard(b, check);
        return "redirect:/board/list";
    }

    //공지사항 작성
    @PostMapping("/notifyadd")
    public String notifyadd(NotifyDto b, Principal principal) {
        int check = 0;
        MemberDto mdto = mservice.getMember(principal.getName());
        b.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getCstatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain(),mdto.getMonthMember()));
        bservice.saveNotify(b, check);
        return "redirect:/board/notify";
    }

    //게시글 수정폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public void editForm(int num, ModelMap map) {
        BoardDto b = bservice.getBoard(num);
        bservice.editCnt(num);
        map.addAttribute("b", b);
    }

    //공지사항 수정폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/notifyedit")
    public void notifyeditForm(int num, ModelMap map) {
        NotifyDto b = bservice.getNotify(num);
        bservice.editCnt2(num);
        map.addAttribute("b", b);
    }

    //게시글 수정완료
    @PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("isAuthenticated()")
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







