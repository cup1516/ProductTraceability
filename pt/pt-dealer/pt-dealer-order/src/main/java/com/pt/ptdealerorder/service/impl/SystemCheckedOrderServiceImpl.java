package com.pt.ptdealerorder.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerorder.entity.SystemOrder;
import com.pt.ptdealerorder.mapper.SystemCheckedOrderMapper;
import com.pt.ptdealerorder.service.SystemCheckedOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SystemCheckedOrderServiceImpl implements SystemCheckedOrderService {


    private SystemCheckedOrderMapper systemCheckedOrderMapper;


    @Override
    public IPage<List<SystemOrder>> getSystemOrderPage(Page page,SystemOrder systemOrder,String userId,String companyId) {
        return systemCheckedOrderMapper.getSystemOrderPage(page,systemOrder,userId,companyId);
    }




}
