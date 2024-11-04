package com.example.project_kpi_27_09_24.dto.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {


     @NotBlank(message = "Refresh   Token  kiritilishi kerak !!!!")
     private  String  refreshToken;
}
