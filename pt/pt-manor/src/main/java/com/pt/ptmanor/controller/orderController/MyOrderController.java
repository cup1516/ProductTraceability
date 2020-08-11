package com.pt.ptmanor.controller.orderController;

import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.SystemCompanyInfoRepository;
import com.pt.ptmanor.mapper.SystemOrderRepository;
import com.pt.ptmanor.mapper.painting.BatchRepository;
import com.pt.ptmanor.mapper.painting.CropsRepository;
import com.pt.ptmanor.mapper.painting.FarmlandRegionRepository;
import com.pt.ptmanor.mapper.user.UserRepository;
import com.pt.ptmanor.pojo.SystemCompanyInfo;
import com.pt.ptmanor.pojo.SystemOrder;
import com.pt.ptmanor.pojo.painting.Batch;
import com.pt.ptmanor.pojo.painting.Crops;
import com.pt.ptmanor.pojo.painting.FarmlandRegion;
import com.pt.ptmanor.pojo.product.Invoice;
import com.pt.ptmanor.pojo.user.SysUser;
import com.pt.ptmanor.service.SystemOrderService;
import com.pt.ptmanor.service.user.UserService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/order")
public class MyOrderController {

    @Autowired
    SystemOrderService systemOrderService;

    @Autowired
    SystemOrderRepository systemOrderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SystemCompanyInfoRepository systemCompanyInfoRepository;

    @Autowired
    CropsRepository cropsRepository;


