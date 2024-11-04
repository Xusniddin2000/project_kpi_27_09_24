package com.example.project_kpi_27_09_24.dto.address;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto {
    private Long  id;
    private String name;
    private RegionDto region;
    private Long region_id;
}
