package com.dw.lms.service;

import com.dw.lms.model.Lms_qa;
import com.dw.lms.repository.Lms_qaRepository;
import com.dw.lms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class Lms_qaService {
    Lms_qaRepository lms_qaRepository;
    UserRepository userRepository;

    public List<Lms_qa> getAllLms_qa() {
        return lms_qaRepository.findAll();
    }


}
