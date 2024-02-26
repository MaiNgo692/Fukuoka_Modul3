package com.ra.manager;

import com.ra.entity.Account;
import com.ra.entity.Employee;
import com.ra.model.ConstStatus;
import com.ra.repository.IRepository;
import com.ra.repository.impl.Repository;
import com.ra.service.impl.AccountServiceImpl;
import com.ra.util.Console;
import com.ra.util.FontColor;
import java.util.List;

public class AccountManager implements Manager{
    AccountServiceImpl accountService = new AccountServiceImpl();
    @Override
    public void run() {
        boolean isExit = true;
        do{
            System.out.println("************ACCOUNT MANAGEMENT*************");
            System.out.println("1. Danh sách tài khoản");
            System.out.println("2. Tạo tài khoản mới");
            System.out.println("3. Cập nhật trạng thái tài khoản");
            System.out.println("4. Tìm kiếm tài khoản");
            System.out.println("5. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            try {
                int select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        showAllAccount();
                        break;
                    case 2:
                        addNewAccount();
                        break;
                    case 3:
                        updateAccountStatus();
                        break;
                    case 4:
                        findByUserNameOrName();
                        break;
                    case 5:
                        System.out.println(FontColor.success("Thoát"));
                        isExit = false;
                        break;
                    default:
                        System.out.println(FontColor.warning("Hãy nhập vào số từ 1 đến 5!"));
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Hãy nhập vào 1 số!"));
            }
        }while (isExit);
    }
    private void showAllAccount(){
        System.out.println("Danh sách tài khoản:");
        printTitle();
        List<Account> accounts = accountService.findAll();
        accounts.forEach(Account::displayData);
        printFooter();
    }
    private void addNewAccount(){
        System.out.println("Nhập thông tin account:");
        System.out.print(FontColor.info("Nhập tên tài khoản: "));
        String userName = Console.sc.nextLine();
        System.out.print(FontColor.info("Nhập mật khẩu: "));
        String password = Console.sc.nextLine();
        boolean permission = inputPermission();
        String empId= inputEmpId();
        boolean accStatus = true;
        Account newAccount = new Account(userName,password,permission,empId,accStatus);
        accountService.add(newAccount);
    }
    private void updateAccountStatus(){

        System.out.print(FontColor.info("Nhập mã tài khoản cần cập nhật: "));
        int  accId = Integer.parseInt(Console.sc.nextLine());
        Account updateAccount = accountService.findId(accId) ;
        if(updateAccount != null){
            updateAccount.displayData();
            System.out.println("Chọn trạng thái cần update:");
            System.out.println("1. Hoạt động");
            System.out.println("2. Không hoạt động");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            int select ;
            boolean isExit =true;
            do{
                try {
                    select = Integer.parseInt(Console.sc.nextLine());
                    if(select ==1){
                        updateAccount.setAccStatus(ConstStatus.AccountStt.ACTIVE);
                        isExit= false;
                    }else if(select==2){
                        updateAccount.setAccStatus(ConstStatus.AccountStt.BLOCK);
                        isExit=false;
                    }else System.out.println(FontColor.warning("Hãy nhập 1 hoặc 2!"));
                }catch (NumberFormatException ex){
                    System.out.println(FontColor.err(ex.getMessage()));
                }
            }while (isExit);
            accountService.edit(updateAccount);
        }else {
            System.out.println(FontColor.warning("Không tìm thấy mã tài khoản " + accId));
        }
    }
    private void findByUserNameOrName(){
        System.out.print(FontColor.info("Nhập thông tin cần tìm:"));
        String key = Console.sc.nextLine();
        List<Account> result = accountService.findByUserNameOrName(key);
        if(!result.isEmpty()){
            printTitle();
            result.forEach(Account::displayData);
            printFooter();
        }else {
            System.out.println(FontColor.warning("Không tìm thấy Account"));
        }
    }
    private boolean inputPermission(){
        System.out.println("Chọn loại tài khoản");
        System.out.println("1. Tài khoản Admin");
        System.out.println("2. Tài khoản User");
        boolean permission=true;
        boolean isExit;
        do{
            isExit = true;
            System.out.print(FontColor.info("Nhập lựa chọn: "));
            try{
                int selectRole = Integer.parseInt(Console.sc.nextLine());
                if (selectRole ==1){
                    permission = false;
                    isExit= false;
                }else if(selectRole == 2){
                    isExit = false;
                }else {
                    System.out.println(FontColor.warning("Nhập vào 1 hoặc 2!"));
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Nhập vào 1 hoặc 2!"));
            }
        }while (isExit);
        return permission;
    }
    public String inputEmpId(){
        IRepository<Employee,String> employeeRepository = new Repository<>();
        String empId;
        do{
            System.out.print(FontColor.info("Nhập mã nhân viên: "));
            empId = Console.sc.nextLine();
            List<Employee> employees = employeeRepository.findAll(Employee.class);
            for (Employee em:employees){
                if(em.getEmpId().equals(empId))
                    return empId;
            }
            System.out.println(FontColor.warning("Không tồn tại mã nhân viên "+ empId));
        }while (true);
    }
    public void printTitle() {
        System.out.printf("|%s|%s|%s|%s|%s|%s|\n", FontColor.centerString(12,"Mã tài khoản"), FontColor.centerString(30,"Tên user"),
                FontColor.centerString(30,"Mật khẩu"),FontColor.centerString(20,"Loại tài khoản") ,FontColor.centerString(20,"Mã nhân viên"),
                FontColor.centerString(17,"Trạng thái"));
    }
    private void printFooter(){
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
