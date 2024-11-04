package com.example.project_kpi_27_09_24.entity.idBerish;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;



@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public  abstract class IdGenerate {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private  Long id;


     private   boolean isDeleted =false;


     @CreationTimestamp
     @Column(updatable = false)
     @JsonIgnore
     private Timestamp  createdAt;

     @LastModifiedDate
     @JsonIgnore
     private Timestamp   updatedAt;

     @CreatedBy
     @Column(updatable = false)
     @JsonIgnore
     private Long   createdBy;

     @LastModifiedBy
     @JsonIgnore
     private Long   updatedBy;


    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

}
