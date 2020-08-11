package com.pt.ptmanor.mapper.painting;


import com.pt.ptmanor.pojo.painting.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch,String>, JpaSpecificationExecutor<Batch> {


    Batch findByIdAndAndCompanyId(String id, String companyId);

    List<Batch> findByIsDeletedAndCompanyId(Integer isDeleted, String companyId);
}
