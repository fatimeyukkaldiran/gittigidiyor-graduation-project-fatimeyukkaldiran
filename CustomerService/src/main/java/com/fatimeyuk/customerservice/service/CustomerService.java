package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.entity.Customer;
import com.fatimeyuk.customerservice.exceptions.BadRequestException;
import com.fatimeyuk.customerservice.exceptions.CustomerNotFoundException;
import com.fatimeyuk.customerservice.exceptions.CustomerWithNationalIdIsAlreadyException;
import com.fatimeyuk.customerservice.exceptions.CustomerWithPhoneNumberIsAlreadyException;
import com.fatimeyuk.customerservice.mappers.CustomerMapper;
import com.fatimeyuk.customerservice.respository.CustomerRepository;
import com.fatimeyuk.customerservice.utils.ErrorMessageConstants;
import com.fatimeyuk.customerservice.utils.validations.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


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


    public String findCustomerByNationalId(String nationalId) {
        Customer foundCustomer = customerRepository.findCustomerByNationalId(nationalId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));

        return foundCustomer.getNationalId();
    }


    @Transactional
    public CustomerRequestDto createCreditRequestByCustomer(CustomerDto customerDto) {

        Customer customer = checkCustomer(customerDto);
        CustomerDto dto = customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(customer));

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(dto.getNationalId(), dto.getMonthlyIncome());
        return customerRequestDto;
    }


    private Customer checkCustomer(CustomerDto customerDto) {
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

    public CustomerRequestDto getCustomer(String nationalId){

       Customer customer = customerRepository.findCustomerByNationalId(nationalId)
        .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageConstants.CUSTOMER_NOT_FOUND));

        CustomerDto customerDto = customerMapper.mapFromCustomerToCustomerDto(customer);
        CustomerRequestDto customerRequestDto = new CustomerRequestDto(customerDto.getNationalId(),customerDto.getMonthlyIncome());

        return customerRequestDto;
    }
}
