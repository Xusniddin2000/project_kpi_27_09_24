package com.example.project_kpi_27_09_24.entity.auth;


import com.example.project_kpi_27_09_24.entity.enums.EntityStatus;
import com.example.project_kpi_27_09_24.entity.idBerish.IdGenerate;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET is_deleted=true WHERE id=?")
@Where(clause = "is_deleted='false'")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User extends IdGenerate implements UserDetails {
    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, name = "password")
    private String password;


    @Enumerated(EnumType.STRING)
    private EntityStatus entityStatus = EntityStatus.ACTIVE;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id")}
            , inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();


    @javax.persistence.Transient
    private Set<Permission> permissions = new HashSet<>();


    private Set<Permission> getPermission(Permission permission) {

        for (Role role : roles) {
            if (role.getPermissions().size() != 0) {
                this.permissions.addAll(role.getPermissions());
            }

        }
        return permissions;
    }


    //   SECURITY   uchun  GrantedAuthority dan   authorities olish uchun

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return   getAuthoritiesFromString(getRoleAndPermissionName());
    }

    @Override
    public String getPassword() {
        return   this.password;
    }

    @Override
    public String getUsername() {
        return   this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return    isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return   isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return   isEnabled;
    }




    private List<String> getRoleAndPermissionName(){
        List<String>   privilleges=new ArrayList<>();
        for  (Role  role:  roles ){
            privilleges.add(role.getRoleName().name());
            if(role.getPermissions().size()!=0){
                for (Permission  permission: role.getPermissions()){
                    privilleges.add(permission.getPermissionName().name());
                }
            }
        }
        return    privilleges;
    }


    private  List<GrantedAuthority>  getAuthoritiesFromString(List<String>  privilleges){
        List<GrantedAuthority>   authorities=new    ArrayList<>();
        for  ( String  privillege: privilleges){

            authorities.add( new SimpleGrantedAuthority(privillege));
        }

        return   authorities;
    }
}
