package com.pt.ptmanor.service.painting;

import org.springframework.data.domain.Page;

public interface MaterialService {

    Page list(int pageNum, int pageRow, String companyId);

    Page findByMany(String materialName, Integer pageNum, Integer pageRow, String companyId);
}
