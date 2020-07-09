package com.pt.ptdealerprod.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.dto.PackageDto;
import com.pt.ptdealerprod.entity.ProdPackage;
import com.pt.ptdealerprod.entity.ProdPackage;

public interface ProdPackageService{
    Boolean savePackage(ProdPackage prodPackage);

    IPage getProdPackagePage(Page page, ProdPackage prodPackage);

    /**
     * 查询包装信息集合
     *
     * @param prodPackage 包装信息
     * @return 包装列表
     */
    List<ProdPackage> selectPackageList(ProdPackage prodPackage);

    /**
     * 查询所有包装
     *
     * @return 包装列表
     */
    List<ProdPackage> selectPackageAll();

    /**
     * 通过包装ID查询包装信息
     *
     * @param packageId 包装ID
     * @return 角色对象信息
     */
    ProdPackage selectPackageById(String packageId);

    /**
     * 校验包装名称
     *
     * @param prodPackage 包装信息
     * @return 结果
     */
    Boolean checkPackageNameUnique(ProdPackage prodPackage);

    /**
     * 校验包装编码
     *
     * @param prodPackage 包装信息
     * @return 结果
     */
    Boolean checkPackageCodeUnique(ProdPackage prodPackage);

    /**
     * 通过包装ID查询包装使用数量
     *
     * @param packageId 包装ID
     * @return 结果
     */
    int countProdPackageById(String packageId);

    /**
     * 删除包装信息
     *
     * @param packageId 包装ID
     * @return 结果
     */
    Boolean deletePackageById(String packageId);

    /**
     * 批量删除包装信息
     *
     * @param packageIds 需要删除的包装ID
     * @return 结果
     * @throws Exception 异常
     */
    Boolean deletePackageByIds(String[] packageIds);

    /**
     * 新增保存包装信息
     *
     * @param packageDto 包装信息
     * @return 结果
     */
    Boolean insertPackage(PackageDto packageDto);

    /**
     * 修改保存包装信息
     *
     * @param packageDto 包装信息
     * @return 结果
     */
    Boolean updatePackage(PackageDto packageDto);

    /**
     * 获取包装列表
     * @return
     */
    List<ProdPackage> getProdPackageList();
}
