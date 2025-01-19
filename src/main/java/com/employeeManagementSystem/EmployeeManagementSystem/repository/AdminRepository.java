package com.employeeManagementSystem.EmployeeManagementSystem.repository;


import com.employeeManagementSystem.EmployeeManagementSystem.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    boolean existsByUsername(String username);

    Admin getAdminByUsername(String username);
}
