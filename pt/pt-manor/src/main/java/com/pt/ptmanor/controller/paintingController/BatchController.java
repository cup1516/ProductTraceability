package com.pt.ptmanor.controller.paintingController;



import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.mapper.painting.BatchRepository;
import com.pt.ptmanor.mapper.user.UserRepository;
import com.pt.ptmanor.pojo.painting.Batch;
import com.pt.ptmanor.pojo.user.SysUser;
import com.pt.ptmanor.service.painting.BatchService;
import com.pt.ptmanor.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/planting/batch")
@RestController
public class BatchController {


    @Autowired
    BatchService batchService;

    @Autowired
    BatchRepository batchRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/list")
    public YunResult getList(int pageNum , int pageRow){

        String companyId = SecurityUtils.getCompanyId();
        Page page = batchService.list(pageNum, pageRow,companyId);
        return YunResult.createBySuccess("查询成功！",page);
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public YunResult add(@RequestBody Batch batch){

        batch.setIsDeleted(0);
        String companyId = SecurityUtils.getCompanyId();
        batch.setCompanyId(companyId);
        batchRepository.save(batch);
        return YunResult.createBySuccess("保存成功！");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public YunResult delete(@RequestBody Batch batch){

        String companyId = SecurityUtils.getCompanyId();

        Batch batch1 = batchRepository.findByIdAndAndCompanyId(batch.getId(), companyId);
        batch1.setIsDeleted(1);
        batchRepository.save(batch1);
        return YunResult.createBySuccess("删除成功！");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public YunResult update(@RequestBody Batch batch){


        String companyId = SecurityUtils.getCompanyId();
        batch.setCompanyId(companyId);
        batchRepository.save(batch);
        return YunResult.createBySuccess("修改成功！");
    }


}
