package com.pt.ptmanor.service;

import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Map;

public interface FinancialFormService {

//    Page list(int pageNum, int pageRow, String companyId);

    Page list(int pageNum ,int pageRow,String companyId);

    Map getList();

    Page findByMany(String companyId, String productName, String orderId, String buyerName, Date etime, Date stime, Integer pageNum, Integer pageRow);

//    Page findByMany(String companyId, String productName, String invoiceId, String buyerName, Date etime, Date stime, Integer pageNum, Integer pageRow);
}
