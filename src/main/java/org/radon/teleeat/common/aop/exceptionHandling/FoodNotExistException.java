package org.radon.teleeat.common.aop.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FoodNotExistException extends ExceptionModel {
    public FoodNotExistException() {
        super("Food not found!", HttpStatus.NOT_FOUND);
    }
}
