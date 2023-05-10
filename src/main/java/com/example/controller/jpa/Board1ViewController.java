package com.example.controller.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Board1View;
import com.example.repository.Board1ViewRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Controller
@RequestMapping(value = "/board1view")
@RequiredArgsConstructor
@Slf4j
public class Board1ViewController {

    final Board1ViewRepository b1vRepository;
    final String format = "board1ViewController => {}";
    
    // num이 없으면 -> 전체
    // num=1 -> and
    // num=2 -> or
    // num=3 -> 글번호 in
    // num=4 -> 제목 in
    @GetMapping(value = "/selectlist.pknu")
    public String selectlistGET(
        Model model,
        @RequestParam(name = "num", defaultValue = "0") int num,
        @RequestParam(name = "no", defaultValue = "") Long no,
        @RequestParam(name = "no3", defaultValue = "") String no3,
        @RequestParam(name = "title", defaultValue = "") String title){
        try {
            List<Board1View> list = new ArrayList<>();
            if(num == 0){
                list = b1vRepository.findAllByOrderByNoDesc();
            }

            else if(num == 1){
                list = b1vRepository.findByNoAndTitleIgnoreCaseOrderByNoDesc(no, title);
            }

            else if(num == 2){
                list = b1vRepository.findByNoOrTitleIgnoreCaseOrderByNoDesc(no, title);
            }

            else if(num == 3){
                String[] arr = no3.split(",");
                long[] arr1 = new long[arr.length];
                for(int i = 0; i<arr.length; i++){
                    arr1[i] = Long.parseLong(arr[i]);
                }
                list = b1vRepository.findByNoIn(arr1);
            }

            else if(num == 4){
                String[] arr = title.split(",");
                 list = b1vRepository.findByTitleIn(arr);
            }
            
           model.addAttribute("list", list);
            return "/board1view/selectlist";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
}
