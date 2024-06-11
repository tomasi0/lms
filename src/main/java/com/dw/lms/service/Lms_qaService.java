package com.dw.lms.service;

import com.dw.lms.model.Lms_qa;
import com.dw.lms.repository.Lms_qaRepository;
import com.dw.lms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Lms_qaService {
    Lms_qaRepository lms_qaRepository;
    UserRepository userRepository;

    public Lms_qaService(Lms_qaRepository lms_qaRepository, UserRepository userRepository) {
        this.lms_qaRepository = lms_qaRepository;
        this.userRepository = userRepository;
    }

    public List<Lms_qa> getAllLms_qa() {
        return lms_qaRepository.findAll();
    }

}
