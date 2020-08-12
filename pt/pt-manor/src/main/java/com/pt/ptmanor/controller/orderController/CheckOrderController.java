package com.pt.ptmanor.controller.orderController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.SystemOrderRepository;
import com.pt.ptmanor.pojo.SystemOrder;
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
@RequestMapping("/checkOrder")
public class CheckOrderController {

    @Autowired
    SystemOrderService systemOrderService;

    @Autowired
    SystemOrderRepository systemOrderRepository;

    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){

        String companyId = SecurityUtils.getCompanyId();
        Page page = systemOrderService.toCheckList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功!",page);
    }


    @RequestMapping("/pass")
    public YunResult pass(@RequestBody SystemOrder systemOrder){


        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckStatus("2");
        byOrderId.setCheckTime(new Date());
        byOrderId.setCheckerId(SecurityUtils.getId());
        byOrderId.setCheckerName(SecurityUtils.getNickName());
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("确认通过!");

    }


    @RequestMapping("/checkBack")
    public YunResult checkBack(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckStatus("3");
        byOrderId.setCheckTime(new Date());
        byOrderId.setCheckerId(SecurityUtils.getId());
        byOrderId.setCheckerName(SecurityUtils.getNickName());
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("确认通过!");

    }

    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){

        String orderId = jsonObject.getString("orderId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        String sellerName = jsonObject.getString("sellerName");
        String productName = jsonObject.getString("productName");

        String userId = SecurityUtils.getId();
        String companyId = SecurityUtils.getCompanyId();

        Page page = systemOrderService.toCheckFindByMany(companyId, userId, sellerName, productName, orderId, pageNum, pageRow);
        return YunResult.createBySuccess(page);
    }


}
