package com.employeeManagementSystem.EmployeeManagementSystem.service;

import com.employeeManagementSystem.EmployeeManagementSystem.model.Admin;
import com.employeeManagementSystem.EmployeeManagementSystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void saveAdmin(Admin admin){
        if (adminRepository.existsByUsername(admin.getUsername())){
            throw  new RequestRejectedException("Admin Already Exists");
        }
        this.adminRepository.save(admin);
    }
    @Override
    public Admin findAdminByUsername(String Username){
        return adminRepository.getAdminByUsername(Username);
    }
}
