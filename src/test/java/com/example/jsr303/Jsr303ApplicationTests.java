package com.example.jsr303;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.validation.*;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Used by Selenium already
import junit.framework.Assert;

@SpringBootTest(classes = {RegularAmount.class})
class Jsr303ApplicationTests {

	@Autowired
	private RegularAmount regularAmount;

	private Set<ConstraintViolation<RegularAmount>> regularAmountViolations() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<RegularAmount>> violations = validator.validate(regularAmount);
		return violations;
	}

	@ParameterizedTest
	@EnumSource(Frequency.class)
	void InvalidAmountGivesNoValidationError(Frequency anyFrequency) {

		// Given any Frequency
		regularAmount.setFrequency(anyFrequency);

		// When a non-numeric Amount is provided
		String nonNumericAmount = "non-numeric";
		regularAmount.setAmount(nonNumericAmount);

		// Then no validation error
		assertTrue(regularAmountViolations().isEmpty());


		// When a blank Amount is provided
		String blankAmount = "   ";
		regularAmount.setAmount(blankAmount);

		// Then no validation error
		assertTrue(regularAmountViolations().isEmpty());

	}

	@ParameterizedTest
	@ValueSource(strings = {"1", "27.13", "  ", "non-numeric"})
	void NullFrequencyGivesNoValidationError(String anyAmount) {
		// Given any Amount
		regularAmount.setAmount(anyAmount);

		// When a null Frequency is provided
		regularAmount.setFrequency(null);

		// Then no validation error
		assertTrue(regularAmountViolations().isEmpty());

	}

	@ParameterizedTest
	@ValueSource(strings = {"1", "27.13", "  ", "non-numeric"})
	void WeeklyFrequencyGivesNoValidationError(String anyAmount) {
		// Given a WEEK Frequency
		Frequency weekFrequency = Frequency.WEEK;
		regularAmount.setFrequency(weekFrequency);

		// When any Amount is provided (dont need check non-numeric or blank Amount)
		regularAmount.setAmount(anyAmount);

		// Then no validation error
		assertTrue(regularAmountViolations().isEmpty());

	}

	@ParameterizedTest
	@ValueSource(strings = {"1", "27.13", "  ", "non-numeric"})
	void MonthlyFrequencyGivesNoValidationError(String anyAmount) {
		// Given a MONTH Frequency
		Frequency monthFrequency = Frequency.MONTH;
		regularAmount.setFrequency(monthFrequency);

		// When any Amount is provided (dont need check non-numeric or blank Amount)
		regularAmount.setAmount(anyAmount);

		// Then no validation error
		assertTrue(regularAmountViolations().isEmpty());

	}

	private static Stream<Arguments> weeksWithDivisibleAmounts() {
		return Stream.of(
				Arguments.of(2, "22.22"),
				Arguments.of(4, "44.44"),
				Arguments.of(13, "144.43"),
				Arguments.of(52, "577.72")
		);
	}

	@ParameterizedTest
	@MethodSource("weeksWithDivisibleAmounts")
	void ValidatedAsValidGivesNoValidationError(int wholeWeeks, String divisibleAmount) {
		// Given a Frequency is in the set TWO_WEEK, FOUR_WEEK, QUARTER, YEAR
		// And an associated Number of Weeks is 2, 4, 13, 52 respectively
		Frequency multipleWeeks = Frequency.valueOfWeeks(wholeWeeks);
		regularAmount.setFrequency(multipleWeeks);

		// When an Amount that divides by the Number of Weeks to a whole number of pence is provided
		regularAmount.setAmount(divisibleAmount);

		// Then no validation error
		assertTrue(regularAmountViolations().isEmpty());

	}

	private static Stream<Arguments> weeksWithNonDivisibleAmounts() {
		return Stream.of(
				Arguments.of(2, "22.23"),
				Arguments.of(4, "44.45"),
				Arguments.of(13, "145.63"),
				Arguments.of(52, "577.75")
		);
	}

	@ParameterizedTest
	@MethodSource("weeksWithNonDivisibleAmounts")
	void ValidatedAsInvalidGivesValidationError(int wholeWeeks, String nonDivisibleAmount) {
		// Given a Frequency is in the set TWO_WEEK, FOUR_WEEK, QUARTER, YEAR
		// And an associated Number of Weeks is 2, 4, 13, 52 respectively
		Frequency multipleWeeks = Frequency.valueOfWeeks(wholeWeeks);
		regularAmount.setFrequency(multipleWeeks);

		// When an Amount that does not divide by the Number of Weeks to a whole number of pence is provided
		regularAmount.setAmount(nonDivisibleAmount);

		// Then a validation error is produced
		assertEquals(regularAmountViolations().size(), 1);

		// Example using junit.framework.Assert
		String Var1 = "Var1";
		String Var2= "Var2";
		String Var3 = "Var3";
		String Var4 = "Var4";

		String formattedResult = String.format("first=%s and second=%s and third=%s and fourth=%s",Var1,Var2,Var3,Var4);

		Assert.assertEquals("Did not get a match",
				"first=Var1 and second=Var2 and third=Var3 and fourth=Var4", formattedResult);

	}

}
