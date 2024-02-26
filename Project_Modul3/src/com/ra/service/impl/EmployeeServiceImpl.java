package com.ra.service.impl;

import com.ra.entity.Employee;
import com.ra.repository.impl.EmployeeRepository;
import com.ra.service.IEmployeeService;
import java.util.List;

public class EmployeeServiceImpl implements IEmployeeService {
    EmployeeRepository employeeRepository = new EmployeeRepository();
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll(Employee.class);
    }

    @Override
    public Employee findId(String id) {
        return employeeRepository.findId(id,Employee.class);
    }

    @Override
    public Employee add(Employee entity) {
        return employeeRepository.add(entity);
    }

    @Override
    public Employee edit(Employee entity) {
        return employeeRepository.edit(entity);
    }

    @Override
    public List<Employee> findByIdOrName(String key) {
        return employeeRepository.findByIdOrName(key);
    }
}
