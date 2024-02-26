package com.ra.repository.impl;

import com.ra.entity.BillDetail;
import com.ra.util.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BillDetailRepository extends Repository<BillDetail,Long>{
    public  List<BillDetail> findByBillId(Long billId){
        List<BillDetail> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst;
        ResultSet rs;
        try{
            conn = MysqlConnect.open();
            Field[]fields =BillDetail.class.getDeclaredFields();
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?", BillDetail.class.getAnnotation(Table.class).name(), "Bill_Id");
            pst =conn.prepareStatement(sql);
//            System.out.println(sql);
            pst.setObject(1,billId);
            rs = pst.executeQuery();
            while (rs.next()){
                result.add(setField(BillDetail.class,rs,fields)) ;
            }
        }catch (Exception ex){
            System.out.println(FontColor.err("Tìm không thành công!"));
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return result;
    }
}
