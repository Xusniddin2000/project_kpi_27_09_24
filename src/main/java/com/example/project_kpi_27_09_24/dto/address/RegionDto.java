package com.example.project_kpi_27_09_24.dto.address;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {

    private Long id;
    @NotEmpty(message = "Region Name Enter: ")
    private String name;

    public RegionDto(Long id) {
        this.id = id;
    }



}
