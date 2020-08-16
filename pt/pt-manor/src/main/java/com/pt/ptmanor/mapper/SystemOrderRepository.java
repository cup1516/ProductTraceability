package com.pt.ptmanor.mapper;

import com.pt.ptmanor.pojo.SaleAmount;
import com.pt.ptmanor.pojo.SystemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemOrderRepository extends JpaRepository<SystemOrder,String>, JpaSpecificationExecutor<SystemOrder> {

    SystemOrder findByOrderId(String orderId);

}
