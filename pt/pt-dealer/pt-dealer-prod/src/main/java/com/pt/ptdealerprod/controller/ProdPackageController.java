
package com.pt.ptdealerprod.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerprod.dto.PackageDto;
import com.pt.ptdealerprod.entity.ProdPackage;
import com.pt.ptdealerprod.service.ProdPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wl
 */
@RestController
@AllArgsConstructor
@RequestMapping("/package" )
@Api(value = "package", tags = "类型表管理")
public class ProdPackageController {

    private ProdPackageService prodPackageService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param prodPackage 加工类型表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getProdPackagePage(Page page, ProdPackage prodPackage) {
        return R.ok(prodPackageService.getProdPackagePage(page, prodPackage,SecurityUtils.getCompanyId()));
    }

    /**
     * 获取类型列表
     * @return
     */
    @GetMapping("/list" )
    public R getProdPackageList() {
        return R.ok(prodPackageService.getProdPackageList(SecurityUtils.getCompanyId()));
    }

    /**
     * 根据类型编号获取详细信息
     */
    @GetMapping(value = "/{packageId}")
    public R getInfo(@PathVariable String packageId)
    {
        return R.ok(prodPackageService.selectPackageById(packageId,SecurityUtils.getCompanyId()));
    }

    /**
     * 新增类型
     */

    @PostMapping
    public R add( @RequestBody PackageDto packageDto)
    {
        prodPackageService.checkPackageNameUnique(packageDto,SecurityUtils.getCompanyId());
        prodPackageService.checkPackageCodeUnique(packageDto,SecurityUtils.getCompanyId());
        packageDto.setCreateBy(SecurityUtils.getUserName());
        return R.ok(prodPackageService.insertPackage(packageDto,SecurityUtils.getCompanyId()));
    }

    /**
     * 修改类型
     */

    @PutMapping
    public R edit(@RequestBody PackageDto packageDto)
    {
        prodPackageService.checkPackageNameUnique(packageDto,SecurityUtils.getCompanyId());
        prodPackageService.checkPackageCodeUnique(packageDto,SecurityUtils.getCompanyId());
        return R.ok(prodPackageService.updatePackage(packageDto,SecurityUtils.getCompanyId()));
    }

    /**
     * 删除类型
     */

    @DeleteMapping("/{packageIds}")
    public R remove(@PathVariable String[] packageIds)
    {
        return R.ok(prodPackageService.deletePackageByIds(packageIds,SecurityUtils.getCompanyId()));
    }

    /**
     * 获取类型选择框列表
     */
    @GetMapping("/optionselect")
    public R optionselect()
    {
        List<ProdPackage> packages = prodPackageService.selectPackageAll(SecurityUtils.getCompanyId());
        return R.ok(packages);
    }

}
