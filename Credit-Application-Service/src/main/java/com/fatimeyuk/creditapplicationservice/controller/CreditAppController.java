package com.fatimeyuk.creditapplicationservice.controller;

import com.fatimeyuk.creditapplicationservice.dto.CreditRequestResultDto;
import com.fatimeyuk.creditapplicationservice.dto.CustomerRequestDto;
import com.fatimeyuk.creditapplicationservice.service.CreditAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/creditApp")
public class CreditAppController {

    private final CreditAppService creditService;

    @PostMapping("/get-credit-result")
    public ResponseEntity<CreditRequestResultDto> getCreditResult(@RequestBody CustomerRequestDto customerRequestDto) {
        return new ResponseEntity<>(creditService.getCreditResult(customerRequestDto), HttpStatus.OK);
    }

    @GetMapping("/credit_results/{nationalId}")
    public  ResponseEntity<List<CreditRequestResultDto>> getCreditResult(@PathVariable String nationalId){
        return new ResponseEntity<>(creditService.getAllCreditRequestResult(nationalId), HttpStatus.OK);
    }


}