package com.example.tomorrow_letter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String nickname;
    private String phone;
    private String password; // 편지 목록
}
