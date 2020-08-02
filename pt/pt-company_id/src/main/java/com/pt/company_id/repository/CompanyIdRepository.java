package com.pt.company_id.repository;

import com.pt.company_id.entity.CompanyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyIdRepository extends JpaRepository<CompanyId, String> {
    CompanyId findByCompanyName(String company_name);
    CompanyId findByUrl(String url);
    CompanyId findByCompanyId(String company_id);
}
