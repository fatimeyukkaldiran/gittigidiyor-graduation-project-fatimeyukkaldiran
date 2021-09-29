package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.dto.CreditRequestResultDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.entity.Customer;
import com.fatimeyuk.customerservice.entity.enums.CreditResult;
import com.fatimeyuk.customerservice.exceptions.CustomerNotFoundException;
import com.fatimeyuk.customerservice.mappers.CustomerMapper;
import com.fatimeyuk.customerservice.respository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerApplyLoanServiceTest {

    private CustomerService customerService;
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;

    private CustomerApplyLoanService loanService;
    private RestTemplate restTemplate;


    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerMapper = mock(CustomerMapper.class);
        customerService = mock(CustomerService.class);
        restTemplate = mock(RestTemplate.class);
        loanService = new CustomerApplyLoanService(restTemplate, customerRepository, customerMapper, customerService);
    }

    @Test
    public void testCreateCreditRequestByNewCustomer_whenNationalIdAndPhoneNumberDoesNotExist_shouldReturnCustomerRequestDto() {
        String nationalId = "53265987532";
        String phoneNumber = "5635214523";

        CustomerDto customerDto = new CustomerDto(1L, nationalId, "Fatime", "Yükkaldıran", phoneNumber, 5623.452);
        Customer requestCreateCustomer = new Customer(nationalId, "Fatime", "Yükkaldıran", phoneNumber, 5623.452);
        CustomerRequestDto expected = new CustomerRequestDto(customerDto.getNationalId(), customerDto.getMonthlyIncome());
        CreditRequestResultDto result = new CreditRequestResultDto(58462.458, nationalId, CreditResult.CONFIRM);

        when(customerService.checkCustomer(customerDto)).thenReturn(requestCreateCustomer);
        when(customerRepository.save(requestCreateCustomer)).thenReturn(requestCreateCustomer);
        when(customerMapper.mapFromCustomerToCustomerDto(requestCreateCustomer)).thenReturn(customerDto);

        String creditUrl = "http://localhost:8081/creditApp/get-credit-result";

        when(restTemplate.postForObject(creditUrl, expected, CreditRequestResultDto.class)).thenReturn(result);

        loanService.getSmsInfo(customerDto, expected);

        verify(restTemplate).postForObject(creditUrl, expected, CreditRequestResultDto.class);

        CustomerRequestDto actual = loanService.createCreditRequestByNewCustomer(customerDto);

        assertAll(
                () -> assertEquals(actual, expected)
        );

        verify(customerRepository).save(requestCreateCustomer);
        verify(customerMapper).mapFromCustomerToCustomerDto(requestCreateCustomer);
        verify(customerService).checkCustomer(customerDto);
    }

    @Test
    public void testCreateCreditRequestByExistCustomer_whenNationalIdExist_shouldReturnCustomerRequestDto() {
        String nationalId = "53265987532";
        CustomerDto customerDto = new CustomerDto(1L, nationalId, "Fatime", "Yükkaldıran", "5365489753", 5623.452);
        Customer customer = new Customer(nationalId, "Fatime", "Yükkaldıran", "5365489753", 5623.452);
        CustomerRequestDto expected = new CustomerRequestDto(customerDto.getNationalId(), customerDto.getMonthlyIncome());
        CreditRequestResultDto result = new CreditRequestResultDto(58462.458, nationalId, CreditResult.CONFIRM);

        when(customerRepository.findCustomerByNationalId(nationalId)).thenReturn(Optional.of(customer));
        when(customerMapper.mapFromCustomerToCustomerDto(customer)).thenReturn(customerDto);


        String creditUrl = "http://localhost:8081/creditApp/get-credit-result";

        when(restTemplate.postForObject(creditUrl, expected, CreditRequestResultDto.class)).thenReturn(result);

        loanService.getSmsInfo(customerDto, expected);

        verify(restTemplate).postForObject(creditUrl, expected, CreditRequestResultDto.class);

        CustomerRequestDto actual = loanService.createCreditRequestByExistCustomer(nationalId);

        assertAll(
                () -> assertEquals(actual, expected)
        );

        verify(customerRepository).findCustomerByNationalId(nationalId);
        verify(customerMapper).mapFromCustomerToCustomerDto(customer);

    }

    @Test
    public void testCreateCreditRequestByExistCustomer_whenCustomerNationalIdDoestNotExist_shouldThrowCustomerNotFoundException() {
        String nationalId = "25468975632";

        when(customerRepository.findCustomerByNationalId(nationalId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->
                loanService.createCreditRequestByExistCustomer(nationalId)
        );

        verify(customerRepository).findCustomerByNationalId(nationalId);
        verifyNoMoreInteractions(customerRepository);
        verifyNoInteractions(customerMapper);

    }

    @Test
    public void testGetSmsInfo() {
        String nationalId = "53265987532";
        String phoneNumber = "5635214523";
        CustomerDto customerDto = new CustomerDto(1L, nationalId, "Fatime", "Yükkaldıran", phoneNumber, 5623.452);
        CustomerRequestDto expected = new CustomerRequestDto(customerDto.getNationalId(), customerDto.getMonthlyIncome());

        CustomerApplyLoanService applyLoanService = mock(CustomerApplyLoanService.class);

        doNothing().when(applyLoanService).getSmsInfo(customerDto, expected);
        applyLoanService.getSmsInfo(customerDto, expected);

        verify(applyLoanService, times(1)).getSmsInfo(customerDto, expected);

    }
}