package com.example.project_kpi_27_09_24.controller;


import com.example.project_kpi_27_09_24.anotation.CurrentUser;
import com.example.project_kpi_27_09_24.dto.UserRequest;
import com.example.project_kpi_27_09_24.entity.auth.User;
import com.example.project_kpi_27_09_24.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {

     @Autowired
     private UserService   userService;

    @GetMapping(value = {"/me", "/me/"})
    public ResponseEntity<?> userMe(@CurrentUser User user) {

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getOneUser(@PathVariable("id")  Long   id){

        return  ResponseEntity.status(HttpStatus.FOUND).body(userService.getByUserId(id));
    }

    @GetMapping(value = {"/all"})
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(userService.all());
    }

    @PostMapping(value = {"/add"})
    public  ResponseEntity<?>  add(@Valid @RequestBody UserRequest userRequest){

        return   ResponseEntity.ok(userService.save(userRequest));
    }

    @PutMapping(value = {"/edit/{id}"})
    public ResponseEntity<?>  edit(@PathVariable("id") Long id,@RequestBody UserRequest userRequest){
        return   ResponseEntity.ok(userService.edit(id, userRequest));
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public ResponseEntity<?>  delete(@PathVariable("id") Long id ){

        return  ResponseEntity.ok(userService.delete(id));
    }
}
