package com.dw.lms.controller;

import com.dw.lms.dto.LectureCategoryCountDto;
import com.dw.lms.dto.LectureProgressDto;
import com.dw.lms.dto.LectureProgressQueryDto;
import com.dw.lms.dto.LectureStatusCountDto;
import com.dw.lms.model.Lecture;
import com.dw.lms.model.Lecture_progress;
import com.dw.lms.service.LectureProgressService;
import com.dw.lms.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LectureProgressController {
    @Autowired
    LectureProgressService lectureProgressService;

    @PutMapping("/progress/updateLearningTime/{lpSeq}/{learningTime}")
    public ResponseEntity<Lecture_progress> updateLearningTime(@PathVariable Long lpSeq, @PathVariable String learningTime) {
        return new ResponseEntity<>(lectureProgressService.updateLearningTime(lpSeq, learningTime),
                HttpStatus.OK);
    }

    @GetMapping("/progress/getAllLectureProgress")
    public ResponseEntity<List<Lecture_progress>> getAllLectureProgress() {
        return new ResponseEntity<>(lectureProgressService.getAllLectureProgress(),
                HttpStatus.OK);
    }

//    @GetMapping("/progress/queryJPQL/{userId}/{lectureId}")
//    public ResponseEntity<List<LectureProgressQueryDto>> getLectureProgressQueryJPQL(@PathVariable String userId, @PathVariable String lectureId){
//        return new ResponseEntity<>(lectureProgressService.getLectureProgressQueryJPQL(userId, lectureId), HttpStatus.OK);
//    }

    @GetMapping("/lms/lecture/progress/{userId}/{lectureId}")
    public ResponseEntity<List<LectureProgressDto>> getLectureProgressDetails(@PathVariable String userId, @PathVariable String lectureId) {
        return new ResponseEntity<>(lectureProgressService.getLectureProgressDetails(userId,lectureId),HttpStatus.OK);
    }

    @GetMapping("/lms/progress/seq/{progressSeq}")
    public ResponseEntity<Lecture_progress> getLectureProgressByProgressSeq(@PathVariable Long progressSeq) {
        return new ResponseEntity<>(lectureProgressService.getLectureProgressByProgressSeq(progressSeq),HttpStatus.OK);
    }



}
