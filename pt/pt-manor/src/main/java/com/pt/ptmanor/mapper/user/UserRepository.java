package com.pt.ptmanor.mapper.user;

import com.pt.ptmanor.pojo.user.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<SysUser,String>, JpaSpecificationExecutor<SysUser> {





    List<SysUser> findByDelFlagAndCompanyId(String delFlag, String companyId);


    SysUser findByUserName(String userName);

    SysUser findByCompanyIdAndUserId(String companyId,String userId);
}
