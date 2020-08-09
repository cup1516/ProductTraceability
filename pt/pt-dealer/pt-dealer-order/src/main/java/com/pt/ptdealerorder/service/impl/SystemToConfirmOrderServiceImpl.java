package com.pt.ptdealerorder.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerorder.entity.SystemOrder;
import com.pt.ptdealerorder.mapper.SystemToConfirmOrderMapper;
import com.pt.ptdealerorder.service.SystemToConfirmOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SystemToConfirmOrderServiceImpl implements SystemToConfirmOrderService {


    private SystemToConfirmOrderMapper systemToConfirmOrderMapper;


    @Override
    public IPage<List<SystemOrder>> getSystemOrderPage(Page page,SystemOrder systemOrder,String userId,String companyId) {
        return systemToConfirmOrderMapper.getSystemOrderPage(page,systemOrder,userId,companyId);
    }



    @Override
    public Boolean changeCheckStatus(SystemOrder systemOrder) {
        systemOrder.setCheckerId(SecurityUtils.getId());
        systemOrder.setCheckerName(SecurityUtils.getNickName());
        return systemToConfirmOrderMapper.changeCheckStatus(systemOrder);
    }
}
