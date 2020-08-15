package com.pt.ptmanor.controller.paintingController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.aspect.BusinessType;
import com.pt.ptmanor.aspect.MyLog;
import com.pt.ptmanor.mapper.painting.*;
import com.pt.ptmanor.pojo.painting.*;
import com.pt.ptmanor.pojo.user.SysUser;
import com.pt.ptmanor.service.painting.WorkService;
import com.pt.ptmanor.service.user.UserService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestMapping("/planting/work")
@RestController
@Api(value = "/planting/work", tags = "工作单管理")
public class WorkController {

    @Autowired
    WorkService workService;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    ProductionRepository productionRepository;

    @Autowired
    FarmlandRegionRepository farmlandRegionRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    CropsRepository cropsRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    WorkTypeRepository workTypeRepository;

    @Autowired
    UserService userService;

    @ApiOperation(value = "待审核分页查询")
    @RequestMapping("/toCheckList")
    public YunResult toCheckList(int pageNum , int pageRow ){
        String companyId = SecurityUtils.getCompanyId();
        String userName = SecurityUtils.getUserName();
        Page page = workService.toCheckList(pageNum, pageRow,userName,companyId);
        return YunResult.createBySuccess(page);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();
        Page page = workService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "物料列表")
    @RequestMapping("/materialList")
    public YunResult materialList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();
        Page page = workService.materialList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "添加作业单")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.INSERT,value = "添加作业单")
    public YunResult save(@RequestBody Work work){
        if (work!= null && !work.equals("")){
            UUID u = UUID.randomUUID();
            String str = u.toString();
            str = str.replace("-","");
            work.setId(str);
            work.setStaff(SecurityUtils.getUserName());
            if(work.getMaterialName()!= ""){
                String companyId = SecurityUtils.getCompanyId();
                Material byMaterialName = materialRepository.findByMaterialNameAndCompanyId(work.getMaterialName(),companyId);
                work.setMaterialCompany(byMaterialName.getMaterialCompany());
                work.setMaterialInformation(byMaterialName.getMaterialInformation());
            }
            work.setCheckNumber(0);
            String companyId = SecurityUtils.getCompanyId();
            work.setIsDeleted(0);
            work.setDate(new Date());
            work.setUpdateDate(new Date());
            work.setCompanyId(companyId);
            workRepository.save(work);
            return  YunResult.createBySuccess("添加成功");
        }
        return YunResult.createByError();
    }

    @ApiOperation(value = "作业单详情")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    @MyLog(businessType= BusinessType.DETAIL,value = "作业单详情")
    public YunResult getById(@RequestParam("id") String id){
        Optional<Work> byId = workRepository.findById(id);
        Work work = byId.get();
        return YunResult.createBySuccess(work);
    }

    @ApiOperation(value = "已审核作业单")
    @RequestMapping("/finalList")
    public YunResult finalList(int pageNum , int pageRow ){
        String companyId = SecurityUtils.getCompanyId();
        Page page = workService.finalList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess(page);
    }

    @ApiOperation(value = "修改作业单")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.UPDATE,value = "修改作业单")
    public YunResult update(@RequestBody Work work){
        work.setCheckNumber(0);
        String companyId = SecurityUtils.getCompanyId();
        work.setUpdateDate(new Date());
        work.setCompanyId(companyId);
        workRepository.save(work);
        return YunResult.createBySuccess("修改成功！");

    }

    @ApiOperation(value = "获取公司ID")
    @RequestMapping(value = "getFarmlandRegionId",method = RequestMethod.GET)
    public YunResult getFarmlandRegionId(){
        String companyId = SecurityUtils.getCompanyId();
        List<FarmlandRegion> all = farmlandRegionRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

    @ApiOperation(value = "获取种植批次")
    @RequestMapping(value = "getBatch",method = RequestMethod.GET)
    public YunResult getBatch(){
        String companyId = SecurityUtils.getCompanyId();
        List<Batch> all = batchRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);

    }

    @ApiOperation(value = "获取农作物列表")
    @RequestMapping(value = "getCrops",method = RequestMethod.GET)
    public YunResult getCrops(){
        String companyId = SecurityUtils.getCompanyId();
        List<Crops> all = cropsRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

    @ApiOperation(value = "物料列表")
    @RequestMapping(value = "getMaterial",method = RequestMethod.GET)
    public YunResult getMaterial(){
        String companyId = SecurityUtils.getCompanyId();
        List<Material> all = materialRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

    @ApiOperation(value = "工作类型列表")
    @RequestMapping(value = "getWorkTypes",method = RequestMethod.GET)
    public YunResult getWorkTypes(){
        String companyId = SecurityUtils.getCompanyId();
        List<WorkType> all = workTypeRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

    @ApiOperation(value = "审核人员列表")
    @RequestMapping(value = "getCheckUsers",method = RequestMethod.GET)
    public YunResult getCheckUsers(){
        String companyId = SecurityUtils.getCompanyId();
        List<SysUser> checkInvoiceUserList = userService.getCheckInvoiceUserList(companyId);
        return YunResult.createBySuccess(checkInvoiceUserList);
    }

}
