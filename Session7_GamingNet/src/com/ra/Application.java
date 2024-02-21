package com.ra;

import com.ra.entity.Computer;
import com.ra.entity.Order;
import com.ra.entity.OrderDetail;
import com.ra.entity.Service;
import com.ra.repository.impl.Repository;
import com.ra.service.ComputerService;
import com.ra.service.OrderDetailService;
import com.ra.service.OrderService;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Application {
    /**
     * Viết chương trình quản lý phòng net.
     * Có các chức năng:
     *      1. Mở máy (ko chọn máy có người đang sử dụng)
     *      2. Tắt máy, tính tiền theo giờ (15000/giờ)
     *      3. Hiển thị danh sách máy trong tiệm, có kèm trạng thái ON/OFF
     *      Yêu cầu BTVN
     *      2. Tắt máy, tính tiền theo giờ (15000/giờ) và dịch vụ đã sử dụng
     *      (Giá mở máy tối thiểu 15000)
     *      4. Thêm dịch vụ cho máy (nước ngọt, mỳ tôm, cafe, ...)
     *      5. Chuyển máy (người chơi có thể chuyển từ máy A -> B)
     */
    public static void main(String[] args) {
        ComputerService computerService = new ComputerService();
        OrderService orderService = new OrderService();
        OrderDetailService orderDetailService = new OrderDetailService();
        Scanner sc = new Scanner(System.in);
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        do{
            System.out.println("____________Menu quản lý quán net:___________");
            System.out.println("1. Mở máy(ko chọn máy có người đang sử dụng)");
            System.out.println("2. Tắt máy, tính tiền theo giờ (15000/giờ) và dịch vụ đã sử dụng(Giá mở máy tối thiểu 15000)");
            System.out.println("3. Hiển thị danh sách máy trong tiệm, có kèm trạng thái ON/OFF");
            System.out.println("4. Thêm dịch vụ cho máy (nước ngọt, mỳ tôm, cafe, ...)");
            System.out.println("5. Chuyển máy (người chơi có thể chuyển từ máy A -> B)");
            System.out.println("6: Thoát");
            System.out.println("Nhập lựa chọn:");
            int select = Integer.parseInt(sc.next());
            sc.nextLine();
            switch (select){
                case 1:
                    System.out.println("1. Mở máy(ko chọn máy có người đang sử dụng)");
                    orderService.addNewOrderAfterOnComputer(computerService.onComputer(sc));
                    break;
                case 2:
                    System.out.println("2. Tắt máy, tính tiền theo giờ (15000/giờ) và dịch vụ đã sử dụng(Giá mở máy tối thiểu 15000)");
                    String offComputerId = computerService.offComputer(sc);
                    System.out.printf("Tổng bill: %f",orderService.printBillAfterOffComputer(offComputerId)+ orderDetailService.billAllOrderDetail(offComputerId));
                    break;
                case 3:
                    System.out.println("3. Hiển thị danh sách máy trong tiệm, có kèm trạng thái ON/OFF");
                    computerService.displayAll();
                    break;
                case 4:
                    System.out.println("4. Thêm dịch vụ cho máy (nước ngọt, mỳ tôm, cafe, ...)");
                    System.out.println("Chọn máy thêm dịch vụ:");
                    Computer selectComputer = computerService.selectComputerOff(sc);
                    OrderDetail orderDetail = orderService.selectService(sc,selectComputer);
                    orderDetailService.add(orderDetail);
                    break;
                case 5:
                    System.out.println("5. Chuyển máy (người chơi có thể chuyển từ máy A -> B)");
                    computerService.changeComputer(sc);
                    break;
                case 6:
                    System.out.println("Kết thúc chương trình!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Hãy chọn từ 1 đến 6!");
            }
        }while (true);

    }
}
