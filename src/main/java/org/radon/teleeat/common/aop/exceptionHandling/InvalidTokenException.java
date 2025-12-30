package org.radon.teleeat.common.aop.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends ExceptionModel{
    public InvalidTokenException() {
        super("Invalid Token!", HttpStatus.UNAUTHORIZED);
    }
}
