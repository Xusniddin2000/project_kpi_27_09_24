package com.example.project_kpi_27_09_24.entity.address;

import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Quarter  extends IdGenerate {

    @Column(unique = true)
    private  Long quarterId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private District district;

    public Quarter(String name,District district) {
        this.name = name;
        this.district=district;
    }

    public Quarter(Long
                           quarterId, String name, District district) {
        this.quarterId = quarterId;
        this.name = name;
        this.district = district;
    }
}
