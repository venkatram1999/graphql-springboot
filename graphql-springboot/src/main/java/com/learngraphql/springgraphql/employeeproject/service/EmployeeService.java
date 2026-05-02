package com.learngraphql.springgraphql.employeeproject.service;

import com.learngraphql.springgraphql.employeeproject.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final List<Employee> employees = new ArrayList<>();
    private final EmployeeEventPublisher employeeEventPublisher;

    public EmployeeService(EmployeeEventPublisher employeeEventPublisher) {
        this.employeeEventPublisher = employeeEventPublisher;

        employees.add(new Employee("1", "Ram", "ram@gmail.com", "Engineering"));
        employees.add(new Employee("2", "Sita", "sita@gmail.com", "HR"));
        employees.add(new Employee("3", "Arjun", "arjun@gmail.com", "Finance"));

        log.info("Initial employees loaded. Count={}", employees.size());
    }

    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees. Count={}", employees.size());
        return employees;
    }

    public Employee getEmployeeById(String id) {
        log.info("Fetching employee by id={}", id);
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Employee createEmployee(String id, String name, String email, String department) {
        boolean exists = employees.stream().anyMatch(e -> e.getId().equals(id));

        if (exists) {
            log.warn("Employee already exists. id={}", id);
            return getEmployeeById(id);
        }

        Employee employee = new Employee(id, name, email, department);
        employees.add(employee);
        employeeEventPublisher.publishCreated(employee);

        log.info("Employee created. id={}, name={}, total={}", id, name, employees.size());
        return employee;
    }

    public Employee updateEmployeeById(String id, String name, String email, String department) {
        Employee employee = getEmployeeById(id);

        if (employee == null) {
            log.warn("Update failed - employee not found. id={}", id);
            return null;
        }

        if (name != null && !name.isBlank()) {
            employee.setName(name);
        }
        if (email != null && !email.isBlank()) {
            employee.setEmail(email);
        }
        if (department != null && !department.isBlank()) {
            employee.setDepartment(department);
        }

        employeeEventPublisher.publishUpdated(employee);
        log.info("Employee updated. id={}, name={}", id, employee.getName());

        return employee;
    }

    public String deleteEmployee(String id) {
        Employee employee = getEmployeeById(id);

        if (employee == null) {
            log.warn("Delete failed - employee not found. id={}", id);
            return "Employee with id " + id + " not found!";
        }

        employees.remove(employee);
        employeeEventPublisher.publishDeleted(employee);

        log.info("Employee deleted. id={}, name={}, remaining={}", id, employee.getName(), employees.size());
        return "Employee " + employee.getName() + " deleted successfully!";
    }
}