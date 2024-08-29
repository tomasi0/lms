package com.dw.lms.repository;

import com.dw.lms.model.CK.Course_registration_CK;
import com.dw.lms.model.Course_registration;
import com.dw.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRegistrationRepository extends JpaRepository<Course_registration, Course_registration_CK> {
    @Query("SELECT u FROM Course_registration u WHERE u.user = :userId")
    List<Course_registration> findByUserId(@Param("userId") String userId);
}
