package com.fatimeyuk.customerservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(example = "25468952134")
    @NotBlank(message = "Identification number is mandatory")
    //@Pattern(regexp = "[1-9]{1}[0-9]{9}[02468]{1}$")
    private String nationalId;

    @ApiModelProperty(example = "Fatime")
    @NotBlank(message = "First Name is mandatory")
   // @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must only include letters.")
    @Size(min=2, max=30)
    private String firstName;

    @ApiModelProperty(example = "Yükkaldıran")
    @NotBlank(message = "Last Name is mandatory")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must only include letters.")
    @Size(min=2, max=30, message = "Last name length must be between 2 and 30.")
    private String lastName;

    @ApiModelProperty(example = "5316523145")
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number just includes numbers")
    private String phoneNumber;

    @ApiModelProperty(example = "5342.52")
    @Min(value = 0)
    private double monthlyIncome;
}
