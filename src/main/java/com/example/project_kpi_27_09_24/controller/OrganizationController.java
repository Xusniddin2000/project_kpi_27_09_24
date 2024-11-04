package com.example.project_kpi_27_09_24.controller;


import com.example.project_kpi_27_09_24.dto.OrganizationDto;
import com.example.project_kpi_27_09_24.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/organization")
public class OrganizationController {


    @Autowired
    private OrganizationService  organizationService;


    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?>  getOrganizationById(@PathVariable("id") String id){
           return   ResponseEntity.status(HttpStatus.FOUND).body(organizationService.getOrganization(Long.parseLong(id)));
    }

    @GetMapping(value = {"/all"})
    public  ResponseEntity<?>  getAllOrganization(){
        return   ResponseEntity.ok(organizationService.getAll());
    }

    @PostMapping(value = {"/add"})
    public ResponseEntity<?>  createOrganization(@Valid @RequestBody OrganizationDto  organizationDto){
        return   ResponseEntity.ok(organizationService.save(organizationDto));
    }

    @PutMapping(value={"/edit/{id}"})
    public  ResponseEntity<?> edit(@PathVariable("id") String id,@RequestBody OrganizationDto organizationDto){
        return   ResponseEntity.ok(organizationService.edit(Long.parseLong(id),organizationDto));
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public  ResponseEntity<?>  deleteOrganization(@PathVariable("id") String id){
          return  ResponseEntity.ok(organizationService.delete(Long.parseLong(id)));
    }
}
