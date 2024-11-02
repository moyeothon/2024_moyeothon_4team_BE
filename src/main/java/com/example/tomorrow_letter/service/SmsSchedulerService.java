package com.example.tomorrow_letter.service;

import com.example.tomorrow_letter.dto.*;
import com.example.tomorrow_letter.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SmsSchedulerService {

    private final SmsService smsService;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final LetterRepository letterRepository;


    // 문자 예약
    public CompletableFuture<Boolean> scheduleSms(Letter letter, User user) {
        LocalDateTime sendAt = letter.getSendAt();
        if (sendAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("예약 시간은 현재 시간 이후여야 합니다.");
        }

        CompletableFuture<Boolean> result = new CompletableFuture<>();

        // 스케줄 작업 설정
        taskScheduler.schedule(() -> {
            try {
                smsService.sendSms(letter, user);   //문자 전송
                letter.setStatus(LetterStatus.SENT);
                letterRepository.save(letter);

                System.out.println("문자 전송 완료: " + letter.getLetter());
                result.complete(true);
            } catch (Exception e) {
                System.out.println("문자 전송 실패: " + e.getMessage());
            }
        }, Timestamp.valueOf(sendAt)); // 예약된 시간에 작업 실행
        return result;
    }
}