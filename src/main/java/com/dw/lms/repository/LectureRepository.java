package com.dw.lms.repository;

import com.dw.lms.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, String> {
}
