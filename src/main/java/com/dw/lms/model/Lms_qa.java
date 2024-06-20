package com.dw.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="lms_qa")
public class Lms_qa {
    @Id
    @Column(name = "lms_qa_seq")
    private Long lmsQaSeq;

    @Column(name="category_id", length = 2)
    private String categoryId;

    @Column(name="lms_qa_title", length = 100, nullable = false)
    private String lmsQaTitle;

    @ManyToOne
    @JoinColumn(name="lms_qa_writer")
    private User user;

    @Column(name="lms_qa_writing_date")
    private LocalDate lmsQaWritingDate;

    @Column(name="lms_qa_answer_content", length = 255)
    private String lmsQaAnswerContent;

    @Column(name="lms_qa_answer_writer", length = 12)
    private String lmsQaAnswerWriter;

    @Column(name="lms_qa_answer_date")
    private LocalDate lmsQaAnswerDate;

    @Column(name="lms_qa_answer_check", length = 1)
    private String lmsQaAnswerCheck;

    @Column(name="sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name="upd_date")
    private LocalDateTime updDate;

    @Column(name="lms_qa_content", nullable = false)
    private String lmsQaContent;



}
