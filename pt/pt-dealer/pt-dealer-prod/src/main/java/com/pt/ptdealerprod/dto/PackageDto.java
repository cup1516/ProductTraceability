package com.pt.ptdealerprod.dto;

import com.pt.ptdealerprod.entity.ProdPackage;
import com.pt.ptdealerprod.entity.ProdPackageUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wl
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PackageDto extends ProdPackage {
	private List<ProdPackageUnit> packageUnits;
}
