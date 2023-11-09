package com.example.demo.dataroom;

import com.example.demo.board.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;


@RequiredArgsConstructor
@Controller
@RequestMapping("/dataroom")
public class DataroomController {
    private final DataroomService service;

    @Value("C:/data/")
    private String path;

    @RequestMapping("/list")
    public void list(Model m) {
        ArrayList<DataroomDto> list = service.getAll();
        m.addAttribute("list", list);
    }

    @GetMapping("/add")
    public void addForm() {

    }

    @PostMapping("/add")
    public String add(DataroomDto dto) {
        MultipartFile f = dto.getF();
        String fname = f.getOriginalFilename();
        File newFile = new File(path + fname);
        try {
            System.out.println("유저아이디:" + dto.getWriter());
            f.transferTo(newFile);
            dto.setFname(fname);
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

    @GetMapping("/edit")
    public void editForm(int num, Model m) {
        DataroomDto dto = service.getDataroom(num);
        m.addAttribute("dto", dto);
    }

    @PostMapping("/edit")
    public String edit(DataroomDto dto) {
        MultipartFile f = dto.getF();
        DataroomDto origin = service.getDataroom(dto.getNum());
        if (f != null && f.getSize() > 0) {
            String fname = f.getOriginalFilename();
            File newFile = new File(path + fname);
            try {
                f.transferTo(newFile);
                dto.setFname(fname);
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
        File delFile = new File(path + origin.getFname());
        delFile.delete();
        service.delDataroom(num);
        return "redirect:/dataroom/list";
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}
