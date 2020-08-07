package com.pt.ptdealerorder.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerorder.entity.SystemOrder;

import java.util.List;

public interface SystemMyOrderService {

    Boolean insertOrder(SystemOrder systemOrder);

    IPage<List<SystemOrder>> getSystemOrderPage(Page page,String userId,String companyId);

    SystemOrder selectOrderById(String orderId);

    Boolean deleteOrderByIds(String[] orderIds);

    Boolean changeCheckFlag(SystemOrder systemOrder);
}
