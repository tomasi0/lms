package com.dw.lms.repository;

import com.dw.lms.model.CK.Course_registration_CK;
import com.dw.lms.model.Course_registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRegistrationRepository extends JpaRepository<Course_registration, Course_registration_CK> {
    @Query("SELECT u FROM Course_registration u WHERE u.user = :userId")
    List<Course_registration> findByUserId(@Param("userId") String userId);

    List<Course_registration> findByUser_UserIdAndLecture_LectureId(String userId, String lectureId);
}