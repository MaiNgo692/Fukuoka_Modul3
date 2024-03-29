package com.ra.manager;

import com.ra.entity.Bill;
import com.ra.model.BillType;
import com.ra.model.ConstStatus;
import com.ra.repository.impl.ReportRepository;
import com.ra.util.Console;
import com.ra.util.FontColor;

import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Map;

public class ReportManager extends Manager<Bill> {
    ReportRepository repository = new ReportRepository();
    @Override
    public void run() {
        boolean isExit= true;
        do{
            System.out.println("\n************REPORT MANAGEMENT*************");
            System.out.println(" 1. Thống kê chi phí theo ngày , tháng, năm");
            System.out.println(" 2. Thống kê chi phí theo khoảng thời gian");
            System.out.println(" 3. Thống kê doanh thu theo ngày , tháng, năm");
            System.out.println(" 4. Thống kê doanh thu theo khoảng thời gian");
            System.out.println(" 5. Thống kê số nhân viên theo từng trạng thái");
            System.out.println(" 6. Thống kê sản phẩm nhập nhiều nhất trong khoảng thời gian");
            System.out.println(" 7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian");
            System.out.println(" 8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian");
            System.out.println(" 9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian");
            System.out.println("10. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));

            try{
                int select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        costStatisticByDate(BillType.IMPORT);
                        break;
                    case 2:
                        costStatisticOverPeriod(BillType.IMPORT);
                        break;
                    case 3:
                        costStatisticByDate(BillType.EXPORT);
                        break;
                    case 4:
                        costStatisticOverPeriod(BillType.EXPORT);
                        break;
                    case 5:
                        empStatisticByStatus();
                        break;
                    case 6:
                        mostProductQuantityOverPeriod(BillType.IMPORT);
                        break;
                    case 7:
                        leastProductQuantityOverPeriod(BillType.IMPORT);
                        break;
                    case 8:
                        mostProductQuantityOverPeriod(BillType.EXPORT);
                        break;
                    case 9:
                        leastProductQuantityOverPeriod(BillType.EXPORT);
                        break;
                    case 10:
                        System.out.println(FontColor.success("Thoát"));
                        isExit =false;
                        break;
                    default:
                        System.out.println(FontColor.warning("Hãy nhập vào số từ 1 đến 10!"));
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Hãy nhập vào 1 số!"));
            }

        }while (isExit);
    }

    private void costStatisticByDate(boolean billType){
        System.out.print(FontColor.info("Nhập ngày(yyyy-MM-dd): "));
        LocalDate date = inputDate();
        System.out.printf("|%s|%s|\n",FontColor.centerString(30,"Ngày"),FontColor.centerString(30,billType== BillType.EXPORT?"Tổng doanh thu":"Tổng chi phí"));
        System.out.printf("|%s|%s|\n",FontColor.centerString(30, String.valueOf(date)),
                                      FontColor.centerString(30,formatVn().format(repository.costStatisticByDate(Date.valueOf(date),billType))+"đ"));

    }
    private LocalDate inputDate(){
        LocalDate date;
        do{
            try {
                String inputDate = Console.sc.nextLine();
                date = LocalDate.parse(inputDate);
                return date;
            }catch (DateTimeParseException ex){
                System.out.println(FontColor.warning("Hãy nhập đúng định dạng (yyyy-MM-dd)!"));

            }
        }while (true);
    }
    private void costStatisticOverPeriod(boolean billType){
        System.out.print(FontColor.info("Nhập ngày bắt đầu(yyyy-MM-dd): "));
        LocalDate fromDate =inputDate();
        System.out.print(FontColor.info("Nhập ngày kết thúc(yyyy-MM-dd): "));
        LocalDate toDate = inputDate();
        System.out.printf("|%s|%s|%s|\n",FontColor.centerString(30,"From Date"),FontColor.centerString(30,"End Date"),FontColor.centerString(30,billType== BillType.EXPORT?"Tổng doanh thu":"Tổng chi phí"));
        System.out.printf("|%s|%s|%s|\n",FontColor.centerString(30, String.valueOf(fromDate)),FontColor.centerString(30, String.valueOf(toDate)),
                    FontColor.centerString(30,formatVn().format(repository.costStatisticOverPeriod(Date.valueOf(fromDate),Date.valueOf(toDate),BillType.EXPORT))+"đ"));

    }
    private void empStatisticByStatus(){
        System.out.println("Thống kê số nhân viên theo trạng thái:");
        System.out.printf("|%s|%s|\n",FontColor.centerString(30,"Trạng thái"),FontColor.centerString(30,"Số lượng nhân viên"));
        System.out.printf("|%s|%s|\n",FontColor.centerString(30,"Hoạt đông"),FontColor.centerString(30,repository.empStatisticByStatus(ConstStatus.EmpStt.ACTIVE).toString()));
        System.out.printf("|%s|%s|\n",FontColor.centerString(30,"Nghỉ chế độ"),FontColor.centerString(30,repository.empStatisticByStatus(ConstStatus.EmpStt.SLEEP).toString()));
        System.out.printf("|%s|%s|\n",FontColor.centerString(30,"Nghỉ việc"),FontColor.centerString(30,repository.empStatisticByStatus(ConstStatus.EmpStt.QUIT).toString()));
    }
    private void mostProductQuantityOverPeriod(boolean billType){
        System.out.print(FontColor.info("Nhập ngày bắt đầu(yyyy-MM-dd): "));
        LocalDate fromDate = inputDate();
        System.out.print(FontColor.info("Nhập ngày kết thúc(yyyy-MM-dd): "));
        LocalDate toDate = inputDate();
        System.out.println("Thống kê sản phẩm nhập nhiều nhất");
        System.out.printf("|%s|%s|%s|%s|\n",FontColor.centerString(30,"From Date"),FontColor.centerString(30,"To Date"),
                    FontColor.centerString(30,"Sản phẩm"),FontColor.centerString(30,"Số lượng"));
        Map<String, Integer> map = repository.mostProductQuantityOverPeriod(Date.valueOf(fromDate), Date.valueOf(toDate),billType);
            for (String i: map.keySet()){
                System.out.printf("|%s|%s|%s|%s|\n",FontColor.centerString(30,fromDate.toString()),FontColor.centerString(30,toDate.toString()),
                        FontColor.centerString(30,i),FontColor.centerString(30, String.valueOf(map.get(i))));
            }
    }
    private void leastProductQuantityOverPeriod(boolean billType){
        System.out.print(FontColor.info("Nhập ngày bắt đầu(yyyy-MM-dd): "));
        LocalDate fromDate = inputDate();
        System.out.print(FontColor.info("Nhập ngày kết thúc(yyyy-MM-dd): "));
        LocalDate toDate = inputDate();
        System.out.println("Thống kê sản phẩm nhập ít nhất");
        System.out.printf("|%s|%s|%s|%s|\n",FontColor.centerString(30,"From Date"),FontColor.centerString(30,"To Date"),
                FontColor.centerString(30,"Sản phẩm"),FontColor.centerString(30,"Số lượng"));
        Map<String, Integer> map = repository.leastProductQuantityOverPeriod(Date.valueOf(fromDate), Date.valueOf(toDate),billType);
        for (String i: map.keySet()){
            System.out.printf("|%s|%s|%s|%s|\n",FontColor.centerString(30,fromDate.toString()),FontColor.centerString(30,toDate.toString()),
                    FontColor.centerString(30,i),FontColor.centerString(30, String.valueOf(map.get(i))));
        }
    }
    private NumberFormat formatVn(){
        Locale localeVN = new Locale("vi", "VN");
        return NumberFormat.getInstance(localeVN);
    }

}
