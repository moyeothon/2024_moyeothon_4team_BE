package com.example.tomorrow_letter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class LetterRequest {

    private String letter;

    private String receivePhone;

    private LocalDateTime sendAt;
}
