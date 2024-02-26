package com.ra.service;

import com.ra.entity.BillDetail;

import java.util.List;

public interface IBillDetailService extends IService<BillDetail, Long> {
    List<BillDetail> findByBillId(Long billId);
}
