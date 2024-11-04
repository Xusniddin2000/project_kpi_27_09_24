package com.example.project_kpi_27_09_24.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationDto {

    private Long   employee_id;
    private Double amount;
    private Double  rate;
    private String date;
    private Long organization_id;
    private String calculationType;

}
