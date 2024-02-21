package com.ra.service;

import com.ra.entity.Computer;
import com.ra.entity.Order;
import com.ra.repository.impl.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class ComputerService extends Repository<Computer,String> {
    OrderService orderService = new OrderService();
    public Computer onComputer(Scanner sc){
        Computer computer = selectComputerOn(sc);
        computer.setStatus(true);
        edit(computer);
        System.out.println("Bật máy tính thành công");
        return computer;
    }
    public String offComputer(Scanner sc){
        Computer computer = selectComputerOff(sc);
        computer.setStatus(false);
        edit(computer);
        System.out.println("Tắt máy tính thành công");
        return computer.getId();
    }
    public void displayAll(){
        System.out.println("Danh sách máy trong tiệm:");
        List<Computer> computers = findAll(Computer.class);
        computers.forEach(c -> c.display());
    }

    public Computer selectComputerOn(Scanner sc){
        boolean isExit=true;
        List<Computer> computers = selectListComputerOnOrOff("off");
        if(!computers.isEmpty()){
            do{
                isExit = true;
                System.out.println("Nhập mã máy:");
                String id = sc.nextLine();
                for (Computer c: computers) {
                    if(c.getId().equals(id)) {
                        return c;
                    }
                }
                System.out.println("Vui lòng nhập lại mã máy trong danh sách!");

            }while (isExit);
        }else {
            System.out.println("Tất cả các máy đã được bật");
        }
        return null;
    }
    public Computer selectComputerOff(Scanner sc){
        boolean isExit;
        List<Computer> computers = selectListComputerOnOrOff("on");
        if(!computers.isEmpty()){
            do{
                isExit = true;
                System.out.println("Nhập mã máy:");
                String id = sc.nextLine();
                for (Computer c: computers) {
                    if(c.getId().equals(id)) {
                        return c;
                    }
                }
                System.out.println("Vui lòng nhập lại mã máy trong danh sách!");

            }while (isExit);
        }else {
            System.out.println("Tất cả các máy đã tắt!");
        }
        return null;
    }
    public List<Computer> selectListComputerOnOrOff(String selectOnOff){
        List<Computer> computers = findAll(Computer.class);
        List<Computer> result = new ArrayList<>();
        if(selectOnOff.equals("on")){
            System.out.println("Danh sách các máy đang mở:");
            computers.forEach(c -> { if(c.isStatus()){c.display(); result.add(c);}});
        }else {
            System.out.println("Danh sách các máy đang tắt:");
            computers.forEach(c -> { if(!c.isStatus()){c.display(); result.add(c);}});
        }
        return result;
    }
    public void changeComputer(Scanner scanner) {
        System.out.println("Chọn máy cần đổi:");
        Computer oldComputer = selectComputerOn(scanner);
        System.out.println("Chọn máy sẽ đổi sang:");
        Computer newComputer = selectComputerOff(scanner);
        oldComputer.setStatus(false);
        newComputer.setStatus(true);
        Order order = orderService.findByComputerId(oldComputer.getId());
        order.setComputerId(newComputer.getId());
        orderService.edit(order);
    }
}
