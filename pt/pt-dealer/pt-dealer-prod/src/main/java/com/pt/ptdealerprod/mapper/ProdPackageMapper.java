package com.pt.ptdealerprod.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.entity.ProdPackage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProdPackageMapper {
    IPage<List<ProdPackage>> getProdPackagePage(Page page, @Param("query") ProdPackage prodPackage);

    /**
     * 查询包装数据集合
     *
     * @param prodPackage 包装信息
     * @return 包装数据集合
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
     * @param prodPackageId 包装ID
     * @return 角色对象信息
     */
    ProdPackage selectPackageById(String prodPackageId);

    /**
     * 删除包装信息
     *
     * @param prodPackageId 包装ID
     * @return 结果
     */
    Boolean deletePackageById(String prodPackageId);

    /**
     * 批量删除包装信息
     *
     * @param prodPackageIds 需要删除的包装ID
     * @return 结果
     */
    Boolean deletePackageByIds(String[] prodPackageIds);

    /**
     * 修改包装信息
     *
     * @param prodPackage 包装信息
     * @return 结果
     */
    Boolean updatePackage(ProdPackage prodPackage);

    /**
     * 新增包装信息
     *
     * @param prodPackage 包装信息
     * @return 结果
     */
    Boolean insertPackage(ProdPackage prodPackage);

    /**
     * 校验包装名称
     *
     * @param prodPackageName 包装名称
     * @return 结果
     */
    ProdPackage checkPackageNameUnique(String prodPackageName);

    /**
     * 校验包装编码
     *
     * @param prodPackageCode 包装编码
     * @return 结果
     */
    ProdPackage checkPackageCodeUnique(String prodPackageCode);

    /**
     * 获取包装列表
     * @return
     */
    List<ProdPackage> getProdPackageList();
}