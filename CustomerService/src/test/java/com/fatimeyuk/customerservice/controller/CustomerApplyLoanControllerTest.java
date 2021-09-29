package com.fatimeyuk.customerservice.controller;

import com.fatimeyuk.customerservice.dto.CreditRequestResultDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.service.CustomerApplyLoanService;
import com.fatimeyuk.customerservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerApplyLoanControllerTest {
    private final String creditUrl = "http://localhost:8081/creditApp/get-credit-result";

    private CustomerApplyLoanService loanService ;
    private CustomerApplyLoanController loanController;
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        loanService = mock(CustomerApplyLoanService.class);
        loanController = new CustomerApplyLoanController(loanService,restTemplate);
    }


    @Test
    public void testCreditRequestByExistCustomer_when_getCreditRequestByExistCustomer_called(){
        CustomerDto customerDto = new CustomerDto();
        CustomerRequestDto requestDto = new CustomerRequestDto(customerDto.getNationalId(),customerDto.getMonthlyIncome());
        CreditRequestResultDto resultDto = new CreditRequestResultDto();

        when(loanService.createCreditRequestByExistCustomer(customerDto.getNationalId())).thenReturn(requestDto);

        when(restTemplate.postForObject(creditUrl, requestDto, CreditRequestResultDto.class)).thenReturn(resultDto);

        ResponseEntity<CreditRequestResultDto> result = loanController.getCreditRequestByExistCustomer(customerDto.getNationalId());

        verify(restTemplate).postForObject(creditUrl, requestDto, CreditRequestResultDto.class);



        assertAll(
                () -> assertEquals(result, resultDto)
        );
    }

}