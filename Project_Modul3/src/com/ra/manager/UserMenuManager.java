package com.ra.manager;

import com.ra.entity.Bill;
import com.ra.model.BillType;
import com.ra.model.ConstStatus;
import com.ra.util.Storage;

import java.util.List;
import java.util.stream.Collectors;

public class UserMenuManager extends ReceiptManager{
    public void listBillByStatus(boolean billType){
        List<Bill> bills;
        if(billType == BillType.IMPORT){
             bills = billService.findAllReceipt();
        }else {
             bills = billService.findAllBill();
        }
        System.out.println("Danh sách phiếu nhập có trạng thái 'Tạo':");

        List<Bill> billCreated = bills.stream().filter(b->b.getEmpIdCreated().equals( Storage.current_user.getEmpId())
                &&b.getBillStatus()== ConstStatus.BillStt.CREATE).collect(Collectors.toList());

        if(!billCreated.isEmpty()){
            printTitle();
            billCreated.forEach(Bill::displayData);
            printFooter();
        }
        System.out.println("Danh sách phiếu nhập có trạng thái 'Duyệt':");
        List<Bill> billApproved = bills.stream().filter(b->b.getEmpIdCreated().equals( Storage.current_user.getEmpId())
                &&b.getBillStatus()== ConstStatus.BillStt.APPROVE).collect(Collectors.toList());
        if(!billApproved.isEmpty()){
            printTitle();
            billApproved.forEach(Bill::displayData);
            printFooter();
        }
        System.out.println("Danh sách phiếu nhập có trạng thái 'Hủy':");
        List<Bill> billCanceled= bills.stream().filter(b->b.getEmpIdCreated().equals(Storage.current_user.getEmpId())
                &&b.getBillStatus()== ConstStatus.BillStt.CANCEL).collect(Collectors.toList());
        if(!billCanceled.isEmpty()){
            printTitle();
            billCanceled.forEach(Bill::displayData);
            printFooter();
        }
    }
}
