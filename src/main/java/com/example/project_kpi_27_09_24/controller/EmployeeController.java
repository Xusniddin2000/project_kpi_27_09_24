package com.example.project_kpi_27_09_24.controller;


import com.example.project_kpi_27_09_24.dto.EmployeeDto;
import com.example.project_kpi_27_09_24.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService   employeeService;

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?>   getEmployeeById(@PathVariable("id") String id){
        return   ResponseEntity.status(HttpStatus.FOUND).body(employeeService.getEmployee(Long.parseLong(id)));
    }

    @GetMapping(value = {"/all"})
    public   ResponseEntity<?>  getAllEmployee(){
        return   ResponseEntity.ok(employeeService.getAll());
    }

    @PostMapping(value = {"/add"})
    public  ResponseEntity<?>  add(@Valid @RequestBody EmployeeDto employeeDto){
         return   ResponseEntity.ok(employeeService.save(employeeDto));
    }

    @PutMapping(value = {"/edit/{id}"})
    public  ResponseEntity<?>  edit(@PathVariable("id")  String id, @RequestBody EmployeeDto  employeeDto){
      return    ResponseEntity.ok(employeeService.edit(Long.parseLong(id),employeeDto));
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public ResponseEntity<?>  delete(@PathVariable("id") String id){
          return   ResponseEntity.ok(employeeService.delete(Long.parseLong(id)));
    }
}
