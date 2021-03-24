package com.eatiko.logic.excepsion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EKCreateUserException extends RuntimeException{
    public EKCreateUserException(String message) {
        super(message);
    }


}
