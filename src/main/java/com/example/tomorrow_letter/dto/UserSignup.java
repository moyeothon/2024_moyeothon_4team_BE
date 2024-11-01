package com.example.tomorrow_letter.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSignup {

    private String phone;

    private String nickname;

    private String password;
}
