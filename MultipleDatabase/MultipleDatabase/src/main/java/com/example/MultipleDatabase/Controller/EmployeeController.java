package com.example.MultipleDatabase.Controller;

import com.example.MultipleDatabase.db2.model.Employee;
import com.example.MultipleDatabase.db2.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/db2/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> all() {
        return employeeRepository.findAll();
    }

    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
}
