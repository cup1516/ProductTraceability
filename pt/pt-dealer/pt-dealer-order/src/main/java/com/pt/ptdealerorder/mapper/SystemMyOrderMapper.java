package com.pt.ptdealerorder.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerorder.entity.SystemOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemMyOrderMapper {
    Boolean insertOrder(SystemOrder systemOrder);

    IPage<List<SystemOrder>> getSystemOrderPage(Page page,@Param("systemOrder") SystemOrder systemOrder,@Param("userId") String userId, @Param("companyId") String companyId);

    SystemOrder selectOrderById(@Param("orderId") String orderId);

    Boolean deleteOrderByIds(@Param("orderIds") String[] orderIds);

    Boolean changeCheckFlag(@Param("systemOrder")SystemOrder systemOrder);

    Boolean updateCorrelatedIds(@Param("systemOrder")SystemOrder systemOrder);
}
