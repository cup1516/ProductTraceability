package com.warehouse.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.logging.LoggingMapper;
import com.warehouse.warehouse.pojo.dto.logging.LoggingDTO;
import com.warehouse.warehouse.pojo.po.logging.Logging;
import com.warehouse.warehouse.service.app.logging.LoggingService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service("LoggingService")
public class LoggingServiceImpl implements LoggingService {
    @Resource
    private LoggingMapper loggingMapper;
    @Override
    public List<LoggingDTO> describeFuzzy(LoggingDTO loggingDTO) {
        Logging logging = new Logging();
        BeanUtils.copyProperties(loggingDTO, logging);
        List<Logging> list = this.loggingMapper.describeFuzzy(logging);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), LoggingDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
}
