package com.dw.lms.service;

import com.dw.lms.dto.AnswerDto;
import com.dw.lms.dto.StatusUpdateDto;
import com.dw.lms.model.Lms_qa;
import com.dw.lms.repository.Lms_qaRepository;
import com.dw.lms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Lms_qaService {
    @Autowired
    private Lms_qaRepository lms_qaRepository;

//    public Page<Lms_qa> getQuestions(int page, int size) {
//        return lms_qaRepository.findAll(PageRequest.of(page, size));
//    }
    public Page<Lms_qa> getQuestions(int page, int size) {
        Page<Lms_qa> questionsPage = lms_qaRepository.findAll(PageRequest.of(page, size));
        for (Lms_qa question : questionsPage) {
            if (question.getLmsQaAnswerContent() != null && !question.getLmsQaAnswerContent().isEmpty()) {
                question.setLmsQaAnswerCheck("Y");
            } else {
                question.setLmsQaAnswerCheck("N");
            }
        }
        return questionsPage;
    }

    public List<Lms_qa> getAllQuestions() {
        return lms_qaRepository.findAll();
    }

    public Optional<Lms_qa> getQuestionById(Long id) {
        return lms_qaRepository.findById(id);
    }

    public Lms_qa saveQuestion(Lms_qa lms_qa) {
        return lms_qaRepository.save(lms_qa);
    }

    public void deleteQuestion(Long id) {
        lms_qaRepository.deleteById(id);
    }

    public Lms_qa answerQuestion(Long id, AnswerDto answerDto) {
        Lms_qa question = lms_qaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        question.setLmsQaAnswerContent(answerDto.getLmsQaAnswerContent());
        question.setLmsQaAnswerWriter(answerDto.getLmsQaAnswerWriter());
        question.setLmsQaAnswerDate(LocalDate.parse(answerDto.getLmsQaAnswerDate()));

        if (answerDto.getLmsQaAnswerContent() != null && !answerDto.getLmsQaAnswerContent().isEmpty()) {
            question.setLmsQaAnswerCheck("Y");
        } else {
            question.setLmsQaAnswerCheck("N");
        }

        return lms_qaRepository.save(question);
    }

    public Lms_qa updateQuestionStatus(Long id, StatusUpdateDto statusUpdateDto) {
        Lms_qa question = lms_qaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        question.setLmsQaAnswerCheck(statusUpdateDto.getLmsQaAnswerCheck());

        return lms_qaRepository.save(question);
    }

}
