package com.learngraphql.springgraphql.employeeproject.controller;

import com.learngraphql.springgraphql.employeeproject.models.Employee;
import com.learngraphql.springgraphql.employeeproject.service.EmployeeEventPublisher;
import com.learngraphql.springgraphql.employeeproject.service.EmployeeService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeEventPublisher employeeEventPublisher;

    public EmployeeController(EmployeeService employeeService,
                              EmployeeEventPublisher employeeEventPublisher) {
        this.employeeService = employeeService;
        this.employeeEventPublisher = employeeEventPublisher;
    }

    @QueryMapping
    public List<Employee> employees() {
        return employeeService.getAllEmployees();
    }

    @QueryMapping
    public Employee employeeById(@Argument String id) {
        return employeeService.getEmployeeById(id);
    }

    @MutationMapping
    public Employee createEmployee(@Argument String id,
                                   @Argument String name,
                                   @Argument String email,
                                   @Argument String department) {
        return employeeService.createEmployee(id, name, email, department);
    }

    @MutationMapping
    public Employee updateEmployeeById(@Argument String id,
                                       @Argument String name,
                                       @Argument String email,
                                       @Argument String department) {
        return employeeService.updateEmployeeById(id, name, email, department);
    }

    @MutationMapping
    public String deleteEmployee(@Argument String id) {
        return employeeService.deleteEmployee(id);
    }

    @SubscriptionMapping
    public Flux<Employee> employeeCreated() {
        return employeeEventPublisher.employeeCreatedStream();
    }

    @SubscriptionMapping
    public Flux<Employee> employeeUpdated() {
        return employeeEventPublisher.employeeUpdatedStream();
    }

    @SubscriptionMapping
    public Flux<Employee> employeeDeleted() {
        return employeeEventPublisher.employeeDeletedStream();
    }
}