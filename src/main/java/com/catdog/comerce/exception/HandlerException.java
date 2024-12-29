package com.catdog.comerce.exception;

import com.catdog.comerce.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(404);
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotStockException.class)
    public ResponseEntity<ErrorResponse> notStock(NotStockException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(404);
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUpdateSellingState.class)
    public ResponseEntity<ErrorResponse> invalidUpdateSellingState(InvalidUpdateSellingState ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(404);
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RepeatedException.class)
    public ResponseEntity<ErrorResponse> repeated(RepeatedException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(404);
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> alreadyExists(AlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(404);
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setMessage(ex.getMessage());
        Map<String,Object> map = new HashMap<>();
        map.put("description", ex.getField1());
        errorResponse.setError(map);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnabledBuyerException.class)
    public ResponseEntity<ErrorResponse> notEnabledBuyer(NotEnabledBuyerException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(404);
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidException(MethodArgumentNotValidException e,HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        e.getBindingResult().getAllErrors().stream().forEach(
                error -> {
                    map.put(((FieldError) error).getField(), error.getDefaultMessage());
                }
        );

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(409);
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setError(map);

        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }
}
