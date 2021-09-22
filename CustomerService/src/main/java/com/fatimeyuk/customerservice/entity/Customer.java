package com.fatimeyuk.customerservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends AbstractBaseEntity{

    private String nationalId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private double monthlyIncome;


}
