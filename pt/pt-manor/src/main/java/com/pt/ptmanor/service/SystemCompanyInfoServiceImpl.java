package com.pt.ptmanor.service;

import com.pt.ptmanor.mapper.SystemCompanyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemCompanyInfoServiceImpl implements SystemCompanyInfoService {

    @Autowired
    SystemCompanyInfoRepository systemCompanyInfoRepository;


}
