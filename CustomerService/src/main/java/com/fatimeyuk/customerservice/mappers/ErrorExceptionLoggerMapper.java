package com.fatimeyuk.customerservice.mappers;

import com.fatimeyuk.customerservice.dto.ErrorExceptionLoggerDto;
import com.fatimeyuk.customerservice.entity.ErrorExceptionLogger;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public abstract class ErrorExceptionLoggerMapper {
    public abstract ErrorExceptionLoggerDto mapExceptionLoggerToDto(ErrorExceptionLogger exceptionLogger);
}

