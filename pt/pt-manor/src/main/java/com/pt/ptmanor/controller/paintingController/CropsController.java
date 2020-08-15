package com.pt.ptmanor.controller.paintingController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.aspect.BusinessType;
import com.pt.ptmanor.aspect.MyLog;
import com.pt.ptmanor.mapper.painting.CropsRepository;
import com.pt.ptmanor.pojo.painting.Crops;
import com.pt.ptmanor.service.painting.CropsService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RequestMapping("/planting/crops")
@RestController
@Api(value = "/planting/crops", tags = "农作物管理")
public class CropsController {

    @Autowired
    CropsRepository cropsRepository;

    @Autowired
    CropsService cropsService;

    @ApiOperation(value = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();
        Page page = cropsService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "条件查询")
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){
        String companyId = SecurityUtils.getCompanyId();
        String name = jsonObject.getString("name");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        Page page = cropsService.findByMany(name,pageNum,pageRow,companyId);
        return YunResult.createBySuccess(page);
    }

    @ApiOperation(value = "增加农作物")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.INSERT,value = "增加农作物")
    public YunResult add(@RequestBody JSONObject jsonObject){
        String companyId = SecurityUtils.getCompanyId();
        UUID u = UUID.randomUUID();
        String str = u.toString();
        str = str.replace("-","");
        String name = jsonObject.getString("name");
        String remark = jsonObject.getString("remark");
        Crops crops = new Crops();
        crops.setIsDeleted(0);
        crops.setName(name);
        crops.setRemark(remark);
        crops.setId(str);
        crops.setCompanyId(companyId);
        cropsRepository.save(crops);
        return YunResult.createBySuccess("保存成功！");
    }

    @ApiOperation(value = "删除农作物")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.DELETE,value = "删除农作物")
    public YunResult delete(@RequestBody Crops crops){
        String companyId = SecurityUtils.getCompanyId();
        Crops crops1 = cropsRepository.findByIdAndCompanyId(crops.getId(), companyId);
        crops1.setIsDeleted(1);
        cropsRepository.save(crops1);
        return YunResult.createBySuccess("删除成功！");
    }

    @ApiOperation(value = "修改农作物")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.UPDATE,value = "修改农作物")
    public YunResult update(@RequestBody Crops crops){
        String companyId = SecurityUtils.getCompanyId();
        Crops crops1 = cropsRepository.findByIdAndCompanyId(crops.getId(), companyId);
        crops1.setName(crops.getName());
        cropsRepository.save(crops1);
        return YunResult.createBySuccess("修改成功！");
    }

}
