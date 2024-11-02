package com.example.tomorrow_letter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserLogin {

    private String nickname;

    private String password;
}
