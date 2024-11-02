package org.fourkibiki.tomorrowletter.moyeothon.service;

import org.fourkibiki.tomorrowletter.moyeothon.dto.LetterDto;
import org.fourkibiki.tomorrowletter.moyeothon.entity.Letter;
import org.fourkibiki.tomorrowletter.moyeothon.repository.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LetterService {

    private final LetterRepository letterRepository;

    @Autowired
    public LetterService(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    // CREATE
    public Letter createLetter(LetterDto letterDto) {
        Letter letter = new Letter(
                letterDto.getTitle(),
                letterDto.getContent(),
                letterDto.getScheduledDate()
        );
        return letterRepository.save(letter);
    }

    // READ: 모든 편지 조회
    public List<Letter> getAllLetters() {
        return letterRepository.findAll();
    }

    // READ: 특정 편지 조회
    public Optional<Letter> getLetterById(Long id) {
        return letterRepository.findById(id);
    }

    // UPDATE: 편지 업데이트
    public Optional<Letter> updateLetter(Long id, LetterDto letterDto) {
        return letterRepository.findById(id).map(letter -> {
            letter.setTitle(letterDto.getTitle());
            letter.setContent(letterDto.getContent());
            return letterRepository.save(letter);
        });
    }

    // DELETE: 편지 삭제
    public boolean deleteLetter(Long id) {
        if (letterRepository.existsById(id)) {
            letterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}