    @RequestMapping("/myList")
    public YunResult getList(int pageNum , int pageRow){

        String companyId = SecurityUtils.getCompanyId();

        Page page = systemOrderService.myList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public YunResult add (@RequestBody SystemOrder systemOrder){

        UUID u = UUID.randomUUID();
        String str = u.toString();
        str = str.replace("-","");
        systemOrder.setOrderId(str);
        systemOrder.setCheckFlag("0");
        systemOrder.setCheckStatus("0");
        systemOrder.setCreateTime(new Date());
        systemOrder.setDelflag("0");
        systemOrder.setCreatorId(SecurityUtils.getId());
        systemOrder.setCreatorName(SecurityUtils.getNickName());
        systemOrder.setBuyerName(systemCompanyInfoRepository.findByCompanyId(systemOrder.getBuyerId()).getCompanyName());

        Optional<SysUser> byId1 = userRepository.findById(systemOrder.getReviewerId());
        SysUser sysUser = byId1.get();

        systemOrder.setReviewerName(sysUser.getNickName());

        Optional<Crops> byId = cropsRepository.findById(systemOrder.getProductId());
        Crops crops = byId.get();
        systemOrder.setProductName(crops.getName());

        systemOrderRepository.save(systemOrder);

        return YunResult.createBySuccess("添加成功！");
    }

    @RequestMapping(value = "/myCompanyInfo",method = RequestMethod.GET)
    public YunResult getCompanyId(){

        return YunResult.createBySuccess(SecurityUtils.getCompanyId());
    }

    @RequestMapping(value = "/myCompanyName",method = RequestMethod.GET)
    public YunResult getCompanyName(){

        SystemCompanyInfo byCompanyId = systemCompanyInfoRepository.findByCompanyId(SecurityUtils.getCompanyId());
        return YunResult.createBySuccess(byCompanyId.getCompanyName());
    }


    @RequestMapping(value = "/companyList",method = RequestMethod.GET)
    public YunResult getCompanyList(){

        List<SystemCompanyInfo> all = systemCompanyInfoRepository.findAll();

        for (int i=0;i<all.size();i++){
            if(all.get(i).getType()=="0"){
                all.remove(i);
                i--;
            }
            System.out.println(all.get(i).getCompanyName());
        }

        return YunResult.createBySuccess(all);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public YunResult delete(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setDelflag("1");
        systemOrderRepository.save(byOrderId);

        return  YunResult.createBySuccess("删除成功！");
    }


    @RequestMapping(value = "/toReview",method = RequestMethod.POST)
    public YunResult toCheck(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
       byOrderId.setCheckFlag("1");
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("删除成功！");
    }

    @RequestMapping(value = "/backToReview",method = RequestMethod.POST)
    public YunResult backToCheck(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckFlag("0");
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("删除成功！");
    }

    @RequestMapping(value = "get",method = RequestMethod.GET)
    public YunResult getById(@RequestParam("id") String id){
        SystemOrder byOrderId = systemOrderRepository.findByOrderId(id);
        return YunResult.createBySuccess(byOrderId);
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public YunResult update(@RequestBody SystemOrder systemOrder){

        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        UUID u = UUID.randomUUID();
        String str = u.toString();
        str = str.replace("-","");
        byOrderId.setNextId(str);
        systemOrderRepository.save(byOrderId);

        systemOrder.setOrderId(str);
        systemOrder.setPreviousId(byOrderId.getOrderId());
        systemOrder.setCheckFlag("0");
        systemOrder.setCheckStatus("0");
        systemOrder.setCreateTime(new Date());
        systemOrder.setDelflag("0");
        systemOrder.setCreatorId(SecurityUtils.getId());
        systemOrder.setCreatorName(SecurityUtils.getNickName());
        systemOrder.setBuyerName(systemCompanyInfoRepository.findByCompanyId(systemOrder.getBuyerId()).getCompanyName());
        Optional<SysUser> byId1 = userRepository.findById(systemOrder.getReviewerId());
        SysUser sysUser = byId1.get();

        systemOrder.setReviewerName(sysUser.getNickName());

        Optional<Crops> byId = cropsRepository.findById(systemOrder.getProductId());
        Crops crops = byId.get();
        systemOrder.setProductName(crops.getName());
        systemOrderRepository.save(systemOrder);
        return  YunResult.createBySuccess("修改成功!");
    }


    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject){

        String orderId = jsonObject.getString("orderId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageRow = jsonObject.getInteger("pageRow");
        String checkFlag = jsonObject.getString("checkFlag");
        String buyerName = jsonObject.getString("buyerName");
        String productName = jsonObject.getString("productName");


        String userId = SecurityUtils.getId();
        String companyId = SecurityUtils.getCompanyId();
        Date etime = jsonObject.getDate("etime");
        Date stime = jsonObject.getDate("stime");

        Page page = systemOrderService.myOrderFindByMany(companyId, userId, stime, etime, checkFlag, buyerName, productName, orderId, pageNum, pageRow);
        return YunResult.createBySuccess(page);

    }



    @RequestMapping(value = "/getCrops",method = RequestMethod.GET)
    public YunResult getCrops(){

        SysUser byUserName = userRepository.findByUserName(SecurityUtils.getUserName());
        String companyId = byUserName.getCompanyId();
        List<Crops> all = cropsRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

    @Autowired
    FarmlandRegionRepository farmlandRegionRepository;

    @RequestMapping(value = "/getFarmlandRegionId",method = RequestMethod.GET)
    public YunResult getFarmlandRegionId(){
        String companyId = SecurityUtils.getCompanyId();
        List<FarmlandRegion> all = farmlandRegionRepository.findByIsDeletedAndCompanyId(0,companyId);


        return YunResult.createBySuccess(all);

    }

    @Autowired
    BatchRepository batchRepository;

    @RequestMapping(value = "/getBatch",method = RequestMethod.GET)
    public YunResult getBatch(){
        String companyId = SecurityUtils.getCompanyId();
        List<Batch> all = batchRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);

    }

    @Autowired
    UserService userService;


    @RequestMapping("/getCheckInvoiceUserList")
    public YunResult getCheckInvoiceUserList(){
        String companyId = SecurityUtils.getCompanyId();
        List<SysUser> checkInvoiceUserList = userService.getCheckInvoiceUserList(companyId);
        return YunResult.createBySuccess(checkInvoiceUserList);
    }





}
