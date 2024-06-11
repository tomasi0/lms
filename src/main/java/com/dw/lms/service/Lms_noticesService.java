package com.dw.lms.service;

import com.dw.lms.model.Lms_notices;
import com.dw.lms.repository.Lms_noticesRepository;
import com.dw.lms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Lms_noticesService {
    Lms_noticesRepository lms_noticesRepository;
    UserRepository userRepository;

    public Lms_noticesService(Lms_noticesRepository lms_noticesRepository, UserRepository userRepository) {
        this.lms_noticesRepository = lms_noticesRepository;
        this.userRepository = userRepository;
    }

    public List<Lms_notices> getAllLms_notices() {
        return lms_noticesRepository.findAll();
    }
}
