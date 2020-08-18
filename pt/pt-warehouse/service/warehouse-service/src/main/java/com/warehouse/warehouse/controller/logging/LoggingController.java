package com.warehouse.warehouse.controller.logging;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warehouse.warehouse.pojo.dto.logging.LoggingDTO;
import com.warehouse.warehouse.service.app.logging.LoggingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/logging",produces = "application/json;charset=utf-8")
public class LoggingController {

    @Resource
    private LoggingService loggingService;
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute LoggingDTO loggingDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<LoggingDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        List<LoggingDTO> list = this.loggingService.describeFuzzy(loggingDTO);
        PageInfo<LoggingDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }
}
