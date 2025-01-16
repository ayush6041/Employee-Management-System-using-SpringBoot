package com.employeeManagementSystem.EmployeeManagementSystem.service;


import com.employeeManagementSystem.EmployeeManagementSystem.model.Admin;
import com.employeeManagementSystem.EmployeeManagementSystem.model.Employee;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminService adminService;
    private final EmployeeService employeeService;

    public CustomUserDetailsService(AdminService adminService, EmployeeService employeeService) {
        this.adminService = adminService;
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin=adminService.findAdminByUsername(username);
        if (admin != null){
            return User.builder()
                    .username(admin.getUsername())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        Employee employee = employeeService.getEmployeeByEmail(username);
        if(employee != null){
            return  User.builder()
                    .username(employee.getEmail())
                    .username(employee.getPassword())
                    .roles("EMPLOYEE")
                    .build();
        }
        throw new UsernameNotFoundException("Username not found with username: " + username);

    }
}
