package com.pt.ptmanor.mapper;


import com.pt.ptmanor.pojo.SaleAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleAmountRepository  extends JpaRepository<SaleAmount,String>, JpaSpecificationExecutor<SaleAmount> {


    List<SaleAmount> findByIsDeletedAndYearAndCompanyId(Integer isDeleted, String year, String companyId);

    List<SaleAmount> findByIsDeletedAndYearAndCropsAndCompanyId(Integer isDeleted, String year, String crops, String companyId);

    List<SaleAmount> findByIsDeletedAndYearAndCropsAndBuyerNameAndCompanyId(Integer isDeleted, String year, String crops, String buyerName, String companyId);

    List<SaleAmount> findByIsDeletedAndYearAndBuyerNameAndCompanyId(Integer isDeleted, String year, String buyerName, String companyId);
}
