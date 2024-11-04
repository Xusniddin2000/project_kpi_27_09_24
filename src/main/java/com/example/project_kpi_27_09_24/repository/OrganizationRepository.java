package com.example.project_kpi_27_09_24.repository;

import com.example.project_kpi_27_09_24.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository    extends JpaRepository<Organization,Long> {

    Optional<Organization>   findOrganizationsByName(String name);
}
