package com.ra.manager;
import com.ra.model.BillType;
import com.ra.service.impl.BillServiceImpl;
import com.ra.util.Console;
import com.ra.util.FontColor;


public class ReceiptManager extends BillManager{
    BillServiceImpl billService = new BillServiceImpl();
    @Override
    public void run() {
        boolean isExit = true;
        do{
            System.out.println("************RECEIPT MANAGEMENT*************");
            System.out.println("1. Danh sách phiếu nhập");
            System.out.println("2. Tạo phiếu nhập");
            System.out.println("3. Cập nhật thông tin phiếu nhập");
            System.out.println("4. Chi tiết phiếu nhập");
            System.out.println("5. Duyệt phiếu nhập");
            System.out.println("6. Tìm kiếm phiếu nhập");
            System.out.println("7. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            try{
                int select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        showAllBill(BillType.IMPORT);
                        break;
                    case 2:
                        addNewBill(BillType.IMPORT);
                        break;
                    case 3:
                        editBill(BillType.IMPORT);
                        break;
                    case 4:
                        showBillDetail(BillType.IMPORT);
                        break;
                    case 5:
                        approveBillByIdOrBillCode(BillType.IMPORT);
                        break;
                    case 6:
                        findByIdOrBillCode(BillType.IMPORT);
                        break;
                    case 7:
                        System.out.println(FontColor.success("Thoát"));
                        isExit= false;
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
}
