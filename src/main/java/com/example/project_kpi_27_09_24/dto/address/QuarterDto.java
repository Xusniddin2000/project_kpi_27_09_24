package com.example.project_kpi_27_09_24.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterDto {
    private  Long id;
    private String name;
    private DistrictDto districtDto;
    private Long district_id;
    public QuarterDto(Long id) {
        this.id = id;
    }
}
