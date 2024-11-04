package com.example.project_kpi_27_09_24.service;


import com.example.project_kpi_27_09_24.dto.address.RegionDto;
import com.example.project_kpi_27_09_24.dto.api.ApiResponse;
import com.example.project_kpi_27_09_24.entity.address.Region;
import com.example.project_kpi_27_09_24.entity.enums.ResponseType;
import com.example.project_kpi_27_09_24.repository.address.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {


    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Region getRegionById(Long id) {
        return regionRepository.findById(id).orElse(null);
    }

    public ApiResponse saveRegion(RegionDto regionDto) {
        Region    region=new Region();
        region.setName(regionDto.getName());

        Region regionSaved= regionRepository.save(region);
        ApiResponse  apiResponse=new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setResponseType(ResponseType.SUCCESS);
        apiResponse.setObject(regionSaved);
        apiResponse.setMessage("Bu Region saqlandi ");
    return   apiResponse;
    }

    public ApiResponse edit(Long id,RegionDto regionDto){

        ApiResponse   apiResponse=new ApiResponse();
        Region region=regionRepository.getReferenceById(id);
        region.setName(regionDto.getName());
        Region regionSaved=regionRepository.save(region);
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Bu  User O'zgartirildi....!!!!");
        apiResponse.setResponseType(ResponseType.INFO);
        apiResponse.setObject(regionSaved);
        return apiResponse;

    }



    public ApiResponse delete(Long id){
        ApiResponse apiResponse =new ApiResponse();
        Optional<Region> regionOptional=regionRepository.findById(id);
        if(!regionOptional.isPresent()){
            apiResponse.setSuccess(false);
            apiResponse.setResponseType(ResponseType.DANGER);
            apiResponse.setMessage("Bu idli User Bazadan  topilmadi..!!!!");
            return apiResponse;
        }

        Region   regionDelete=regionRepository.findById(id).get();
        regionDelete.setDeleted(true);
        regionRepository.save(regionDelete);
        apiResponse.setSuccess(true);
        apiResponse.setResponseType(ResponseType.INFO);
        apiResponse.setMessage(" Bu User o'chirildi..!!>>");

        return   apiResponse;
    }

}
