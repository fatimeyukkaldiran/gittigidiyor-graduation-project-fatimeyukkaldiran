package com.fatimeyuk.creditapplicationservice.mappers;


import com.fatimeyuk.creditapplicationservice.dto.CreditRequestResultDto;
import com.fatimeyuk.creditapplicationservice.entity.CreditRequestResult;

public interface CreditAppMapper {
    CreditRequestResult mapFromCreditRequestDtoToCustomer(CreditRequestResultDto requestResultDto);
    CreditRequestResult mapFromCreditRequestToCreditRequestDto(CreditRequestResult creditRequestResult);
}
