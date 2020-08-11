package com.pt.ptmanor.service;


import com.pt.ptmanor.aspect.SysLog;
import com.pt.ptmanor.aspect.SysLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements SysLogService{

    @Autowired
    SysLogRepository sysLogRepository;

    @Override
    public Page list(int pageNum, int pageRow) {

        Pageable pageable  = PageRequest.of(pageNum-1,pageRow);
        Page<SysLog> Page = sysLogRepository.findAll(pageable);

        return Page ;
    }
}
