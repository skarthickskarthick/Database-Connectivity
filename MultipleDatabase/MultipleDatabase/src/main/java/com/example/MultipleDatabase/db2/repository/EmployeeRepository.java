package com.example.MultipleDatabase.db2.repository;

import com.example.MultipleDatabase.db2.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}

