package com.example.tomorrow_letter.controller;


import com.example.tomorrow_letter.dto.*;
import com.example.tomorrow_letter.repository.LetterRepository;
import com.example.tomorrow_letter.service.LetterService;
import com.example.tomorrow_letter.service.SmsSchedulerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letter")
public class LetterController {

    private final LetterService letterService;

    @PostMapping("/add")
    public ResponseEntity<?> addLetter(@RequestBody LetterRequest letterRequest, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환

        if (session == null || session.getAttribute("user") == null) { //로그인하지 않았거나 로그인 상태가 아닌 경우
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 필요");
        }

        System.out.println("letter = " + letterRequest);
        //User user = (User) session.getAttribute("user");
        int userid = (int) session.getAttribute("user");

        return letterService.save(letterRequest, userid);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLetter(@RequestBody LetterUpdate letterUpdate, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) { //로그인하지 않았거나 로그인 상태가 아닌 경우
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 필요");
        }
        System.out.println("letter = " + letterUpdate);
        int userid = (int) session.getAttribute("user");

        return letterService.updateLetter(letterUpdate, userid);

    }

/*        @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;


    무료버전이어서 용량 초과했다는 249 오류 발생
    @GetMapping("/chat")
    public String chat(@RequestParam(name = "ask")String prompt){

        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }*/

}
