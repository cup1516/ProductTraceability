package com.pt.ptmanor.mapper.painting;


import com.pt.ptmanor.pojo.painting.FarmlandRegionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmlandRegionTypeRepository extends JpaRepository<FarmlandRegionType,String>, JpaSpecificationExecutor<FarmlandRegionType> {


    List<FarmlandRegionType> findByIsDeletedAndCompanyId(Integer isDeleted, String companyId);

    FarmlandRegionType findByIdAndCompanyId(String id, String companyId);

}
