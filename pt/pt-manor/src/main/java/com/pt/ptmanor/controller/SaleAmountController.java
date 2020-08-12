package com.pt.ptmanor.controller;

import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.SaleAmountRepository;
import com.pt.ptmanor.service.SaleAmountService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RequestMapping("/saleAmount")
@RestController
@ApiOperation(value = "销量图表和表单")
public class SaleAmountController {

    @Autowired
    SaleAmountService saleAmountService;

    @Autowired
    SaleAmountRepository saleAmountRepository;

    @ApiOperation(value = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();
        Page page = saleAmountService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "条件查询")
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){
        String productName = jsonObject.getString("productName");
        Date stime = jsonObject.getDate("stime");
        Date etime = jsonObject.getDate("etime");
        Integer pageRow = jsonObject.getInteger("pageRow");
        Integer pageNum = jsonObject.getInteger("pageNum");
        String companyId = SecurityUtils.getCompanyId();
        Page page = saleAmountService.findByMany(companyId,productName, stime, etime, pageNum, pageRow);
        return YunResult.createBySuccess(page);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping("/chart")
    public YunResult chart(){

        String companyId = SecurityUtils.getCompanyId();
        Map chart = saleAmountService.chart(companyId);
        return YunResult.createBySuccess("查询成功",chart);
    }

    @ApiOperation(value = "销量图表条件查询")
    @RequestMapping(value = "/chartFindList",method = RequestMethod.POST)
    public YunResult getList(@RequestBody JSONObject jsonObject){
        String buyerName = jsonObject.getString("buyerName");
        String productName = jsonObject.getString("productName");
        Date year = jsonObject.getDate("year");
        String companyId = SecurityUtils.getCompanyId();
        Map chart = saleAmountService.findChart(year, productName, buyerName,companyId);
        return YunResult.createBySuccess("查询成功",chart);
    }
    
}
