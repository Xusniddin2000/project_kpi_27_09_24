package com.example.project_kpi_27_09_24.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

     @Bean
     public AuditorAware<Long>  auditorAware() {

          return   new SecurityAware();
     }
}
