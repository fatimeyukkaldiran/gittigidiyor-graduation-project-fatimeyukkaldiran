package com.fatimeyuk.customerservice.controller;

import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.service.LoginEndpoint;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    Logger log = LoggerFactory.getLogger(LoginEndpoint.class);

    private final CustomerService customerService;


    @PostMapping("/save")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
       log.info("Send request to create a new customer..");
        Optional<CustomerDto> result = customerService.createCustomer(customerDto);
        log.info("Customer was created successfully..");
        return new ResponseEntity<>(result.get(), HttpStatus.OK);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        log.info("Send request to get all customers.. ");
        List<CustomerDto> customerDto = customerService.getCustomers();

        return new ResponseEntity<>(customerDto, HttpStatus.OK);

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
      log.info("Send request to update exist customer..");

        Optional<?> result = customerService.updateCustomer(customerDto);

        log.info("Customer was updated successfully..");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{nationalId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String nationalId) {
        log.info("Send request to delete a customer..");

        customerService.deleteCustomerByNationalId(nationalId);
        log.info("Customer was deleted successfully..");

        return new ResponseEntity<>("customer deleted ...", HttpStatus.OK);

    }



    @GetMapping("getAllDesc")
    @ResponseBody
    public ResponseEntity<List<CustomerDto>> getAllSorted(){
        log.info("Get all customers sort by desc");

        return new ResponseEntity<>(customerService.getAllSorted(), HttpStatus.OK);
    }

}
