package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.dto.CustomerDto;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    Logger log = LoggerFactory.getLogger(CustomerService.class);


    @Transactional
    public Optional<CustomerDto> createCustomer(CustomerDto customerDto) {

        Customer customer = checkCustomer(customerDto);

        return Optional.of(customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(customer)));

    }

    @Transactional(readOnly = true)
    public Optional<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customerDtoList = customerMapper.mapFromCustomerListToCustomerDtoList(customerRepository.findAll());
        return Optional.of(customerDtoList);

    }


    @Transactional
    public Optional<CustomerDto> updateCustomer(CustomerDto customerDto) {

        Customer foundCustomer = customerRepository.findCustomerByNationalId(customerDto.getNationalId())
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));

        foundCustomer.setFirstName(customerDto.getFirstName());
        foundCustomer.setLastName(customerDto.getLastName());
        foundCustomer.setMonthlyIncome(customerDto.getMonthlyIncome());
        foundCustomer.setPhoneNumber(customerDto.getPhoneNumber());


        return Optional.of(customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(foundCustomer)));
    }


    @Transactional
    public void deleteCustomerByNationalId(String nationalId) {

        customerRepository.deleteByNationalId(findCustomerByNationalId(nationalId).get());
    }


    @Transactional(readOnly = true)
    public Optional<String> findCustomerByNationalId(String nationalId) {
        log.info("Start to find customer by national id..");
        Customer foundCustomer = customerRepository.findCustomerByNationalId(nationalId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));

        CustomerDto customerDto = customerMapper.mapFromCustomerToCustomerDto(foundCustomer);
        return Optional.of(customerDto.getNationalId());
    }


    @Transactional(readOnly = true)
    protected Customer checkCustomer(CustomerDto customerDto) {

        log.info("control customer by all requires");

        if (customerRepository.doesNationalIdExist(customerDto.getNationalId())) {
            throw new CustomerWithNationalIdIsAlreadyException(ErrorMessageConstants.CUSTOMER_NATIONAL_ID_ALREADY_EXISTS);
        }
        if (customerRepository.doesPhoneNumberExist(customerDto.getPhoneNumber())) {
            throw new CustomerWithPhoneNumberIsAlreadyException(ErrorMessageConstants.CUSTOMER_PHONE_ALREADY_EXISTS);
        }
        CustomerValidator.validateNationalId(customerDto.getNationalId());

        log.warn(String.format("Customer national must be greater than 11 or finish not single number: ", customerDto.getNationalId()));

        Customer customer = customerMapper.mapFromCustomerDtoToCustomer(customerDto);

        return customer;
    }


    @Transactional(readOnly = true)
    public List<CustomerDto> getAllSorted() {
        Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapFromCustomerToCustomerDto)
                .collect(Collectors.toList());
    }


}