package com.pt.ptmanor.mapper.painting;


import com.pt.ptmanor.pojo.painting.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material,String>, JpaSpecificationExecutor<Material> {

    Material findByMaterialNameAndCompanyId(String materialName, String companyId);

    List<Material> findByIsDeletedAndCompanyId(Integer isDeleted, String companyId);
}
