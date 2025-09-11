package com.employeeManagementSystem.EmployeeManagementSystem.controller;

import com.employeeManagementSystem.EmployeeManagementSystem.model.Admin;
import com.employeeManagementSystem.EmployeeManagementSystem.service.AdminService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public  String loginForm(){
        return "login";
    }
    @GetMapping("/registerAdmin")
    public String showRegistrationForm(Model model){
        return "admin_register";
    }
    @PostMapping("/registerAdmin")
    public String registerAdmin(String username,String password,String confirmPassword, Model model){
        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "Password do not match");
            return  "admin_register";
        }
        String encodedPassword=passwordEncoder.encode(password);

        Admin admin= new Admin(username,encodedPassword);

        adminService.saveAdmin(admin);

        System.out.println("Admin Registered: "+ username);

        return "redirect:/adminLogin?registered=true";

    }


}
