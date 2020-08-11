package com.pt.ptmanor.mapper;


import com.pt.ptmanor.pojo.FinancialForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialFormRepository extends JpaRepository<FinancialForm,String>, JpaSpecificationExecutor<FinancialForm> {

    List<FinancialForm> findByIsDeletedAndYearAndCompanyId(Integer isDeleted, String year, String companyId);

    List<FinancialForm> findByIsDeletedAndAndBuyerNameAndYearAndCompanyId(Integer isDeleted, String buyerName, String year, String companyId);

    List<FinancialForm> findByIsDeletedAndAndBuyerNameAndProductNameAndYearAndCompanyId(Integer isDeleted, String buyerName, String productName, String year, String companyId);
}
