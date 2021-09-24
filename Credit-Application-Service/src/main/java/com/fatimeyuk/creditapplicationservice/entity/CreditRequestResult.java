package com.fatimeyuk.creditapplicationservice.entity;

import com.fatimeyuk.creditapplicationservice.entity.enums.CreditResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditRequestResult extends AbstractBaseEntity{

    private double creditLimit;
    private String nationalId;

    @Enumerated(EnumType.STRING)
    private CreditResult creditResult;
}
