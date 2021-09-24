package com.fatimeyuk.customerservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    //@NumberFormat(style = NumberFormat.Style.NUMBER)
    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number just includes numbers")
    private String phoneNumber;

    @ApiModelProperty(example = "5342.52")
    private double monthlyIncome;
}
