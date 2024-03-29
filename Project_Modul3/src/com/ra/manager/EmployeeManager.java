package com.ra.manager;

import com.ra.entity.Account;
import com.ra.entity.Employee;
import com.ra.service.IAccountService;
import com.ra.service.impl.AccountServiceImpl;
import com.ra.service.impl.EmployeeServiceImpl;
import com.ra.util.Console;
import com.ra.util.FontColor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeManager extends Manager<Employee> {
    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    @Override
    public void run() {
        boolean isExit = true;
        do{
            System.out.println("************EMPLOYEE MANAGEMENT*************");
            System.out.println("1. Danh sách nhân viên");
            System.out.println("2. Thêm mới nhân viên");
            System.out.println("3. Cập nhật thông tin nhân viên");
            System.out.println("4. Cập nhật trạng thái nhân viên");
            System.out.println("5. Tìm kiếm nhân viên");
            System.out.println("6. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            try{
                int select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        showAllEmployee();
                        break;
                    case 2:
                        addNewEmployee();
                        break;
                    case 3:
                        editEmployee();
                        break;
                    case 4:
                        updateEmployeeStatus();
                        break;
                    case 5:
                        findByIdOrName();
                        break;
                    case 6:
                        System.out.println(FontColor.success("Thoát"));
                        isExit = false;
                        break;
                    default:
                        System.out.println(FontColor.warning("Hãy nhập vào số từ 1 đến 6!"));
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Hãy nhập vào 1 số!"));
            }
        }while (isExit);
    }

    private void showAllEmployee(){
        currentPage = 1;
        System.out.println("Danh sách sản phẩm:");
        List<Employee> employeeList = employeeService.findAll();
        showPageCurrent(employeeList);
    }
    @Override
    public void showPageCurrent(List<Employee> listData) {
        List<Employee> showData=showDataPage(listData);
        printTitle();
        showData.forEach(Employee::displayData);
        printFooter();
        showPage(listData);
    }
    private void addNewEmployee(){
        System.out.println("Nhập thông tin nhân viên:");
        System.out.print(FontColor.info("Nhập mã nhân viên: "));
        String empId = Console.sc.nextLine();
        System.out.print(FontColor.info("Nhập tên nhân viên: "));
        String empName = Console.sc.nextLine();
        Date birthOfDate = setBirthOfDate();
        System.out.print(FontColor.info("Nhập email: "));
        String email = Console.sc.nextLine();
        System.out.print(FontColor.info("Nhập Số điện thoại: "));
        String phone = Console.sc.nextLine();
        System.out.print(FontColor.info("Nhập địa chỉ: "));
        String address = Console.sc.nextLine();
        System.out.println(FontColor.info("Nhập trạng thái: "));
        int empStatus = setEmpStatus();
        Employee newEmp = new Employee(empId,empName,birthOfDate,email,phone,address,empStatus);
        employeeService.add(newEmp);
    }
    private void editEmployee(){
        IAccountService<Account> accountService = new AccountServiceImpl();
        System.out.print(FontColor.info("Nhập mã nhân viên cần update:"));
        String empId = Console.sc.nextLine();
        Employee updateEmp = employeeService.findId(empId);
        if(updateEmp!= null){
            boolean isExit = true;
            do{
                System.out.println("Nhập trường muốn cập nhật:");
                System.out.println("1. Tên nhân viên");
                System.out.println("2. Ngày sinh");
                System.out.println("3. Email");
                System.out.println("4. Số điện thoại");
                System.out.println("5. Địa chỉ");
                System.out.println("6. Trạng thái");
                System.out.println("7. Thoát");
                System.out.print(FontColor.info("Nhập lựa chọn: "));
                int select;
                try{
                    select = Integer.parseInt(Console.sc.nextLine());
                    switch (select){
                        case 1:
                            System.out.println("Tên cũ: "+ updateEmp.getEmpName());
                            System.out.print(FontColor.info("Nhập Tên mới: "));
                            String empName = Console.sc.nextLine();
                            updateEmp.setEmpName(empName);
                            break;
                        case 2:
                            System.out.println("Ngày sinh cũ: "+ updateEmp.getBirthOfDate());
                            updateEmp.setBirthOfDate(setBirthOfDate());
                            break;
                        case 3:
                            System.out.println("Email cũ: " + updateEmp.getEmail());
                            System.out.print(FontColor.info("Nhập email mới: "));
                            String email = Console.sc.nextLine();
                            updateEmp.setEmail(email);
                            break;
                        case 4:
                            System.out.println("Sô điện thoại cũ: " + updateEmp.getPhone());
                            System.out.print(FontColor.info("Nhập Số điện thoại mới: "));
                            String phone = Console.sc.nextLine();
                            updateEmp.setPhone(phone);
                            break;
                        case 5:
                            System.out.println("Địa chỉ cũ: " + updateEmp.getAddress());
                            System.out.print(FontColor.info("Nhập địa chỉ mới: "));
                            String address = Console.sc.nextLine();
                            updateEmp.setAddress(address);
                            break;
                        case 6:
                            System.out.println("Trạng thái cũ: " + (updateEmp.getEmpStatus() == 0 ?"Hoạt động":updateEmp.getEmpStatus() ==1?"Nghỉ chế độ":"Nghỉ việc"));
                            updateEmp.setEmpStatus(setEmpStatus());
                            break;
                        case 7:
                            employeeService.edit(updateEmp);
                            isExit=false;
                            break;
                        default:
                            System.out.println(FontColor.warning("Hãy nhập từ 1 đến 7!"));
                            break;
                    }
                }catch (NumberFormatException ex){
                    System.out.println(FontColor.err(ex.getMessage()));
                }
            }while (isExit);
                Account account = accountService.findByEmpId(updateEmp.getEmpId());
                if(account!=null){
                    account.setAccStatus(updateEmp.getEmpStatus() != 1 && updateEmp.getEmpStatus() != 2);
                    accountService.edit(account);
                }
        }else {
            System.out.println(FontColor.warning("Không tìm thấy mã nhân viên " + empId));
        }
    }
    private void updateEmployeeStatus(){
        IAccountService<Account> accountService = new AccountServiceImpl();
        System.out.print(FontColor.info("Nhập mã nhân viên cần update:"));
        String empId = Console.sc.nextLine();
        Employee updateEmp = employeeService.findId(empId);
        if(updateEmp!= null){
            updateEmp.setEmpStatus(setEmpStatus());
            if(updateEmp.getEmpStatus() ==1 || updateEmp.getEmpStatus()==2){
                Account account = accountService.findByEmpId(updateEmp.getEmpId());
                account.setAccStatus(false);
                accountService.edit(account);
                employeeService.edit(updateEmp);
            }
        }else {
            System.out.println(FontColor.warning("Không tìm thấy mã nhân viên " + empId));
        }
    }
    private void findByIdOrName(){
        System.out.print(FontColor.info("Nhập mã nhân viên hoặc tên nhân viên: "));
        String key = Console.sc.nextLine();
        List<Employee>  result = employeeService.findByIdOrName(key);
        if(!result.isEmpty()){
            showPageCurrent(result);
        }else {
            System.out.println(FontColor.warning("Không tìm thấy nhân viên nào!"));
        }
    }
    private Date setBirthOfDate(){
        System.out.print(FontColor.info("Nhập ngày sinh nhân viên(yyyy-MM-dd): "));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthOfDate = null;
        boolean isExit =true;
        do{
            try{
                birthOfDate = sdf.parse(Console.sc.nextLine());
                isExit = false;
            }catch (ParseException ex){
                System.out.println(FontColor.warning("Hãy nhập định dạng yyyy-MM-dd"));
            }
        }while (isExit);

        return birthOfDate;
    }
    private int setEmpStatus(){
        boolean isExit =true;
        int select = 0;
        System.out.println("0. Hoạt động");
        System.out.println("1. Nghỉ chế độ");
        System.out.println("2. Nghỉ việc");
        System.out.print(FontColor.info("Nhập lựa chọn:"));
        do{
            try {
                select = Integer.parseInt(Console.sc.nextLine());
                if(select==0||select==1|| select==2){
                    isExit = false;
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Hãy nhập vào số từ 0 đến 2!"));
            }

        }while (isExit);
        return select;
    }
    public void printTitle() {
        System.out.printf("|%s|%s|%s|%s|%s|%s|%s|\n", FontColor.centerString(15,"Mã nhân viên"), FontColor.centerString(30,"Tên nhân viên"),
                FontColor.centerString(20,"Ngày sinh"),FontColor.centerString(20,"Email") ,FontColor.centerString(20,"Số điện thoại"),
                FontColor.centerString(30,"Địa chỉ"),FontColor.centerString(17,"Trạng thái"));
    }
    private void printFooter(){
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
