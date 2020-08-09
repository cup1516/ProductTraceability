package com.pt.ptdealerorder.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerorder.entity.SystemOrder;
import com.pt.ptdealerorder.mapper.SystemToCheckOrderMapper;
import com.pt.ptdealerorder.service.SystemToCheckOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SystemToCheckOrderServiceImpl implements SystemToCheckOrderService {


    private SystemToCheckOrderMapper systemToCheckOrderMapper;


    @Override
    public IPage<List<SystemOrder>> getSystemOrderPage(Page page,SystemOrder systemOrder,String userId,String companyId) {
        return systemToCheckOrderMapper.getSystemOrderPage(page,systemOrder,userId,companyId);
    }



    @Override
    public Boolean changeCheckStatus(SystemOrder systemOrder) {
        return systemToCheckOrderMapper.changeCheckStatus(systemOrder);
    }
}
