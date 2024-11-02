package org.fourkibiki.tomorrowletter.moyeothon.controller;

import org.fourkibiki.tomorrowletter.moyeothon.dto.LetterDto;
import org.fourkibiki.tomorrowletter.moyeothon.entity.Letter;
import org.fourkibiki.tomorrowletter.moyeothon.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/letters")
public class LetterController {

    private final LetterService letterService;

    @Autowired
    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    // CREATE: 편지 생성
    @PostMapping
    public ResponseEntity<Letter> createLetter(@RequestBody LetterDto letterDto) {
        Letter createdLetter = letterService.createLetter(letterDto);
        return ResponseEntity.ok(createdLetter);
    }

    // READ: 모든 편지 조회
    @GetMapping
    public ResponseEntity<List<Letter>> getAllLetters() {
        List<Letter> letters = letterService.getAllLetters();
        return ResponseEntity.ok(letters);
    }

    // READ: 특정 편지 조회
    @GetMapping("/{id}")
    public ResponseEntity<Letter> getLetterById(@PathVariable Long id) {
        Optional<Letter> letter = letterService.getLetterById(id);
        return letter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE: 편지 업데이트 (제목과 내용만 업데이트 가능하도록 예시)
    @PutMapping("/{id}")
    public ResponseEntity<Letter> updateLetter(@PathVariable Long id, @RequestBody LetterDto letterDto) {
        Optional<Letter> updatedLetter = letterService.updateLetter(id, letterDto);
        return updatedLetter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: 편지 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLetter(@PathVariable Long id) {
        boolean deleted = letterService.deleteLetter(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}