package com.pt.ptdealerprod.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.pt.ptdealerprod.entity.ProdPackageUnit;
import com.pt.ptdealerprod.mapper.ProdPackageUnitMapper;
import com.pt.ptdealerprod.service.ProdPackageUnitService;
@Service
public class ProdPackageUnitServiceImpl implements ProdPackageUnitService{

    @Resource
    private ProdPackageUnitMapper prodPackageUnitMapper;


}
