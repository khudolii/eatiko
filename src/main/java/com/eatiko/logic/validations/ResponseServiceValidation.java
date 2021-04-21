package com.eatiko.logic.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseServiceValidation {
    public ResponseEntity<Object> mapValidationService(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            if (!CollectionUtils.isEmpty(result.getAllErrors())) {
                for (ObjectError objectError : result.getAllErrors()) {
                    errors.put(objectError.getCode(), objectError.getDefaultMessage());
                }
            }

            for (FieldError fieldError : result.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
