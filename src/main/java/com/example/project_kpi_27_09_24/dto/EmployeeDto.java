package com.example.project_kpi_27_09_24.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @NotEmpty(message = "Employee firstName Enter: ")
    private String firstName;
    @NotEmpty(message = "Employee lastName Enter: ")
    private String lastName;
    @NotEmpty(message = "PINFL Enter: ")
    @Size(min = 14,max = 14,message = "PINFL 14 ta belgidan iborat bo'lishi kerak ")
    private String pinfl;

    private String hireDate;

    private Long organization_id;


}
