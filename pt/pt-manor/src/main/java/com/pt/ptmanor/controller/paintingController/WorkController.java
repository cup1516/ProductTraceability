package com.pt.ptmanor.controller.paintingController;


import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.painting.*;
import com.pt.ptmanor.pojo.painting.*;
import com.pt.ptmanor.pojo.user.SysUser;
import com.pt.ptmanor.service.painting.WorkService;
import com.pt.ptmanor.service.user.UserService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestMapping("/planting/work")
@RestController
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




    @RequestMapping("/toCheckList")
    public YunResult toCheckList(int pageNum , int pageRow ){


        String companyId = SecurityUtils.getCompanyId();
        String userName = SecurityUtils.getUserName();
        Page page = workService.toCheckList(pageNum, pageRow,userName,companyId);
        return YunResult.createBySuccess(page);
    }

    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){

        String companyId = SecurityUtils.getCompanyId();
        Page page = workService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @RequestMapping("/materialList")
    public YunResult materialList(int pageNum , int pageRow){

        String companyId = SecurityUtils.getCompanyId();
        Page page = workService.materialList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    //添加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
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


    @RequestMapping(value = "get",method = RequestMethod.GET)
    public YunResult getById(@RequestParam("id") String id){
        Optional<Work> byId = workRepository.findById(id);
        Work work = byId.get();
        return YunResult.createBySuccess(work);
    }


    //审核完成的列表
    @RequestMapping("/finalList")
    public YunResult finalList(int pageNum , int pageRow ){
        String companyId = SecurityUtils.getCompanyId();
        Page page = workService.finalList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess(page);
    }



    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public YunResult delete(@RequestBody Work work){

        String id = work.getId();
        Optional<Work> byId = workRepository.findById(id);
        Work work1 = byId.get();
//        if(work1.getWork().equals("采摘")){
//            String companyId = SecurityUtils.getCompanyId();
//            Optional<Production> byId1 = productionRepository.findById(work.getId());
//            Production production1 = byId1.get();
//            production1.setIsDeleted(1);
//            production1.setCompanyId(companyId);
//            productionRepository.save(production1);
//        }
        work1.setIsDeleted(1);
        workRepository.save(work1);
        return YunResult.createBySuccess("删除成功！");
    }

    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){

        String id = jsonObject.getString("id");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        String crops = jsonObject.getString("crops");
        String work = jsonObject.getString("work");
        String staff = jsonObject.getString("staff");
        String findFarmlandRegionId = jsonObject.getString("findFarmlandRegionId");
        Date etime = jsonObject.getDate("etime");
        Date stime  = jsonObject.getDate("stime");
        String companyId = SecurityUtils.getCompanyId();
        Page page = workService.findByMany(companyId,stime, etime, work, crops, staff, id, findFarmlandRegionId, pageNum, pageRow);
        return  YunResult.createBySuccess(page);
    }

    @RequestMapping(value = "/findByManyMaterial",method = RequestMethod.POST)
    public YunResult findMaterial(@RequestBody JSONObject jsonObject){
        String companyId = SecurityUtils.getCompanyId();
        String id = jsonObject.getString("id");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        String crops = jsonObject.getString("crops");
        String work = jsonObject.getString("work");
        String staff = jsonObject.getString("staff");
        String field = jsonObject.getString("field");
        Date etime = jsonObject.getDate("etime");
        Date stime  = jsonObject.getDate("stime");
        Page page = workService.findByManyMaterial(companyId,stime, etime, work, crops, staff, id, field, pageNum, pageRow);
        return  YunResult.createBySuccess(page);

    }



    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public YunResult update(@RequestBody Work work){


        work.setCheckNumber(0);
        String companyId = SecurityUtils.getCompanyId();
        work.setUpdateDate(new Date());
        work.setCompanyId(companyId);
        workRepository.save(work);
        return YunResult.createBySuccess("修改成功！");

    }

    @RequestMapping(value = "getFarmlandRegionId",method = RequestMethod.GET)
    public YunResult getFarmlandRegionId(){
        String companyId = SecurityUtils.getCompanyId();
        List<FarmlandRegion> all = farmlandRegionRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }


    @RequestMapping(value = "getBatch",method = RequestMethod.GET)
    public YunResult getBatch(){
        String companyId = SecurityUtils.getCompanyId();
        List<Batch> all = batchRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);

    }


    @RequestMapping(value = "getCrops",method = RequestMethod.GET)
    public YunResult getCrops(){

        String companyId = SecurityUtils.getCompanyId();
        List<Crops> all = cropsRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }


    @RequestMapping(value = "getMaterial",method = RequestMethod.GET)
    public YunResult getMaterial(){
        String companyId = SecurityUtils.getCompanyId();
        List<Material> all = materialRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }


    @Autowired
    WorkTypeRepository workTypeRepository;

    @RequestMapping(value = "getWorkTypes",method = RequestMethod.GET)
    public YunResult getWorkTypes(){
        String companyId = SecurityUtils.getCompanyId();
        List<WorkType> all = workTypeRepository.findByIsDeletedAndCompanyId(0,companyId);

        return YunResult.createBySuccess(all);
    }


    @Autowired
    UserService userService;

    @RequestMapping(value = "getCheckUsers",method = RequestMethod.GET)
    public YunResult getCheckUsers(){

        String companyId = SecurityUtils.getCompanyId();
        List<SysUser> checkInvoiceUserList = userService.getCheckInvoiceUserList(companyId);

        return YunResult.createBySuccess(checkInvoiceUserList);
    }






}
