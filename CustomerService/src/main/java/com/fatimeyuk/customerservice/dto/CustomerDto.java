package com.fatimeyuk.customerservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(example = "25468952134")
    @NotBlank(message = "Identification number is mandatory")
    private String nationalId;

    @ApiModelProperty(example = "Fatime")
    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @ApiModelProperty(example = "Yükkaldıran")
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @ApiModelProperty(example = "5316523145")
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @ApiModelProperty(example = "5342.52")
    @NotBlank(message = "Monthly income is mandatory")
    private double monthlyIncome;
}
