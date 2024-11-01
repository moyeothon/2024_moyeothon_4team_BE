
package com.example.tomorrow_letter.controller;


import com.example.tomorrow_letter.dto.Letter;
import com.example.tomorrow_letter.dto.User;
import com.example.tomorrow_letter.dto.UserLogin;
import com.example.tomorrow_letter.dto.UserSignup;
import com.example.tomorrow_letter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignup userSignup) throws Exception {

        ResponseEntity<?> response = userService.save(userSignup);

        if(response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("가입된 회원입니다.");
        }
        return response;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin,
                                   HttpServletRequest request) throws Exception
    {

        System.out.println("userLogin = " + userLogin.toString());
        User responseMember = userService.login(userLogin);

        System.out.println("responseMember = " + responseMember);
        if (responseMember==null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보 없거나 아이디 또는 비밀번호가 맞지 않습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", responseMember);
        session.setMaxInactiveInterval(60 * 30);
        System.out.println("Session ID: " + session.getId());
        System.out.println("Session User: " + session.getAttribute("user"));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMember);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        HttpSession session=request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃");
    }

    @GetMapping("/get/letter")
    public Object getLetter(HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        if (session == null || session.getAttribute("user") == null) { //로그인하지 않았거나 로그인 상태가 아닌 경우
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 필요");
        }

        User user = (User) session.getAttribute("user");

        //Letter returnLetter =letterService.save(letterRequest, user);
        List<Letter> list = userService.getAllLetter(user);

        if(list.isEmpty()){
            return ResponseEntity.ok("들어 있는 편지가 없음");
        }
        return list;
    }

}
