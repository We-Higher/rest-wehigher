package com.example.demo.approval;

import com.example.demo.approval.expense.ExpenseDto;
import com.example.demo.approval.expense.ExpenseService;
import com.example.demo.approval.report.ReportDto;
import com.example.demo.approval.report.ReportService;
import com.example.demo.approval.vacation.VacationDto;
import com.example.demo.approval.vacation.VacationService;
import com.example.demo.employee.EmployeeDto;
import com.example.demo.employee.EmployeeService;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/approval")
public class approvalController {
    @Autowired
    private ExpenseService eservice;
    @Autowired
    private ReportService rservice;
    @Autowired
    private VacationService vservice;
    @Autowired
    private EmployeeService empservice;
    @Autowired
    private MemberService mservice;

    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }

    @GetMapping("/draft")
    public void list(Model map) {
        ArrayList<ExpenseDto> elist = eservice.getAll();
        ArrayList<ReportDto> rlist = rservice.getAll();
        ArrayList<VacationDto> vlist = vservice.getAll();
        map.addAttribute("elist", elist);
        map.addAttribute("rlist", rlist);
        map.addAttribute("vlist", vlist);
    }

    @GetMapping("/mydraft")
    public void listByMember(Model map) {
        ArrayList<ExpenseDto> elist = eservice.getAll();
        ArrayList<ReportDto> rlist = rservice.getAll();
        ArrayList<VacationDto> vlist = vservice.getAll();
        map.addAttribute("elist", elist);
        map.addAttribute("rlist", rlist);
        map.addAttribute("vlist", vlist);
    }

    @GetMapping("/process")
    public void listById(Model map, Principal principal) {
        ArrayList<ExpenseDto> elist = eservice.getAll();
        ArrayList<ReportDto> rlist = rservice.getAll();
        ArrayList<VacationDto> vlist = vservice.getAll();
        MemberDto mdto = mservice.getMember(principal.getName());
        map.addAttribute("m",mdto);
        map.addAttribute("elist", elist);
        map.addAttribute("rlist", rlist);
        map.addAttribute("vlist", vlist);
    }

    @GetMapping("/expense/edit")
    public String editExpense(ExpenseDto edto, Model map) {
        edto = eservice.getById(edto.getExpenseNum());
        map.addAttribute("edto", edto);
        return "approval/edit/editExpense";
    }

    @GetMapping("/report/edit")
    public String editReport(ReportDto rdto, Model map) {
        rdto = rservice.getById(rdto.getReportNum());
        System.out.println("rdto = " + rdto);
        map.addAttribute("rdto", rdto);
        return "approval/edit/editReport";
    }

    @GetMapping("/vacation/edit")
    public String editVacation(VacationDto vdto, Model map) {
        vdto = vservice.getById(vdto.getVacationNum());
        System.out.println("vdto = " + vdto);
        map.addAttribute("vdto", vdto);
        return "approval/edit/editVacation";
    }

    //1차 결재자 선택
    @GetMapping("/approvalList1")
    public ModelAndView approvalList1() {
        ModelAndView mav = new ModelAndView("approval/approvalList1");
        ArrayList<EmployeeDto> list = empservice.getAll();
        mav.addObject("list", list);
        return mav;
    }

    //2차 결재자 선택
    @GetMapping("/approvalList2")
    public ModelAndView approvalList2() {
        ModelAndView mav = new ModelAndView("approval/approvalList2");
        ArrayList<EmployeeDto> list = empservice.getAll();
        mav.addObject("list", list);
        return mav;
    }

    //지출결의서 결재
    @PostMapping("/approval/expense/approve")
    public String approveExpense() {
        return "redirect:/approval/process";
    }

}
