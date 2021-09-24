package com.fatimeyuk.customerservice.utils.validations;

import com.fatimeyuk.customerservice.exceptions.BadRequestException;
import com.fatimeyuk.customerservice.utils.ErrorMessageConstants;

public class CustomerValidator{

    public static void validateNationalId(String nationalId){
        int x = Character.getNumericValue(nationalId.charAt(nationalId.length() - 1));

        if (x%2 != 0 || nationalId.length() > 11) {
            throw new BadRequestException(ErrorMessageConstants.WRONG_NATIONAL_ID);
        }
    }
}
