package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.library.Student2;
import com.example.entity.library.Student2Projection;
import com.example.repository.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/student2")
@RequiredArgsConstructor
@Slf4j
public class RestStudent2Controller {
    final Student2Repository s2Repository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


    @PostMapping(value = "insert.json")
    public Map<String, Object> insertPOST(@RequestBody Student2 obj) { 
        Map<String, Object> retMap = new HashMap<>();
        try {
            obj.setPassword(bcpe.encode(obj.getPassword()));
            log.info("{}", obj.toString());
            s2Repository.save(obj);
            retMap.put("status", 200);
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    // ROOT/api/student2/emailcheck.json?email=이메일
    // 이메일 중복확인용
    @GetMapping(value = "/emailcheck.json")
    public Map<String, Object> emailcheckGET(@RequestParam(name = "email") String email) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            retMap.put("status", 200);
            retMap.put("chk", s2Repository.countByEmail(email));
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    // 이메일이 전달되면 회원의 이름, 연락처가 반환되는
    @GetMapping(value = "/selectone.json")
    public Map<String, Object> selectoneGET(@RequestParam(name = "email") String email) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            Student2Projection obj2 = s2Repository.findByEmail(email);
            retMap.put("status", 200);
            retMap.put("obj", obj2);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
    
}
