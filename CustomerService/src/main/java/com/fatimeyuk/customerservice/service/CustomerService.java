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
    public List<CustomerDto> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapFromCustomerToCustomerDto)
                .collect(Collectors.toList());
    }


    @Transactional
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
    public void deleteCustomerByNationalId(String nationalId) {

        customerRepository.deleteByNationalId(findCustomerByNationalId(nationalId));
    }


    @Transactional(readOnly = true)
    protected String findCustomerByNationalId(String nationalId) {
        log.info("Start to find customer by national id..");
        Customer foundCustomer = customerRepository.findCustomerByNationalId(nationalId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));

        return foundCustomer.getNationalId();
    }


    @Transactional(readOnly = true)
    protected Customer checkCustomer(CustomerDto customerDto) {

        log.info("control customer by all requires");

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
    public List<CustomerDto> getAllSorted() {
        Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapFromCustomerToCustomerDto)
                .collect(Collectors.toList());
    }


}