package com.pt.ptmanor.mapper.painting;


import com.pt.ptmanor.pojo.painting.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType,String>, JpaSpecificationExecutor<WorkType> {

    List<WorkType>findByIsDeletedAndCompanyId(Integer isDeleted, String companyId);
}
