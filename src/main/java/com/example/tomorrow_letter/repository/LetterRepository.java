package com.example.tomorrow_letter.repository;

import com.example.tomorrow_letter.dto.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    public Letter findById(int id);
}
