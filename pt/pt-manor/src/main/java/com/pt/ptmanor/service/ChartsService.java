package com.pt.ptmanor.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.Map;

public interface ChartsService   {



    Map getFinancialList( String companyId);

    Map financialFindList(String year, String buyerName, String companyId);

    Map findByMany(Date year, String buyerName, String productName, String companyId);



}
