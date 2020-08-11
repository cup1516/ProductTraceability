package com.pt.ptmanor.controller.orderController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.SystemOrderRepository;
import com.pt.ptmanor.pojo.SystemOrder;
import com.pt.ptmanor.pojo.user.SysUser;
import com.pt.ptmanor.service.SystemOrderService;
import com.pt.ptmanor.service.user.UserService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reviewOrder")
public class ReviewerOrderController {

    @Autowired
    SystemOrderService systemOrderService;

    @Autowired
    SystemOrderRepository systemOrderRepository;

    @RequestMapping("/toReviewList")
    public YunResult getList(int pageNum , int pageRow){

        String companyId = SecurityUtils.getCompanyId();
        Page page = systemOrderService.toReviewList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功!",page);
    }

    @RequestMapping(value = "/reviewBack",method = RequestMethod.POST)
    public YunResult checkBack(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckFlag("3");
        byOrderId.setReviewerTime(new Date());
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("打回成功!");
    }

    @RequestMapping(value = "/pass",method = RequestMethod.POST)
    public YunResult pass(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckFlag("2");
        byOrderId.setReviewerTime(new Date());
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("通过审核!");
    }



    @RequestMapping(value = "/toCheck",method = RequestMethod.POST)
    public YunResult toCheck(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckStatus("1");
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("发送确认!");
    }


    @RequestMapping(value = "/backToCheck",method = RequestMethod.POST)
    public YunResult backToCheck(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckStatus("0");
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("撤回成功!");
    }

    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){

        String orderId = jsonObject.getString("orderId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        String creatorName = jsonObject.getString("creatorName");
        String buyerName = jsonObject.getString("buyerName");
        String productName = jsonObject.getString("productName");


        String userId = SecurityUtils.getId();
        String companyId = SecurityUtils.getCompanyId();
        Date etime = jsonObject.getDate("etime");
        Date stime = jsonObject.getDate("stime");

        Page page = systemOrderService.toReviewFindByMany(companyId, userId, stime, etime, creatorName, buyerName, productName, orderId, pageNum, pageRow);
        return YunResult.createBySuccess(page);

    }

    @Autowired
    UserService userService;

    @RequestMapping(value = "getFinancialUserList",method = RequestMethod.GET)
    public YunResult getCheckUsers(){
        String companyId = SecurityUtils.getCompanyId();
        List<SysUser> SysUser = userService.getFinancialUserList(companyId);
        return YunResult.createBySuccess(SysUser);
    }





}
