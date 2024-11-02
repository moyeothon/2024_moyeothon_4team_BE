package org.fourkibiki.tomorrowletter.moyeothon.dto;

import java.time.LocalDateTime;

public class LetterDto {

    private String title;
    private String content;
    private LocalDateTime scheduledDate;

    // 기본 생성자
    public LetterDto() {
    }

    // 생성자
    public LetterDto(String title, String content, LocalDateTime scheduledDate) {
        this.title = title;
        this.content = content;
        this.scheduledDate = scheduledDate;
    }

    // Getter 및 Setter 메서드
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}