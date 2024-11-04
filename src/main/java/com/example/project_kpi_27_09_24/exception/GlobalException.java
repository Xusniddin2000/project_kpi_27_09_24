package com.example.project_kpi_27_09_24.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //get  all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        Map<Object, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", "" + HttpStatus.METHOD_NOT_ALLOWED);

        //get  all  errors
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMethod() + "---{ METHOD NOTO'G'RI YUBORILDI..!!>>}");

        errors.add(ex.getSupportedHttpMethods() + "--{ USHBU METHODLAR ISHLATILISHI ZARUR..!!>>}");


        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }
/*

    @ExceptionHandler(value = {ConstraintViolationException.class})
        public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, HttpServletResponse response) throws IOException {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", "" + HttpStatus.BAD_REQUEST);

        //Get all errors
        List<String> errors = new ArrayList<>();
        String[] data = StringUtils.split(ex.getMessage(), ".");
        String[] keys = StringUtils.split(data[1], ":");
        errors.add(keys[0] + "->" + keys[1]);
        body.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
*/


}
