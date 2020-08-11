package com.pt.ptmanor.service.user;

import com.pt.ptmanor.mapper.user.UserRepository;
import com.pt.ptmanor.pojo.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<SysUser> getCheckInvoiceUserList(String companyId) {

        List<SysUser> all = userRepository.findByDelFlagAndCompanyId("0",companyId);

        return all;

    }


    @Override
    public List<SysUser> getFinancialUserList(String companyId) {
        List<SysUser> all = userRepository.findByDelFlagAndCompanyId("0",companyId);

        return all;
    }
}
