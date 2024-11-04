package com.example.project_kpi_27_09_24.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultModel   extends   Result {
    private  Object  object;
    private  Map<String,String>   data;
    private List<Object>  objectList;

}
