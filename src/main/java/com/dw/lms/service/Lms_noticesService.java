package com.dw.lms.service;

import com.dw.lms.model.Lms_notices;
import com.dw.lms.repository.Lms_noticesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class Lms_noticesService {
    @Autowired
    private Lms_noticesRepository lms_noticesRepository;

    public void incrementViewCount(Long id) {
        lms_noticesRepository.incrementViewCount(id);
    }

    public Page<Lms_notices> getAllNotices(int page, int size) {
        return lms_noticesRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lmsNoticesWritingDate")));
    }

    public Lms_notices getNoticeById(Long id) {
        return lms_noticesRepository.findById(id).orElse(null);
    }

    public Lms_notices saveNotice(Lms_notices notice) {
        return lms_noticesRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        lms_noticesRepository.deleteById(id);
    }
}