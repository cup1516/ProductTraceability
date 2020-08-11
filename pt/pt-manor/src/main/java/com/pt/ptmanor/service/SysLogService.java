package com.pt.ptmanor.service;

import org.springframework.data.domain.Page;

public interface SysLogService {


    Page list(int pageNum, int pageRow);

}
