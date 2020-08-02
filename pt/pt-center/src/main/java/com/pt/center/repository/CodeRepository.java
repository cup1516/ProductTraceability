package com.pt.center.repository;


import com.pt.center.newentity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Integer> {
    List<Code> findByCode(String code);
}
