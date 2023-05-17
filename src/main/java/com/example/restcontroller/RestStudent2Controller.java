package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    
    final JwtUtil1 jwtUtil1; // 컴포넌트 객체 생성
    final Student2Repository s2Repository; // 저장소 객체 생성
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); // 암호화 객체

    //127.0.0.1:9090/ROOT/api/student2/login.json
    @PostMapping(value="/login.json")
    public Map<String, Object> loginPOST(@RequestBody Student2 student2) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 1. 이메일, 암호 전송 확인
            log.info("{}", student2.toString());

            // 2. 이메일을 이용해서 정보를 가져옴.
            Student2 retStudent2 = s2Repository.findById(student2.getEmail()).orElse(null);

            // 3. 실패시 전송할 데이터
            retMap.put( "status", 0 );

            // 4. 암호가 일치하는지 확인 => 전송된 hash되지 않은 암호와 DB에 해시된 암호 일치 확인
            if(  bcpe.matches( student2.getPassword(), retStudent2.getPassword()) ) {
                retMap.put( "status", 200 );
                retMap.put( "token", jwtUtil1.createJwt(retStudent2.getEmail(), retStudent2.getName() ) );
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            retMap.put( "status", -1 );
            retMap.put( "error", e.getMessage() );
        }
        return retMap;
    }

    //회원탈퇴, 비밀번호변경, 회원정보수정... 로그인이 되어야 되는 모든것.
    //회원정보수정 => 토큰을 주세요. 검증해서 성공하면 정보수정을 진행
    @PostMapping(value = "/update.json")
    public Map<String,Object> updatePOST(@RequestHeader(name="token") String token, @RequestBody Student2 obj){ 
        Map<String,Object> retMap=new HashMap<>();
    try{
        //1. 토큰을 받아서 출력
        log.info("{}",token);
        log.info("{}", obj.toString());

        //2. 실패시 전달값
        retMap.put("status",0);

        //3. 토큰을 검증 후 성공
        Student2 obj1 =jwtUtil1.checkJwt(token);
        if(obj1 != null){
            //1.이메일을 이용해서 기존데이터 가져오기
            Student2 obj2=s2Repository.findById(obj1.getEmail()).orElse(null);

            //2. obj2에 필요한 정보 저장하기
            obj2.setName(obj.getName());
            obj2.setPhone(obj.getPhone());

            //3. obj2를 다시 저장하기
            s2Repository.save(obj2);
            retMap.put("status",200);
             }
        }catch(Exception e){
            e.printStackTrace();
            retMap.put("status",-1);
            retMap.put("error",e.getMessage());
        }
        return retMap;
    }   

    


    
    @PostMapping(value="/insert.json")
    public Map<String, Object> insertPOST(@RequestBody Student2 obj) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            obj.setPassword( bcpe.encode( obj.getPassword() ) );
            log.info("{}", obj.toString());
            s2Repository.save(obj);
            retMap.put("status" ,200);
        } catch (Exception e) {
            e.printStackTrace(); //개발자용 debug
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
