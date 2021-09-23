package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.entity.ErrorExceptionLogger;
import com.fatimeyuk.customerservice.repository.ErrorExceptionLoggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ErrorExceptionLoggerService {

    private ErrorExceptionLoggerRepository loggerRepository;

    @Autowired
    public ErrorExceptionLoggerService(ErrorExceptionLoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    @Transactional
    public Optional<ErrorExceptionLogger> save(ErrorExceptionLogger errorExceptionLogger){
        return Optional.of(loggerRepository.save(errorExceptionLogger));
    }

    @Transactional(readOnly = true)
    public Optional<List<ErrorExceptionLogger>> findAll(){
        return Optional.of(loggerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<List<ErrorExceptionLogger>> findAllByStatus(int status){
        return Optional.of(loggerRepository.getAllByStatus(status));
    }

    @Transactional(readOnly = true)
    public Optional<List<ErrorExceptionLogger>> getByTimestampBetween(Date date){
        return Optional.of(loggerRepository.findByExceptionDate(date));
    }
}
