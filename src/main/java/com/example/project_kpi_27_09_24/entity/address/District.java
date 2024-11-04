package com.example.project_kpi_27_09_24.entity.address;

import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class District extends IdGenerate {

    @Column(unique = true)
    private Long districtId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Region region;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "district")
    @JsonIgnore
    private List<Quarter> quarters;

    public District(Long districtId, String name, Region region) {
        this.districtId = districtId;
        this.name = name;
        this.region = region;
    }

    public District(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    public Long getIntId() {
        return districtId;
    }

    public void setIntId(Long dist_id) {
        this.districtId = dist_id;
    }
}
