package com.fatimeyuk.creditapplicationservice.service;


import com.fatimeyuk.creditapplicationservice.dto.CreditRequestResultDto;
import com.fatimeyuk.creditapplicationservice.dto.CustomerRequestDto;
import com.fatimeyuk.creditapplicationservice.entity.CreditRequestResult;
import com.fatimeyuk.creditapplicationservice.entity.enums.CreditResult;
import com.fatimeyuk.creditapplicationservice.mappers.CreditRequestResultMapper;
import com.fatimeyuk.creditapplicationservice.repository.CreditAppRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import springfox.documentation.service.LoginEndpoint;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditAppService {
    Logger log = LoggerFactory.getLogger(LoginEndpoint.class);
    private final CreditAppRepository creditRepository;
    private final CreditRequestResultMapper creditRequestResultMapper;

    public List<CreditRequestResultDto> getAllCreditRequestResult(String nationalId) {

        return creditRepository.findAllByNationalId(nationalId)
                .stream()
                .map(creditRequestResultMapper::mapFromCreditRequestToCreditRequestDto)
                .collect(Collectors.toList());
    }

    public CreditRequestResultDto saveCreditRequestResult(CreditRequestResultDto requestDto) {
        CreditRequestResult creditRequest = new CreditRequestResult();

        creditRequest.setCreditLimit(requestDto.getCreditLimit());
        creditRequest.setCreditResult(requestDto.getCreditResult());
        creditRequest.setNationalId(requestDto.getNationalId());

        CreditRequestResult request = creditRepository.save(creditRequest);



        return new CreditRequestResultDto(request.getCreditLimit(), request.getNationalId(), request.getCreditResult());
    }


    private final static double CREDIT_LIMIT_MULTIPLIER = 4;

    public CreditRequestResultDto getCreditResult(CustomerRequestDto customerRequestDto) {
        int score = getCreditScore(customerRequestDto.getNationalId());

        CreditRequestResultDto requestDto = new CreditRequestResultDto();

        if (score < 500) {
            requestDto.setCreditLimit(0);
            requestDto.setCreditResult(CreditResult.REJECTION);
            requestDto.setNationalId(customerRequestDto.getNationalId());
        }
        if (score >= 500 && score < 1000 && customerRequestDto.getMonthlyIncome() <= 5000) {
            requestDto.setCreditLimit(10000);
            requestDto.setCreditResult(CreditResult.CONFIRM);
            requestDto.setNationalId(customerRequestDto.getNationalId());
        }
        if (score >= 500 && score < 1000 && customerRequestDto.getMonthlyIncome() > 5000) {
            requestDto.setCreditLimit(20000);
            requestDto.setCreditResult(CreditResult.CONFIRM);
            requestDto.setNationalId(customerRequestDto.getNationalId());
        }
        if (score >= 1000) {
            double newLimit = customerRequestDto.getMonthlyIncome() * CREDIT_LIMIT_MULTIPLIER;
            requestDto.setCreditLimit(newLimit);
            requestDto.setCreditResult(CreditResult.CONFIRM);
            requestDto.setNationalId(customerRequestDto.getNationalId());


        }
        return saveCreditRequestResult(requestDto);
    }

    public int getCreditScore(String nationalId) {
        int x = Character.getNumericValue(nationalId.charAt(nationalId.length() - 1));
        int score = 0;

        if (x == 0) {
            score = 2000;
        }
        if (x == 2) {
            score = 550;
        }
        if (x == 4) {
            score = 1000;
        }
        if (x == 6) {
            score = 400;
        }
        if (x == 8) {
            score = 900;
        }
        return score;
    }



}
