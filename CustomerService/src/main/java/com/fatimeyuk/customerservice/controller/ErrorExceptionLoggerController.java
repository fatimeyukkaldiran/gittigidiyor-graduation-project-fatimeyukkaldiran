package com.fatimeyuk.customerservice.controller;


import com.fatimeyuk.customerservice.dto.ErrorExceptionLoggerDto;
import com.fatimeyuk.customerservice.entity.ErrorExceptionLogger;
import com.fatimeyuk.customerservice.service.ErrorExceptionLoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exception-logger")
public class ErrorExceptionLoggerController {

    ErrorExceptionLoggerService errorExceptionLoggerService;

    @Autowired
    public ErrorExceptionLoggerController(ErrorExceptionLoggerService errorExceptionLoggerService) {
        this.errorExceptionLoggerService = errorExceptionLoggerService;
    }

    @GetMapping("/by-date/{date}")
    public List<ErrorExceptionLoggerDto> findByLocalDate(@PathVariable String date) {

        return errorExceptionLoggerService.getByErrorExceptionDate(date);
    }


    @GetMapping("/by-error-code/{statusCode}")
    public ResponseEntity<List<ErrorExceptionLoggerDto>> findByErrorCode(@PathVariable int statusCode) {
        return ResponseEntity.ok(errorExceptionLoggerService.getByStatusCode(statusCode));
    }

}
