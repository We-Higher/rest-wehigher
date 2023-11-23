package com.example.demo.dataroom;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@Controller
@RequestMapping("/dataroom")
public class DataroomController {

    private final DataroomService service;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    private final MemberService memberService;

    @RequestMapping("/list")
    public void list(Model m, @RequestParam(value = "page", defaultValue = "1") int page, Principal principal, Model map) {
        Page<Dataroom> paging = this.service.getList(page - 1);
        m.addAttribute("paging", paging);
        ArrayList<DataroomDto> list = service.getAll();
        m.addAttribute("list", list);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter1);
        MemberDto mdto = memberService.getMember(principal.getName());
        map.addAttribute("date", date);
        map.addAttribute("name", mdto.getName());
    }

//    @GetMapping("/add")
//    public void addForm(Principal principal, Model map) {
//        System.out.println("principal = " + principal);
//        System.out.println("principal.getName() = " + principal.getName());
//        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String date = LocalDateTime.now().format(formatter1);
//        MemberDto mdto = memberService.getMember(principal.getName());
//        map.addAttribute("date", date);
//        map.addAttribute("name", mdto.getName());
//    }

    @PostMapping("/add")
    public String add(Principal principal, DataroomDto dto) {
        MultipartFile f = dto.getF();
        String fname = f.getOriginalFilename();
        MemberDto mdto = memberService.getMember(principal.getName());
        dto.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getCstatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain()));
        File newFile = new File(path + fname);
        try {
            f.transferTo(newFile);
            dto.setFname(fname);
            //dto.setMember(Member.builder().id(mdto.getId()).build());
            service.save(dto);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/dataroom/list";
    }

    @GetMapping("/detail")
    public void detailForm(int num, Model m) {
        DataroomDto dto = service.getDataroom(num);
        m.addAttribute("dto", dto);
    }

    @GetMapping("/edit")
    public void editForm(int num, Model m) {
        DataroomDto dto = service.getDataroom(num);
        m.addAttribute("dto", dto);
    }


    @PostMapping(value = "/edit", consumes = "multipart/form-data")
    public String edit(@RequestParam("f") MultipartFile file, Principal principal, DataroomDto dto) {
        System.out.println(dto.getF());
        MultipartFile f = dto.getF();
        DataroomDto origin = service.getDataroom(dto.getNum());
        MemberDto mdto = memberService.getMember(principal.getName());
        dto.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getCstatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain()));

        if (f != null && f.getSize() > 0) {
            String fname = f.getOriginalFilename();
            File newFile = new File(path + fname);
            try {
                f.transferTo(newFile);
                dto.setFname(fname);
                //dto.setMember(Member.builder().id(mdto.getId()).build());
                File delFile = new File(path + origin.getFname());
                delFile.delete();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            dto.setFname(origin.getFname());
        }
        dto.setWdate(origin.getWdate());
        service.save(dto);
        return "redirect:/dataroom/list";
    }

    @GetMapping("/del")
    public String del(int num) {
        DataroomDto origin = service.getDataroom(num);
        if (origin != null) {
            File delFile = new File(path + origin.getFname());
            delFile.delete();
            service.delDataroom(num);
        }
        return "redirect:/dataroom/list";
    }

    @PostMapping("/search")
    public String searchReferenceList(ReferenceSearch referenceSearch, Pageable pageable, Model model) {
        Page<DataroomDto> listReference = service.getSearchReference(referenceSearch, pageable);
        model.addAttribute("paging", listReference);
        System.out.println("listReference = " + listReference);
        return "dataroom/list";
    }


    @RequestMapping("/down")
    public ResponseEntity<byte[]> down(String fname, int num) {
        File f = new File(path + fname);

        HttpHeaders header = new HttpHeaders();

        ResponseEntity<byte[]> result = null;
        try {
            header.add("Content-Type", Files.probeContentType(f.toPath()));
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + URLEncoder.encode(fname, "UTF-8") + "\"");
            result = new ResponseEntity<byte[]>(
                    FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK
            );
            service.editCnt(num);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}