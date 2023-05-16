package com.example.controller.jpa;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.library.Student2;
import com.example.repository.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/student2")
public class Student2Controller {
    
    final Student2Repository s2Repository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @GetMapping(value = "/insert.do")
    public String insertGET() {
        try {
            return "/student2/insert";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Student2 obj) {
        try {
            // 암호는 bcpe를 이용하여 암호화하기
            obj.setPassword(bcpe.encode(obj.getPassword()));
            log.info("{}", obj.toString());
            s2Repository.save(obj);
            return "redirect:/student2/insert.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 127.0.0.1:9090/ROOT/student2/login.do
    @GetMapping(value = "/login.do")
    public String loginGET() {
        try {
            return "/student2/login";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/home.do")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {
        try {
            model.addAttribute("user", user);
            return "/student2/home";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    
}
