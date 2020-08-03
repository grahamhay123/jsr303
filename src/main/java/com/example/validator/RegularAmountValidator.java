package com.example.validator;

import com.example.jsr303assignment.Frequency;
import com.example.jsr303assignment.RegularAmount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;


public class RegularAmountValidator implements
        ConstraintValidator<RegularAmountConstraint, RegularAmount> {
//        ConstraintValidator<RegularAmountConstraint, Object> {


    @Override
    public void initialize(RegularAmountConstraint regularAmount) {
    }

/*
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {
        return contactField != null && contactField.matches("[0-9]+")
                && (contactField.length() > 8) && (contactField.length() < 14);
    }
*/

    @Override
    public boolean isValid(RegularAmount regularAmount,
                           ConstraintValidatorContext cxt) {

        boolean result = true;

        String amountString = regularAmount.getAmount();

        // Null frequency gives no validation error
        // Weekly and Monthly amounts give no validation error
        // Non-numeric or blank amount (whitespaces only) gives no validation error
        // Validate amounts for multiples of weeks other than a week and month
        if (regularAmount.getFrequency() != null &&
            regularAmount.getFrequency().multipleOfAWeek &&
            regularAmount.getFrequency() != Frequency.WEEK &&
            regularAmount.getFrequency() != Frequency.MONTH &&
            !amountIsNonNumericOrBlank(amountString) ) {
            // Monetary amount must be divisible to a weekly value that is a whole number of pence
            double regularAmt = Double.parseDouble(amountString);
            int weeks = regularAmount.getFrequency().weeks;

            // Calculate weekly amount
            double weeklyAmount = regularAmt/weeks;

            // Find the number of decimal places in weekly amount
            String weeklyAmountString = Double.toString(Math.abs(weeklyAmount));
            int integerPart = weeklyAmountString.indexOf('.');
            int numDecimalPlaces = weeklyAmountString.length() - integerPart - 1;

            // Validation error if there are more than 2 decimal places after the '.'
            if (numDecimalPlaces > 2) {
                result = false;
            }

        }

        return result;
    }

    private boolean amountIsNonNumericOrBlank(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            return true;
        }

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return !pattern.matcher(amount).matches();
    }

}
