package com.fatimeyuk.customerservice.utils.validations;

import com.fatimeyuk.customerservice.exceptions.DateFormatterWrongException;
import com.fatimeyuk.customerservice.utils.ErrorMessageConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateformatValidator {

    public static void validateDate(String date, DateTimeFormatter formatter){
        try{
            LocalDate.parse(date,formatter);
        }catch (DateTimeParseException e){
            throw new DateFormatterWrongException(ErrorMessageConstants.WRONG_DATE_FORMATTER);
        }
    }
}
