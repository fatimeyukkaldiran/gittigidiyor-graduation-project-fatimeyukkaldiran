package com.fatimeyuk.customerservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ErrorExceptionLogger extends AbstractBaseEntity{

    private Integer statusCode;
    private String errorMessage;
    private LocalDate exceptionDate;
}
