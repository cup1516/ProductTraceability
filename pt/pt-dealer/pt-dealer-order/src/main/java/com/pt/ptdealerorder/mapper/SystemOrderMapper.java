package com.pt.ptdealerorder.mapper;

import com.pt.ptdealerorder.entity.SystemOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemOrderMapper {
    Boolean insertOrder(SystemOrder systemOrder);
}