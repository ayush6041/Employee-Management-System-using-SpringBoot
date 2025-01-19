package com.employeeManagementSystem.EmployeeManagementSystem.controller;


import com.employeeManagementSystem.EmployeeManagementSystem.model.Employee;
import com.employeeManagementSystem.EmployeeManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class MyEmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Method to get the currently logged-in employee from the authentication object
    private Employee getAuthenticatedEmployee(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            String username= authentication.getName();// get the username (email)
            return employeeService.getEmployeeByEmail(username);// Assumiing Employee  has username (email) field
        }
         return null; //Return null if authentication is not available
    }

    //Show the employee profile page
    @GetMapping("/dashboard")
    public String viewProfile(Model model){
        Employee employee= getAuthenticatedEmployee();
        if( employee == null){
            model.addAttribute("error", " Employee not found or not authenticated");
            return "error";
        }
        model.addAttribute("employee", employee);
        return "employee_profile";
    }

    // Show the form to update the logged-in employee's data
    @GetMapping("/updateProfile")
    public String showUpdateProfileForm(Model model){
        Employee employee= getAuthenticatedEmployee();
        if(employee == null){
            model.addAttribute("error", " Employee not found or not authenticated");
            return "error";
        }
        model.addAttribute("employee", employee);
        return "self_update_employee";
    }

    //Show the registration form for employee
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("employee", new Employee());
         return "employee_register";
    }

    // Handle  the registration form submission for employee
    @PostMapping("/register")
    public  String registerEmployee(@ModelAttribute("employee") Employee employee, Model model){
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        employeeService.saveEmployee(employee);

        return "redirect:/employee/login?registered=true";
    }

    //save the updated employee data
    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,Model model){


        Employee authenticatedEmployee=getAuthenticatedEmployee();

        if(authenticatedEmployee == null){
            model.addAttribute("error", "Employee not authenticated");
            return "error";
        }

        //Validate input fields

        if(bindingResult.hasErrors()){
            model.addAttribute("error", "Validation errors occurred");
            return "self_update_employee";
        }
        else {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }

        //Update only allowed fields

        authenticatedEmployee.setEmail(employee.getEmail());
        authenticatedEmployee.setFirst_name(employee.getFirst_name());
        authenticatedEmployee.setLast_name((employee.getLast_name()));
        authenticatedEmployee.setPassword(employee.getPassword());



        employeeService.saveEmployee(authenticatedEmployee);
        return "redirect:/employee/dashboard";
    }


}
