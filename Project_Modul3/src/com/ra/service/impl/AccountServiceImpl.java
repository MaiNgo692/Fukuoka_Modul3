package com.ra.service.impl;

import com.ra.entity.Account;
import com.ra.entity.Employee;
import com.ra.repository.IRepository;
import com.ra.repository.impl.AccountRepository;
import com.ra.repository.impl.Repository;
import com.ra.service.IAccountService;
import java.util.List;


public class AccountServiceImpl implements IAccountService<Account> {
    AccountRepository accountRepository = new AccountRepository();
    IRepository<Employee,String> employeeRepository = new Repository<>();

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll(Account.class);
    }

    @Override
    public Account findId(Integer id) {
        return accountRepository.findId(id,Account.class);
    }

    @Override
    public Account add(Account entity) {
        return accountRepository.add(entity);
    }

    @Override
    public Account edit(Account entity) {
        return accountRepository.edit(entity);
    }

    @Override
    public Account login(String user, String pass) {
        List<Account> accountList = accountRepository.findAll(Account.class);
        for (Account ac: accountList) {
            if(ac.getUserName().equals(user)&ac.getPassword().equals(pass))
                return ac;
        }
        return null;
    }

    @Override
    public List<Account> findByUserNameOrName(String key) {
        return accountRepository.findByUserNameOrName(key);
    }

    @Override
    public Account findByEmpId(String empId) {
        return accountRepository.findByEmpId(empId);
    }

}
