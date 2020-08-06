package com.pt.center.repository;

import com.pt.center.newentity.CompanyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAccountRepository extends JpaRepository<CompanyAccount,Integer> {
    CompanyAccount findByCode(String code);
}
