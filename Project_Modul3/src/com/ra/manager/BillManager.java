package com.ra.manager;

import com.ra.entity.Account;
import com.ra.entity.Bill;
import com.ra.entity.BillDetail;
import com.ra.entity.Product;
import com.ra.model.BillType;
import com.ra.model.ConstStatus;
import com.ra.model.PermissionType;
import com.ra.repository.impl.AccountRepository;
import com.ra.service.IProductService;
import com.ra.service.impl.BillDetailServiceImpl;
import com.ra.service.impl.BillServiceImpl;
import com.ra.service.impl.ProductServiceImpl;
import com.ra.util.Console;
import com.ra.util.FontColor;
import com.ra.util.Storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BillManager extends Manager<Bill> {
    BillServiceImpl billService = new BillServiceImpl();
    AccountManager accountManager =new AccountManager();
    BillDetailServiceImpl billDetailService = new BillDetailServiceImpl();
    IProductService<Product> productRepository = new ProductServiceImpl();
    AccountRepository accountRepository = new AccountRepository();
    @Override
    public void run() {
        boolean isExit= true;
        do{
            System.out.println("************BILL MANAGEMENT*************");
            System.out.println("1. Danh sách phiếu xuất");
            System.out.println("2. Tạo phiếu xuất");
            System.out.println("3. Cập nhật thông tin phiếu xuất");
            System.out.println("4. Chi tiết phiếu xuất");
            System.out.println("5. Duyệt phiếu xuất");
            System.out.println("6. Tìm kiếm phiếu xuất");
            System.out.println("7. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            try{
                int select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        showAllBill(BillType.EXPORT);
                        break;
                    case 2:
                        addNewBill(BillType.EXPORT);
                        break;
                    case 3:
                        editBill(BillType.EXPORT);
                        break;
                    case 4:
                        showBillDetail(BillType.EXPORT);
                        break;
                    case 5:
                        approveBillByIdOrBillCode(BillType.EXPORT);
                        break;
                    case 6:
                        findByIdOrBillCode(BillType.EXPORT);
                        break;
                    case 7:
                        System.out.println(FontColor.success("Thoát"));
                        isExit = false;
                        break;
                    default:
                        System.out.println(FontColor.warning("Hãy nhập vào số từ 1 đến 7!"));
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Hãy nhập vào 1 số!"));
            }
        }while (isExit);
    }

    public void showAllBill(boolean billType){
        List<Bill> bills;
        if(billType == BillType.EXPORT){
            System.out.println("Danh sách phiếu xuất:");
            bills = billService.findAllBill();
        }else {
            System.out.println("Danh sách phiếu nhập:");
            bills = billService.findAllReceipt();
        }
        if(bills!=null){
            printTitle();
            bills.forEach(Bill::displayData);
            printFooter();
        }else {
            System.out.println(FontColor.warning("Danh sách trống!"));
        }
    }
    public void addNewBill(boolean billType){
        List<BillDetail> billDetails = new ArrayList<>();
        boolean isExit = false;
        while (!isExit){
            Product product = inputProductId(billType);
                try{
                    int quantity;
                    if(billType == BillType.EXPORT){
                        quantity = inputProductQuantity(product);
                    }else {
                        System.out.print(FontColor.info("Nhập số lượng:"));
                        quantity = Integer.parseInt(Console.sc.nextLine());
                    }
                        BillDetail billDetail = new BillDetail();
                        billDetail.setProductId(product.getProductId());
                        billDetail.setQuantity(quantity);
                        System.out.print(FontColor.info("Nhập giá:"));
                        float price = Float.parseFloat(Console.sc.nextLine());
                        billDetail.setPrice(price);
                        boolean isExistBillDetail = false;
                        for(BillDetail bd:billDetails){
                            if(bd.getProductId().equals(billDetail.getProductId())&& Objects.equals(bd.getPrice(), billDetail.getPrice())){
                                isExistBillDetail = true;
                                break;
                            }
                        }
                        if(!isExistBillDetail){
                            billDetails.add(billDetail);
                        }
                        System.out.print(FontColor.info("Bạn có muốn tiếp tục nhập không(có/không)?:"));
                        String ans = Console.sc.nextLine();
                        if(!ans.equals("có")){
                            isExit = true;
                        }
                }catch (NumberFormatException ex){
                    System.out.println(FontColor.err("Hãy nhập vào một số"));
                }
        }
        if(billDetails.size() > 0){
            printBillDetailTitle();
            billDetails.forEach(BillDetail::displayData);
            Bill newBill = inputBill(billType);
            Bill addBill =billService.add(newBill);
            billDetails.forEach(bd -> {
                bd.setBillId(addBill.getBillId());
                billDetailService.add(bd);
            });
        }
    }
    private Product inputProductId(boolean billType){
        do{
            System.out.print(FontColor.info("Nhập mã sản phẩm:"));
            String productId = Console.sc.nextLine();
            Product product = productRepository.findId(productId);
            if(product!= null&& product.isProductStatus()){
                if(billType == BillType.IMPORT){
                    return product;
                }else if(product.getQuantity()>0){
                    return product;
                }else {
                    System.out.println(FontColor.warning("Hết hàng!"));
                }
            }else {
                System.out.println(FontColor.warning("Mã sản phẩm không tồn tại hoặc không hoạt động!"));
            }
        }while (true);
    }
    public int inputProductQuantity(Product product){
        do{
            System.out.print(FontColor.info("Nhập số lượng sản phẩm:"));
            int quantity = Integer.parseInt(Console.sc.nextLine());
            if(product.getQuantity() >0){
                if (quantity > 0 && quantity <= product.getQuantity()) {
                    return quantity;
                }else {
                    System.out.println(FontColor.warning("Hãy nhập số lượng nhỏ hơn " +product.getQuantity()));
                }
            }
        }while (true);
    }
    public Bill inputBill(boolean billType){
        Bill bill = new Bill();
        bill.setBillCode(("HD" + System.currentTimeMillis()).substring(0, 9));
        bill.setBillType(billType);
        bill.setEmpIdCreated(Storage.current_user.getEmpId());
        bill.setCreated(new Date());
        boolean isExit = true;
        String empAuth;
        do{
            System.out.println("Nhập mã user duyệt: ");
            empAuth= accountManager.inputEmpId();
            Account  account = accountRepository.findByEmpId(empAuth);
            if(account.isPermission()!=PermissionType.ADMIN){
                System.out.println(FontColor.warning("Nhân viên không có quyền duyệt!"));
            }else {
                isExit =false;
            }
        }while (isExit);
        bill.setEmpIdAuth(empAuth);
        bill.setBillStatus((int) ConstStatus.BillStt.CREATE);
        return bill;
    }
    public void editBill(boolean billType){
        List<Bill> editBills = getBillForEdit(billType);
        if(editBills.size()>0){
            printTitle();
            editBills.forEach(Bill::displayData);
            printFooter();
            Bill editBill = findByIdOrBillCode(billType);
            boolean isExistBill = false;
            for (Bill b:editBills){
                if(b.getBillId() .equals(editBill.getBillId()) ){
                    isExistBill = true;
                    break;
                }
            }
            if(editBill!=null && isExistBill){
                if(editBill.getBillStatus()!=ConstStatus.BillStt.APPROVE){
                    Bill editedBill=billService.edit(selectEditBillField(editBill,billType));
                    if(editedBill!=null){
                        System.out.println(FontColor.success("Cập nhật thành công!"));
                        printTitle();
                        editedBill.displayData();
                        printFooter();
                    }
                }else {
                    System.out.println(FontColor.warning("Không thể cập nhật bill đã duyệt!"));
                }
            }else {
                System.out.println(FontColor.warning("Không tìm thấy Bill"));
            }
        }else {
            System.out.println(FontColor.warning("User không có bill"));
        }
    }
    private List<Bill> getBillForEdit(boolean billType){
        List<Bill> bills;
        if(billType == BillType.IMPORT){
            bills = billService.findAllReceipt();
        }else {
            bills = billService.findAllBill();
        }
        if(Storage.current_user.isPermission()== PermissionType.USER){
            bills = bills.stream().filter(b->b.getEmpIdCreated().equals(Storage.current_user.getEmpId())).collect(Collectors.toList());
        }
        return bills;
    }
    private List<Bill> getBillForApprove(boolean billType){
        List<Bill> bills;
        if(billType == BillType.IMPORT){
            bills = billService.findAllReceipt();
        }else {
            bills = billService.findAllBill();
        }
        if(Storage.current_user.isPermission()== PermissionType.ADMIN){
            bills = bills.stream().filter(b->b.getEmpIdAuth().equals(Storage.current_user.getEmpId())).collect(Collectors.toList());
        }
        return bills;
    }
    private Bill selectEditBillField(Bill bill,boolean billType){
//        bill.displayData();
        int select;
        boolean isExit=true;
        do{
            System.out.println("Hãy chọn trường muốn edit:");
            System.out.println("1. Người duyệt");
            System.out.println("2. Trạng thái");
            System.out.println("3. Chi tiết phiếu");
            System.out.println("4. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            try{
                select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        System.out.println(FontColor.info("Nhập mã nhân viên duyệt:"));
                        String empAuth= accountManager.inputEmpId();
                        bill.setEmpIdAuth(empAuth);
                        break;
                    case 2:
                        boolean isExit1 =true;
                        int select1;
                        do{
                            System.out.println("Hãy chọn trạng thái:");
                            System.out.println("1. Tạo");
                            System.out.println("2. Hủy");
                            System.out.print(FontColor.info("Nhập lựa chọn:"));
                            select1 = Integer.parseInt(Console.sc.nextLine());
                            if(select1!=1 && select1!=2){
                                System.out.println(FontColor.warning("Hãy nhập 1 hoặc 2!"));
                            }
                            else {
                                bill.setBillStatus((int) (select1==1?ConstStatus.BillStt.CREATE:ConstStatus.BillStt.CANCEL));
                                isExit1 = false;
                            }
                        }while (isExit1);
                        break;
                    case 3:
                        List<BillDetail> billDetails = billDetailService.findByBillId(bill.getBillId());
                        if(billDetails!=null){
                            printBillDetailTitle();
                            billDetails.forEach(BillDetail::displayData);
                            printBillDetailFooter();
                            editBillDetail(billDetails,billType);
                        }else {
                            System.out.println(FontColor.warning("Không tìm thấy billdetail!"));
                        }
                        break;
                    case 4:
                        isExit = false;
                        break;
                    default:
                        System.out.println(FontColor.warning("Hãy nhập từ 1 đến 4!"));
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning(ex.getMessage()));
            }
        }while (isExit);

        return bill;
    }
    public void editBillDetail(List<BillDetail> billDetails, boolean billType){
        System.out.print(FontColor.info("Nhập mã billDetail:"));
        do{
            try{
                Long billDetailId= Long.valueOf(Console.sc.nextLine());
                boolean isExistBillDetail = false;
                BillDetail billDetail = null;
                for(BillDetail bd:billDetails){
                    if(bd.getBillDetailId().equals(billDetailId)){
                        isExistBillDetail = true;
                        billDetail = bd;
                        break;
                    }
                }
                if(isExistBillDetail){
                    printBillDetailTitle();
                    billDetail.displayData();
                    printBillDetailFooter();
                    System.out.println("Nhập trường cần edit:");
                    Product product = inputProductId(billType);
                    billDetail.setProductId(product.getProductId());
                    if(billType == BillType.EXPORT){
                        billDetail.setQuantity(inputProductQuantity(product));
                    }else {
                        System.out.print(FontColor.info("Nhập số lượng:"));
                        int quantity = Integer.parseInt(Console.sc.nextLine());
                        billDetail.setQuantity(quantity);
                    }
                    System.out.print(FontColor.info("Nhập giá:"));
                    float price = Float.parseFloat(Console.sc.nextLine());
                    billDetail.setPrice(price);
                    BillDetail editedBilDetail = billDetailService.edit(billDetail);
                    if(editedBilDetail!=null){
                        System.out.println(FontColor.success("Cập nhật thành công billDetail!"));
                        printBillDetailTitle();
                        editedBilDetail.displayData();
                        printBillDetailFooter();
                    }
                    return;
                }else {
                    System.out.println(FontColor.warning("Không tìm thấy billDetail mã "+billDetailId));
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning(ex.getMessage()));
            }
        }while (true);


    }
    public void approveBillByIdOrBillCode(boolean billType){
        List<Bill> bills = getBillForApprove(billType);
        List<Bill> billCreatedList = bills.stream().filter(b->b.getBillStatus()==ConstStatus.BillStt.CREATE).collect(Collectors.toList());
        printTitle();
        billCreatedList.forEach(Bill::displayData);
        printFooter();
        Bill approveBill = findByIdOrBillCode(billType);
        if(approveBill!=null&& Objects.equals(Storage.current_user.getEmpId(), approveBill.getEmpIdAuth())){
            List<BillDetail> billDetails = billDetailService.findByBillId(approveBill.getBillId());
            if(billType==BillType.EXPORT){
                billDetails.forEach(bd->{
                    Product product = productRepository.findId(bd.getProductId());
                    if(product.getQuantity()>bd.getQuantity()){
                        product.setQuantity(product.getQuantity()-bd.getQuantity());
                        productRepository.edit(product);
                    }else {
                        System.out.println(FontColor.warning("Không đủ số lượng trong kho!"));
                        System.out.println(FontColor.warning("Vui lòng kiểm tra lại!"));
                    }

                });
            }else {
                billDetails.forEach(bd->{
                    Product product = productRepository.findId(bd.getProductId());
                    product.setQuantity(product.getQuantity()+bd.getQuantity());
                    productRepository.edit(product);
                });
            }
            approveBill.setBillStatus((int) ConstStatus.BillStt.APPROVE);
            approveBill.setAuthDate(new Date());
            Bill billApproved = billService.edit(approveBill);
            if(billApproved!= null){
                System.out.println(FontColor.success("Phiếu đã được duyệt!"));
                printTitle();
                billApproved.displayData();
                printFooter();
            }
        }else {
            System.out.println(FontColor.warning("Bạn không có quyền duyệt bill này!"));
        }
    }
    public Bill findByIdOrBillCode(boolean billType){
        System.out.print(FontColor.info("Nhập mã bill hoặc bill code:"));
        long id = 0L;
        String billCode="";
        String input ="";
        Bill findBill;
        try{
            input = Console.sc.nextLine();
            id = Long.parseLong(input);
        }catch (NumberFormatException ex){
            billCode = input;
        }finally {
            findBill =billService.findByIdOrBillCode(id,billCode,billType);
            if(findBill!= null){
                printTitle();
                findBill.displayData();
                printFooter();

            }else {
                System.out.println(FontColor.success("Không tìm thấy bill"));
            }
        }
        return findBill;
    }
    public void showBillDetail(boolean billType){
        Bill bill = findByIdOrBillCode(billType);
        if(bill!=null){
            List<BillDetail> billDetails = billDetailService.findByBillId(bill.getBillId());
            if(billDetails!=null){
                printBillDetailTitle();
                billDetails.forEach(BillDetail::displayData);
                printBillDetailFooter();
            }else {
                System.out.println(FontColor.warning("Không tìm thấy billdetail!"));
            }
        }else {
            System.out.println(FontColor.warning("Không tìm thấy bill!"));
        }
    }
    public void printBillDetailTitle() {
        System.out.printf("|%s|%s|%s|%s|%s|\n", FontColor.centerString(15,"Mã detail"), FontColor.centerString(15,"Mã Bill"),
                FontColor.centerString(20,"Mã sản phẩm"),FontColor.centerString(20,"Số lượng") ,FontColor.centerString(20,"Giá"));
    }
    public void printTitle() {
        System.out.printf("|%s|%s|%s|%s|%s|%s|%s|%s|\n", FontColor.centerString(15,"Mã bill"), FontColor.centerString(15,"Mã Bill Code"),
                FontColor.centerString(20,"Bill type"),FontColor.centerString(20,"Mã nhân viên tạo") ,FontColor.centerString(20,"Ngày tạo"),
                FontColor.centerString(20,"Mã nhân viên duyệt"),FontColor.centerString(20,"Ngày duyệt"),FontColor.centerString(17,"Trạng thái"));
    }
    public void printFooter(){
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    public void printBillDetailFooter(){
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
}
