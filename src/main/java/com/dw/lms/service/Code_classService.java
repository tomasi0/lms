package com.dw.lms.service;

import com.dw.lms.model.Code_class;
import com.dw.lms.repository.Code_classRepository;
import com.dw.lms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Code_classService {
    Code_classRepository code_classRepository;
    UserRepository userRepository;

    public Code_classService(Code_classRepository code_classRepository, UserRepository userRepository) {
        this.code_classRepository = code_classRepository;
        this.userRepository = userRepository;
    }

    public List<Code_class> getAllCode_class() {
        return code_classRepository.findAll();
    }









}
