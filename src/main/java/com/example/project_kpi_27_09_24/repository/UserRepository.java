package com.example.project_kpi_27_09_24.repository;


import com.example.project_kpi_27_09_24.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User>   findByUserName(String userName);

    Optional<User>   findById(Long  id);



    boolean   existsByUserName(String  username);
}
