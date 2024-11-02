package org.fourkibiki.tomorrowletter.moyeothon.repository;

import org.fourkibiki.tomorrowletter.moyeothon.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
}
