package com.pt.ptdealerprod.mapper;

import com.pt.ptdealerprod.entity.ProdPackageUnit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProdPackageUnitMapper {
    Boolean batchPackageUnit(List<ProdPackageUnit> packageUnitList);

    Boolean deletePackageUnit(@Param("packageId") String packageId);

    List<ProdPackageUnit> listPackageUnit(@Param("packageId") String packageId);
}