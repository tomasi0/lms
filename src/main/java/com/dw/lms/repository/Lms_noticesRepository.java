package com.dw.lms.repository;

import com.dw.lms.model.Lms_notices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Lms_noticesRepository extends JpaRepository<Lms_notices, Long> {
    @Modifying
    @Query("UPDATE Lms_notices n SET n.lmsNoticesViewCount = n.lmsNoticesViewCount + 1 WHERE n.lmsNoticesSeq = :id")
    void incrementViewCount(@Param("id") Long id);
}