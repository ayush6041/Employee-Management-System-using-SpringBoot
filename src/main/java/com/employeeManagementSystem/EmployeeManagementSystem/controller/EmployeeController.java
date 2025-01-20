package com.employeeManagementSystem.EmployeeManagementSystem.controller;


import com.employeeManagementSystem.EmployeeManagementSystem.model.Employee;
import com.employeeManagementSystem.EmployeeManagementSystem.service.EmployeeService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@RolesAllowed("ROLE_ADMIN")
public class EmployeeController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    // Only accessible by users with role "ADMIN"

    @Autowired
    private EmployeeService employeeService;


    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public  String viewHomePage(Model model){
        model.addAttribute("listEmployee",employeeService.getAllEmployee());
        return "index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model)
    {
        //Create model attribute to bind form data
        Employee employee= new Employee();
        model.addAttribute("employee",employee);
        return "new_employee";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("updateEmployee")
    public String updateEmployee(@ModelAttribute("employee") Employee employee){
        if( employee.getPassword() == null || employee.getPassword().isEmpty()){
            Employee employee1=employeeService.getEmployeeById(employee.getId());
            employee.setPassword(employee1.getPassword());
        }
        else{
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model){
        Employee employee= employeeService.getEmployeeById(id);
        employee.setPassword("");
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }
}
