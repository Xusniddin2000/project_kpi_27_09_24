package com.example.project_kpi_27_09_24.repository;

import com.example.project_kpi_27_09_24.entity.CalculationTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CalculationRepository   extends JpaRepository<CalculationTable,Long> {

    @Query(value = "select employees.pinfl, sum(calculation_table.rate) from calculation_table left join " +
            " employees ON employees.id = calculation_table.employee_id " +
            " where extract(year from calculation_table.date)=?1 " +
            " and  extract(month from calculation_table.date)=?2 " +
            " and calculation_table.rate >=?3 group by employees.pinfl ", nativeQuery = true)
    List<Object[]> findEmployeesWithHigherRate(Integer year, Integer month, Double rate);

    @Query(value = "select employees.pinfl, sum(amount), count(employees.organization_id) " +
            " from calculation_table LEFT join employees ON employees.id = calculation_table.employee_id " +
            "inner join organization ON organization.id = calculation_table.organization_id " +
            "where extract(year from calculation_table.date)=?1 and extract(month from calculation_table.date)=?2 " +
            " group by  employees.pinfl ", nativeQuery = true)
    List<Object[]> findEmployeesPinflAndAmount(Integer year, Integer month);



    @Query(value = "select employees.pinfl,employees.first_name,employees.last_name,  " +
            " organization.name, organization.id , " +
            " avg(calculation_table.amount)   from calculation_table left join " +
            " employees ON employees.id = calculation_table.employee_id " +
            " inner  join organization ON organization.id = calculation_table.organization_id " +
            " where extract(year from calculation_table.date)=?1 and extract(month from calculation_table.date)=?2  " +
            " group by organization.name, employees.pinfl, employees.first_name, employees.last_name,organization.id " +
            " having organization.id in(?3) ", nativeQuery = true)
    List<Object[]> findEmployeesAverageAmount(Integer year, Integer month, List<Long> orgIds);
}