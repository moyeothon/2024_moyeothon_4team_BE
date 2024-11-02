
package com.example.tomorrow_letter.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="user")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nickname;
    private String password;
    private String phone;
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    @JsonBackReference
    @ToString.Exclude
    private List<Letter> letterStore = new ArrayList<>();


   @JsonBackReference
    public List<Letter> getSentLetter() {  //보낸 문자만 가져오기
        return letterStore.stream()
                .filter(letter ->
                        letter.getLetterisSent().equals(LetterisSent.SENT))
                .toList();
    }

    @JsonBackReference// 받은 문자 목록 가져오기
    public List<Letter> getReceivedLetter() {
        return letterStore.stream()
                .filter(letter ->
                        letter.getLetterisSent().equals(LetterisSent.RECEIVED))
                .toList();
    }
}

