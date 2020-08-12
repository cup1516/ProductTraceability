package com.pt.ptmanor.controller.paintingController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.painting.FarmlandLocationRepository;
import com.pt.ptmanor.mapper.painting.FarmlandRegionRepository;
import com.pt.ptmanor.mapper.painting.FarmlandRegionTypeRepository;
import com.pt.ptmanor.pojo.painting.FarmlandLocation;
import com.pt.ptmanor.pojo.painting.FarmlandRegion;
import com.pt.ptmanor.pojo.painting.FarmlandRegionType;
import com.pt.ptmanor.service.painting.FarmlandRegionService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping("/planting/farmlandRegion")
@RestController
@Api(value = "/planting/farmlandRegion", tags = "田地区块")
public class FarmlandRegionController {


    @Autowired
    FarmlandRegionService farmlandRegionService;

    @Autowired
    FarmlandRegionRepository farmlandRegionRepository;

    @Autowired
    FarmlandLocationRepository farmlandLocationRepository;

    @Autowired
    FarmlandRegionTypeRepository farmlandRegionTypeRepository;

    @ApiOperation(value = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();

        Page page = farmlandRegionService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "添加田地区块")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public YunResult add (@RequestBody FarmlandRegion farmlandRegion){
        String companyId = SecurityUtils.getCompanyId();
        String farmlandLocationName = farmlandRegion.getFarmlandLocationName();
        if (farmlandLocationName != ""){
            FarmlandLocation byFarmlandLocationName = farmlandLocationRepository.findByFarmlandLocationNameAndCompanyId(farmlandRegion.getFarmlandLocationName(),companyId);
            farmlandRegion.setIsDeleted(0);
            String companyId1 = SecurityUtils.getCompanyId();
            farmlandRegion.setFarmlandLocation(byFarmlandLocationName);
            farmlandRegion.setCompanyId(companyId1);
            farmlandRegionRepository.save(farmlandRegion);
            return YunResult.createBySuccess("添加成功！");
        }
        return  YunResult.createByErrorMessage("参数错误");
    }

    @ApiOperation(value = "根据ID查询区块")
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public YunResult find (@RequestParam String farmlandRegionId){
        Optional<FarmlandRegion> byId = farmlandRegionRepository.findById(farmlandRegionId);
        FarmlandRegion farmlandRegion = byId.get();
        return YunResult.createBySuccess("查询成功",farmlandRegion);
    }

    @ApiOperation(value = "条件查找")
    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){
        String farmlandRegionId = jsonObject.getString("farmlandRegionId");
        String farmlandRegionPrincipal = jsonObject.getString("farmlandRegionPrincipal");
        String farmlandRegionType = jsonObject.getString("farmlandRegionType");
        String farmlandRegionExplain = jsonObject.getString("farmlandRegionExplain");
        String farmlandLocationName = jsonObject.getString("farmlandLocationName");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        Page page = farmlandRegionService.findByMany(farmlandRegionId, farmlandRegionPrincipal, farmlandRegionType, farmlandRegionExplain, farmlandLocationName, pageNum, pageRow);
        return YunResult.createBySuccess(page);
    }

    @ApiOperation(value = "修改区块")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public YunResult update(@RequestBody FarmlandRegion farmlandRegion){
        farmlandRegionRepository.save(farmlandRegion);
        return YunResult.createBySuccess("修改成功！");
    }

    @ApiOperation(value = "删除区块")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public YunResult delete(@RequestBody FarmlandRegion farmlandRegion){
        String companyId = SecurityUtils.getCompanyId();
        String farmlandRegionId = farmlandRegion.getFarmlandRegionId();
        FarmlandRegion byFarmlandRegionId = farmlandRegionRepository.findByFarmlandRegionIdAndCompanyId(farmlandRegionId,companyId);
        byFarmlandRegionId.setIsDeleted(1);
        farmlandRegionRepository.save(byFarmlandRegionId);
        return YunResult.createBySuccess("删除成功！");
    }

    @ApiOperation(value = "田地区域列表")
    @RequestMapping(value = "/farmlandLocationName",method = RequestMethod.GET)
    public YunResult farmlandLocationName(){
        String companyId = SecurityUtils.getCompanyId();
        List<FarmlandLocation> all = farmlandLocationRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

    @ApiOperation(value = "区块详情")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public YunResult getById(@RequestParam("id") String id){
        String companyId = SecurityUtils.getCompanyId();
        FarmlandRegion farmlandRegion = farmlandRegionRepository.findByFarmlandRegionIdAndCompanyId(id,companyId);
        return YunResult.createBySuccess(farmlandRegion);
    }

    @ApiOperation(value = "田地类型")
    @RequestMapping(value = "getFarmlandRegionType",method = RequestMethod.GET)
    public YunResult getFarmlandRegionId(){
        String companyId = SecurityUtils.getCompanyId();
        List<FarmlandRegionType> all = farmlandRegionTypeRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

}
