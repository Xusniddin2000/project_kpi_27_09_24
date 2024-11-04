package com.example.project_kpi_27_09_24.repository.address;

import com.example.project_kpi_27_09_24.entity.address.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository   extends JpaRepository<Region,Long> {
   boolean  existsByName(String name);
   Optional<Region>   findByName(String name);

   boolean  existsByRegionId(Long regionId);
   Optional<Region>   findByRegionId(Long regionId);



}
