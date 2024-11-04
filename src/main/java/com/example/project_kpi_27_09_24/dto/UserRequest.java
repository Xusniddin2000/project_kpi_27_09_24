package com.example.project_kpi_27_09_24.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

         @NotEmpty(message ="User ismini kiriting: ")
         private  String  firstName;

         @NotEmpty(message = "User familyasini kiriting :")
         private  String  lastName;


         @NotEmpty(message = "Enter phone number: ")
         private  String  phoneNumber;

         @NotEmpty(message = "User Logini kiriting: ")
         private  String  userName;

         @NotEmpty(message = "Email kiriting va  tasdiqlang :")
          private  String   email;

         @NotEmpty(message = "User passwordni kiriting:")
         private  String  password;




}
