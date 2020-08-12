package com.pt.ptmanor.controller.paintingController;

import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.painting.WorkTypeRepository;
import com.pt.ptmanor.pojo.painting.WorkType;
import com.pt.ptmanor.service.painting.WorkTypeService;
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


@RequestMapping("/planting/workType")
@RestController
@Api(value = "/planting/workType", tags = "工作类型")
public class WorkTypeController {

    @Autowired
    WorkTypeService workTypeService;

    @Autowired
    WorkTypeRepository workTypeRepository;

    @ApiOperation(value = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();
        Page page = workTypeService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "添加工作类型")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public YunResult add(@RequestBody JSONObject jsonObject){
        String companyId = SecurityUtils.getCompanyId();
        UUID u = UUID.randomUUID();
        String str = u.toString();
        str = str.replace("-","");
        String name = jsonObject.getString("name");
        Boolean needMaterial = jsonObject.getBoolean("needMaterial");
        WorkType workType = new WorkType();
        if (needMaterial == true){
            String a  = "是";
            workType.setNeedMaterial(a);
        }else {
            String a = "否";
            workType.setNeedMaterial(a);
        }
        workType.setName(name);
        workType.setId(str);
        workType.setIsDeleted(0);
        workType.setCompanyId(companyId);
        workTypeRepository.save(workType);
        return YunResult.createBySuccess("保存成功！");
    }

    @ApiOperation(value = "删除工作类型")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public YunResult delete(@RequestBody WorkType workType){
        System.out.println(workType.getId());
        Optional<WorkType> byId = workTypeRepository.findById(workType.getId());
        WorkType workType1 = byId.get();
        workType1.setIsDeleted(1);
        workTypeRepository.save(workType1);
        return YunResult.createBySuccess("删除成功！");
    }

}
