package com.example.project_kpi_27_09_24.repository.address;

import com.example.project_kpi_27_09_24.entity.address.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository   extends JpaRepository<District,Long> {

    boolean existsByName(String name);
    Optional<District> findByName(String name);

    boolean  existsByDistrictId(Long districtId);

    Optional<District> findByDistrictId(Long districtId);

}
