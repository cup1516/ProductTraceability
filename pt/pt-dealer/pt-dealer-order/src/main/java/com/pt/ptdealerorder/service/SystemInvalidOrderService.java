package com.pt.ptdealerorder.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerorder.entity.SystemOrder;

import java.util.List;

public interface SystemInvalidOrderService {



    IPage<List<SystemOrder>> getInvalidOrderPage(Page page, String userId, String companyId);


}
