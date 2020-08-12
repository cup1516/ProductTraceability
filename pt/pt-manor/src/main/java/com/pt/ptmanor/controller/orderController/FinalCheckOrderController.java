package com.pt.ptmanor.controller.orderController;

import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.SystemOrderRepository;
import com.pt.ptmanor.service.SystemOrderService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/finalCheckOrder")
@Api(value = "finalCheckOrder", tags = "已确认订单")
public class FinalCheckOrderController {

    @Autowired
    SystemOrderService systemOrderService;

    @Autowired
    SystemOrderRepository systemOrderRepository;

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow)
    {
        String companyId = SecurityUtils.getCompanyId();
        Page page = systemOrderService.finalCheckList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "条件查询", notes = "条件查询")
    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject)
    {
        String orderId = jsonObject.getString("orderId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        String checkerName = jsonObject.getString("checkerName");
        String sellerName = jsonObject.getString("sellerName");
        String productName = jsonObject.getString("productName");
        String companyId = SecurityUtils.getCompanyId();
        Date etime = jsonObject.getDate("etime");
        Date stime = jsonObject.getDate("stime");
        Page page = systemOrderService.finalCheckFindByMnay(stime,etime,companyId, sellerName, productName, orderId, checkerName, pageNum, pageRow);
        return YunResult.createBySuccess(page);
    }

}
