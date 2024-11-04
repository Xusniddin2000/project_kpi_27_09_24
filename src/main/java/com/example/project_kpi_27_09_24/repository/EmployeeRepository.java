package com.example.project_kpi_27_09_24.repository;

import com.example.project_kpi_27_09_24.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository   extends JpaRepository<Employee,Long> {

    Optional<Employee>   findEmployeeByFirstName(String firstName);
    Optional<Employee>   findEmployeeByPinflLike(String pinfl);

}
