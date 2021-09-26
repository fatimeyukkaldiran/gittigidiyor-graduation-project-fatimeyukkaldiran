package com.fatimeyuk.customerservice.controller;

import com.fatimeyuk.customerservice.dto.CreditRequestResultDto;
import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.dto.CustomerRequestDto;
import com.fatimeyuk.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
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

    private final String creditUrl = "http://localhost:8081/creditApp/get-credit-result";
    private final CustomerService customerService;
    private final RestTemplate restTemplate;

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
       log.info("SAVE CUSTOMER");
        Optional<CustomerDto> result = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(result.get(), HttpStatus.OK);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        log.info("GET ALL CUSTOMERS");
        List<CustomerDto> customerDto = customerService.getCustomers();
        return new ResponseEntity<>(customerDto, HttpStatus.OK);

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        log.info("UPDATE CUSTOMER");
        Optional<?> result = customerService.updateCustomer(customerDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{nationalId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String nationalId) {
        customerService.deleteCustomerByNationalId(nationalId);

        return new ResponseEntity<>("customer deleted ...", HttpStatus.OK);
    }



    @GetMapping("getAllDesc")
    @ResponseBody
    public ResponseEntity<List<CustomerDto>> getAllSorted(){
        return new ResponseEntity<>(customerService.getAllSorted(), HttpStatus.OK);
    }

}
