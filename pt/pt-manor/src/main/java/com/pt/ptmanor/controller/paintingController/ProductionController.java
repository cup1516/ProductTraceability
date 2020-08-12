package com.pt.ptmanor.controller.paintingController;

import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.service.painting.ProductionService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.Api;
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

@RequestMapping("/production")
@RestController
@Api(value = "/production", tags = "产量表单")
public class ProductionController {

    @Autowired
    ProductionService productionService;

    @ApiOperation(value = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();
        Page page = productionService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "条件查找")
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public YunResult  find(@RequestBody JSONObject jsonObject){
        String crops = jsonObject.getString("crops");
        Date stime = jsonObject.getDate("stime");
        Date etime = jsonObject.getDate("etime");
        Integer pageRow = jsonObject.getInteger("pageRow");
        Integer pageNum = jsonObject.getInteger("pageNum");
        String companyId = SecurityUtils.getCompanyId();
        Page page = productionService.findByMany(companyId,crops, stime, etime, pageNum, pageRow);
        return YunResult.createBySuccess(page);
    }

    @ApiOperation(value = "产量图表")
    @RequestMapping("chart")
    public YunResult chart(){
        String s1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        s1 = sdf.format(date);
        String companyId = SecurityUtils.getCompanyId();
        Map chart = productionService.chart(s1,companyId);
        return YunResult.createBySuccess("查询成功",chart);
    }

    @ApiOperation(value = "产量图表条件查找")
    @RequestMapping(value = "findChart",method = RequestMethod.POST)
    public YunResult getList(@RequestBody JSONObject jsonObject){
        String crops = jsonObject.getString("crops");
        Date year = jsonObject.getDate("year");
        String s1;
        if (year==null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            s1 = sdf.format(date);
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            s1 = sdf.format(year);
        }
        if (!crops.isEmpty()){
            String companyId = SecurityUtils.getCompanyId();
            Map chart = productionService.findChart(s1, crops,companyId);
            return YunResult.createBySuccess("查询成功！",chart);
        }
        String companyId = SecurityUtils.getCompanyId();
        Map chart = productionService.chart(s1,companyId);
        return YunResult.createBySuccess("查询成功",chart);
    }

}
