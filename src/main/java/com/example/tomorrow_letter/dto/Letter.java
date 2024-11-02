package com.example.tomorrow_letter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="letter")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String letter;

    private String receivePhone;

    private LocalDateTime createdAt;

    private LocalDateTime sendAt;

    @Enumerated(EnumType.STRING)
    private LetterStatus status;  //발송예정, 발송완료

    @Enumerated(EnumType.STRING)
    private LetterisSent letterisSent;  //받은 편지인지 보낸 편지인지

    @ManyToOne
    @JoinColumn(name = "user_id") // 외래 키
    @JsonIgnoreProperties("letterStore")
    private User user;

}
