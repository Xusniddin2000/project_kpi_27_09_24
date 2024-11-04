package com.example.project_kpi_27_09_24.entity;


import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employees")
//@SQLDelete(sql = "UPDATE employees  SET is_deleted=true  WHERE id=?")
//@Where(clause = "is_deleted='false'")
public class Employee extends IdGenerate {

    private  String firstName;
    private  String lastName;
    @Column(nullable = false,unique = true)
    private  String pinfl;
    private LocalDate hireDate;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private  Organization organization;
}
