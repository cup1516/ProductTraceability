package com.pt.ptportal.dao;


import com.pt.ptportal.entity.notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface noticeDao extends JpaRepository<notice, Integer>, JpaSpecificationExecutor<notice> {
     List<notice> findAllByNameAndStatusAndCompanyId(String name, Integer status,String companyId);
     List<notice> findAllByStatusAndIsCheckAndCompanyId(Integer status, int isCheck,String companyId);
     Optional<notice> findByIdAndCompanyIdAndStatus(Integer id, String companyId,Integer status);
}
