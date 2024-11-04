package com.example.project_kpi_27_09_24.service;


import com.example.project_kpi_27_09_24.dto.CalculationDto;
import com.example.project_kpi_27_09_24.dto.api.ApiResponse;
import com.example.project_kpi_27_09_24.entity.CalculationTable;
import com.example.project_kpi_27_09_24.entity.Organization;
import com.example.project_kpi_27_09_24.entity.enums.CalculationType;
import com.example.project_kpi_27_09_24.entity.enums.ResponseType;
import com.example.project_kpi_27_09_24.repository.CalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalculationService {
    @Autowired
    private CalculationRepository  calculationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private  OrganizationService  organizationService;


    public List<Object[]>  getAllByMonthsAndRate(Integer year,Integer month,Double rate){
       // System.out.println(year);
       // System.out.println(month);
       // System.out.println(rate);
        List<Object[]> data=calculationRepository.findEmployeesWithHigherRate(year,month,rate);
        return data;
    }

    public List<Object[]>  getAllByPinflAndAmount(Integer year,Integer month){
         System.out.println(year);
         System.out.println(month);
        List<Object[]> data=calculationRepository.findEmployeesPinflAndAmount(year,month);
        return data;
    }

    public List<Object[]>  getAllAverageAmount(Integer year,Integer month,Long orgId){
        System.out.println(year);
        System.out.println(month);
        Organization organization=organizationService.getById(orgId);
        List<Long> idList=new ArrayList<>();
        idList.add(orgId);
        if (!organization.getSubOrganizations().isEmpty()){
            for (Organization org :  organization.getSubOrganizations()) {
                idList.add(org.getId());
            }
        }

        List<Object[]> data=calculationRepository.findEmployeesAverageAmount(year,month,idList);
        return data;
    }

    public List<CalculationTable>   getAll(){return   calculationRepository.findAll();}

    public ApiResponse getById(Long id){
           ApiResponse  apiResponse=new ApiResponse();
        Optional<CalculationTable>   calculationTable=calculationRepository.findById(id);
        if(calculationTable.isPresent()){
               apiResponse.setSuccess(true);
               apiResponse.setObject(calculationTable.get());
               apiResponse.setResponseType(ResponseType.SUCCESS);
               return   apiResponse;
        }
          apiResponse.setSuccess(false);
          apiResponse.setResponseType(ResponseType.DANGER);
          apiResponse.setMessage("Bu Calculation topilmadi..!!!");
          return   apiResponse;
    }

    public   ApiResponse   save(CalculationDto calculationDto){
        ApiResponse   apiResponse=new  ApiResponse();
        CalculationTable calculationTable=new CalculationTable();

        calculationTable.setEmployee(employeeService.getById(calculationDto.getEmployee_id()));
        calculationTable.setCalculationType(CalculationType.valueOf(calculationDto.getCalculationType().toUpperCase()));
        calculationTable.setOrganization(organizationService.getById(calculationDto.getOrganization_id()));
        calculationTable.setAmount(calculationDto.getAmount());
        calculationTable.setDate(LocalDate.parse(calculationDto.getDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        calculationTable.setRate(calculationDto.getRate());
        CalculationTable  calculationSave=calculationRepository.save(calculationTable);
        apiResponse.setResponseType(ResponseType.SUCCESS);
        apiResponse.setMessage("CalculationTable  Saqlandi...");
        apiResponse.setSuccess(true);
        apiResponse.setObject(calculationSave);
        return apiResponse;
    }

    public  ApiResponse  edit(Long  id,CalculationDto  calculationDto){
          ApiResponse   apiResponse=new ApiResponse();
          Optional<CalculationTable>   calculationTableOptional=calculationRepository.findById(id);
          if(calculationTableOptional.isPresent()){
              CalculationTable  calculationTable=calculationTableOptional.get();
              calculationTable.setCalculationType(CalculationType.valueOf(calculationDto.getCalculationType().toUpperCase()));
              calculationTable.setEmployee(employeeService.getById(calculationDto.getEmployee_id()));
              calculationTable.setOrganization(organizationService.getById(calculationDto.getOrganization_id()));
              calculationTable.setAmount(calculationDto.getAmount());
              calculationTable.setDate(LocalDate.parse(calculationDto.getDate(),DateTimeFormatter.ofPattern("yyyy.MM.dd")));
              calculationTable.setRate(calculationDto.getRate());
              CalculationTable   calculationTableEdit=calculationRepository.save(calculationTable);
              apiResponse.setSuccess(true);
              apiResponse.setResponseType(ResponseType.SUCCESS);
              apiResponse.setMessage("Bu CalculationTable O'zgartirildi..!!>>");
              apiResponse.setObject(calculationTableEdit);
              return    apiResponse;

          }
          apiResponse.setSuccess(false);
          apiResponse.setMessage("Bu Calculation Table topilmadi..!!");
          apiResponse.setResponseType(ResponseType.DANGER);
          return    apiResponse;
    }


    public  ApiResponse  delete(Long  id){
        ApiResponse   apiResponse=new ApiResponse();
        Optional<CalculationTable>   calculationTableOptional=calculationRepository.findById(id);
        if(calculationTableOptional.isPresent()){
            calculationRepository.deleteById(id);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Bu Calculation Table o'chirildi....!!");
            apiResponse.setResponseType(ResponseType.DANGER);
            return    apiResponse;

        }
        apiResponse.setSuccess(false);
        apiResponse.setMessage("Bu Calculation Table topilmadi..!!");
        apiResponse.setResponseType(ResponseType.DANGER);
        return    apiResponse;
    }
}
