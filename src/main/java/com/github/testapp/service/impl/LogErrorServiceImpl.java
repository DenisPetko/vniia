package com.github.testapp.service.impl;

import com.github.testapp.db.entity.LogErrorEntity;
import com.github.testapp.db.repository.LogErrorRepository;
import com.github.testapp.service.LogErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogErrorServiceImpl implements LogErrorService {

    private final LogErrorRepository logErrorRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLogError(String message) {
        logErrorRepository.save(
                LogErrorEntity.builder()
                        .message(message)
                        .date(LocalDateTime.now())
                        .build());
    }
}
