package com.pt.ptmanor.controller;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.service.ChartsService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RequestMapping("yun/charts/")
@RestController
@Component
public class ChartsController {

    @Autowired
    private ChartsService chartsService;


	@RequestMapping("moneyList")
    public YunResult getMoney(){

        String companyId = SecurityUtils.getCompanyId();
        Map map = chartsService.getFinancialList(companyId);
        return YunResult.createBySuccess("查询成功",map);
    }


    @RequestMapping(value = "findList",method = RequestMethod.POST)
    public YunResult getList(@RequestBody JSONObject jsonObject){

        String buyerName = jsonObject.getString("buyerName");
        String productName = jsonObject.getString("productName");
        Date year = jsonObject.getDate("year");

        String companyId = SecurityUtils.getCompanyId();

        Map map = chartsService.findByMany(year,buyerName,productName,companyId);
        return YunResult.createBySuccess("查询成功",map);
    }


}
