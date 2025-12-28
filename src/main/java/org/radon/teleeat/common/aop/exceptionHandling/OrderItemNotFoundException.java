package org.radon.teleeat.common.aop.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrderItemNotFoundException extends ExceptionModel{
    public OrderItemNotFoundException() {
        super("No order item found with this information!", HttpStatus.NOT_FOUND);
    }
}
