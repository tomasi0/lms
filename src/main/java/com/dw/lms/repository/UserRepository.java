package com.dw.lms.repository;

import com.dw.lms.dto.UserDto;
import com.dw.lms.model.Lecture;
import com.dw.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userID);
    Optional<User> findByUserNameKor(String userName); // findByUserName 에서 findByUserNameKor 로 변경
}
