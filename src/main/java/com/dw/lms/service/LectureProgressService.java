package com.dw.lms.service;

import com.dw.lms.dto.LectureProgressDto;
import com.dw.lms.exception.ResourceNotFoundException;
import com.dw.lms.model.Lecture_progress;
import com.dw.lms.model.User;
import com.dw.lms.repository.LectureProgressRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LectureProgressService {
    @Autowired
    LectureProgressRepository lectureProgressRepository;

    private String calculLearningTime(String previousTime, String learningTime) {
        // 시간 문자열에서 각 부분을 추출
        int prevHour = Integer.parseInt(previousTime.substring(0, 2));
        int prevMin = Integer.parseInt(previousTime.substring(2, 4));
        int prevSec = Integer.parseInt(previousTime.substring(4, 6));

        int learnHour = Integer.parseInt(learningTime.substring(0, 2));
        int learnMin = Integer.parseInt(learningTime.substring(2, 4));
        int learnSec = Integer.parseInt(learningTime.substring(4, 6));

        // 시간을 합산
        int totalHour = prevHour + learnHour;
        int totalMin = prevMin + learnMin;
        int totalSec = prevSec + learnSec;

        // 초와 분이 60 이상인 경우에 대한 처리
        if (totalSec >= 60) {
            totalMin += totalSec / 60;
            totalSec %= 60;
        }

        if (totalMin >= 60) {
            totalHour += totalMin / 60;
            totalMin %= 60;
        }

        // 시간, 분, 초를 두 자리 숫자로 포맷팅하여 합침
        String sumLearningTime = String.format("%02d%02d%02d", totalHour, totalMin, totalSec);

        return sumLearningTime;
    }

    private static int convertToSeconds(String convertTime) {
        int hours = Integer.parseInt(convertTime.substring(0, 2));
        int minutes = Integer.parseInt(convertTime.substring(2, 4));
        int seconds = Integer.parseInt(convertTime.substring(4, 6));

        return hours * 3600 + minutes * 60 + seconds;
    }

    private static double calculatePercentage(int currentTimeInSeconds, int baseTimeInSeconds) {

        // 기준시간 보다 크면 100%
        if (currentTimeInSeconds >= baseTimeInSeconds) {
            return 100.00;
        }

        if (baseTimeInSeconds == 0) {
            throw new IllegalArgumentException("Base time in seconds cannot be zero");
        }
        return ((double) currentTimeInSeconds / baseTimeInSeconds) * 100;
    }

    public Lecture_progress updateLearningTime(Long lpSeq, String learningTime) {

        // Lecture Process Seq 로 전체 데이터 조회
        Lecture_progress inputLectureProgress = lectureProgressRepository.findById(lpSeq)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String previousTime = inputLectureProgress.getLearningTime();

        if (previousTime.length() < 6 || previousTime == null) {
            previousTime = "000000";
        }

        String inputlearningTime = calculLearningTime(previousTime, learningTime);

        System.out.println("previousTime: " + previousTime);
        System.out.println("learningTime: " + learningTime);
        System.out.println("inputlearningTime: " + inputlearningTime);

        inputLectureProgress.setLearningTime(inputlearningTime);

        // 현재 날짜와 시간을 한 번만 가져옴
        LocalDateTime now = LocalDateTime.now();
        inputLectureProgress.setLastLearningDatetime(now);
        inputLectureProgress.setUpdDate(now); // 현재일시

//        refLearningTime 로드
//        [learning_contents]
//        lecture_id
//        learning_contents_seq
//        learning_playtime

//        finalLectureContentsSeq
//        [course_registration]
//        user_id
//        lecture_id
//        final_lecture_contents_seq
//        progress_lecture_contents_seq [진도 횟차] => Update

//        lecture_completed_check => Y => progress_rate => 100 %
//        progress_rate           => inputlearningTime / refLearningTime
//        테스트용으로 기준시간 1분

//        progress_rate 가 100% 이면 setCompleteLearningDatetime 완료일자 세팅

//        String currentTime = "010203"; // 현재 시간 "HHMMSS" 형식
//        String baseTime    = "050101"; // 기준 시간 "HHMMSS" 형식

        String currentTime = inputlearningTime; // 현재 시간 "HHMMSS" 형식
        String baseTime    = "000100"; // 기준 시간 "HHMMSS" 형식 1분

        int currentTimeInSeconds = convertToSeconds(currentTime);
        int baseTimeInSeconds = convertToSeconds(baseTime);

        double percentage = calculatePercentage(currentTimeInSeconds, baseTimeInSeconds);

        long percentageLong = (long)percentage;

        inputLectureProgress.setProgressRate(percentageLong);

        System.out.printf("Percentage: %.2f%%\n", percentage);

        // complete_learning_datetime => inputlearningTime 하고 목차 refLearningTime 로드 비교 같거나 클때에만 아래 적용
        if (percentage == 100.00) {
            inputLectureProgress.setCompleteLearningDatetime(now); // 현재일시
            inputLectureProgress.setLearningCount(inputLectureProgress.getLearningCount() + 1); // 완료시 이전 수강횟수 + 1
        }

        // [Start, Pause, Stop] => Pause 및 Stop 시에 반영
        // learning_count => 이전 횟수 + 1

        // 사용자 객체를 저장
        return lectureProgressRepository.save(inputLectureProgress);
    }

    public List<Lecture_progress> getAllLectureProgress() {
        return lectureProgressRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;
    public List<Object[]> progressQueryDto( String userId,String lectureId){
        String sqlQuery = "SELECT A.learning_contents_seq " +
                "     , B.learning_contents " +
                "     , A.progress_rate " +
                "     , A.learning_count " +
                "     , IFNULL(DATE_FORMAT(A.last_learning_datetime, '%Y-%m-%d %H:%i:%s'),'') as last_learning_datetime " +
                "     , IFNULL(concat('(', DATE_FORMAT(A.complete_learning_datetime, '%Y-%m-%d %H:%i:%s'), ')'),'') as complete_learning_datetime " +
                "     , IF(LENGTH(IFNULL(A.learning_time,'')) = 0,'', concat(mid(A.learning_time,1,2),'시', " +
                "              mid(A.learning_time,3,2),'분', " +
                "              mid(A.learning_time,5,2),'초')) as learning_time " +
                "     , concat('(',  " +
                "              mid(B.learning_playtime,1,2),'시', " +
                "              mid(B.learning_playtime,3,2),'분', " +
                "              mid(B.learning_playtime,5,2),'초', ')') as learning_playtime " +
                "     , B.learning_pdf_path " +
                "     , B.learning_video_path " +
                "     , A.lecture_progress_seq " +
                "  FROM lecture_progress  A " +
                "  LEFT JOIN " +
                "       learning_contents B " +
                "    ON ( A.lecture_id            = B.lecture_id " +
                "     AND A.learning_contents_seq = B.learning_contents_seq) " +
                " WHERE A.user_id    = :userId " +
                "   AND A.lecture_id = :lectureId " +
                " ORDER BY A.learning_contents_seq ";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("userId", userId);
        query.setParameter("lectureId", lectureId);


        return query.getResultList();
    }

    public List<LectureProgressDto> getLectureProgressDetails( String userId, String lectureId){
        List<LectureProgressDto> lectureProgress = new ArrayList<>();
        List<Object[]> results = progressQueryDto(userId,lectureId);
        for (Object[] row: results){
            Long column1Value = Long.valueOf(row[0].toString()); // learning_contents_seq
            String column2Value = row[1].toString(); // learning_contents
            Long column3Value = Long.valueOf(row[2].toString()); // progress_rate
            Long column4Value = Long.valueOf(row[3].toString()); // learning_count
            String column5Value = row[4].toString(); // last_learning_datetime
            String column6Value = row[5].toString(); // complete_learning_datetime
            String column7Value = row[6].toString(); // learning_time
            String column8Value = row[7].toString(); // learning_playtime
            String column9Value = row[8].toString(); // learning_pdf_path
            String column10Value = row[9].toString(); // learning_video_path
            Long column11Value = Long.valueOf(row[10].toString()); // lecture_progress_seq

//            private Long learning_contents_seq;
//            private String learning_contents;
//            private Long progress_rate;
//            private Long learning_count;
//            private String last_learning_datetime;
//            private String complete_learning_datetime;
//            private String learning_time;
//            private String learning_playtime;
//            private String learning_pdf_path;
//            private String learning_video_path;
//            private Long lecture_progress_seq; // 240701 추가

            LectureProgressDto lectureProgressDto = new LectureProgressDto(column1Value, column2Value, column3Value, column4Value, column5Value,
                    column6Value, column7Value, column8Value, column9Value, column10Value, column11Value);
            lectureProgress.add(lectureProgressDto);

        }
        return lectureProgress;
    }

    public Lecture_progress getLectureProgressByProgressSeq(Long progressSeq) {
        Lecture_progress lectureProgress = lectureProgressRepository.findById(progressSeq)
                .orElseThrow(() -> new EntityNotFoundException("LectureProgressService Lecture_progress NotFound"));

        return lectureProgress;
    }



}
