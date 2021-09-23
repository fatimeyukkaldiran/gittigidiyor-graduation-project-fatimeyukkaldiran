package com.fatimeyuk.customerservice.utils.validations;

import com.fatimeyuk.customerservice.exceptions.CustomerWithPhoneNumberIsAlreadyException;
import com.fatimeyuk.customerservice.utils.ErrorMessageConstants;

public class CustomerValidation {

    public static void phoneNumberValidate(int count){
        if (count > 0)
            throw new CustomerWithPhoneNumberIsAlreadyException(ErrorMessageConstants.CUSTOMER_PHONE_ALREADY_EXISTS);
    }
}
