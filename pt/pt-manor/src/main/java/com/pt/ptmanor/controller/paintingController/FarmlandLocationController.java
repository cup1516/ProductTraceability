package com.pt.ptmanor.controller.paintingController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.painting.FarmlandLocationRepository;
import com.pt.ptmanor.pojo.painting.FarmlandLocation;
import com.pt.ptmanor.service.painting.FarmlandLocationService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/planting/farmlandLocation")
@RestController
@Api(value = "/planting/farmlandLocation", tags = "田地区域")
public class FarmlandLocationController {

    @Autowired
    FarmlandLocationService farmlandLocationService;

    @Autowired
    FarmlandLocationRepository farmlandLocationRepository;

    @ApiOperation(value = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();
        Page page = farmlandLocationService.list(pageNum, pageRow, companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "增加田地区域")
    @RequestMapping("/add")
    public YunResult add (@RequestBody FarmlandLocation farmlandLocation){
        String companyId = SecurityUtils.getCompanyId();
        UUID u = UUID.randomUUID();
        String str = u.toString();
        str = str.replace("-","");
        farmlandLocation.setFarmlandLocationId(str);
        farmlandLocation.setIsDeleted(0);
        farmlandLocation.setCompanyId(companyId);
        farmlandLocationRepository.save(farmlandLocation);
        return YunResult.createBySuccess("添加成功！");
    }

    @ApiOperation(value = "条件查询")
    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){
        String farmlandLocationId = jsonObject.getString("farmlandLocationId");
        String farmlandLocationName = jsonObject.getString("farmlandLocationName");
        String farmlandPrincipal = jsonObject.getString("farmlandPrincipal");
        String farmlandArea = jsonObject.getString("farmlandArea");
        String farmlandExplain = jsonObject.getString("farmlandExplain");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        String companyId = SecurityUtils.getCompanyId();
        Page page = farmlandLocationService.findByMany(farmlandLocationId,farmlandLocationName,farmlandPrincipal, farmlandArea,farmlandExplain,pageNum,pageRow,companyId);
        return YunResult.createBySuccess(page);
    }

    @ApiOperation(value = "修改田地区域")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public YunResult update(@RequestBody FarmlandLocation farmlandLocation){
        String companyId = SecurityUtils.getCompanyId();
        farmlandLocation.setCompanyId(companyId);
        farmlandLocationRepository.save(farmlandLocation);
        return YunResult.createBySuccess("修改成功！");
    }

    @ApiOperation(value = "删除田地区域")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public YunResult delete(@RequestBody FarmlandLocation farmlandLocation){
        String companyId = SecurityUtils.getCompanyId();
        String farmlandLocationId = farmlandLocation.getFarmlandLocationId();
        FarmlandLocation byFarmlandLocationId = farmlandLocationRepository.findByFarmlandLocationIdAndCompanyId(farmlandLocationId,companyId);
        byFarmlandLocationId.setIsDeleted(1);
        farmlandLocationRepository.save(byFarmlandLocationId);
        return YunResult.createBySuccess("删除成功！");
    }

    @ApiOperation(value = "区域详情")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public YunResult getById(@RequestParam("id") String id){
        String companyId = SecurityUtils.getCompanyId();
        FarmlandLocation farmlandLocation = farmlandLocationRepository.findByFarmlandLocationIdAndCompanyId(id, companyId);
        return YunResult.createBySuccess(farmlandLocation);
    }

}
