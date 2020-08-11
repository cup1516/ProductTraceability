package com.pt.ptmanor.service.painting;

import org.springframework.data.domain.Page;

public interface FarmlandRegionService {

    Page list(int pageNum, int pageRow, String companyId);



    Page findByMany(String farmlandRegionId, String farmlandRegionPrincipal, String farmlandRegionType, String farmlandRegionExplain, String farmlandLocationName, Integer pageNum, Integer pageRow);
}
