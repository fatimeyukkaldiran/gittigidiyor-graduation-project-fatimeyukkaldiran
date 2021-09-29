package com.fatimeyuk.customerservice.mappers;

import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.entity.Customer;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface CustomerMapper {
    Customer mapFromCustomerDtoToCustomer(CustomerDto customerDto);

    List<CustomerDto> mapFromCustomerListToCustomerDtoList (List<Customer> customerList);

    CustomerDto mapFromCustomerToCustomerDto(Customer customer);
}


