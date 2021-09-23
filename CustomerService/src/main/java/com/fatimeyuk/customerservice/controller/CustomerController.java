package com.fatimeyuk.customerservice.api.controller;

import com.fatimeyuk.customerservice.dto.CreditRequestDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerMiniDto;
import com.fatimeyuk.customerservice.entity.Customer;
import com.fatimeyuk.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final String creditUrl = "http://localhost:8081/creditScore/get-credit-result";


    private final CustomerService customerService;
    private final RestTemplate restTemplate;


    @PostMapping("/save")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto resultCustomer = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(resultCustomer, HttpStatus.OK);

    }

    @GetMapping("/getByNationalId/{nationalId}")
    public ResponseEntity<CreditRequestDto> getCustomerByNationalId(@PathVariable String nationalId){
        CustomerMiniDto customerMiniDto = customerService.getCustomer(nationalId);
        CreditRequestDto result = restTemplate.postForObject(creditUrl, customerMiniDto, CreditRequestDto.class);
        customerService.saveCreditRequest(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreditRequestDto> getCreditRequest(

            @RequestBody CustomerDto customerDto) {

        CustomerMiniDto miniDto = customerService.createCreditRequestByCustomer(customerDto);

        CreditRequestDto result = restTemplate.postForObject(creditUrl, miniDto, CreditRequestDto.class);
        customerService.saveCreditRequest(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
