package com.pt.ptmanor.mapper.painting;


import com.pt.ptmanor.pojo.painting.Crops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropsRepository extends JpaRepository<Crops,String>, JpaSpecificationExecutor<Crops> {

    Crops findByIdAndCompanyId(String id, String companyId);

    List<Crops>findByIsDeletedAndCompanyId(Integer isDeleted, String companyId);


}
