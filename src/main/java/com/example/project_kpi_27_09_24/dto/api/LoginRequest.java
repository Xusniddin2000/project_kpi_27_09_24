package com.example.project_kpi_27_09_24.dto.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

     private  String  userName;
     private  String  password;
}
