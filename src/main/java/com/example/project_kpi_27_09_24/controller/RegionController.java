package com.example.project_kpi_27_09_24.controller;


import com.example.project_kpi_27_09_24.dto.address.RegionDto;
import com.example.project_kpi_27_09_24.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/regions")
public class RegionController {


    @Autowired
    private RegionService regionService;

    @GetMapping(value={"/all"})
    public ResponseEntity<?> getAllRegions() {
        return ResponseEntity.ok(regionService.getAllRegions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegionById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(regionService.getRegionById(Long.parseLong(id)));
    }

    @PostMapping(value = {"/add"})
    public ResponseEntity<?> createRegion(@Valid @RequestBody RegionDto regionDto) {
        return ResponseEntity.ok(regionService.saveRegion(regionDto));
    }

    @PostMapping(value = {"/edit/{id}"})
    public ResponseEntity<?>  edit(@PathVariable("id")  String id,@RequestBody  RegionDto regionDto){
        return   ResponseEntity.ok(regionService.edit(Long.parseLong(id),regionDto));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>  deleteRegion(@PathVariable("id") String  id) {
        return ResponseEntity.ok(regionService.delete(Long.parseLong(id)));
    }
}
