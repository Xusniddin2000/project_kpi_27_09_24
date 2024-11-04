package com.example.project_kpi_27_09_24.repository.address;

import com.example.project_kpi_27_09_24.entity.address.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuarterRepository   extends JpaRepository<Quarter,Long> {

    boolean  existsByName(String name);
    Optional<Quarter>  findByName(String name );

    boolean   existsByQuarterId(Long quarterId);
    Optional<Quarter>  findByQuarterId(Long quarterId);

}
