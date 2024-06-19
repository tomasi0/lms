package com.dw.lms.repository;

import com.dw.lms.model.CK.Learning_contents_CK;
import com.dw.lms.model.Learning_contents;
import com.dw.lms.model.Lecture;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LearningContentsRepository extends JpaRepository<Learning_contents, Learning_contents_CK> {
    List<Learning_contents> findByLecture_LectureId(String lectureId, Sort sort);
}
