package com.pt.ptdealerorder.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerorder.constant.CheckFlagConstants;
import com.pt.ptdealerorder.entity.SystemOrder;
import com.pt.ptdealerorder.mapper.SystemMyOrderMapper;
import com.pt.ptdealerorder.service.SystemMyOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SystemMyOrderServiceImpl implements SystemMyOrderService {


    private SystemMyOrderMapper systemMyOrderMapper;

    @Override
    public Boolean insertOrder(SystemOrder systemOrder) {
        systemOrder.setOrderId(IdUtils.simpleUUID());
        systemOrder.setCreatorId(SecurityUtils.getId());
        systemOrder.setCreatorName(SecurityUtils.getNickName());
        systemOrder.setSellerId(SecurityUtils.getCompanyId());
        systemOrder.setSellerName(SecurityUtils.getCompanyName());
        if (StrUtil.isNotEmpty(systemOrder.getPreviousId())){
            SystemOrder preOrder = selectOrderById(systemOrder.getPreviousId());
            preOrder.setNextId(systemOrder.getOrderId());
            systemMyOrderMapper.updateCorrelatedIds(preOrder);
        }
        return systemMyOrderMapper.insertOrder(systemOrder);
    }

    @Override
    public IPage<List<SystemOrder>> getSystemOrderPage(Page page,SystemOrder systemOrder,String userId,String companyId) {
        return systemMyOrderMapper.getSystemOrderPage(page,systemOrder,userId,companyId);
    }

    @Override
    public SystemOrder selectOrderById(String orderId) {
        return systemMyOrderMapper.selectOrderById(orderId);
    }

    @Override
    public Boolean deleteOrderByIds(String[] orderIds) {
        for (String orderId : orderIds) {
            SystemOrder systemOrder = selectOrderById(orderId);
            switch (systemOrder.getCheckFlag()){
                case "1": throw new CustomException(CheckFlagConstants.Exception_CheckFlag_1_Delete);
                case "2": throw new CustomException(CheckFlagConstants.Exception_CheckFlag_2_Delete);
                case "3": throw new CustomException(CheckFlagConstants.Exception_CheckFlag_3_Delete);
            }
        }
        systemMyOrderMapper.deleteOrderByIds(orderIds);
        return Boolean.TRUE;
    }

    @Override
    public Boolean changeCheckFlag(SystemOrder systemOrder) {
        return systemMyOrderMapper.changeCheckFlag(systemOrder);
    }
}
