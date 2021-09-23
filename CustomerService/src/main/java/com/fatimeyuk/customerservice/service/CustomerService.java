package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.dto.CreditRequestDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerMiniDto;
import com.fatimeyuk.customerservice.entity.CreditRequest;
import com.fatimeyuk.customerservice.entity.Customer;
import com.fatimeyuk.customerservice.exceptions.BadRequestException;
import com.fatimeyuk.customerservice.mappers.CreditRequestMapper;
import com.fatimeyuk.customerservice.mappers.CustomerMapper;
import com.fatimeyuk.customerservice.repository.CreditRequestRepository;
import com.fatimeyuk.customerservice.repository.CustomerRepository;
import com.fatimeyuk.customerservice.utils.validations.CustomerValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CustomerService {
        private final CustomerRepository customerRepository;
        private final CustomerMapper customerMapper;
        private final CreditRequestMapper requestMapper;
        private final CreditRequestRepository requestRepository;

        @Transactional
        public CustomerMiniDto createCreditRequestByCustomer(CustomerDto customerDto){
            boolean isExists = customerRepository.selectExistsNationalId(customerDto.getNationalId());

            if (isExists){
                throw new BadRequestException("Customer with National ID :" + customerDto.getNationalId() + "is already exist!");
            }

            Customer customer = customerMapper.mapFromCustomerDtoToCustomer(customerDto);
            CustomerDto dto = customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(customer));

            CustomerMiniDto miniDto = new CustomerMiniDto(dto.getNationalId(),dto.getMonthlyIncome());
            return  miniDto;
        }

    public void saveCreditRequest(CreditRequestDto requestDto){

        CreditRequest creditRequest = requestMapper.mapFromCreditRequestDtoToCreditRequest(requestDto);
        requestRepository.save(creditRequest);
    }


    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {

        boolean isExists = customerRepository.selectExistsNationalId(customerDto.getNationalId());

        if (isExists){
            throw new BadRequestException("Customer with National ID :" + customerDto.getNationalId() + "is already exist!");
        }

        int countPhoneNumber = customerRepository.selectExistsPhoneNumber(customerDto.getPhoneNumber());
        CustomerValidation.phoneNumberValidate(countPhoneNumber);
        Customer customer = customerMapper.mapFromCustomerDtoToCustomer(customerDto);
        return customerMapper.mapFromCustomerToCustomerDto(customerRepository.save(customer));
    }

    public CustomerMiniDto getCustomer(String nationalId){
       Customer customer = customerRepository.findCustomerByNationalId(nationalId);

        CustomerDto customerDto = customerMapper.mapFromCustomerToCustomerDto(customer);
        CustomerMiniDto customerMiniDto = new CustomerMiniDto(customerDto.getNationalId(),customerDto.getMonthlyIncome());

        return customerMiniDto;
    }


}
