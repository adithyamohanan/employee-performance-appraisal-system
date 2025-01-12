package com.example.performance_appraisal.service;

import com.example.performance_appraisal.model.Employee;
import com.example.performance_appraisal.model.RatingCategory;
import com.example.performance_appraisal.repository.EmployeeRepository;
import com.example.performance_appraisal.repository.RatingCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceAppraisalService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RatingCategoryRepository ratingCategoryRepository;

    public Map<String, Object> calculatePerformanceAppraisal() {
        Map<String, Object> response = new HashMap<>();

        List<Employee> employees = employeeRepository.findAll();
        List<RatingCategory> ratingCategories = ratingCategoryRepository.findAll();

        int totalEmployees = employees.size();
        int categoryACount = 0, categoryBCount = 0, categoryCCount = 0, categoryDCount = 0, categoryECount = 0;

        for (Employee employee : employees) {
            String rating = employee.getRating();
            switch (rating) {
                case "A": categoryACount++; break;
                case "B": categoryBCount++; break;
                case "C": categoryCCount++; break;
                case "D": categoryDCount++; break;
                case "E": categoryECount++; break;
            }
        }

        double categoryAPercentage = (double) categoryACount / totalEmployees * 100;
        double categoryBPercentage = (double) categoryBCount / totalEmployees * 100;
        double categoryCPercentage = (double) categoryCCount / totalEmployees * 100;
        double categoryDPercentage = (double) categoryDCount / totalEmployees * 100;
        double categoryEPercentage = (double) categoryECount / totalEmployees * 100;

        double standardCategoryAPercentage = ratingCategories.stream().filter(c -> c.getRating().equals("A")).findFirst().get().getStandardPercentage();
        double standardCategoryBPercentage = ratingCategories.stream().filter(c -> c.getRating().equals("B")).findFirst().get().getStandardPercentage();
        double standardCategoryCPercentage = ratingCategories.stream().filter(c -> c.getRating().equals("C")).findFirst().get().getStandardPercentage();
        double standardCategoryDPercentage = ratingCategories.stream().filter(c -> c.getRating().equals("D")).findFirst().get().getStandardPercentage();
        double standardCategoryEPercentage = ratingCategories.stream().filter(c -> c.getRating().equals("E")).findFirst().get().getStandardPercentage();

        
        DecimalFormat df = new DecimalFormat("#.0");

        String categoryAPercentageFormatted = df.format(categoryAPercentage);
        String categoryBPercentageFormatted = df.format(categoryBPercentage);
        String categoryCPercentageFormatted = df.format(categoryCPercentage);
        String categoryDPercentageFormatted = df.format(categoryDPercentage);
        String categoryEPercentageFormatted = df.format(categoryEPercentage);

        double categoryADeviation = categoryAPercentage - standardCategoryAPercentage;
        double categoryBDeviation = categoryBPercentage - standardCategoryBPercentage;
        double categoryCDeviation = categoryCPercentage - standardCategoryCPercentage;
        double categoryDDeviation = categoryDPercentage - standardCategoryDPercentage;
        double categoryEDeviation = categoryEPercentage - standardCategoryEPercentage;

        
        String categoryADeviationFormatted = df.format(categoryADeviation);
        String categoryBDeviationFormatted = df.format(categoryBDeviation);
        String categoryCDeviationFormatted = df.format(categoryCDeviation);
        String categoryDDeviationFormatted = df.format(categoryDDeviation);
        String categoryEDeviationFormatted = df.format(categoryEDeviation);

        // Putting formatted values into the response
        response.put("Category A", Map.of(
                "actualPercentage", categoryAPercentageFormatted + "%",
                "deviation", categoryADeviationFormatted
        ));
        response.put("Category B", Map.of(
                "actualPercentage", categoryBPercentageFormatted + "%",
                "deviation", categoryBDeviationFormatted
        ));
        response.put("Category C", Map.of(
                "actualPercentage", categoryCPercentageFormatted + "%",
                "deviation", categoryCDeviationFormatted
        ));
        response.put("Category D", Map.of(
                "actualPercentage", categoryDPercentageFormatted + "%",
                "deviation", categoryDDeviationFormatted
        ));
        response.put("Category E", Map.of(
                "actualPercentage", categoryEPercentageFormatted + "%",
                "deviation", categoryEDeviationFormatted
        ));

        // Suggest employees whose ratings need revision based on deviation
        List<Employee> employeesToRevise = new ArrayList<>();
        for (Employee employee : employees) {
            String rating = employee.getRating();
            switch (rating) {
                case "A":
                    if (categoryADeviation < 0) employeesToRevise.add(employee);
                    break;
                case "B":
                    if (categoryBDeviation < 0) employeesToRevise.add(employee);
                    break;
                case "C":
                    if (categoryCDeviation < 0) employeesToRevise.add(employee);
                    break;
                case "D":
                    if (categoryDDeviation < 0) employeesToRevise.add(employee);
                    break;
                case "E":
                    if (categoryEDeviation < 0) employeesToRevise.add(employee);
                    break;
            }
        }

        // Add employees to response if any need revision
        if (!employeesToRevise.isEmpty()) {
            List<Map<String, String>> employeesToReviseList = new ArrayList<>();
            for (Employee employee : employeesToRevise) {
                Map<String, String> employeeDetails = new HashMap<>();
                employeeDetails.put("Employee ID", String.valueOf(employee.getId()));
                employeeDetails.put("Name", employee.getName());
                employeeDetails.put("Current Rating", employee.getRating());
                employeesToReviseList.add(employeeDetails);
            }
            response.put("Employees to Revise", employeesToReviseList);
        } else {
            response.put("Employees to Revise", "No employee ratings need revision.");
        }

        return response;
    }

    // New method to add employee
    public void addEmployee(Employee employee) {
        // Save the employee data to the repository
        employeeRepository.save(employee);
    }

    public boolean updateEmployee(int id, Employee employee) {
        // Check if employee exists
        if (employeeRepository.existsById(id)) {
            // Set the employee's ID to ensure it's not overwritten
            employee.setId(id);
            employeeRepository.save(employee);  // Update the employee
            return true;
        }
        return false;  // Employee not found
    }

    public boolean deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
