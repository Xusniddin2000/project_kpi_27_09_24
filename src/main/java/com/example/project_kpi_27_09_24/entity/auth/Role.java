package com.example.project_kpi_27_09_24.entity.auth;



import com.example.project_kpi_27_09_24.entity.enums.RoleLevel;
import com.example.project_kpi_27_09_24.entity.enums.RoleName;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Where(clause = "is_deleted='false' ")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   Long  id;

    private  String  description;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Enumerated(EnumType.STRING)
    private RoleLevel roleLevel=RoleLevel.LOW;

   @ManyToMany(fetch = FetchType.LAZY)
   @JoinTable(name = "roles_permissions",joinColumns ={@JoinColumn(name="role_id")}
               , inverseJoinColumns = {@JoinColumn(name = "permission_id")})
   private Set<Permission>   permissions=new HashSet<>();


    public  Role(String description, RoleName  roleName, Permission permission){
              this.description=description;
              this.roleName=roleName;
              this.permissions.add(permission);
    }

    public  Role  ( String  description,RoleName  roleName,Set<Permission> permissions){
               this.description=description;
               this.roleName=roleName;
               this.permissions=permissions;
    }

    public  void   setPermission(Permission permission){   this.permissions.add(permission);   }

    public  void   removePermission(Permission permission){ this.permissions.remove(permission);  }


}
