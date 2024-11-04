package com.example.project_kpi_27_09_24.entity.auth;


import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="refreshtoken")
public class RefreshToken extends IdGenerate implements Serializable {

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private String refreshToken;


    @Column(nullable = false)
    private Instant expiryDate;
}
