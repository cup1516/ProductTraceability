package com.pt.company_id.repository;

import com.pt.company_id.entity.CompanyId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyIdRepository extends JpaRepository<CompanyId, String> {
    CompanyId findByCompanyName(String company_name);
    CompanyId findByUrl(String url);
    CompanyId findByCompanyId(String company_id);
    List<CompanyId> findByCompanyNameLike(String company_name);
}
