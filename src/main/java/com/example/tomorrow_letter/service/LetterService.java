
package com.example.tomorrow_letter.service;

import com.example.tomorrow_letter.dto.*;
import com.example.tomorrow_letter.repository.LetterRepository;
import com.example.tomorrow_letter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;
    private final SmsSchedulerService smsSchedulerService;
    private final UserService userService;

    public ResponseEntity<?> save(LetterRequest letterRequest, int userid) {

        User user = userRepository.findById(userid).orElse(null);
        if (user == null) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 필요함");}

        Letter letter = Letter.builder()
                .letter(letterRequest.getLetter())
                .receivePhone(letterRequest.getReceivePhone())
                .sendAt(letterRequest.getSendAt())
                .createdAt(LocalDateTime.now())
                .status(LetterStatus.WAIT)
                .letterisSent(LetterisSent.SENT)
                .user(user)
                .build();

       /* User existUser = userRepository.findByNickname(user.getNickname());
        if(existUser != null & existUser.getPassword().equals(user.getPassword())) {
            Optional<Letter> existLetter = letterRepository.findBySendAtAndReceivePhone(letter.getSendAt(), letter.getReceivePhone());
            if (existLetter.isEmpty()) {  //동일한 예약 편지 없을 때
                existUser.getLetterStore().add(letter);
                userRepository.save(existUser);
                //return ResponseEntity.ok(letter);
                return letter;
            } else {
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 동일한 편지예약이 존재합니다.");
                return null;  //이미 동일한 편지예약이 존재합니다.
            }
        }
        return null;  //등록된 사용자가 없습니다
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록된 사용자가 없습니다");*/

        letterRepository.save(letter);

        try {
            CompletableFuture<Boolean> result = smsSchedulerService.scheduleSms(letter, user); // 문자 발송 예약
            result.thenAccept(success -> {
                if (success) {
                    userService.addReceivedLetter(user, letter);
                }
            }).exceptionally(ex -> {
                System.out.println("문자 전송 실패: " + ex.getMessage());
                return null;
            });

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 중 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(letter);
    }

    public ResponseEntity<?> updateLetter(LetterUpdate letterUpdate, int userid) {
        User user = userRepository.findById(userid).orElse(null);
        if (user == null) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 필요함");}

        Letter letter =letterRepository.findById(letterUpdate.getLetterId());

        if (letter==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 편지입니다.");
        }

        letter.setLetter(letterUpdate.getLetter());
        letter.setReceivePhone(letterUpdate.getReceivePhone());
        letter.setSendAt(letterUpdate.getSendAt());
        Letter returnLetter = letterRepository.save(letter);
        return ResponseEntity.ok(returnLetter);
    }

}

