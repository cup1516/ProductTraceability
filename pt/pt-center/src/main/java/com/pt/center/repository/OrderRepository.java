package com.pt.center.repository;

import com.pt.center.newentity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByNumber(String number);

    @Transactional
    void deleteByNumber(String number);
}
