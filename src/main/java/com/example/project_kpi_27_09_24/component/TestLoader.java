package com.example.project_kpi_27_09_24.component;

import com.example.project_kpi_27_09_24.dto.CalculationDto;
import com.example.project_kpi_27_09_24.dto.EmployeeDto;
import com.example.project_kpi_27_09_24.dto.OrganizationDto;
import com.example.project_kpi_27_09_24.dto.address.DistrictDto;
import com.example.project_kpi_27_09_24.dto.address.QuarterDto;
import com.example.project_kpi_27_09_24.dto.address.RegionDto;
import com.example.project_kpi_27_09_24.entity.address.Country;
import com.example.project_kpi_27_09_24.entity.address.District;
import com.example.project_kpi_27_09_24.entity.address.Quarter;
import com.example.project_kpi_27_09_24.entity.address.Region;
import com.example.project_kpi_27_09_24.repository.address.CountryRepository;
import com.example.project_kpi_27_09_24.repository.address.DistrictRepository;
import com.example.project_kpi_27_09_24.repository.address.QuarterRepository;
import com.example.project_kpi_27_09_24.repository.address.RegionRepository;
import com.example.project_kpi_27_09_24.service.CalculationService;
import com.example.project_kpi_27_09_24.service.EmployeeService;
import com.example.project_kpi_27_09_24.service.OrganizationService;
import com.example.project_kpi_27_09_24.utils.json.AddressModel;
import com.example.project_kpi_27_09_24.utils.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class TestLoader implements CommandLineRunner {


    @Value("${spring.sql.init.mode}")
    private String initMode;


    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private QuarterRepository quarterRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CalculationService calculationService;

    @Override
    public void run(String... args) throws Exception {


        //AddressModel Jsonni yuklash
        if (initMode.equals("always")) {

            AddressModel addressModel = JsonData.getModelJson("regions.json");
            Country country;
            String countryName = "O'zbekiston";
            if (!countryRepository.existsByName(countryName)) {
                country = countryRepository.save(new Country(countryName));
            } else {
                country = countryRepository.findByName(countryName).orElseThrow(EntityNotFoundException::new);
            }


            // System.out.println(addressModel.toString());


            List<QuarterDto> quarterData = addressModel.getQuarters();
            List<DistrictDto> districtData = addressModel.getDistricts();
            List<RegionDto> regionData = addressModel.getRegions();
            //System.out.println(districtData.toString());
            System.out.println(quarterData.toString());

//            int count = 0;
//            for (QuarterDto dto : quarterData) {
//                DistrictDto districtDto = getDistrict(dto.getDistrict_id(), districtData);
//                if(districtDto !=null){
//                    RegionDto regionDto = getRegion(districtDto.getRegion_id(), regionData);
//                }else{
//                    System.out.println("DistrictDto is null for district_id: " + dto.getDistrict_id());
//                }
//                count++;
//            }

            //Region save fromJson
            for (RegionDto region : regionData) {
                if (!regionRepository.existsByRegionId(region.getId())) {
                    Region regionDto = regionRepository.save(new Region(region.getId(), region.getName(), country));
                    //   System.out.println(regionS);
                }
            }

            //District   save from json data
            districtData.forEach(dto -> {
                RegionDto region = null;
                for (RegionDto regionDto : regionData) {
                    if (regionDto.getId() == dto.getRegion_id()) {
                        region = regionDto;
                    }
                }
                Region regionSave = regionRepository.findByRegionId(region.getId()).orElseThrow(EntityNotFoundException::new);

                if (!districtRepository.existsByDistrictId(dto.getId())) {
                    District district = districtRepository.save(new District(dto.getId(), dto.getName(), regionSave));
                    //  System.out.println(district);
                }
            });

            quarterData.forEach(quarterDto -> {
                DistrictDto district = null;
                for (DistrictDto districtDto : districtData) {
                    if (districtDto.getId() == quarterDto.getDistrict_id()) {
                        district = districtDto;
                        System.out.println("Ishladi if district");
                    }
                }

                // district null bo'lish ehtimoli borligini tekshirish
                if (district != null) {
                    District districtSave = districtRepository.findByDistrictId(district.getId())
                            .orElseThrow(EntityNotFoundException::new);
                    if (!quarterRepository.existsByQuarterId(quarterDto.getId())) {
                        Quarter quarter = quarterRepository.save(new Quarter(quarterDto.getId(), quarterDto.getName(), districtSave));
                        System.out.println(quarter);
                    }
                } else {
                    System.out.println("District is null for quarter_id: " + quarterDto.getId());
                }
            });
//            //Quarter  save from Json
//            quarterData.forEach(quarterDto -> {
//                DistrictDto district = null;
//                for (DistrictDto districtDto : districtData) {
//                    if (districtDto.getId() == quarterDto.getDistrict_id()) {
//                        district = districtDto;
//                        System.out.println(" Ishladi if disrict");
//                    }
//
//                }
//                District districtSave = districtRepository.findByDistrictId(district.getId()).orElseThrow(EntityNotFoundException::new);
//                if (!quarterRepository.existsByQuarterId(quarterDto.getId())) {
//                    Quarter quarter = quarterRepository.save(new Quarter(quarterDto.getId(), quarterDto.getName(), districtSave));
//                    //  System.out.println(quarter);
//                }
//
//            });
            //  District districtBaza = districtRepository.findByDistrictId(Objects.requireNonNull(districtDto).getRegion_id()).orElse(null);


//            for (QuarterDto dto : quarterData) {
//                DistrictDto districtDto = getDistrict(dto.getDistrict_id(), districtData);
//                if (districtDto != null) { // districtDto null emasligini tekshirish
//                    RegionDto regionDto = getRegion(districtDto.getRegion_id(), regionData);
//                    if (regionDto != null) {
//
//                    } else {
//                        System.out.println("RegionDto is null for region_id: " + districtDto.getRegion_id());
//                    }
//                } else {
//                    System.out.println("DistrictDto is null for district_id: " + dto.getDistrict_id());
//                }
//            }
        }

       // Organization
        if(initMode.equals("always")){
            OrganizationDto organizationDto=new OrganizationDto();
             organizationDto.setName("NKMK Kombinati");
             organizationDto.setRegion_id(6L);
             organizationDto.setParent(null);
             organizationService.save(organizationDto);

            EmployeeDto employeeDto=new EmployeeDto();
             employeeDto.setFirstName("Ali");
             employeeDto.setLastName("Aliyev");
             employeeDto.setPinfl("12345678912345");
             employeeDto.setHireDate("");
             employeeDto.setOrganization_id(1L);
             employeeService.save(employeeDto);

            CalculationDto calculationDto=new CalculationDto();
              calculationDto.setCalculationType("AWARD");
              calculationDto.setAmount(7500000D);
              calculationDto.setOrganization_id(1L);
              calculationDto.setDate("2024-06-04");
              calculationDto.setRate(1D);
              calculationDto.setEmployee_id(1L);
              calculationDto.setOrganization_id(1L);
              calculationService.save(calculationDto);


        }


    }


    private DistrictDto getDistrict(Long dis_id, List<DistrictDto> list) {
        for (DistrictDto districtDto : list) {
            if (dis_id.equals(districtDto.getId())) { // equals bilan taqqoslash yaxshiroq
                return districtDto;
            }
        }
        return null; // Agar mos DistrictDto topilmasa null qaytariladi
    }

    private RegionDto getRegion(Long reg_id, List<RegionDto> list) {

        RegionDto regionDto = null;
        for (RegionDto regDto : list) {
            if (reg_id == regDto.getId()) {

                regionDto = regDto;
            }

        }
        return regionDto;
    }


}
