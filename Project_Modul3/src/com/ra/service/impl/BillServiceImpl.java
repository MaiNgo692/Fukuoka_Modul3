package com.ra.service.impl;

import com.ra.entity.Bill;
import com.ra.repository.impl.BillRepository;
import com.ra.service.IBillService;
import java.util.List;
import java.util.stream.Collectors;

public class BillServiceImpl implements IBillService {
    BillRepository billRepository = new BillRepository();
    @Override
    public List<Bill> findAll() {
        return billRepository.findAll(Bill.class);
    }
    @Override
    public Bill findId(Long id) {
        return billRepository.findId(id,Bill.class);
    }
    @Override
    public Bill add(Bill entity) {
        return billRepository.add(entity);
    }
    @Override
    public Bill edit(Bill entity) {
        return billRepository.edit(entity);
    }
    @Override
    public List<Bill> findAllReceipt() {
        List<Bill> allBill = findAll();
        return allBill.stream().filter(Bill::isBillType).collect(Collectors.toList());
    }
    @Override
    public List<Bill> findAllBill() {
        List<Bill> allBill = findAll();
        return allBill.stream().filter(b-> !b.isBillType()).collect(Collectors.toList());
    }
    @Override
    public Bill findByIdOrBillCode(Long id, String billCode, boolean billType) {
        return billRepository.findByIdOrBillCode(id, billCode, billType);
    }

}
