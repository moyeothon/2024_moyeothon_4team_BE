package com.example.tomorrow_letter.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String ReceivePhone;

    private LocalDateTime createdAt;

    private LocalDateTime sendAt;

    @Enumerated(EnumType.STRING)
    private LetterStatus status;  //발송예쩡, 발송완료

    @ManyToOne
    @JoinColumn(name = "user_id") // 외래 키
    @JsonIgnore
    private User user;

}
