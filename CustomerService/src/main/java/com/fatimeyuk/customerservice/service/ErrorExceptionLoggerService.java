package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.ErrorExceptionLoggerDto;
import com.fatimeyuk.customerservice.entity.ErrorExceptionLogger;
import com.fatimeyuk.customerservice.mappers.ErrorExceptionLoggerMapper;
import com.fatimeyuk.customerservice.respository.ErrorExceptionLoggerRepository;
import com.fatimeyuk.customerservice.utils.validations.DateformatValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ErrorExceptionLoggerService {

    private final ErrorExceptionLoggerRepository loggerRepository;
    private final ErrorExceptionLoggerMapper exceptionLoggerMapper;

    @Transactional
    public Optional<ErrorExceptionLogger> save(ErrorExceptionLogger errorExceptionLogger) {
        return Optional.of(loggerRepository.save(errorExceptionLogger));
    }

    @Transactional(readOnly = true)
    public List<ErrorExceptionLoggerDto> getByErrorExceptionDate(String date) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        DateformatValidator.validateDate(date,formatter);
        LocalDate datetime = LocalDate.parse(date, formatter);

        return loggerRepository.findByExceptionDate(datetime)
                .stream()
                .map(exceptionLoggerMapper::mapExceptionLoggerToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ErrorExceptionLoggerDto> getByStatusCode(int statusCode) {
        return loggerRepository.findByStatusCode(statusCode)
                .stream()
                .map(exceptionLoggerMapper::mapExceptionLoggerToDto)
                .collect(Collectors.toList());
    }



}
