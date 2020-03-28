package ru.javawebinar.topjava.repository;

import javax.validation.*;
import java.util.Set;

public class JdbcUtil {
    private Validator validator;

    public <T> boolean checkValidation(T object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
        Set<ConstraintViolation<T>> validates = validator.validate(object);
        if (validates.size() == 0) {
            return true;
        }
        throw new ConstraintViolationException(validates);
    }
}
