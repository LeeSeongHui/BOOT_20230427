package com.example.controller.jpa;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Search;
import com.example.entity.Restaurant1;
import com.example.entity.Restaurant1ID;
import com.example.repository.Restaurant1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/restaurant1")
@RequiredArgsConstructor
@Slf4j
public class Restaurant1Controller {

    final String format = "Restuarant1Controller => {}";
    final Restaurant1Repository r1Repository; // 저장소 객체

    // 127.0.0.1:9090/ROOT/restaurant1/insert.food
    @GetMapping(value = "/insert.food")
    public String insertGET() {
        try {
            return "/restaurant1/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insert.food")
    public String insertPOST(@ModelAttribute Restaurant1 restaurant1) {
        try {
            r1Repository.save(restaurant1);
            return "redirect:/restaurant1/selectlist.food";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 식당 전체 목록 표시(페이지 네이션, 연락처, 이름별, 종류별, 주소별 검색)
    @GetMapping(value = "/selectlist.food")
    public String selectlistGET(
        Model model,
        // dto > Search
        @ModelAttribute Search obj ){
        try {
            // 페이지 네이션 => 페이지번호가 0부터
            PageRequest pageRequest = PageRequest.of(obj.getPage()-1, 10);

            List<Restaurant1> list = r1Repository.findByPhoneContainingOrderByNoDesc(obj.getText(), pageRequest);
            long total = r1Repository.countByPhoneContaining(obj.getText());

            if(obj.getType().equals("name")) {
                list = r1Repository.findByNameContainingOrderByNoDesc(obj.getText(), pageRequest);
                total = r1Repository.countByNameContaining(obj.getText());

            }
            else if(obj.getType().equals("address")) {
                list = r1Repository.findByAddressContainingOrderByNoDesc(obj.getText(), pageRequest);
                total = r1Repository.countByAddressContaining(obj.getText());
                
            }
            else if(obj.getType().equals("type")) {
                list = r1Repository.findByTypeOrderByNoDesc(obj.getText(), pageRequest);
                total = r1Repository.countByType(obj.getText());
                
            }
            model.addAttribute("list", list);
            model.addAttribute("pages", (total-1)/10 + 1);
            model.addAttribute("search", obj);
            return "/restaurant1/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home/do";
        }
    }

    @PostMapping(value = "/delete.food")
    public String deletePOST(@ModelAttribute Restaurant1ID obj){
        try {
            log.info(format, obj.toString());
            r1Repository.deleteById(obj);
            return "redirect:/restaurant1/selectlist.food";

            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    
}
