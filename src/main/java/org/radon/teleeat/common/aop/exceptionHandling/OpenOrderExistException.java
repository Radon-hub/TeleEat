package org.radon.teleeat.common.aop.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OpenOrderExistException extends ExceptionModel{
    public OpenOrderExistException() {
        super("There is an open order for this user !", HttpStatus.BAD_REQUEST);
    }
}

