package org.radon.teleeat.common.aop.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FoodExistException extends ExceptionModel {
    public FoodExistException() {
        super("Food with this name, already exists!", HttpStatus.CONFLICT);
    }
}
