package com.example.performance_appraisal.controller;

import com.example.performance_appraisal.model.Employee;
import com.example.performance_appraisal.service.PerformanceAppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/performance-appraisal")
public class PerformaceController {

    @Autowired
    private PerformanceAppraisalService performanceAppraisalService;

    @GetMapping("/calculate")
    public Map<String, Object> calculatePerformaceAppraisal() {
        return performanceAppraisalService.calculatePerformanceAppraisal();
    }

    @PostMapping("/add-employee")
    public String addEmployee(@RequestBody Employee employee) {
        // You can call your service method to save the employee data in the database
        performanceAppraisalService.addEmployee(employee);

        return "Employee added successfully!";
    }

    @PutMapping("/update-employee/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        // Call the service method to update the employee by ID
        boolean isUpdated = performanceAppraisalService.updateEmployee(id, employee);
        if (isUpdated) {
            return "Employee updated successfully!";
        } else {
            return "Employee with ID " + id + " not found!";
        }
    }

    @DeleteMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable int id) {
        // Call the service method to delete the employee by ID
        boolean isDeleted = performanceAppraisalService.deleteEmployee(id);
        if (isDeleted) {
            return "Employee with ID " + id + " deleted successfully!";
        } else {
            return "Employee with ID " + id + " not found!";
        }
    }

}
