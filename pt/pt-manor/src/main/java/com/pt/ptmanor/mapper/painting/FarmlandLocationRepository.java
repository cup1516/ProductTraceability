package com.pt.ptmanor.mapper.painting;



import com.pt.ptmanor.pojo.painting.FarmlandLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FarmlandLocationRepository extends JpaRepository<FarmlandLocation,String>, JpaSpecificationExecutor<FarmlandLocation> {

    FarmlandLocation findByFarmlandLocationNameAndCompanyId(String FarmlandLocationName, String companyId);

    FarmlandLocation findByFarmlandLocationIdAndCompanyId(String farmlandLocationId, String companyId);

    List<FarmlandLocation> findByIsDeletedAndCompanyId(Integer isDeleted, String companyId);
}
