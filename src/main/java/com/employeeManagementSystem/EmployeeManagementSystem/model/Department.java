package com.employeeManagementSystem.EmployeeManagementSystem.model;

public enum Department {

    HLS("Healthcare and Life Sciences"),
    BFSI("Banking, Finance Services and Insurance"),
    ADMS("Application Development and Maintenance Services"),
    CIS("Cloud Infrastructure Services");

    private final String description;

    Department(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
