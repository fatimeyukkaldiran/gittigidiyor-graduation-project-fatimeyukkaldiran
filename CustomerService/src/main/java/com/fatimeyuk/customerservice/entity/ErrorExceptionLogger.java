package com.fatimeyuk.customerservice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ErrorExceptionLogger extends AbstractBaseEntity{

    private Integer status;
    private String errorMessage;
    private Date exceptionDate;
}
