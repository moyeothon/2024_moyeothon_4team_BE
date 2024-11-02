package com.example.tomorrow_letter.service;


import com.example.tomorrow_letter.dto.Letter;
import com.example.tomorrow_letter.dto.User;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.MessageType;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsService {


    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.api.number}")
    private String fromPhoneNumber;


    public Boolean sendSms(Letter letter, User user) throws CoolsmsException {
        DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");

        //수신자에게 메시지
        Message messageToReceive = new Message();
        messageToReceive.setTo(letter.getReceivePhone());    // 수신 전화번호
        messageToReceive.setFrom(fromPhoneNumber);    // 발신 전화번호
        messageToReceive.setType(MessageType.valueOf("SMS"));
        messageToReceive.setText(letter.getLetter());

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            //messageService.send((List<Message>) message);
            List<Message> messagesReceive = new ArrayList<>();
            messagesReceive.add(messageToReceive);
            messageService.send(messagesReceive); // List<Message> 형태로 전송

            if(!letter.getReceivePhone().equals(user.getPhone())){ //수신자, 발신자 다를 경우만 전송
                Message messageToSend = new Message();
                messageToSend.setTo(user.getPhone());    // 수신 전화번호
                messageToSend.setFrom(fromPhoneNumber);    // 발신 전화번호
                messageToSend.setType(MessageType.valueOf("SMS"));
                messageToSend.setText(letter.getReceivePhone()+"에게 메시지를 보냈습니다.");

                //List<Message> messagesSend = new ArrayList<>();
                //messagesSend.add(messageToSend);
                //messageService.send(messagesSend); // List<Message> 형태로 전송
            }
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
        return true;
    }


}
