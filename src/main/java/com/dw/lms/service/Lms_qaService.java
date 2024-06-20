package com.dw.lms.service;

import com.dw.lms.model.Lms_qa;
import com.dw.lms.repository.Lms_qaRepository;
import com.dw.lms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Lms_qaService {
    @Autowired
    private Lms_qaRepository lms_qaRepository;

    public Page<Lms_qa> getQuestions(int page, int size) {
        return lms_qaRepository.findAll(PageRequest.of(page, size));
    }

    public List<Lms_qa> getAllQuestions() {
        return lms_qaRepository.findAll();
    }

    public Optional<Lms_qa> getQuestionById(Long id) {
        return lms_qaRepository.findById(id);
    }

    public Lms_qa saveQuestion(Lms_qa lmsQa) {
        return lms_qaRepository.save(lmsQa);
    }

    public void deleteQuestion(Long id) {
        lms_qaRepository.deleteById(id);
    }


}
