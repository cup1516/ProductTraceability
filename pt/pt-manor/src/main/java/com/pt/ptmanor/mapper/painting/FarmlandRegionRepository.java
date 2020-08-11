package com.pt.ptmanor.mapper.painting;


import com.pt.ptmanor.pojo.painting.FarmlandRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmlandRegionRepository extends JpaRepository<FarmlandRegion,String>, JpaSpecificationExecutor<FarmlandRegion> {


    FarmlandRegion findByFarmlandRegionIdAndCompanyId(String farmlandRegionId, String companyId);

    List<FarmlandRegion> findByIsDeletedAndCompanyId(Integer isDeleted, String companyId);



}
