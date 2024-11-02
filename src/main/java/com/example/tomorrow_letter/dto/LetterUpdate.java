package com.example.tomorrow_letter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class LetterUpdate {

    private int letterId;

    private String letter;

    private String receivePhone;

    private LocalDateTime sendAt;

}
