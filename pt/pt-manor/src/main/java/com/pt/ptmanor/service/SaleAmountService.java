package com.pt.ptmanor.service;

import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Map;

public interface SaleAmountService {

    Page list(int pageNum, int pageRow, String companyId);

    Page findByMany(String companyId, String productName, Date stime, Date etime, Integer pageNum, Integer pageRow);

    Map chart( String companyId);

    Map findChart(Date year, String productName, String buyerName, String companyId);
}
