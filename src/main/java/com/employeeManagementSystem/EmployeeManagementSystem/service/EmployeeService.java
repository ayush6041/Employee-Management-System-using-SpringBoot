package com.employeeManagementSystem.EmployeeManagementSystem.service;

import com.employeeManagementSystem.EmployeeManagementSystem.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployee();

    Employee getEmployeeById(long id);

    void saveEmployee(Employee employee);

    void deleteEmployeeById(long id);

    Employee getEmployeeByEmail(String userName);
}
