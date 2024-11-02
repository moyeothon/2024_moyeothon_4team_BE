package com.example.tomorrow_letter.repository;

import com.example.tomorrow_letter.dto.Letter;
import com.example.tomorrow_letter.dto.LetterStatus;
import com.example.tomorrow_letter.dto.LetterisSent;
import com.example.tomorrow_letter.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(int id);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByPhone(String phone);

    @Query("SELECT l FROM Letter l WHERE l.user.id = :userId")
    List<Letter> findAllLettersByUserId(@Param("userId") int userId);

 /*   @Query("SELECT l FROM Letter l WHERE l.user.id = :userId AND l.letterisSent = :isSent")
    List<Letter> findSentLetters(@Param("userId") int userId, @Param("isSent") LetterisSent isSent);


    @Query("SELECT l FROM Letter l WHERE l.user.id = :userId AND l.letterisSent = :isSent")
    List<Letter> findReceivedLetters(@Param("userId") int userId, @Param("isSent") LetterisSent isSent);
*/}
