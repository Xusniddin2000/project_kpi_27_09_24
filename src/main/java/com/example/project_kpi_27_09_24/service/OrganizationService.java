package com.example.project_kpi_27_09_24.service;


import com.example.project_kpi_27_09_24.dto.OrganizationDto;
import com.example.project_kpi_27_09_24.dto.api.ApiResponse;
import com.example.project_kpi_27_09_24.dto.api.ApiResponseModel;
import com.example.project_kpi_27_09_24.entity.Organization;
import com.example.project_kpi_27_09_24.entity.enums.ResponseType;
import com.example.project_kpi_27_09_24.repository.OrganizationRepository;
import com.example.project_kpi_27_09_24.repository.address.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private RegionRepository regionRepository;



    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Organization getById(Long id) {
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (organizationOptional.isPresent()) {
            return organizationOptional.get();
        }
        return null;
    }

    public ApiResponse getOrganization(Long id) {
        ApiResponse result = new ApiResponse();
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (organizationOptional.isPresent()) {
            result.setSuccess(true);
            result.setObject(organizationOptional.get());
            result.setResponseType(ResponseType.SUCCESS);
            return result;
        }
        result.setSuccess(false);
        result.setResponseType(ResponseType.DANGER);
        result.setMessage("Bu Organization topilmadi !!!.");
        return result;
    }

    public ApiResponseModel checkEmployee(String firstName) {
        ApiResponseModel responseModel = new ApiResponseModel();
        organizationRepository.findOrganizationsByName(firstName).ifPresentOrElse(
                user -> {
                    responseModel.setSuccess(true);
                    responseModel.setObject(user);
                    responseModel.setResponseType(ResponseType.SUCCESS);
                },
                () -> {
                    responseModel.setResponseType(ResponseType.INFO);
                    responseModel.setMessage("Ushbu Employee mavjud emas..!!");
                    responseModel.setSuccess(false);
                }
        );
        return responseModel;
    }


    public ApiResponse save(OrganizationDto organizationRequest) {
        ApiResponse apiResponse = new ApiResponse();
        Organization organization = new Organization();
        if (organizationRequest.getParent() != null) {
            Optional<Organization> orgzationOptional = organizationRepository.findById(organizationRequest.getParent());
            orgzationOptional.ifPresent(organization::setParent);
        }
        organization.setName(organizationRequest.getName());
        organization.setRegion(regionRepository.getReferenceById(organizationRequest.getRegion_id()));
        Organization organizationSaved = organizationRepository.save(organization);
        apiResponse.setResponseType(ResponseType.SUCCESS);
        apiResponse.setMessage("Organization Saqlandi...");
        apiResponse.setSuccess(true);
        apiResponse.setObject(organizationSaved);
        return apiResponse;
    }

    public ApiResponse edit(Long id, OrganizationDto organizationDto) {
        ApiResponse apiResponse = new ApiResponse();
        Organization organization = organizationRepository.getReferenceById(id);
        organization.setName(organizationDto.getName());
        organization.setRegion(regionRepository.findById(organizationDto.getRegion_id()).get());
        if (organizationDto.getParent() != null) {
            Optional<Organization> orgzationOptional = organizationRepository.findById(organizationDto.getParent());
            orgzationOptional.ifPresent(organization::setParent);
        }
        Organization organizationS = organizationRepository.save(organization);
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Bu Organization o'zgartirildi..!!!");
        apiResponse.setResponseType(ResponseType.INFO);
        apiResponse.setObject(organizationS);
        return apiResponse;

    }

    public ApiResponse delete(Long id) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (!organizationOptional.isPresent()) {
            apiResponse.setSuccess(false);
            apiResponse.setResponseType(ResponseType.DANGER);
            apiResponse.setMessage("Organization topilmadi..!!!");
            return apiResponse;
        }
        Organization organizationDeleted = organizationRepository.findById(id).get();
        organizationDeleted.setDeleted(true);
        organizationRepository.save(organizationDeleted);
        apiResponse.setSuccess(true);
        apiResponse.setResponseType(ResponseType.INFO);
        apiResponse.setMessage(" Bu Organization o'chirildi...!!!");

        return apiResponse;
    }
}
