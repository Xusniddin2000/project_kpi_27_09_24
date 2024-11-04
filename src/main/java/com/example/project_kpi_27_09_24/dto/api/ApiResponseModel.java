package com.example.project_kpi_27_09_24.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  ApiResponseModel  extends  ApiResponse {
    private List<Object>   objects;



}
