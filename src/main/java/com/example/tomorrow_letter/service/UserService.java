
package com.example.tomorrow_letter.service;

import com.example.tomorrow_letter.dto.*;
import com.example.tomorrow_letter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
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

        if(userRepository.findByNickname(userSignup.getNickname()).isPresent()) {
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
        UserDTO returnUser = entityToUserDTO(user);
        return ResponseEntity.ok(returnUser);
    }

    public Optional<User> login(UserLogin userLogin){
        Optional<User> user = userRepository.findByNickname(userLogin.getNickname());

        if(user.isPresent()&&user.get().getPassword().equals(userLogin.getPassword())) {
            return user;
        }
        return Optional.empty();

    }

    public List<Letter> getAllLetter(int userid){
        User user = findUser(userid);

        if(user==null){
            return Collections.emptyList();
        }
        List<Letter> list = userRepository.findAllLettersByUserId(user.getId());
        return list;
    }

    public List<Letter> getSentLetter(int userid){
        User user = findUser(userid);

        if(user==null){
            return Collections.emptyList();
        }
        return user.getSentLetter();
    }

    public List<Letter> getReceivedLetter(int userid){
        User user = findUser(userid);

        if(user==null){
            return Collections.emptyList();
        }
        return user.getReceivedLetter();
    }


    public User findUser(int userid){
        User user = userRepository.findById(userid).orElse(null);

        return user;
    }

    public UserDTO entityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNickname(user.getNickname());
        userDTO.setPassword(userDTO.getPassword());
        return userDTO;
    }

    public void addReceivedLetter(User user, Letter letter) {
        Optional<User> receiver =userRepository.findByPhone(letter.getReceivePhone());
        if(receiver.isPresent()){
            Letter receivedLetter = Letter.builder()
                    .letter(letter.getLetter())
                    .receivePhone(letter.getReceivePhone())
                    .status(LetterStatus.SENT)
                    .letterisSent(LetterisSent.RECEIVED)
                    .sendAt(letter.getSendAt())
                    .user(receiver.get())
                    .build();

            receiver.get().getLetterStore().add(receivedLetter);
            userRepository.save(receiver.get());
            System.out.println("user = " + user.getLetterStore());

        }
    }
}


