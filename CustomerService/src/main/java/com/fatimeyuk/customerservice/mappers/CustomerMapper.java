package com.fatimeyuk.customerservice.mappers;

import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.entity.Customer;
import org.mapstruct.Mapper;



@Mapper
public interface CustomerMapper {
    Customer mapFromCustomerDtoToCustomer(CustomerDto customerDto);
    CustomerDto mapFromCustomerToCustomerDto(Customer customer);
}
