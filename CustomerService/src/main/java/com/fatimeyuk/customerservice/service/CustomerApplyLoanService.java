package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.entity.Customer;
import com.fatimeyuk.customerservice.exceptions.CustomerNotFoundException;
import com.fatimeyuk.customerservice.exceptions.CustomerWithNationalIdIsAlreadyException;
import com.fatimeyuk.customerservice.exceptions.CustomerWithPhoneNumberIsAlreadyException;
import com.fatimeyuk.customerservice.mappers.CustomerMapper;
import com.fatimeyuk.customerservice.respository.CustomerRepository;
import com.fatimeyuk.customerservice.utils.ErrorMessageConstants;
import com.fatimeyuk.customerservice.utils.validations.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerApplyLoanService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final String creditUrl = "http://localhost:8081/creditApp/get-credit-result";


    @Transactional
    public CustomerRequestDto createCreditRequestByNewCustomer(CustomerDto customerDto) {

        Customer customer = checkCustomer(customerDto);

        CustomerDto dto = customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(customer));

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(dto.getNationalId(), dto.getMonthlyIncome());

        getSmsInfo(customerDto, customerRequestDto);

        return customerRequestDto;
    }

    private Customer checkCustomer(CustomerDto customerDto) {
        return getCustomer(customerDto, customerRepository, customerMapper);
    }

    public static Customer getCustomer(CustomerDto customerDto, CustomerRepository customerRepository, CustomerMapper customerMapper) {
        if (customerRepository.selectExistsNationalId(customerDto.getNationalId())) {
            throw new CustomerWithNationalIdIsAlreadyException(ErrorMessageConstants.CUSTOMER_NATIONAL_ID_ALREADY_EXISTS);
        }
        if (customerRepository.selectExistsPhoneNumber(customerDto.getPhoneNumber()) > 0) {
            throw new CustomerWithPhoneNumberIsAlreadyException(ErrorMessageConstants.CUSTOMER_PHONE_ALREADY_EXISTS);
        }
        CustomerValidator.validateNationalId(customerDto.getNationalId());

        Customer customer = customerMapper.mapFromCustomerDtoToCustomer(customerDto);

        return customer;
    }

    @Transactional(readOnly = true)
    public CustomerRequestDto createCreditRequestByExistCustomer(String nationalId) {

        Customer customer = customerRepository.findCustomerByNationalId(nationalId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));

        CustomerDto customerDto = customerMapper.mapFromCustomerToCustomerDto(customer);


        CustomerRequestDto customerRequestDto = new CustomerRequestDto(customerDto.getNationalId(), customerDto.getMonthlyIncome());
        getSmsInfo(customerDto, customerRequestDto);

        return customerRequestDto;
    }

    private void getSmsInfo(CustomerDto customerDto, CustomerRequestDto customerRequestDto) {
        CustomerServiceImpl.getCreditRequest(customerDto, customerRequestDto, restTemplate, creditUrl, log);
    }


}
