package com.pt.ptmanor.controller.orderController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.SystemOrderRepository;
import com.pt.ptmanor.service.SystemOrderService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/invalidOrder")
public class invalidOrderController {
    @Autowired
    SystemOrderService systemOrderService;

    @Autowired
    SystemOrderRepository systemOrderRepository;

    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){

        String companyId = SecurityUtils.getCompanyId();
        Page page = systemOrderService.invalidOrderList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }


    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){

        String orderId = jsonObject.getString("orderId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        String reviewerName = jsonObject.getString("reviewerName");
        String buyerName = jsonObject.getString("buyerName");
        String productName = jsonObject.getString("productName");

        String userId = SecurityUtils.getId();
        String companyId = SecurityUtils.getCompanyId();
        Date etime = jsonObject.getDate("etime");
        Date stime = jsonObject.getDate("stime");

        Page page = systemOrderService.invalidFindByMany(companyId, userId, stime, etime, reviewerName, buyerName, productName, orderId, pageNum, pageRow);
        return YunResult.createBySuccess(page);
    }


}
