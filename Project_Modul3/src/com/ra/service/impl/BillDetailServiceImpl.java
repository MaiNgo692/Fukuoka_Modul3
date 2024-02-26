package com.ra.service.impl;

import com.ra.entity.BillDetail;
import com.ra.repository.IRepository;
import com.ra.repository.impl.BillDetailRepository;
import com.ra.repository.impl.Repository;
import com.ra.service.IBillDetailService;
import com.ra.util.FontColor;

import java.util.List;

public class BillDetailServiceImpl implements IBillDetailService {
    BillDetailRepository billDetailRepository = new BillDetailRepository();
    @Override
    public List<BillDetail> findAll() {
        return billDetailRepository.findAll(BillDetail.class);
    }

    @Override
    public BillDetail findId(Long id) {
        return billDetailRepository.findId(id,BillDetail.class);
    }

    @Override
    public BillDetail add(BillDetail entity) {
        return billDetailRepository.add(entity);
    }

    @Override
    public BillDetail edit(BillDetail entity) {
        return billDetailRepository.edit(entity);
    }


    @Override
    public List<BillDetail> findByBillId(Long billId) {
        return billDetailRepository.findByBillId(billId);
    }
}
