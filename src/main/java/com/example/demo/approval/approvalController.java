package com.example.demo.approval;

import com.example.demo.approval.expense.ExpenseDto;
import com.example.demo.approval.expense.ExpenseService;
import com.example.demo.approval.report.ReportDto;
import com.example.demo.approval.report.ReportService;
import com.example.demo.approval.vacation.VacationDto;
import com.example.demo.approval.vacation.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/approval")
public class approvalController {
    @Autowired
    private ExpenseService eservice;
    @Autowired
    private ReportService rservice;
    @Autowired
    private VacationService vservice;

    @GetMapping("/draft")
    public void list(Model map) {
        ArrayList<ExpenseDto> elist = eservice.getAll();
        ArrayList<ReportDto> rlist = rservice.getAll();
        ArrayList<VacationDto> vlist = vservice.getAll();
        map.addAttribute("elist",elist);
        map.addAttribute("rlist",rlist);
        map.addAttribute("vlist",vlist);
    }

    @GetMapping("/expense/edit")
    public String editExpense(ExpenseDto edto,Model map){
        edto = eservice.getById(edto.getExpenseNum());
        map.addAttribute("edto",edto);
        return "approval/edit/editExpense";
    }

    @GetMapping("/report/edit")
    public String editReport(ReportDto rdto,Model map){
        rdto = rservice.getById(rdto.getReportNum());
        System.out.println("rdto = " + rdto);
        map.addAttribute("rdto",rdto);
        return "approval/edit/editReport";
    }

    @GetMapping("/vacation/edit")
    public String editVacation(VacationDto vdto,Model map){
        vdto = vservice.getById(vdto.getVacationNum());
        System.out.println("vdto = " + vdto);
        map.addAttribute("vdto",vdto);
        return "approval/edit/editVacation";
    }
}
