package com.example.project_kpi_27_09_24.repository.address;


import com.example.project_kpi_27_09_24.entity.address.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Long> {
    boolean existsByName(String name);

    Optional<Country> findByName(String name);
}
