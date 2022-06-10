package com.exercise.assessment.exception;

import com.exercise.assessment.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler<T> {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ResponseWrapper<T>> handleNotFoundException(NotFoundException exception) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setErrors(exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<ResponseWrapper<T>> handleBadRequestException(BadRequestException exception) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setErrors(exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
