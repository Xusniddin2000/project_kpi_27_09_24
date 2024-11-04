package com.example.project_kpi_27_09_24.entity;


import com.example.project_kpi_27_09_24.entity.address.Region;
import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "organization")
//@SQLDelete(sql = "UPDATE organization  SET is_deleted=true  WHERE id=?")
//@Where(clause = "is_deleted='false'")
public class Organization extends IdGenerate {


    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent",nullable = true)
    @JsonIgnore
    private  Organization  parent;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent")
    @JsonIgnore
    private List<Organization>  subOrganizations;

    public String getParent() {
        return parent.name;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", region=" + region +
                ", children=" + subOrganizations.toString() +
                '}';
    }
}
