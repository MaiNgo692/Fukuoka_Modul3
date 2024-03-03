package com.ra;

import com.ra.entity.*;
import com.ra.manager.*;
import com.ra.model.BillType;
import com.ra.repository.impl.ReportRepository;
import com.ra.service.IAccountService;
import com.ra.service.impl.*;
import com.ra.util.Console;
import com.ra.util.FontColor;
import com.ra.util.Storage;


public class Application {
    public static void main(String[] args) {
//            ProductServiceImpl productService = new ProductServiceImpl();
//            List<Product> products = productService.findAll();
//            productService.printTitle();
//            products.forEach(p -> p.displayData());
//        AccountServiceImpl accountService = new AccountServiceImpl();
//        accountService.printTitle();
//        List<Account> accounts = accountService.findAll();
//        accounts.forEach(ac -> ac.displayData());
//            BillServiceImpl billService = new BillServiceImpl();
//            billService.printTitle();
//            List<Bill> bills = billService.findAll();
//            bills.forEach(b->b.displayData());
//        BillDetailImpl billDetail = new BillDetailImpl();
//        List<BillDetail>billDetails = billDetail.findAll();
//        billDetail.printTitle();
//        billDetails.forEach(bd -> bd.displayData());
//             EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
//             employeeService.printTitle();
//             List<Employee> employees = employeeService.findAll();
//             employees.forEach(em -> em.displayData());
//        ReportRepository repository = new ReportRepository();
//        System.out.println(repository.costStatisticByDate("2024-02-25"));


        IAccountService<Account> accountService = new AccountServiceImpl();
        Account loginAccount;
        boolean isExit=true;
        do {
            System.out.println("Mời đăng nhập hệ thống!");
            System.out.print(FontColor.info("Enter User Name:"));
            String userName = Console.sc.nextLine();
            System.out.print(FontColor.info("Enter Password: "));
            String password = Console.sc.nextLine();
            loginAccount = accountService.login(userName,password);
            if(loginAccount!=null){
                isExit=false;
            }else {
                System.out.println(FontColor.warning("Mật khẩu hoặc password không đúng!"));
            }
        }while (isExit);
        Storage.current_user = loginAccount;
        if(Storage.current_user.isPermission()){
            System.out.println(FontColor.success("Đăng nhập thành công tài khoản User: " + loginAccount.getUserName()));
            userMainMenu();
        }else {
            System.out.println(FontColor.success("Đăng nhập thành công tài khoản Admin:" + loginAccount.getUserName()));
            adminMainMenu();
        }
    }
    private static void adminMainMenu(){
        ProductManager productManager = new ProductManager();
        EmployeeManager employeeManager =new EmployeeManager();
        AccountManager accountManager = new AccountManager();
        ReceiptManager receiptManager = new ReceiptManager();
        BillManager billManager = new BillManager();
        ReportManager reportManager = new ReportManager();
        do {
            System.out.println("****************WAREHOUSE MANAGEMENT******************");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý nhân viên");
            System.out.println("3. Quản lý tài khoản");
            System.out.println("4. Quản lý phiếu nhập");
            System.out.println("5. Quản lý phiếu xuất");
            System.out.println("6. Quản lý báo cáo");
            System.out.println("7. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            try {
                int select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        productManager.run();
                        break;
                    case 2:
                        employeeManager.run();
                        break;
                    case 3:
                        accountManager.run();
                        break;
                    case 4:
                        receiptManager.run();
                        break;
                    case 5:
                        billManager.run();
                        break;
                    case 6:
                        reportManager.run();
                        break;
                    case 7:
                        System.out.println(FontColor.success("Thoát chương trình!"));
                        System.exit(0);
                    default:
                        System.out.println(FontColor.warning("Hãy nhập vào số từ 1 đến 7!"));
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Hãy nhập vào 1 số!"));
            }
        }while (true);

    }

    private static void userMainMenu(){
        UserMenuManager userMenuManager = new UserMenuManager();
        do{
            System.out.println("******************WAREHOUSE MANAGEMENT****************");
            System.out.println("1.  Danh sách phiếu nhập theo trạng thái");
            System.out.println("2.  Tạo phiếu nhập");
            System.out.println("3.  Cập nhật phiếu nhập");
            System.out.println("4.  Tìm kiếm phiếu nhập");
            System.out.println("5.  Danh sách phiếu xuất theo trạng thái");
            System.out.println("6.  Tạo phiếu xuất");
            System.out.println("7.  Cập nhật phiếu xuất");
            System.out.println("8.  Tìm kiếm phiếu xuất");
            System.out.println("9.  Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            int select = Integer.parseInt(Console.sc.nextLine());
            switch (select){
                case 1:
                    userMenuManager.listBillByStatus(BillType.IMPORT);
                    break;
                case 2:
                    userMenuManager.addNewBill(BillType.IMPORT);
                    break;
                case 3:
                    userMenuManager.editBill(BillType.IMPORT);
                    break;
                case 4:
                    userMenuManager.findByIdOrBillCode(BillType.IMPORT);
                    break;
                case 5:
                    userMenuManager.listBillByStatus(BillType.EXPORT);
                    break;
                case 6:
                    userMenuManager.addNewBill(BillType.EXPORT);
                    break;
                case 7:
                    userMenuManager.editBill(BillType.EXPORT);
                    break;
                case 8:
                    userMenuManager.findByIdOrBillCode(BillType.EXPORT);
                    break;
                case 9:
                    System.out.println(FontColor.success("Thoát chương trình!"));
                    System.exit(0);
                    break;
                default:
                    System.out.println(FontColor.warning("Hãy nhập vào số từ 1 đến 9!"));
                    break;
            }
        }while (true);
    }
//    public static String getPasswordWithoutConsole(String prompt) {
//
//        final JPasswordField passwordField = new JPasswordField();
//        return JOptionPane.showConfirmDialog(
//                null,
//                passwordField,
//                prompt,
//                JOptionPane.OK_CANCEL_OPTION,
//                JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(passwordField.getPassword()) : "";
//    }
//
//    private static class ConsoleReader {
//    }
}
