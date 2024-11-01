package com.example.tomorrow_letter.repository;

import com.example.tomorrow_letter.dto.Letter;
import com.example.tomorrow_letter.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByNickname(String nickname);
    public Optional<User> findByPhone(String phone);
    public Optional<User> findByPassword(String password);
    @Query("SELECT l FROM Letter l WHERE l.user.id = :userId")
    List<Letter> findLettersByUserId(@Param("userId") int userId);
}
