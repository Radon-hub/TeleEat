package org.radon.teleeat.common.aop.exceptionHandling;


import org.radon.teleeat.common.dto.ErrorResponse;
import org.radon.teleeat.common.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionModel extends RuntimeException {

    private final String message;
    private final HttpStatus status;

    public ExceptionModel(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }


    public ResponseEntity<Response> makeResponse(String path) {
        return ResponseEntity.status(status).body(
                new Response(
                        null,
                        new ErrorResponse(
                                status.value(),
                                status.name(),
                                message,
                                path
                        )
                )
        );
    }


}
