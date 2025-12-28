package org.radon.teleeat.common.aop.exceptionHandling;

import jakarta.servlet.http.HttpServletRequest;
import org.radon.teleeat.common.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    public GlobalExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }


    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Response> handleException(UserNotFound ex) {
        return ex.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Response> handleException(UserExistException ex) {
        return ex.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(BadArgumentsException.class)
    public ResponseEntity<Response> handleException(BadArgumentsException ex) {
        return ex.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(FoodExistException.class)
    public ResponseEntity<Response> handleException(FoodExistException ex) {
        return ex.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(FoodNotExistException.class)
    public ResponseEntity<Response> handleException(FoodNotExistException ex) {
        return ex.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(OpenOrderExistException.class)
    public ResponseEntity<Response> handleException(OpenOrderExistException ex) {
        return ex.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(OrderNotExistException.class)
    public ResponseEntity<Response> handleException(OrderNotExistException ex) {
        return ex.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<Response> handleException(OrderItemNotFoundException ex) {
        return ex.makeResponse(request.getRequestURI());
    }

}
