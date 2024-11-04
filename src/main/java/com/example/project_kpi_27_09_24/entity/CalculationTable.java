package com.example.project_kpi_27_09_24.entity;


import com.example.project_kpi_27_09_24.entity.enums.CalculationType;
import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity(name="calculation_table")
@SQLDelete(sql = "UPDATE  calculation_table SET  is_deleted=true  WHERE  id=?")
@Where(clause = "is_deleted='false'")
public class CalculationTable  extends IdGenerate {

     private  Double  amount;
     private Double  rate;

     private LocalDate date;

     @Enumerated(EnumType.STRING)
     private CalculationType calculationType;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "employee_id",nullable = false)
     private  Employee  employee;

     @ManyToOne
     @JoinColumn(name = "organization_id")
     private  Organization organization;
}
