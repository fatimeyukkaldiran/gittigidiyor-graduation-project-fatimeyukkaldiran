package com.fatimeyuk.customerservice.controller;

import com.fatimeyuk.customerservice.dto.CreditRequestResultDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.entity.enums.CreditResult;
import com.fatimeyuk.customerservice.exceptions.BadRequestException;
import com.fatimeyuk.customerservice.service.CustomerApplyLoanService;
import com.fatimeyuk.customerservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerApplyLoanControllerTest {

    private final String creditUrl = "http://localhost:8081/creditApp/get-credit-result";

    @Mock
    private CustomerApplyLoanService loanService ;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private CustomerApplyLoanController loanController;

/*    @BeforeEach
    public void setUp() {
        loanService = mock(CustomerApplyLoanService.class);
        loanController = new CustomerApplyLoanController(loanService,restTemplate);
    }
*/


    @Test
    public void testCreditRequestByExistCustomer_whenGetCreditRequestByExistCustomer_called_shouldReturnCreditRequestResultDto(){
        CustomerDto customerDto = new CustomerDto(1L,"25465897532","Fatime","Yükkaldıran","5425361235",5623.45);
        CustomerRequestDto requestDto = new CustomerRequestDto(customerDto.getNationalId(),customerDto.getMonthlyIncome());
        CreditRequestResultDto expected = new CreditRequestResultDto(58462.458, "25465897532", CreditResult.CONFIRM);

        when(loanService.createCreditRequestByExistCustomer(customerDto.getNationalId())).thenReturn(requestDto);

        when(restTemplate.postForObject(creditUrl, requestDto, CreditRequestResultDto.class)).thenReturn(expected);

        ResponseEntity<CreditRequestResultDto> result = loanController.getCreditRequestByExistCustomer(customerDto.getNationalId());

        verify(restTemplate).postForObject(creditUrl, requestDto, CreditRequestResultDto.class);

        CreditRequestResultDto  actual = result.getBody();


        assertAll(
                () -> assertEquals(actual, expected)
        );
    }

    @Test
    public void testCreditRequestByNewCustomer_whenGetCreditRequestByNewCustomer_called_shouldReturnCreditRequestResultDto() {
        CustomerDto customerDto = new CustomerDto(1L, "25465897532", "Fatime", "Yükkaldıran", "5425361235", 5623.45);
        CustomerRequestDto requestDto = new CustomerRequestDto(customerDto.getNationalId(), customerDto.getMonthlyIncome());
        CreditRequestResultDto expected = new CreditRequestResultDto(58462.458, "25465897532", CreditResult.CONFIRM);

        when(loanService.createCreditRequestByNewCustomer(customerDto)).thenReturn(requestDto);

        when(restTemplate.postForObject(creditUrl, requestDto, CreditRequestResultDto.class)).thenReturn(expected);

        ResponseEntity<CreditRequestResultDto> result = loanController.getCreditRequestByNewCustomer(customerDto);

        verify(restTemplate).postForObject(creditUrl, requestDto, CreditRequestResultDto.class);

        CreditRequestResultDto actual = result.getBody();


        assertAll(
                () -> assertEquals(actual, expected)
        );
    }

    @Test
    public void testGetAllCreditApplicationsByNationalId_whenResultNotEmpty_shouldReturnCreditRequestResultDtoList(){
        String nationalId = "25469853256";

        List<CreditRequestResultDto> expected = Arrays.asList(
                new CreditRequestResultDto (58462.00,"25465897532", CreditResult.CONFIRM),
                new CreditRequestResultDto (58462.00,"25465897532", CreditResult.CONFIRM)
        );
        String creditUrl = "http://localhost:8081/creditApp/credit_results/";

        when(restTemplate.getForObject(creditUrl + nationalId, List.class)).thenReturn(expected);

        ResponseEntity<List<CreditRequestResultDto>> result = loanController.getAllCreditApplicationsByNationalId(nationalId);
        List<CreditRequestResultDto> actual = result.getBody();
        verify(restTemplate).getForObject(creditUrl+nationalId, List.class);

        assertAll(
                () -> assertEquals(actual, expected)
        );
    }

    @Test
    public void testGetAllCreditApplicationsByNationalId_whenResultEmpty_shouldThrowBadRequestException(){
        String nationalId = "25469853256";

        String creditUrl = "http://localhost:8081/creditApp/credit_results/";

        when(restTemplate.getForObject(creditUrl + nationalId, List.class)).thenReturn(Collections.singletonList(Optional.empty()));
        assertThrows(BadRequestException.class, () ->
              loanController.getAllCreditApplicationsByNationalId(nationalId)
);
        verify(restTemplate).getForObject(creditUrl+nationalId, List.class);



    }
}