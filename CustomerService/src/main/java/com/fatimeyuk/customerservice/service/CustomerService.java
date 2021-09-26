package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDto> getAllSorted();
    void deleteCustomerByNationalId(String nationalId);
    Optional<?> updateCustomer(CustomerDto customerDto);
    Optional<CustomerDto> createCustomer(CustomerDto customerDto);

    List<CustomerDto> getCustomers();
}
