package com.fatimeyuk.customerservice.controller;


import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private CustomerService customerService;
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        customerService = mock(CustomerService.class);
        customerController = new CustomerController(customerService);
    }

    @Test
    public void testGetAllCustomers() {
        //given
        List<CustomerDto> expected = new ArrayList<>();
        when(customerService.getAllCustomers()).thenReturn(Optional.of(expected));

        //when
        ResponseEntity<List<CustomerDto>> result = customerController.getAllCustomers();
        List<CustomerDto> actual = result.getBody();

        //then
        assertAll(
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    public void testSaveCustomer_when_saveCustomer_called() {
        CustomerDto expected = new CustomerDto();

        when(customerService.createCustomer(any())).thenReturn(Optional.of(expected));

        ResponseEntity<CustomerDto> result = customerController.createCustomer(any());
        CustomerDto actual = result.getBody();

        assertAll(
                () -> assertEquals(expected, actual)
        );

    }

    @Test
    public void testUpdateCustomer_when_updateCustomer_called() {
        CustomerDto expected = new CustomerDto();
        when(customerService.updateCustomer(any())).thenReturn(Optional.of(expected));

        ResponseEntity<CustomerDto> result = customerController.updateCustomer(any());
        CustomerDto actual = result.getBody();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );

    }

    @Test
    void testDeleteCustomer_when_deleteCustomerByNationalId_called() {
        CustomerDto expected = new CustomerDto();

        doNothing().when(customerService).deleteCustomerByNationalId(expected.getNationalId());

        ResponseEntity<?> result = customerController.deleteCustomer(expected.getNationalId());


        assertAll(
                () -> assertNotNull(result)
        );
    }
}