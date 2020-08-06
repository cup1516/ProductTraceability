package com.pt.ptdealerorder.service.impl;

import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerorder.entity.SystemOrder;
import com.pt.ptdealerorder.mapper.SystemOrderMapper;
import com.pt.ptdealerorder.service.SystemOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class SystemOrderServiceImpl implements SystemOrderService{


    private SystemOrderMapper systemOrderMapper;

    @Override
    public Boolean insertOrder(SystemOrder systemOrder) {
        systemOrder.setOrderId(IdUtils.simpleUUID());
        systemOrder.setCreatorId(SecurityUtils.getId());
        systemOrder.setCreatorName(SecurityUtils.getNickName());
        systemOrder.setSellerId(SecurityUtils.getCompanyId());
        return systemOrderMapper.insertOrder(systemOrder);
    }
}
