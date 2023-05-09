package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Board;
import com.example.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor // 클래스에서만 가능함, 인터페이스에서는 안됨.
public class BoardController {

    @Autowired BoardService bService;
    
    @GetMapping(value="/insert.do")
    public String insertGET() {
        return "/board/insert";
    }

    @GetMapping(value = "/select.do")
    public String selectGET(Model model) {
        List<Board> list = bService.selectBoardList();
        System.out.println(list.toString());
        model.addAttribute("list", list);

        return "/board/select";
    }


    @GetMapping(value = {"/selectone.do"})
    public String selectoneGET(Model model, @RequestParam(name="no", defaultValue = "0", required = false) long no){

        if(no == 0) {
            return "redirect:select.do";
        }

        Board obj = bService.selectBoardOne(no);
        System.out.println(obj.toString());
        model.addAttribute("obj", obj); // key를 생략할 경우, 변수명 obj가 키값임.

        return "/board/selectone";
    }

    @GetMapping(value="/update.do")
    public String updateGET(Model model, @RequestParam(name="no", defaultValue = "0", required = false) long no) {
        if(no == 0) {
            return "redirect:select.do";
        }
        Board obj = bService.selectBoardOne(no);
        model.addAttribute("obj",obj);

        return "/board/update";
    }

    // 하나 받을때 사용 => @RequestParam(name = "title") String title
    // 여러개 받을때 사용 => @ModelAttribute Board board (조건 : dto의 변수이름과 form태그 name이 같아야 사용 가능) 
    @PostMapping(value = "/insert.do")
    public String insertPost(@ModelAttribute Board board ) {
       // 입력받은 값 출력 
        System.out.println(board.toString());
        
        // DB에 추가
        int ret = bService.insertBoardOne(board);
        if(ret == 1) { // 성공시
            return "redirect:select.do";
        }
        // 실패시
        return "redirect:insert.do";    
    }

    @PostMapping(value = "/update.do")
    public String updatePost(@ModelAttribute Board board){
        int ret = bService.updateBoardOne(board);
        if(ret == 1) {
            return "redirect:selectone.do?no" + board.getNo();
        }
        return "redirect:update.do?no" + board.getNo();
    }

    @PostMapping(value = "/delete.do")
    public String deletePost(@RequestParam(name="no", defaultValue = "0", required = false) long no){
        System.out.println(no);

        if(no == 0) {
            return "redirect:select.do";
        }
        int ret = bService.deleteBoardOne(no);
        if(ret == 1){
            return "redirect:select.do";
        }
        return "redirect:boardone.do?no" + no;
    }
}
