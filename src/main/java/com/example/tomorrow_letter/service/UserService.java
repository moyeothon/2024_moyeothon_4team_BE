
package com.example.tomorrow_letter.service;

import com.example.tomorrow_letter.dto.Letter;
import com.example.tomorrow_letter.dto.User;
import com.example.tomorrow_letter.dto.UserLogin;
import com.example.tomorrow_letter.dto.UserSignup;
import com.example.tomorrow_letter.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<?> save(UserSignup userSignup) {

        if (userSignup.getNickname() == null || userSignup.getNickname().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("닉네임은 필수 입력 항목입니다.");
        }else if (userSignup.getPassword() == null || userSignup.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호는 필수 입력 항목입니다.");
        }else if (userSignup.getPhone() == null || userSignup.getPhone().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("핸드폰 번호는 필수 입력 항목입니다.");
        }

        if(userRepository.findByNickname(userSignup.getNickname())!=null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임 입니다.");
        } else if (userRepository.findByPhone(userSignup.getPhone()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 전화번호 입니다.");
        }

        User user = User.builder()
                .nickname(userSignup.getNickname())
                .password(userSignup.getPassword())
                .phone(userSignup.getPhone())
                .createdAt(LocalDateTime.now())
                .build();

        System.out.println("user.toString() = " + user.toString());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    public User login(UserLogin userLogin){
        User user = userRepository.findByNickname(userLogin.getNickname());

        if(user.getPassword().equals(userLogin.getPassword())) {
            return user;
        }
        return null;

    }

    public List<Letter> getAllLetter(User user){
        return userRepository.findLettersByUserId(user.getId());
    }

}


