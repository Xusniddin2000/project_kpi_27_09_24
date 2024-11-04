package com.example.project_kpi_27_09_24.dto;


import com.example.project_kpi_27_09_24.entity.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private  Boolean  success=false;
    private  Boolean   type;
    private ResponseType responseType;
    private  String   message;


    public Result(Boolean success, ResponseType responseType) {
        this.success = success;
        this.responseType = responseType;
    }

    public Result(Boolean success, ResponseType responseType, String message) {
        this.success = success;
        this.responseType = responseType;
        this.message = message;
    }
}

