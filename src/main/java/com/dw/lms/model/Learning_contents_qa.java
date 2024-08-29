package com.dw.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="learning_contents_qa")
public class Learning_contents_qa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_contents_qa_seq")
    private Long learningContentsQASeq;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="lecture_id", referencedColumnName = "lecture_id"),
            @JoinColumn(name="learning_contents_seq", referencedColumnName = "learning_contents_seq")
    })
    private Learning_contents learning_contents;

    @Column(name = "qustion_seq")
    private Integer qustionSeq; // (Question: 1,2,3)

    @Column(name = "qustion_content", length = 255)
    private String qustionContent;

    @Column(name = "answer1_content", length = 255)
    private String answer1Content;

    @Column(name = "answer2_content", length = 255)
    private String answer2Content;

    @Column(name = "answer3_content", length = 255)
    private String answer3Content;

    @Column(name = "answer4_content", length = 255)
    private String answer4Content;

    @Column(name = "correct_answer")
    private Integer correctAnswer; // (correctAnswer: 1,2,3,4)

    @Column(name = "sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
