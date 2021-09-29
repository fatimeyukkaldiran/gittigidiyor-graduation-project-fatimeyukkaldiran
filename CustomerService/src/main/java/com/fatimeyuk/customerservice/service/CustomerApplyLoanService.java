package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.dto.CreditRequestResultDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.entity.Customer;
import com.fatimeyuk.customerservice.entity.enums.CreditResult;
import com.fatimeyuk.customerservice.exceptions.CustomerNotFoundException;
import com.fatimeyuk.customerservice.mappers.CustomerMapper;
import com.fatimeyuk.customerservice.respository.CustomerRepository;
import com.fatimeyuk.customerservice.utils.ErrorMessageConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerApplyLoanService {

    private final String creditUrl = "http://localhost:8081/creditApp/get-credit-result";
    private final RestTemplate restTemplate;

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    Logger log = LoggerFactory.getLogger(CustomerApplyLoanService.class);

    @Transactional
    public CustomerRequestDto createCreditRequestByNewCustomer(CustomerDto customerDto) {

        Customer customer = customerService.checkCustomer(customerDto);

        CustomerDto dto = customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(customer));

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(dto.getNationalId(), dto.getMonthlyIncome());

        getSmsInfo(customerDto, customerRequestDto);

        return customerRequestDto;
    }


    @Transactional(readOnly = true)
    public CustomerRequestDto createCreditRequestByExistCustomer(String nationalId) {

        Customer customer = customerRepository.findCustomerByNationalId(nationalId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));
        log.warn("Customer could not found by national id: " + nationalId);
        CustomerDto customerDto = customerMapper.mapFromCustomerToCustomerDto(customer);

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(customerDto.getNationalId(), customerDto.getMonthlyIncome());

        getSmsInfo(customerDto, customerRequestDto);

        return customerRequestDto;
    }


    public void getSmsInfo(CustomerDto customerDto, CustomerRequestDto customerRequestDto) {
        CreditRequestResultDto result = restTemplate.postForObject(creditUrl, customerRequestDto, CreditRequestResultDto.class);

        log.info("Credit result sent to phone number " + customerDto.getPhoneNumber());

        log.info("Dear customer your loan application has been received. Your application's result:" +

                (result.getCreditResult().equals(CreditResult.CONFIRM)? "confirm": "reject") +" .Your credit Limit: " +

                (result.getCreditResult().equals(CreditResult.CONFIRM)? result.getCreditLimit(): "" ) );
    }
}
