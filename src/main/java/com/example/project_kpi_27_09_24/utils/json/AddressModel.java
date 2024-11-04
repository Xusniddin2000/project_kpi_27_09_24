package com.example.project_kpi_27_09_24.utils.json;


import com.example.project_kpi_27_09_24.dto.address.RegionDto;
import com.example.project_kpi_27_09_24.dto.address.DistrictDto;
import com.example.project_kpi_27_09_24.dto.address.QuarterDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {
      private List<RegionDto> regions;
      private List<DistrictDto>  districts;
      private List<QuarterDto>   quarters;
}
