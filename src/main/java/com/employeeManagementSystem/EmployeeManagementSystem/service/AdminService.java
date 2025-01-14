package com.employeeManagementSystem.EmployeeManagementSystem.service;

import com.employeeManagementSystem.EmployeeManagementSystem.model.Admin;

public interface AdminService {
    void saveAdmin(Admin admin);
    Admin findAdminByUsername(String username);
}
