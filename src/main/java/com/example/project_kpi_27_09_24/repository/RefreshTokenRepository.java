package com.example.project_kpi_27_09_24.repository;


import com.example.project_kpi_27_09_24.entity.auth.RefreshToken;
import com.example.project_kpi_27_09_24.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByRefreshToken(String token);

    int deleteByUser(User user);

    Optional<RefreshToken> findByUser(User user);
}