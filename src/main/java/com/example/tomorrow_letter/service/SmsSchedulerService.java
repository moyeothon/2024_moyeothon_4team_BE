package com.example.tomorrow_letter.service;

import com.example.tomorrow_letter.dto.Letter;
import com.example.tomorrow_letter.dto.LetterStatus;
import com.example.tomorrow_letter.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SmsSchedulerService {

    private final SmsService smsService;
    private final ThreadPoolTaskScheduler taskScheduler;

    // 문자 예약
    public void scheduleSms(Letter letter) {
        LocalDateTime sendAt = letter.getSendAt();
        if (sendAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("예약 시간은 현재 시간 이후여야 합니다.");
        }

        // 스케줄 작업 설정
        taskScheduler.schedule(() -> {
            try {
                smsService.sendSms(letter);
                letter.setStatus(LetterStatus.SENT);
                System.out.println("문자 전송 완료: " + letter.getLetter());
                System.out.println("letter = " + letter.toString());
            } catch (Exception e) {
                System.out.println("문자 전송 실패: " + e.getMessage());
            }
        }, Timestamp.valueOf(sendAt)); // 예약된 시간에 작업 실행
    }
}