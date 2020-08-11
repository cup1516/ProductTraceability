package com.pt.ptmanor.controller.paintingController;

import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.painting.MaterialRepository;
import com.pt.ptmanor.pojo.painting.Material;
import com.pt.ptmanor.service.painting.MaterialService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RequestMapping("/planting/material")
@RestController
public class MaterialController {

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    MaterialService materialService;



    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){

        String companyId = SecurityUtils.getCompanyId();
        Page page = materialService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public YunResult add(@RequestBody JSONObject jsonObject){

        UUID u = UUID.randomUUID();
        String str = u.toString();
        str = str.replace("-","");
        String materialName = jsonObject.getString("materialName");
        String materialInformation = jsonObject.getString("materialInformation");
        String materialCompany = jsonObject.getString("materialCompany");


        String companyId = SecurityUtils.getCompanyId();
        Material material = new Material();
        material.setCompanyId(companyId);
        material.setId(str);
        material.setIsDeleted(0);
        material.setMaterialCompany(materialCompany);
        material.setMaterialName(materialName);
        material.setMaterialInformation(materialInformation);

        materialRepository.save(material);
        return YunResult.createBySuccess("保存成功！");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public YunResult delete(@RequestBody Material material){

        Optional<Material> byId = materialRepository.findById(material.getId());
        Material material1 = byId.get();
        material1.setIsDeleted(1);
        materialRepository.save(material1);
        return YunResult.createBySuccess("删除成功！");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public YunResult update(@RequestBody Material material){
        String companyId = SecurityUtils.getCompanyId();
        material.setCompanyId(companyId);
        material.setIsDeleted(0);
        materialRepository.save(material);
        return YunResult.createBySuccess("修改成功！");
    }

    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){

        String companyId = SecurityUtils.getCompanyId();
        String materialName = jsonObject.getString("materialName");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        Page page = materialService.findByMany(materialName,pageNum,pageRow,companyId);
        return YunResult.createBySuccess(page);
    }

}
