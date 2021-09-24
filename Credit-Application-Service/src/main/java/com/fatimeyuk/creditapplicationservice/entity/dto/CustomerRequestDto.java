package com.fatimeyuk.creditapplicationservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMiniDto {
    private String nationalId;
    private double monthlyIncome;

}
