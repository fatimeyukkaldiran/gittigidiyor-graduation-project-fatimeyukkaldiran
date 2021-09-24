package com.fatimeyuk.creditapplicationservice.mappers;


import com.fatimeyuk.creditapplicationservice.dto.CreditRequestResultDto;
import com.fatimeyuk.creditapplicationservice.entity.CreditRequestResult;
import org.mapstruct.Mapper;

@Mapper
public interface CreditRequestResultMapper {
    CreditRequestResult mapFromCreditRequestDtoToCreditRequest(CreditRequestResultDto requestResultDto);
    CreditRequestResultDto mapFromCreditRequestToCreditRequestDto(CreditRequestResult creditRequestResult);
}
