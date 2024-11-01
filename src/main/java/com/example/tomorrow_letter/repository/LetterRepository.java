package com.example.tomorrow_letter.repository;

import com.example.tomorrow_letter.dto.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {

}
