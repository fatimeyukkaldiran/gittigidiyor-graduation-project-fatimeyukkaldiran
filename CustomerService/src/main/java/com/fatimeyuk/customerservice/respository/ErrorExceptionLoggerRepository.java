package com.fatimeyuk.customerservice.respository;


import com.fatimeyuk.customerservice.entity.ErrorExceptionLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ErrorExceptionLoggerRepository extends JpaRepository<ErrorExceptionLogger, Long> {
    List<ErrorExceptionLogger> findByExceptionDate(LocalDate exceptionThrowDate);

    List<ErrorExceptionLogger> findByStatusCode(int statusCode);

}

