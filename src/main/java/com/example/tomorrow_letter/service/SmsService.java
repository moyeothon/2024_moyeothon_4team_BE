package com.example.tomorrow_letter.service;


import com.example.tomorrow_letter.dto.Letter;
import net.nurigo.java_sdk.Coolsms;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.MessageType;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SmsService {


    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.api.number}")
    private String fromPhoneNumber;

    public void sendSms(Letter letter) throws CoolsmsException {
        DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");

        //HashMap<String, String> message = new HashMap<>();
        Message message = new Message();
        message.setTo(letter.getReceivePhone());    // 수신 전화번호
        message.setFrom(fromPhoneNumber);    // 발신 전화번호
        message.setType(MessageType.valueOf("SMS"));
        message.setText(letter.getLetter());

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            //messageService.send((List<Message>) message);
            List<Message> messages = new ArrayList<>();
            messages.add(message);
            messageService.send(messages); // List<Message> 형태로 전송
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }


}
