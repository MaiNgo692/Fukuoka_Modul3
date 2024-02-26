package com.ra.service;

import com.ra.entity.Bill;

import java.util.List;

public interface IBillService extends IService<Bill,Long>{
    List<Bill> findAllReceipt();
    List<Bill> findAllBill();
    Bill findByIdOrBillCode(Long id, String billCode, boolean billType);
}
