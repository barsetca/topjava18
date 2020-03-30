package ru.javawebinar.topjava.repository;

import javax.validation.*;
import java.util.Set;

public class JdbcUtil {
    private static Validator validator;
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    public static <T> boolean checkValidation(T object) {
        validator = VALIDATOR_FACTORY.usingContext().getValidator();
        Set<ConstraintViolation<T>> validates = validator.validate(object);
        if (validates.size() == 0) {
            return true;
        }
        throw new ConstraintViolationException(validates);
    }
}
