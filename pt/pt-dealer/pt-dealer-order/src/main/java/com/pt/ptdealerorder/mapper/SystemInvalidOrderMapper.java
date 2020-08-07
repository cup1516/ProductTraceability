package com.pt.ptdealerorder.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerorder.entity.SystemOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemInvalidOrderMapper {


    IPage<List<SystemOrder>> getInvalidOrderPage(Page page, @Param("userId") String userId, @Param("companyId") String companyId);


}
