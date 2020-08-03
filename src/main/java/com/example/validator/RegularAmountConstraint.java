package com.example.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegularAmountValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RegularAmountConstraint {

    String message() default "Invalid regular amount entered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
