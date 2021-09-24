package com.fatimeyuk.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorExceptionLoggerDto {
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long id;

    private Integer statusCode;
    private String errorMessage;
    private LocalDate exceptionDate;
}
