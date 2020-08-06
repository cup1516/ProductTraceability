package com.pt.center.repository;


import com.pt.center.newentity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByName(String name);
    Company findByCode(String code);
    Company findByNumber(String number);
    Company findByUsername(String username);
}
