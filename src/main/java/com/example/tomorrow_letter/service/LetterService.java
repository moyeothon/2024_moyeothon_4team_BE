
package com.example.tomorrow_letter.service;

import com.example.tomorrow_letter.dto.Letter;
import com.example.tomorrow_letter.dto.LetterRequest;
import com.example.tomorrow_letter.dto.LetterStatus;
import com.example.tomorrow_letter.dto.User;
import com.example.tomorrow_letter.repository.LetterRepository;
import com.example.tomorrow_letter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

    public Letter save(LetterRequest letterRequest, User user) {
        Letter letter = Letter.builder()
                .letter(letterRequest.getLetter())
                .ReceivePhone(letterRequest.getReceivePhone())
                .sendAt(letterRequest.getSendAt())
                .createdAt(LocalDateTime.now())
                .status(LetterStatus.WAIT)
                .user(user)
                .build();
        User user1 = userRepository.findByNickname(user.getNickname());
        if(user1 != null & user1.getPassword().equals(user.getPassword())) {
            user1.getLetterStore().add(letter);
            userRepository.save(user1);
        }
        letterRepository.save(letter);
        return letter;
    }

}

