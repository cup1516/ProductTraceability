package com.pt.ptmanor.controller;


import com.pt.ptmanor.service.SysLogService;
import com.pt.ptmanor.util.YunResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor/syslog")
@ApiOperation(value = "系统日志")
public class SysLogController {

    @Autowired
    SysLogService sysLogService;

    @RequestMapping("/list")
    @ApiOperation(value = "分页查询")
    public YunResult getList(int pageNum , int pageRow){
        Page list = sysLogService.list(pageNum, pageRow);
        return YunResult.createBySuccess("查询成功！",list);
    }
}
