package com.example.project_kpi_27_09_24.dto.api;


import com.example.project_kpi_27_09_24.entity.enums.ResponseType;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private   boolean    success;
    private   String     message;
    private ResponseType responseType;
    private   Object     object;


 public   ApiResponse (boolean success){
       this.success=success;

 }
 public  ApiResponse(boolean success,String message,ResponseType responseType){
              this.success=success;
              this.message=message;
              this.responseType=responseType;
  }

    public ApiResponse(boolean success, String message, Object object) {
            this.success = success;
            this.message = message;
            this.object = object;
    }

    public   ApiResponse (boolean success,String  message){
             this.success=success;
             this.message=message;

    }
}
