package com.pt.ptdealerorder.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerorder.entity.SystemOrder;
import com.pt.ptdealerorder.mapper.SystemInvalidOrderMapper;
import com.pt.ptdealerorder.service.SystemInvalidOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SystemInvalidOrderServiceImpl implements SystemInvalidOrderService {


    private SystemInvalidOrderMapper systemInvalidOrderMapper;


    @Override
    public IPage<List<SystemOrder>> getInvalidOrderPage(Page page,String userId,String companyId) {
        return systemInvalidOrderMapper.getInvalidOrderPage(page,userId,companyId);
    }

}
