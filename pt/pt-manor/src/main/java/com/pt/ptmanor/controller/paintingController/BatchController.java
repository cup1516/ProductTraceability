package com.pt.ptmanor.controller.paintingController;



import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.aspect.BusinessType;
import com.pt.ptmanor.aspect.MyLog;
import com.pt.ptmanor.mapper.painting.BatchRepository;
import com.pt.ptmanor.mapper.user.UserRepository;
import com.pt.ptmanor.pojo.painting.Batch;
import com.pt.ptmanor.service.painting.BatchService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/planting/batch")
@RestController
@Api(value = "/planting/batch", tags = "种植批次")
public class BatchController {

    @Autowired
    BatchService batchService;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "分页查询")
    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){
        String companyId = SecurityUtils.getCompanyId();
        Page page = batchService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }

    @ApiOperation(value = "添加种植批次")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.INSERT,value = "添加种植批次")
    public YunResult add(@RequestBody Batch batch){
        batch.setIsDeleted(0);
        String companyId = SecurityUtils.getCompanyId();
        batch.setCompanyId(companyId);
        batchRepository.save(batch);
        return YunResult.createBySuccess("保存成功！");
    }

    @ApiOperation(value = "删除种植批次")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.DELETE,value = "删除种植批次")
    public YunResult delete(@RequestBody Batch batch){
        String companyId = SecurityUtils.getCompanyId();
        Batch batch1 = batchRepository.findByIdAndAndCompanyId(batch.getId(), companyId);
        batch1.setIsDeleted(1);
        batchRepository.save(batch1);
        return YunResult.createBySuccess("删除成功！");
    }

    @ApiOperation(value = "更新批次")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @MyLog(businessType= BusinessType.UPDATE,value = "更新批次")
    public YunResult update(@RequestBody Batch batch){
        String companyId = SecurityUtils.getCompanyId();
        batch.setCompanyId(companyId);
        batchRepository.save(batch);
        return YunResult.createBySuccess("修改成功！");
    }

}
