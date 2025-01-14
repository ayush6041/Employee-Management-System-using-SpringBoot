package com.employeeManagementSystem.EmployeeManagementSystem.model;


import jakarta.persistence.*;
import jakarta.transaction.UserTransaction;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name="first_name")
    private String first_name;

    @Column(nullable = false, name = "last_name")
    private String last_name;

    @Column(nullable = false,name = "DOB")
    private String dateOfBirth;

    @Column(nullable = false, name="Gender")
    private String Gender;

    @Column(nullable = false,name = "Phone no")
    private  long Phone_no;

    @Column(nullable = false, name = "Designation")
    private String Designation;

    @Column(nullable = false, name = "Address")
    private String Address;

    @Column(nullable = false, name = "Joining_date")
    private String joining_Date;

    @Column(nullable = false ,name = "Salary")
    private double salary;

    @Column(name="email", nullable = false)
    private  String email;

    @Column(nullable = false, name = "department")
    private String department;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name="performance")
    private  String performance;

    // getters and setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public long getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(long phone_no) {
        Phone_no = phone_no;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getJoining_Date() {
        return joining_Date;
    }

    public void setJoining_Date(String joining_Date) {
        this.joining_Date = joining_Date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }
}
