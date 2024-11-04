package com.example.project_kpi_27_09_24.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RefreshTokenException   extends   RuntimeException {


    private   static final  Long  serialVersionUID=1L;

    public RefreshTokenException(String  token,String message){

    }

}
