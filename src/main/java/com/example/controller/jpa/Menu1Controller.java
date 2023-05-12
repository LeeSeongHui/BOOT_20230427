package com.example.controller.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Menu1;
import com.example.entity.Menu1ImageProjection;
import com.example.repository.Menu1Repository;
import com.example.repository.Restaurant1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/menu1")
@RequiredArgsConstructor
@Slf4j
public class Menu1Controller {

    final String format = "Menu1Controller => {}";
    final Menu1Repository m1Repository; // 저장소 객체
    final Restaurant1Repository r1Repository; // 저장소 객체

    @Value("${default.image}") String DEFAULTIMAGE;
    final ResourceLoader resourceLoader;

    // 이미지는 DB에서 꺼내서 url형태로 변경시켜야 함.
    // 127.0.0.1:9090/ROOT/boardimage1/image?no=1
    // <img src="/ROOT/menu1/image?no=?"/>
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name="no", defaultValue="0") long no) throws IOException {
        Menu1ImageProjection obj = m1Repository.findByNo(BigInteger.valueOf(no));

        HttpHeaders headers = new HttpHeaders(); // import org.springframework.http.HttpHeaders;
        if(obj != null) { // 이미지가 존재할 경우
            if(obj.getImagesize().longValue() > 0) {
                headers.setContentType(MediaType.parseMediaType(obj.getImagetype()));
                return new ResponseEntity<>(obj.getImagedata(), headers, HttpStatus.OK);
            }
        }
        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream(); // exception 발생됨.
        headers.setContentType(MediaType.IMAGE_PNG); // 첨부한 이미지 파일 타입
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }


    @GetMapping(value = "/insert.food")
    public String insertGET(
        Model model,
        @RequestParam(name = "rno") long rno,
        @RequestParam(name = "rphone") String rphone) {
        try {
            List<Menu1> list = m1Repository.findByRestaurant1_phone(rphone);
            model.addAttribute("rno", rno);
            model.addAttribute("rphone", rphone);
            model.addAttribute("list", list);
            return "/menu1/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insert.food")
    public String insertPOST(
        @ModelAttribute Menu1 obj,
        @RequestParam(name = "tmpFile") MultipartFile file) {
        try {
            // 파일은 수동으로 obj에 추가하기
            obj.setImagedata(file.getInputStream().readAllBytes());
            obj.setImagesize(BigInteger.valueOf(file.getSize()));
            obj.setImagetype(file.getContentType());
            obj.setImagename(file.getOriginalFilename());

            log.info(format, obj.toString());

            m1Repository.save(obj);
            return "redirect:/menu1/insert.food?rno=" + obj.getRestaurant1().getNo() + "&rphone=" + obj.getRestaurant1().getPhone();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/delete.food")
    public String deletePOST(
            @RequestParam(name = "no") long no,
            @RequestParam(name = "rno") long rno,
            @RequestParam(name = "rphone") String rphone){
        try {
            m1Repository.deleteById( BigInteger.valueOf(no) );
            return "redirect:/menu1/insert.food?rno=" + rno + "&rphone=" + rphone;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/update.food")
    public String updateGET(
            Model model,
            @RequestParam(name = "no") long no,
            @RequestParam(name = "rno") long rno,
            @RequestParam(name = "rphone") String rphone){
            try {
                Menu1 obj = m1Repository.findById(BigInteger.valueOf(no)).orElse(null);
                model.addAttribute("obj", obj);
                model.addAttribute("rno", rno);
                model.addAttribute("rphone", rphone);
                return "/menu1/update";
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/home.do";
            }
        }

        @PostMapping(value = "/update.food")
        public String updatePOST(
            @ModelAttribute Menu1 obj,
            @RequestParam(name = "tmpFile") MultipartFile file){
                try {
                    log.info(obj.toString());
                    log.info(file.toString());

                    // 기존데이터 읽기
                    Menu1 obj1 = m1Repository.findById(obj.getNo()).orElse(null);

                    // 저장하면 자동으로 DB에 변경됨.
                    obj1.setName(obj.getName());
                    obj1.setPrice(obj.getPrice());
                    
                    // 기존 데이터를 읽어서 필요한 부분 변경후 다시 저장하기
                    if(file.isEmpty() == false) {
                        obj1.setImagedata(file.getBytes());
                        obj1.setImagename(file.getOriginalFilename());
                        obj1.setImagesize(BigInteger.valueOf(file.getSize()));
                        obj1.setImagetype(file.getContentType());
                    }

                    m1Repository.save(obj1);

                    return "redirect:/menu1/insert.food?rno=" + obj.getRestaurant1().getNo() + "&rphone=" + obj.getRestaurant1().getPhone();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:/home.do";
                }
            }

}
