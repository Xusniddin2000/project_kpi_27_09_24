package com.example.project_kpi_27_09_24.entity.address;

import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Country extends IdGenerate {

     @Column(unique = true)
     private String name;

     @OneToMany(fetch = FetchType.LAZY,mappedBy = "country")
     private List<Region> regions;

     public Country(String s) {
          this.name=s;
     }
}
