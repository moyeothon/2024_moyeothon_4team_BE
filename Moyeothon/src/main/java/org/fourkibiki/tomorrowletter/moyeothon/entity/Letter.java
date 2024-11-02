package org.fourkibiki.tomorrowletter.moyeothon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    private LocalDateTime scheduledDate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String status = "PENDING"; // 기본 상태는 "PENDING"
    private LocalDateTime sentAt;

    public Letter(String title, String content, LocalDateTime scheduledDate) {
        this.title = title;
        this.content = content;
        this.scheduledDate = scheduledDate;
    }
}