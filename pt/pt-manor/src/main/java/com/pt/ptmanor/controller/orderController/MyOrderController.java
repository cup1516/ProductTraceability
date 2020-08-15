package com.pt.ptmanor.controller.orderController;

import com.alibaba.fastjson.JSONObject;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.aspect.BusinessType;
import com.pt.ptmanor.aspect.MyLog;
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
import com.pt.ptmanor.pojo.user.SysUser;
import com.pt.ptmanor.service.SystemOrderService;
import com.pt.ptmanor.service.user.UserService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/order")
@Api(value = "order", tags = "我的订单")
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

    @Autowired
    UserService userService;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    FarmlandRegionRepository farmlandRegionRepository;

    @ApiOperation(value = "分页查询")
    @RequestMapping("/myList")
    public YunResult getList(int pageNum , int pageRow)
    {
        String companyId = SecurityUtils.getCompanyId();
        Page page = systemOrderService.myList(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "添加票据")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.OTHER,value = "查看我的票据")
    public YunResult add (@RequestBody SystemOrder systemOrder)
    {
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
        SysUser sysUser = userRepository.findByCompanyIdAndUserId(SecurityUtils.getCompanyId(), systemOrder.getReviewerId());
        systemOrder.setReviewerName(sysUser.getNickName());
        Optional<Crops> byId = cropsRepository.findById(systemOrder.getProductId());
        Crops crops = byId.get();
        systemOrder.setProductName(crops.getName());
        systemOrderRepository.save(systemOrder);
        return YunResult.createBySuccess("添加成功！");
    }

    @ApiOperation(value = "我的公司ID")
    @RequestMapping(value = "/myCompanyInfo",method = RequestMethod.GET)
    public YunResult getCompanyId()
    {
        return YunResult.createBySuccess(SecurityUtils.getCompanyId());
    }

    @ApiOperation(value = "我的公司名")
    @RequestMapping(value = "/myCompanyName",method = RequestMethod.GET)
    public YunResult getCompanyName()
    {
        SystemCompanyInfo byCompanyId = systemCompanyInfoRepository.findByCompanyId(SecurityUtils.getCompanyId());
        return YunResult.createBySuccess(byCompanyId.getCompanyName());
    }

    @ApiOperation(value = "删除订单")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.DELETE,value = "删除订单")
    public YunResult delete(@RequestBody SystemOrder systemOrder)
    {
        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setDelflag("1");
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("删除成功！");
    }

    @ApiOperation(value = "送审订单")
    @RequestMapping(value = "/toReview",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.UPDATE,value = "送审订单")
    public YunResult toCheck(@RequestBody SystemOrder systemOrder)
    {
        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckFlag("1");
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("删除成功！");
    }

    @ApiOperation(value = "撤回送审")
    @RequestMapping(value = "/backToReview",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.UPDATE,value = "撤回送审")
    public YunResult backToCheck(@RequestBody SystemOrder systemOrder)
    {
        SystemOrder byOrderId = systemOrderRepository.findByOrderId(systemOrder.getOrderId());
        byOrderId.setCheckFlag("0");
        systemOrderRepository.save(byOrderId);
        return  YunResult.createBySuccess("删除成功！");
    }

    @ApiOperation(value = "订单详情")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    @MyLog(businessType= BusinessType.OTHER,value = "订单详情")
    public YunResult getById(@RequestParam("id") String id)
    {
        SystemOrder byOrderId = systemOrderRepository.findByOrderId(id);
        return YunResult.createBySuccess(byOrderId);
    }

    @ApiOperation(value = "修改订单")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.UPDATE,value = "修改订单")
    public YunResult update(@RequestBody SystemOrder systemOrder)
    {
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

    @ApiOperation(value = "条件查找")
    @RequestMapping(value = "/findByMany",method = RequestMethod.POST)
    public YunResult find(@RequestBody JSONObject jsonObject)
    {
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


    @ApiOperation(value = "农作物列表")
    @RequestMapping(value = "/getCrops",method = RequestMethod.GET)
    public YunResult getCrops()
    {
        String companyId = SecurityUtils.getCompanyId();
        List<Crops> all = cropsRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

    @ApiOperation(value = "地块编号列表")
    @RequestMapping(value = "/getFarmlandRegionId",method = RequestMethod.GET)
    public YunResult getFarmlandRegionId()
    {
        String companyId = SecurityUtils.getCompanyId();
        List<FarmlandRegion> all = farmlandRegionRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);
    }

    @ApiOperation(value = "种植批次列表")
    @RequestMapping(value = "/getBatch",method = RequestMethod.GET)
    public YunResult getBatch()
    {
        String companyId = SecurityUtils.getCompanyId();
        List<Batch> all = batchRepository.findByIsDeletedAndCompanyId(0,companyId);
        return YunResult.createBySuccess(all);

    }

    @ApiOperation(value = "获取审批人员名单")
    @RequestMapping("/getCheckInvoiceUserList")
    public YunResult getCheckInvoiceUserList()
    {
        String companyId = SecurityUtils.getCompanyId();
        List<SysUser> checkInvoiceUserList = userService.getCheckInvoiceUserList(companyId);
        return YunResult.createBySuccess(checkInvoiceUserList);
    }

}
