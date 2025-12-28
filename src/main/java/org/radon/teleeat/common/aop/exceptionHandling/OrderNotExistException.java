package org.radon.teleeat.common.aop.exceptionHandling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotExistException extends ExceptionModel{
    public OrderNotExistException() {
        super("Order not found!", HttpStatus.NOT_FOUND);
    }
}
