package com.fatimeyuk.creditscoreservice.controller;

import com.fatimeyuk.creditscoreservice.dto.CreditRequestDto;
import com.fatimeyuk.creditscoreservice.dto.CustomerMiniDto;
import com.fatimeyuk.creditscoreservice.service.CreditScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/creditScore")
public class CreditScoreController {
    private final RestTemplate restTemplate;


    private final CreditScoreService creditScoreService;

    @PostMapping("/get-credit-result")
    public ResponseEntity<CreditRequestDto> getCreditResult(@RequestBody CustomerMiniDto customerMiniDto) {
        return new ResponseEntity<>(creditScoreService.getCreditResult(customerMiniDto), HttpStatus.OK);
    }



}