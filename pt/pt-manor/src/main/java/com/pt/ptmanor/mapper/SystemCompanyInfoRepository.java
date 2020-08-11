package com.pt.ptmanor.mapper;

import com.pt.ptmanor.pojo.SystemCompanyInfo;
import com.pt.ptmanor.pojo.SystemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemCompanyInfoRepository extends JpaRepository<SystemCompanyInfo,String>, JpaSpecificationExecutor<SystemCompanyInfo> {

    SystemCompanyInfo findByCompanyId(String companyId);

}
