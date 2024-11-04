package com.example.project_kpi_27_09_24.repository;


import com.example.project_kpi_27_09_24.entity.auth.Role;
import com.example.project_kpi_27_09_24.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository   extends JpaRepository<Role,Long> {

    boolean    existsByRoleName(RoleName roleName);

    Set<Role>   findByRoleName(RoleName  roleName);
}
