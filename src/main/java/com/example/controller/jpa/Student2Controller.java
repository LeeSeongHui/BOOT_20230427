package com.example.controller.jpa;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    @GetMapping(value = "/mylogin.do")
    public String myloginGET(){
        return "/student2/mylogin";
    }


    @PostMapping(value = "/myloginaction.do")
    public String myloginAction(@ModelAttribute Student2 obj){
        try {
            log.info("{}", obj.toString());

            //DetailsService를 사용하지 않고 세션에 저장하기
            // 1. 기존 자료읽기
            Student2 obj1 = s2Repository.findById(obj.getEmail()).orElse(null);

            // 2. 전달한 아이디와 읽은 데이터 암호 비교
            if(bcpe.matches(obj.getPassword(), obj1.getPassword())) {
               
                // 3. 세션에 저장할 객체 생성하기 (저장할 객체, null, 권한)
                String[] strRole = {"ROLE_STUDENT2"};
                Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);

                User user = new User(obj1.getEmail(), obj1.getPassword(), role);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, role);

                // 수동으로 세션에 저장(로그인)
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);

                /* 
                // 수동으로 세션에 저장(로그아웃)
                Authentication authenticationToken1 = SecurityContextHolder.getContext().getAuthentication();
                if(authenticationToken1 != null) {
                    new SecurityContextLogoutHandler().logout(request, response, authenticationToken1);
                }
                */
            }
            return "redirect:/student2/home.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/student2/home.do";
        }
    }

    
}
