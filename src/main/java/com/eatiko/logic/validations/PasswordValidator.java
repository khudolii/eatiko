package com.eatiko.logic.validations;

import com.eatiko.logic.annotations.PasswordMatches;
import com.eatiko.logic.dto.ACLUserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        ACLUserDTO aclUserDTO = (ACLUserDTO) o;
        return aclUserDTO.getPassword().equals(aclUserDTO.getConfirmPassword());
    }

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }
}
