package com.pt.ptdealerprod.mapper;

import com.pt.ptdealerprod.entity.ProdPackageUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProdPackageUnitMapper {
    Boolean batchPackageUnit(@Param("packageUnitList") List<ProdPackageUnit> packageUnitList,@Param("companyId") String companyId);

    Boolean deletePackageUnit(@Param("packageId") String packageId,@Param("companyId") String companyId);

    List<ProdPackageUnit> listPackageUnit(@Param("packageId") String packageId,@Param("companyId") String companyId);
}