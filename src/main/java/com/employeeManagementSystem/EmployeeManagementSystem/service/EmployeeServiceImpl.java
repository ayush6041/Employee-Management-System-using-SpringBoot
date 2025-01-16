package com.employeeManagementSystem.EmployeeManagementSystem.service;


import com.employeeManagementSystem.EmployeeManagementSystem.model.Employee;
import com.employeeManagementSystem.EmployeeManagementSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements  EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional <Employee> optional = employeeRepository.findById(id);
        Employee employee;
        if(optional.isPresent()){
            employee=optional.get();
        }
        else{
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(long id) {
        this.employeeRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        Optional<Employee> employee= employeeRepository.findEmployeeByEmail(email);

        if(employee.isEmpty()){
            throw new RuntimeException("Employee not found for id :: "+ email);
        }
        return employee.get();
    }
}
