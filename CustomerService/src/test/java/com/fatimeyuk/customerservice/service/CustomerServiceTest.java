package com.fatimeyuk.customerservice.service;

import com.fatimeyuk.customerservice.TestGenerate;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.entity.Customer;
import com.fatimeyuk.customerservice.exceptions.CustomerNotFoundException;
import com.fatimeyuk.customerservice.exceptions.CustomerWithNationalIdIsAlreadyException;
import com.fatimeyuk.customerservice.exceptions.CustomerWithPhoneNumberIsAlreadyException;
import com.fatimeyuk.customerservice.mappers.CustomerMapper;
import com.fatimeyuk.customerservice.respository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest extends TestGenerate {

    private CustomerService customerService;
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerMapper = mock(CustomerMapper.class);
        customerService = new CustomerService(customerRepository, customerMapper);
    }

    @Test
    public void testGetAllCustomers_shouldReturnCustomerDtoList() {

        //first block preparation
        List<Customer> customerList = generateCustomerList();
        List<CustomerDto> expected = generateCustomerDtoList(customerList);

        //second block condition
        when(customerRepository.findAll()).thenReturn(customerList);
        when(customerMapper.mapFromCustomerListToCustomerDtoList(customerList)).thenReturn(expected);

        //third block service call
        List<CustomerDto> actual = customerService.getAllCustomers().get();

        //fourth block equality
        assertAll(
                () -> assertEquals(actual, expected)
        );

        verify(customerRepository).findAll();
        verify(customerMapper).mapFromCustomerListToCustomerDtoList(customerList);

    }


    @Test
    public void testFindCustomerByNationalId_whenDoesNationalIdExist_shouldReturnCustomerNationalId() {
        String nationalId = "25468975632";
        Customer customer = generateCustomer(nationalId);
        CustomerDto expected = generateCustomerDto(nationalId);

        when(customerRepository.findCustomerByNationalId(nationalId)).thenReturn(Optional.of(customer));
        when(customerMapper.mapFromCustomerToCustomerDto(customer)).thenReturn(expected);

        String actual = customerService.findCustomerByNationalId(nationalId).get();

        assertAll(
                () -> assertEquals(actual, expected.getNationalId())
        );

        verify(customerRepository).findCustomerByNationalId(nationalId);
        verify(customerMapper).mapFromCustomerToCustomerDto(customer);

    }

    @Test
    public void testFindCustomerByNationalId_whenNationalIdDoesNotExist_shouldThrowCustomerNotFoundException() {
        String nationalId = "25468975632";

        when(customerRepository.findCustomerByNationalId(nationalId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->
                customerService.findCustomerByNationalId(nationalId)
        );

        verify(customerRepository).findCustomerByNationalId(nationalId);
        verifyNoInteractions(customerMapper);

    }

    @Test
    public void testCreateCustomer_whenDoesNationalIdExist_shouldThrowCustomerWithNationalIdIsAlreadyException() {
        String nationalId = "25468975632";

        CustomerDto expected = generateCustomerDto(nationalId);

        when(customerRepository.doesNationalIdExist(nationalId)).thenReturn(true);

        assertThrows(CustomerWithNationalIdIsAlreadyException.class, () ->
                customerService.checkCustomer(expected)
        );

        verify(customerRepository).doesNationalIdExist(nationalId);

        verifyNoInteractions(customerMapper);

    }

    @Test
    public void testCreateCustomer_whenDoePhoneNumberExist_shouldThrowCustomerWithPhoneNumberIsAlreadyException() {

        String phoneNumber = "5326548975";

        CustomerDto expected = withPhoneNumberGenerateCustomerDto(phoneNumber);

        when(customerRepository.doesPhoneNumberExist(phoneNumber)).thenReturn(true);

        assertThrows(CustomerWithPhoneNumberIsAlreadyException.class, () ->
                customerService.checkCustomer(expected)
        );

        verify(customerRepository).doesPhoneNumberExist(phoneNumber);
        verifyNoInteractions(customerMapper);

    }


    @Test
    public void testCreateCustomer_whenNationalIdAndPhoneNumberDoesNotExist_shouldReturnCustomerDto() {
        String nationalId = "53265987532";
        String phoneNumber = "5635214523";

        CustomerDto customerDto = new CustomerDto(1L, nationalId, "Fatime", "Yükkaldıran", phoneNumber, 5623.452);
        Customer requestCreateCustomer = new Customer(nationalId, "Fatime", "Yükkaldıran", phoneNumber, 5623.452);

        when(customerRepository.doesNationalIdExist(nationalId)).thenReturn(Boolean.FALSE);
        when(customerRepository.doesPhoneNumberExist(phoneNumber)).thenReturn(Boolean.FALSE);
        when(customerMapper.mapFromCustomerDtoToCustomer(customerDto)).thenReturn(requestCreateCustomer);
        when(customerRepository.save(requestCreateCustomer)).thenReturn(requestCreateCustomer);
        when(customerMapper.mapFromCustomerToCustomerDto(requestCreateCustomer)).thenReturn(customerDto);

        CustomerDto actual = customerService.createCustomer(customerDto).get();

        assertAll(
                () -> assertEquals(actual, customerDto)
        );

        verify(customerRepository).save(requestCreateCustomer);
        verify(customerMapper).mapFromCustomerDtoToCustomer(customerDto);
        verify(customerMapper).mapFromCustomerToCustomerDto(requestCreateCustomer);
    }

    @Test
    public void testUpdateCustomer_whenDoesCustomerNationalIdExist_shouldReturnCustomerDto() {
        String nationalId = "53265987532";
        Customer customer = new Customer(nationalId, "Fatime", "Yükkaldıran", "5436251325", 5623.452);
        Customer requestUpdateCustomer = new Customer(nationalId, "Fatime", "Güler", "5369875365", 6500.300);
        CustomerDto expected = new CustomerDto(1L, nationalId, "Fatime", "Güler", "5369875365", 6500.300);


        when(customerRepository.findCustomerByNationalId(nationalId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(requestUpdateCustomer)).thenReturn(requestUpdateCustomer);
        when(customerMapper.mapFromCustomerToCustomerDto(requestUpdateCustomer)).thenReturn(expected);

        CustomerDto actual = customerService.updateCustomer(expected).get();

        assertAll(
                () -> assertEquals(actual, expected)
        );

        verify(customerRepository).save(requestUpdateCustomer);
        verify(customerMapper).mapFromCustomerToCustomerDto(requestUpdateCustomer);
    }

    @Test
    public void testUpdateCustomer_whenCustomerNationalIdDoestNotExist_shouldThrowCustomerNotFoundException() {
        String nationalId = "25468975632";
        CustomerDto request = new CustomerDto(1L, nationalId, "Fatime", "Güler", "5369875365", 6500.300);

        when(customerRepository.findCustomerByNationalId(request.getNationalId())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->
                customerService.updateCustomer(request)
        );

        verify(customerRepository).findCustomerByNationalId(nationalId);
        verifyNoMoreInteractions(customerRepository);
        verifyNoInteractions(customerMapper);

    }

    @Test
    public void testDeleteCustomer_whenCustomerNationalIdExist_shouldReturnDeleteCustomer() {
        String nationalId = "25468975632";
        CustomerDto request = new CustomerDto(1L, nationalId, "Fatime", "Güler", "5369875365", 6500.300);
        Customer customer = new Customer(nationalId, "Fatime", "Güler", "5369875365", 6500.300);

        when(customerRepository.findCustomerByNationalId(request.getNationalId())).thenReturn(Optional.of(customer));
        when(customerMapper.mapFromCustomerToCustomerDto(customer)).thenReturn(request);

        customerService.deleteCustomerByNationalId(request.getNationalId());

        verify(customerRepository).findCustomerByNationalId(request.getNationalId());
        verify(customerMapper).mapFromCustomerToCustomerDto(customer);
    }

    @Test
    public void testDeleteCustomer_whenNationalIdDoesNotExist_shouldThrowCustomerNotFoundException() {
        String nationalId = "25468975632";

        when(customerRepository.findCustomerByNationalId(nationalId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->
                customerService.deleteCustomerByNationalId(nationalId)
        );

        verify(customerRepository).findCustomerByNationalId(nationalId);

        verifyNoInteractions(customerMapper);

    }
}

