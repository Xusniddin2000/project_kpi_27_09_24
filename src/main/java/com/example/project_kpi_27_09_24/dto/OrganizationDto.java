package com.example.project_kpi_27_09_24.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto {

    @NotEmpty(message = "Organization name: ")
    private String name;

    private Long region_id;

    private Long parent;

}
