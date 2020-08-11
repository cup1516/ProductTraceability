package com.pt.ptmanor.controller;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.FinancialFormRepository;
import com.pt.ptmanor.service.FinancialFormService;
import com.pt.ptmanor.service.SystemOrderService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/financaial")
@RestController
public class FinancialFormController {

    @Autowired
    FinancialFormRepository financialFormRepository;

    @Autowired
    FinancialFormService financialFormService;

    @Autowired
    SystemOrderService systemOrderService;


    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();

        Page page = financialFormService.list(pageNum, pageRow,companyId);

        return YunResult.createBySuccess("查询成功！",page);
    }

    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){

        String orderId = jsonObject.getString("orderId");
        String buyerName = jsonObject.getString("buyerName");
        String productName = jsonObject.getString("productName");

        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");

        Date etime = jsonObject.getDate("etime");
        Date stime = jsonObject.getDate("stime");

        String companyId = SecurityUtils.getCompanyId();
        Page page = financialFormService.findByMany(companyId,productName,orderId, buyerName, etime, stime, pageNum, pageRow);
        return YunResult.createBySuccess("查询成功！",page);
    }



}
