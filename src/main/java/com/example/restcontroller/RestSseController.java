package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

// websocket
// socket.io
// mqtt, xmpp
// SSE => Server send Eventes

@Slf4j
@RestController
public class RestSseController {

    // 접속하는 클라이언트의 정보를 보관할 변수
    private static final Map<String, SseEmitter> clients = new HashMap<>(); // map으로 만듦

    // 클라이언트가 접속했을때 수행
    @GetMapping(value="/api/sse/subscribe")
    public SseEmitter subscribe(@RequestParam(name="id") String id) {
        // sse 객체 생성
        SseEmitter emitter = new SseEmitter(1000L * 1200); // 2분동안 접속유지
        clients.put(id, emitter); // map으로 만들어놓은거에 넣음

        // 클라이언트 연결 중지 및 완료 되면 clients변수에서 정보제거
        emitter.onTimeout(() -> clients.remove(id));
        emitter.onCompletion(() -> clients.remove(id));
        return emitter;
    }

    // 클라이언트가 전송을 했을 때 수행
    // {"ret":1, "abc":"def"}
    @GetMapping(value="/api/sse/publish")
    public void publish(@RequestParam(name = "message") String message) {

        // map에 보관된 개수만큼 반복하던 키값을 꺼냄
        for(String id : clients.keySet()) {
            try {
                // map의 키를 이용해서 value값을 꺼냄
                SseEmitter emitter = clients.get(id);

                // 클라이언트로 메시지 전송
                emitter.send(message, MediaType.APPLICATION_JSON);
            }           
            catch (Exception e) {
                clients.remove(id);
            }
        }
    }

    
}
