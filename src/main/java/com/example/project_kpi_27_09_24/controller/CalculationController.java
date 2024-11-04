package com.example.project_kpi_27_09_24.controller;


import com.example.project_kpi_27_09_24.dto.CalculationDto;
import com.example.project_kpi_27_09_24.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user/calculation")
public class CalculationController {


    @Autowired
    private CalculationService   calculationService;


    @GetMapping(value = {"/overwork"})
    public ResponseEntity<?>  getOverWork(@RequestParam("month") String month,
                                          @RequestParam("rate") String rate){
        String[] numbers=month.split("\\.");
        int year=Integer.parseInt(numbers[0]);
        int monthD=Integer.parseInt(numbers[1]);
        double rates=Double.parseDouble(rate);
        List<Object[]> data=calculationService.getAllByMonthsAndRate(year,monthD,rates);
        return ResponseEntity.ok().body(data);

    }


    @GetMapping(value = {"/amount"})
    public ResponseEntity<?>  getPinflAmount(@RequestParam("month") String month){
        String[] numbers=month.split("\\.");
        int year=Integer.parseInt(numbers[0]);
        int monthD=Integer.parseInt(numbers[1]);
        List<Object[]> data=calculationService.getAllByPinflAndAmount(year,monthD);
        return ResponseEntity.ok().body(data);

    }

    @GetMapping(value = {"/average"})
    public ResponseEntity<?>  getSalaryAmountAverage(@RequestParam("month") String month,
                                                     @RequestParam("organization_id") String organization){
        String[] numbers=month.split("\\.");
        int year=Integer.parseInt(numbers[0]);
        int monthD=Integer.parseInt(numbers[1]);
        List<Object[]> data=calculationService.getAllAverageAmount(year,monthD, Long.valueOf(organization));
        return ResponseEntity.ok().body(data);

    }
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getOrganizationById(@PathVariable("id") String id){
        return   ResponseEntity.status(HttpStatus.FOUND).body(calculationService.getById(Long.parseLong(id)));
    }

    @GetMapping(value = {"/all"})
    public  ResponseEntity<?>  getAll(){
        return   ResponseEntity.ok(calculationService.getAll());
    }

    @PostMapping(value = {"/add"})
    public ResponseEntity<?>  create(@Valid @RequestBody CalculationDto calculationDto){
        return   ResponseEntity.ok(calculationService.save(calculationDto));
    }

    @PutMapping(value={"/edit/{id}"})
    public  ResponseEntity<?> edit(@PathVariable("id") String id,@RequestBody CalculationDto calculationDto){
        return   ResponseEntity.ok(calculationService.edit(Long.parseLong(id),calculationDto));
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public  ResponseEntity<?>  delete(@PathVariable("id") String id){
        return  ResponseEntity.ok(calculationService.delete(Long.parseLong(id)));
    }

}
