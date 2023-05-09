package com.example.controller;




import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping(value = "/member")
@Slf4j // lombok지원 디버깅
@RequiredArgsConstructor
public class MemberController {

    final MemberMapper mMapper; // 매퍼객체 생성
    final HttpSession httpSession; // 세션객체 생성


    // 회원가입
    @GetMapping(value="/join.do")
    public String joinGET() {
        log.info("member={}", "joinGET");
        return "/member/join";
    }
    
    @PostMapping(value="/join.do")
    public String joinPOST( @ModelAttribute Member obj) {
        log.info("join.do POST => {}", obj.toString());
        // 매퍼호출
        int ret = mMapper.insertMemberOne(obj);
        if(ret==1) {
            return "redirect:/home.do"; // 성공시 홈으로
        }
        return "redirect:/join.do"; // 실패시 회원가입으로
    }



    // 로그인
    @GetMapping(value="/login.do")
    public String loginGET() {
        return "/member/login";
        
    }

    @PostMapping(value="/login.do")
    public String loginPOST(@ModelAttribute Member member) {
        log.info("login.do => {}", member.toString()); // view에서 잘 전송되었는지 출력

        Member obj =  mMapper.selectMemberOne(member); // 로그인한 사용자의 정보 반화
        if(obj != null) {
            log.info("login1.do => {}", obj.toString());

            // 세션에 2개의 정보 '아이디'와 '이름' 추가하기 (기본시간 30분)
            // 다른페이지에서 세션의 아이디가 존재하는지 확인후 로그인 여부 판단
            httpSession.setAttribute("USERID", obj.getId());
            httpSession.setAttribute("USERNAME", obj.getName());
            httpSession.setAttribute("ROLE", obj.getRole());
            return "redirect:/home.do"; // 로그인 성공 시
        }
        return "redirect:login.do"; // 로그인 실패 시
    }



    // 로그아웃
    // GET, POST가 같은 동작을 함.
    @RequestMapping(value = "/logout.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutPOST() {
        httpSession.invalidate(); // 세션의 정보를 다 지움
        return "redirect:/home.do";
    }

}
