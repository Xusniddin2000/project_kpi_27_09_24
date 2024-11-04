package com.example.project_kpi_27_09_24.entity.address;

import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Region extends IdGenerate {


    @Column(unique = true)
    private Long regionId;


    @Column(unique = true)
    private String name;



    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    private List<District> districts;

    public Region(String name, Country country) {
        this.name = name;
        this.country = country;
    }
    public Region(Long regionId, String name, Country country) {
        this.regionId = regionId;
        this.name = name;
        this.country = country;
    }

    public Long getIntId() {
        return this.regionId;
    }

    public void setIntId(Long reg_id) {
        this.regionId = reg_id;
    }


}
