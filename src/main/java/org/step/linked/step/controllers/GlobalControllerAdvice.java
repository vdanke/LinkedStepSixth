package org.step.linked.step.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.step.linked.step.dto.ResponseFailed;
import org.step.linked.step.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseFailed> handleNotFoundException(HttpServletRequest request, NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new ResponseFailed(
                        request.getRequestURI(),
                        request.getMethod(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                ));
    }
}
