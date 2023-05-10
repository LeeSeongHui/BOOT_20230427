package com.example.controller.jpa;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.PurchaseView;
import com.example.repository.PurchaseViewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/purchaseview")
@RequiredArgsConstructor
@Slf4j
public class PurchaseViewController {

    final PurchaseViewRepository pvRepository;
    final String format = "PurchaseViewController => {}";
    
    // 127.0.0.1:9090/ROOT/purchaseview/selectlist.pknu
    @GetMapping(value = "/selectlist.pknu")
    public String selectlistGET(Model model){
        try {
            List<PurchaseView> list = pvRepository.findAll();
            model.addAttribute("list", list);
            return "/purchase/selectlist";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
}
