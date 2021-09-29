package com.fatimeyuk.customerservice;

import com.fatimeyuk.customerservice.dto.CustomerDto;
import com.fatimeyuk.customerservice.entity.Customer;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestGenerate {
    private static Long userId = 100L;
    public static List<Customer> generateCustomerList() {
        List<String> nationalIdList = Arrays.asList(
                "25468975320",
                "25468975322",
                "25468975324",
                "25468975326",
                "25468975328"
        );

        return IntStream.range(1, 6).mapToObj(x ->
                new Customer(
                        nationalIdList.get(x - 1).toString(),
                        "Fatime",
                        "Yükkaldıran",
                        "5463256423",
                        6700.0)

        ).collect(Collectors.toList());
    }

    public static List<CustomerDto> generateCustomerDtoList(List<Customer> customerList) {
        return customerList.stream().map(from -> new CustomerDto(
                from.getId(),
                from.getNationalId(),
                from.getFirstName(),
                from.getLastName(),
                from.getPhoneNumber(),
                from.getMonthlyIncome()

        )).collect(Collectors.toList());
    }

    public static Customer generateCustomer(String nationalId) {

        return new Customer("25468975632", "Ege", "Deniz", "5468956324", 7500.250);
    }

    public static CustomerDto generateCustomerDto(String nationalId) {

        return new CustomerDto(userId,nationalId,"Ege", "Deniz", "5468956324", 7500.250);

    }

    public static CustomerDto withPhoneNumberGenerateCustomerDto(String phoneNumber){
        return new CustomerDto(userId,"254897563250","Ege", "Deniz", phoneNumber, 7500.250);
    }

}
