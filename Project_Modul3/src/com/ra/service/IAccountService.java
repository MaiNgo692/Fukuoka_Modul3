package com.ra.service;

import com.ra.entity.Account;

import java.util.List;
import java.util.Scanner;

public interface IAccountService<Account> extends IService<Account,Integer>{
    Account login(String user, String pass);
    List<Account> findByUserNameOrName(String key);
    Account findByEmpId(String empId);
}
