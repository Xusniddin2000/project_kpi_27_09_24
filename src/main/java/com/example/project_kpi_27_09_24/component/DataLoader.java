package com.example.project_kpi_27_09_24.component;


import com.example.project_kpi_27_09_24.entity.auth.Permission;
import com.example.project_kpi_27_09_24.entity.auth.Role;
import com.example.project_kpi_27_09_24.entity.auth.User;
import com.example.project_kpi_27_09_24.entity.enums.PermissionName;
import com.example.project_kpi_27_09_24.entity.enums.RoleName;
import com.example.project_kpi_27_09_24.repository.PermissionRepository;
import com.example.project_kpi_27_09_24.repository.RoleRepository;
import com.example.project_kpi_27_09_24.repository.UserRepository;
import com.example.project_kpi_27_09_24.service.CalculationService;
import com.example.project_kpi_27_09_24.service.OrganizationService;
import com.example.project_kpi_27_09_24.utils.json.AddressModel;
import com.example.project_kpi_27_09_24.utils.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${spring.sql.init.mode}")
    private String initMode;
    @Autowired
    private CalculationService calculationService;
    @Autowired
    private OrganizationService organizationService;

    @Override
    public void run(String... args) throws Exception {
        String date="2024.06";
        double rate=1;
        String[] numbers=date.split("\\.");
        int year=Integer.parseInt(numbers[0]);
        int monthD=Integer.parseInt(numbers[1]);
       // List<Object[]> data=calculationService.getAllByMonthsAndRate(year,monthD,rate);
       // List<Object[]> data=calculationService.getAllByPinflAndAmount(year,monthD);
        //List<Object[]> data=calculationService.getAllAverageAmount(year,monthD,2L);
       // data.forEach(objects -> System.out.println(objects[0]+"-"+objects[1]+" - "+objects[2]));

        AddressModel addressModel = JsonData.getModelJson("regions.json");
        System.out.println(addressModel.getQuarters().size());
        if (initMode.equals("always")) {

            if (!permissionRepository.existsByPermissionName(PermissionName.USER_CREATE)) {
                Permission permissionP1;
                permissionP1 = permissionRepository.save(
                        new Permission("User yaratish", PermissionName.USER_CREATE));
            }


            if (!permissionRepository.existsByPermissionName(PermissionName.USER_EDIT)) {
                Permission permissionP2 = permissionRepository.save(
                        new Permission("User o'zgartirish", PermissionName.USER_EDIT));
            }


            if (!permissionRepository.existsByPermissionName(PermissionName.USER_VIEW)) {
                Permission permissionP3 = permissionRepository.save(
                        new Permission("Userlarni ko'rish", PermissionName.USER_VIEW));
            }


            if (!permissionRepository.existsByPermissionName(PermissionName.USER_DELETE)) {
                Permission permissionP4 = permissionRepository.save(
                        new Permission("User Delete", PermissionName.USER_DELETE));
            }

            if (!roleRepository.existsByRoleName(RoleName.ROLE_ADMIN)) {
                roleRepository.save(new Role("Adminstrator", RoleName.ROLE_ADMIN,
                        new HashSet<Permission>(Arrays.asList(
                                permissionRepository.getByPermissionName(PermissionName.USER_CREATE).get(),
                                permissionRepository.getByPermissionName(PermissionName.USER_VIEW).get(),
                                permissionRepository.getByPermissionName(PermissionName.USER_DELETE).get(),
                                permissionRepository.getByPermissionName(PermissionName.USER_EDIT).get()
                        ))
                ));
            }

            if (!roleRepository.existsByRoleName(RoleName.ROLE_MODERATOR)) {
                roleRepository.save(new Role("Moderator", RoleName.ROLE_MODERATOR,
                        new HashSet<Permission>(Arrays.asList(
                                permissionRepository.getByPermissionName(PermissionName.USER_CREATE).get(),
                                permissionRepository.getByPermissionName(PermissionName.USER_EDIT).get(),
                                permissionRepository.getByPermissionName(PermissionName.USER_VIEW).get()
                        ))
                ));

            }

            if (!roleRepository.existsByRoleName(RoleName.ROLE_USER)) {
                roleRepository.save(new Role("Foydalanuvchi", RoleName.ROLE_USER,
                        new HashSet<Permission>(Arrays.asList(
                                permissionRepository.getByPermissionName(PermissionName.USER_CREATE).get(),
                                permissionRepository.getByPermissionName(PermissionName.USER_EDIT).get()
                        ))
                ));
            }


        }

        if (initMode.equals("always")) {

            User user = new User();
            user.setFirstName("Xusniddin");
            user.setLastName("Xushbaqtov");
            user.setUserName("admin");
            user.setEmail("xusniddin@gmail.com");
            user.setPhoneNumber("+998888083220");
            user.setPassword(passwordEncoder.encode("12345"));

            user.setRoles(roleRepository.findByRoleName(RoleName.ROLE_ADMIN));
            User userSaved = userRepository.save(user);
            System.out.println(userSaved.toString());

        }
        if (initMode.equals("always")) {

            User user = new User();
            user.setFirstName("Ahmad");
            user.setLastName("Ahmadov");
            user.setUserName("user");
            user.setEmail("user@gmail.com");
            user.setPhoneNumber("+9988835264427");
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(roleRepository.findByRoleName(RoleName.ROLE_USER));

            User userSaved = userRepository.save(user);
            System.out.println(userSaved.toString());
        }
        if (initMode.equals("always")) {

            User user = new User();
            user.setFirstName("Ahmad");
            user.setLastName("Ahmadov");
            user.setUserName("moderator");
            user.setEmail("user@gmail.com");
            user.setPhoneNumber("+9988835264427");
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(roleRepository.findByRoleName(RoleName.ROLE_MODERATOR));

            User userSaved = userRepository.save(user);
            System.out.println(userSaved.toString());
        }


    }
}
