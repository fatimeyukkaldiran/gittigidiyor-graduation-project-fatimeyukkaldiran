package com.fatimeyuk.customerservice.service;


import com.fatimeyuk.customerservice.dto.CreditRequestResultDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.entity.Customer;

import com.fatimeyuk.customerservice.entity.enums.CreditResult;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.service.LoginEndpoint;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fatimeyuk.customerservice.service.CustomerApplyLoanService.getCustomer;


@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final String creditUrl = "http://localhost:8081/creditApp/get-credit-result";


    private final RestTemplate restTemplate;

    @Transactional
    @Override
    public Optional<CustomerDto> createCustomer(CustomerDto customerDto) {

        Customer customer = checkCustomer(customerDto);

        return Optional.of(customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(customer)));

    }


    @Transactional(readOnly = true)
    public List<CustomerDto> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapFromCustomerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<?> updateCustomer(CustomerDto customerDto) {

        Customer foundCustomer = customerRepository.findCustomerByNationalId(customerDto.getNationalId())
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));

        foundCustomer.setFirstName(customerDto.getFirstName());
        foundCustomer.setLastName(customerDto.getLastName());
        foundCustomer.setMonthlyIncome(customerDto.getMonthlyIncome());
        foundCustomer.setPhoneNumber(customerDto.getPhoneNumber());

        return Optional.of(customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(foundCustomer)));

    }


    @Transactional
    @Override
    public void deleteCustomerByNationalId(String nationalId) {

        customerRepository.deleteByNationalId(findCustomerByNationalId(nationalId));
    }


    public String findCustomerByNationalId(String nationalId) {
        Customer foundCustomer = customerRepository.findCustomerByNationalId(nationalId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));

        return foundCustomer.getNationalId();
    }



    private Customer checkCustomer(CustomerDto customerDto) {
        return getCustomer(customerDto, customerRepository, customerMapper);
    }



    @Override
    public List<CustomerDto> getAllSorted() {
        Sort sort = Sort.by(Sort.Direction.DESC,"firstName");
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapFromCustomerToCustomerDto)
                .collect(Collectors.toList());
    }



    private void getSmsInfo(CustomerDto customerDto, CustomerRequestDto customerRequestDto) {
        getCreditRequest(customerDto, customerRequestDto, restTemplate, creditUrl, log);
    }

    static void getCreditRequest(CustomerDto customerDto, CustomerRequestDto customerRequestDto, RestTemplate restTemplate, String creditUrl, Logger log) {
        CreditRequestResultDto result = restTemplate.postForObject(creditUrl, customerRequestDto, CreditRequestResultDto.class);
        log.info("Credit result sent to phone number " + customerDto.getPhoneNumber());
        log.info("Dear customer your loan application has been received. Your application's result:" + (result.getCreditResult().equals(CreditResult.CONFIRM)? "confirm": "reject") +" .Your credit Limit: " + (result.getCreditResult().equals(CreditResult.CONFIRM)? result.getCreditLimit(): "" ) );
    }


}
