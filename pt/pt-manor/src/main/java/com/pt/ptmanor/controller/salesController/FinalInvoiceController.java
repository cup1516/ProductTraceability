package com.pt.ptmanor.controller.salesController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.product.InvoiceRepository;
import com.pt.ptmanor.service.product.InvoiceService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/finalInvoice")
@RestController
public class FinalInvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceRepository invoiceRepository;



    //审核完成的列表
    @RequestMapping("/list")
    public YunResult finalList(int pageNum , int pageRow ){
        String companyId = SecurityUtils.getCompanyId();

        Page page = invoiceService.finalList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess(page);
    }


    @RequestMapping(value = "findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){
        String companyId = SecurityUtils.getCompanyId();
        String orderId = jsonObject.getString("orderId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        Integer checkFlag = jsonObject.getInteger("checkFlag");
        String buyerName = jsonObject.getString("buyerName");
        String productName = jsonObject.getString("productName");

        Date etime = jsonObject.getDate("etime");
        Date stime = jsonObject.getDate("stime");

        Page page = invoiceService.finalFindByMany(companyId,stime, etime, checkFlag,buyerName, productName, orderId, pageNum, pageRow);
        return YunResult.createBySuccess(page);
    }

}
