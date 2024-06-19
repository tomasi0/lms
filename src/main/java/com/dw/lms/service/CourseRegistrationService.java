package com.dw.lms.service;

import com.dw.lms.dto.LectureCategoryCountDto;
import com.dw.lms.dto.LectureStatusCountDto;
import com.dw.lms.model.Course_history;
import com.dw.lms.model.Course_registration;
import com.dw.lms.repository.CourseRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseRegistrationService {
    @Autowired
    CourseRegistrationRepository courseRegistrationRepository;

    public List<Course_registration> getAllRegistration() {
        return courseRegistrationRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> executeNativeQueryDto(String userId) {
        String sqlQuery = "select 'A'        as lecture_status_id " +
                          "     , '수강신청' as lecture_status_name " +
                          "     , count(*)   as lecture_status_count " +
                          "     , 0          as sort_seq " +
                          "  from course_registration a " +
                          " where a.user_id = :userId " +
                          " union all " +
                          "select a.lecture_status as lecture_status_id " +
                          "     , b.code_name      as lecture_status_name " +
                          "     , count(*)         as category_count " +
                          "     , b.sort_seq       as sort_seq " +
                          "  from code_class_detail   b " +
                          "     , course_registration a " +
                          " where b.code_class = 'LECTURE_STATUS' " +
                          "   and a.lecture_status = b.code " +
                          "   and a.user_id = :userId " +
                          " group by " +
                          "       a.lecture_status " +
                          "     , b.code_name " +
                          "     , b.sort_seq " +
                          " union all " +
                          "select a.code      as lecture_status_id " +
                          "     , a.code_name as lecture_status_name " +
                          "     , 0           as category_count " +
                          "     , a.sort_seq  as sort_seq " +
                          "  from code_class_detail a " +
                          " where a.code_class = 'LECTURE_STATUS' " +
                          "   and not exists ( select * " +
                          "                      from course_registration b " +
                          "                     where b.user_id = :userId " +
                          "                       and b.lecture_status = a.code ) " +
                          " order by 4 ";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("userId", userId);

        return query.getResultList(); // Returns a list of Object arrays
    }

    // Native Query 사용2 => Dto 에 담는 예제
    public List<LectureStatusCountDto> getLectureStatusCountJPQL(String userId) {

        List<LectureStatusCountDto> targetDto = new ArrayList<>();
        List<Object[]> results = executeNativeQueryDto(userId);

// [LectureCategoryCountDto]
//    private String LectureStatusId;
//    private String LectureStatusName;
//    private Long LectureStatusCount;

        // Process the results
        for (Object[] row : results) {
            // Access each column in the row
            System.out.println("StatusId:    "+row[0].toString());
            System.out.println("StatusName:  "+row[1].toString());
            System.out.println("StatusCount: "+row[2].toString());
            System.out.println("SortSeq:     "+row[3].toString());

            String  column1Value = row[0].toString(); // Assuming column1 is of type String
            String  column2Value = row[1].toString(); // Assuming column2 is of type String
            Long    column3Value = Long.valueOf(row[2].toString()); // Assuming column3 is of type int
            Long    column4Value = Long.valueOf(row[3].toString()); // Assuming column3 is of type int

            System.out.println("column1Value: "+ column1Value);
            System.out.println("column2Value: "+ column2Value);
            System.out.println("column3Value: "+ column3Value);
            System.out.println("column4Value: "+ column4Value);

            LectureStatusCountDto lectureStatusCountDto = new LectureStatusCountDto(column1Value, column2Value, column3Value, column4Value);
            targetDto.add(lectureStatusCountDto);
            // Do something with the values...
        }

        return targetDto;
    }











}
