package com.example.project_kpi_27_09_24.repository;


import com.example.project_kpi_27_09_24.entity.auth.Permission;
import com.example.project_kpi_27_09_24.entity.enums.PermissionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository   extends JpaRepository<Permission,Long> {

    boolean  existsByPermissionName(PermissionName permissionName);

    Optional<Permission>  getByPermissionName(PermissionName  permissionName);


}
