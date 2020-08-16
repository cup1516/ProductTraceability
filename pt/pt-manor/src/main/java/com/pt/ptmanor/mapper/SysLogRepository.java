package com.pt.ptmanor.mapper;


import com.pt.ptmanor.pojo.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface SysLogRepository extends JpaRepository<SysLog,String>, JpaSpecificationExecutor<SysLog> {
}
