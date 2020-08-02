package com.pt.center.repository;

import com.pt.center.newentity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {
    Consumer findByCode(String code);
    Consumer findByPhone(String phone);


    @Transactional
    void deleteByPhone(String phone);

}
