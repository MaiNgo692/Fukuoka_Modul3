package com.ra.service;

import com.ra.entity.Employee;
import com.ra.service.IService;

import java.util.List;
import java.util.Scanner;

public interface IEmployeeService extends IService<Employee,String> {
    List<Employee> findByIdOrName(String key);
}
