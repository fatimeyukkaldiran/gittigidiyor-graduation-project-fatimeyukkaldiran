package com.fatimeyuk.customerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatimeyuk.customerservice.dto.CreditRequestResultDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final String creditUrl = "http://localhost:8081/creditApp/get-credit-result";
    private final CustomerService customerService;
    private final RestTemplate restTemplate;

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        Optional<CustomerDto> result = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(result.get(), HttpStatus.OK);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<CustomerDto> customerDto = customerService.getCustomers();
        return new ResponseEntity<>(customerDto, HttpStatus.OK);

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDto customerDto){
        Optional<?> result = customerService.updateCustomer(customerDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{nationalId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String nationalId){
       customerService.deleteCustomerByNationalId(nationalId);

        return new ResponseEntity<>("customer deleted ...", HttpStatus.OK);
    }


    @GetMapping("/getByNationalId/{nationalId}")
    public ResponseEntity<CreditRequestResultDto> getCustomerByNationalId(@PathVariable String nationalId){
        CustomerRequestDto customerRequestDto = customerService.getCustomer(nationalId);
        CreditRequestResultDto result = restTemplate.postForObject(creditUrl, customerRequestDto, CreditRequestResultDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CreditRequestResultDto> getCreditRequest(@RequestBody CustomerDto customerDto) {

        CustomerRequestDto requestDto = customerService.createCreditRequestByCustomer(customerDto);

        CreditRequestResultDto result = restTemplate.postForObject(creditUrl, requestDto, CreditRequestResultDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{nationalId}")
    public ResponseEntity<List<CreditRequestResultDto>> findCreditResultByNationalId(@PathVariable String nationalId){
        List<CreditRequestResultDto> resultDtos = new ArrayList<>();

       resultDtos = restTemplate.getForObject("http://localhost:8081/creditApp/credit_results/" + nationalId, List.class);

       return new ResponseEntity<>(resultDtos, HttpStatus.OK);    }
}