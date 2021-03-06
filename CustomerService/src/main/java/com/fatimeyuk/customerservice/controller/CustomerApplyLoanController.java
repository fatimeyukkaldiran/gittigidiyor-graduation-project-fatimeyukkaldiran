
package com.fatimeyuk.customerservice.controller;

import com.fatimeyuk.customerservice.dto.CreditRequestResultDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.exceptions.BadRequestException;
import com.fatimeyuk.customerservice.service.CustomerApplyLoanService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.service.LoginEndpoint;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerApplyLoanController {

    Logger log = LoggerFactory.getLogger(LoginEndpoint.class);

    private final String creditUrl = "http://localhost:8081/creditApp/get-credit-result";
    private final CustomerApplyLoanService customerApplyLoanService;
    private final RestTemplate restTemplate;


    @GetMapping("/getByNationalId/{nationalId}")
    public ResponseEntity<CreditRequestResultDto> getCreditRequestByExistCustomer(@PathVariable String nationalId) {
        log.info("Send request to credit application for exist customer..");

        CustomerRequestDto customerRequestDto = customerApplyLoanService.createCreditRequestByExistCustomer(nationalId);
        CreditRequestResultDto result = restTemplate.postForObject(creditUrl, customerRequestDto, CreditRequestResultDto.class);
        log.info("Credit application is done.");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CreditRequestResultDto> getCreditRequestByNewCustomer(@Valid @RequestBody CustomerDto customerDto) {
        log.info("Send request to credit application for new customer..");
        CustomerRequestDto customerRequestDto = customerApplyLoanService.createCreditRequestByNewCustomer(customerDto);

        CreditRequestResultDto result = restTemplate.postForObject(creditUrl, customerRequestDto, CreditRequestResultDto.class);

        log.info("Credit application is done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{nationalId}")
    public ResponseEntity<List<CreditRequestResultDto>> getAllCreditApplicationsByNationalId(@PathVariable String nationalId) {
        log.info("Send request to get credit applications..");
        List<CreditRequestResultDto> result = restTemplate.getForObject("http://localhost:8081/creditApp/credit_results/" + nationalId, List.class);

        if (result.isEmpty()) {
            throw new BadRequestException("Not exist credit application with this national Id");
        }
        log.info("To get credit applications are done ..");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
