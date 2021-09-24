package com.fatimeyuk.creditapplicationservice.dto;

import com.fatimeyuk.creditapplicationservice.entity.enums.CreditResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequestResultDto {

    private double creditLimit;
    private String nationalId;
    private CreditResult creditResult;
}
