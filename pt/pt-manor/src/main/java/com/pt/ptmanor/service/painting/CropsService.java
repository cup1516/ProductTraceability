package com.pt.ptmanor.service.painting;

import org.springframework.data.domain.Page;

public interface CropsService {
    Page list(int pageNum, int pageRow, String companyId);

    Page findByMany(String name, Integer pageNum, Integer pageRow, String companyId);
}
