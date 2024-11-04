package com.example.project_kpi_27_09_24.service;


import com.example.project_kpi_27_09_24.dto.EmployeeDto;
import com.example.project_kpi_27_09_24.dto.api.ApiResponse;
import com.example.project_kpi_27_09_24.dto.api.ApiResponseModel;
import com.example.project_kpi_27_09_24.entity.Employee;
import com.example.project_kpi_27_09_24.entity.enums.ResponseType;
import com.example.project_kpi_27_09_24.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository   employeeRepository;

    @Autowired
    private OrganizationService  organizationService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");




    public List<Employee>  getAll(){
        return   employeeRepository.findAll();
    }

    public Employee getById(Long id){
        Optional<Employee>  employee=employeeRepository.findById(id);
        if(employee.isPresent()){
            return   employee.get();
        }
                return   null;
    }


    public ApiResponse  getEmployee(Long id){
        ApiResponse  result=new ApiResponse();
        Optional<Employee>  employee=employeeRepository.findById(id);
        if(employee.isPresent()){
            result.setSuccess(true);
            result.setObject(employee.get());
            result.setResponseType(ResponseType.SUCCESS);
            return   result;
        }
           result.setSuccess(false);
           result.setResponseType(ResponseType.DANGER);
           result.setMessage("Bu Employee topilmadi !!!.");
           return   result;
    }

    public ApiResponseModel  checkEmployee(String firstName){
           ApiResponseModel  responseModel=new  ApiResponseModel();
           employeeRepository.findEmployeeByFirstName(firstName).ifPresentOrElse(
                user -> {
                       responseModel.setSuccess(true);
                       responseModel.setObject(user);
                       responseModel.setResponseType(ResponseType.SUCCESS);
                },
               () ->{
                       responseModel.setResponseType(ResponseType.INFO);
                       responseModel.setMessage("Ushbu Employee mavjud emas..!!");
                       responseModel.setSuccess(false);
               }
           );
           return   responseModel;
    }

    public   ApiResponse   save(EmployeeDto employeeRequest){
        ApiResponse   apiResponse=new  ApiResponse();
        Optional<Employee>   employeeOptional=employeeRepository.findEmployeeByPinflLike(employeeRequest.getPinfl());
        if(employeeOptional.isPresent()){
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Bu Employee PNFL avval kiritilgan..!!!");
                apiResponse.setResponseType(ResponseType.INFO);
            return   apiResponse;
        }
           Employee  employee=new   Employee();
           employee.setFirstName(employeeRequest.getFirstName());
           employee.setLastName(employeeRequest.getLastName());
           employee.setPinfl(employeeRequest.getPinfl());
           employee.setOrganization(organizationService.getById(employeeRequest.getOrganization_id()));
           if(employeeRequest.getHireDate() !=null && !employeeRequest.getHireDate().isEmpty()){
                  employee.setHireDate(LocalDate.parse(employeeRequest.getHireDate(),formatter));
           }
         Employee  employeeSaved= employeeRepository.save(employee);
               apiResponse.setResponseType(ResponseType.SUCCESS);
               apiResponse.setMessage("Employee Saqlandi...");
               apiResponse.setSuccess(true);
               apiResponse.setObject(employeeSaved);
               return apiResponse;
    }


    public   ApiResponse  getByEmployeeId(Long id){
          ApiResponse   apiResponse=new  ApiResponse();
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Employee  topildi....!!!! ");
            apiResponse.setObject(employeeOptional.get());
              return  apiResponse;
        }
         apiResponse.setSuccess(false);
         apiResponse.setMessage("Bu Idli Employee  topilmadi...!!!!");
             return   apiResponse;

    }

    public ApiResponse edit(Long  id, EmployeeDto employeeRequest) {
        ApiResponse apiResponse =new ApiResponse();
        Employee employee=employeeRepository.getReferenceById(id);
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        if(employeeRequest.getHireDate() !=null && !employeeRequest.getHireDate().isEmpty()){
            employee.setHireDate(LocalDate.parse(employeeRequest.getHireDate(),formatter));
        }
        employee.setOrganization(organizationService.getById(employeeRequest.getOrganization_id()));
        employee.setPinfl(employeeRequest.getPinfl());
        Employee employeeSave= employeeRepository.save(employee);
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Bu  Employee O'zgartirildi....!!!!");
        apiResponse.setResponseType(ResponseType.INFO);
        apiResponse.setObject(employeeSave);
        return apiResponse;
    }

    public  ApiResponse  delete(Long id){
        ApiResponse   apiResponse=new  ApiResponse();
        Optional<Employee>  employeeOptional=employeeRepository.findById(id);
        if(!employeeOptional.isPresent()){
                  apiResponse.setSuccess(false);
                  apiResponse.setResponseType(ResponseType.DANGER);
                  apiResponse.setMessage("Bu Idli Employee topilmadi!!!");
            return   apiResponse;
        }
         Employee   employeeDeleted=employeeRepository.findById(id).get();
            employeeDeleted.setDeleted(true);
            employeeRepository.save(employeeDeleted);
            apiResponse.setSuccess(true);
            apiResponse.setResponseType(ResponseType.INFO);
            apiResponse.setMessage("  Bu Employee  o'chirildi!!");

            return   apiResponse;

    }
}
