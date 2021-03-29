package com.eatiko.logic.excepsion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EKClientReadableException extends Exception{
    public EKClientReadableException(String s) {
        super(s);
    }

    public EKClientReadableException(Throwable throwable) {
        super(throwable);
    }
}
