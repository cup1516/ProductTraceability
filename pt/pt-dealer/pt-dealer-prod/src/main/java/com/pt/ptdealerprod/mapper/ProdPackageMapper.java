package com.pt.ptdealerprod.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.entity.ProdPackage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProdPackageMapper {
    IPage<List<ProdPackage>> getProdPackagePage(Page page, @Param("query") ProdPackage prodPackage,@Param("companyId") String companyId);

    /**
     * 查询包装数据集合
     *
     * @param prodPackage 包装信息
     * @return 包装数据集合
     */
    List<ProdPackage> selectPackageList(@Param("prodPackage") ProdPackage prodPackage,@Param("companyId") String companyId);

    /**
     * 查询所有包装
     *
     * @return 包装列表
     */
    List<ProdPackage> selectPackageAll(@Param("companyId") String companyId);

    /**
     * 通过包装ID查询包装信息
     *
     * @param prodPackageId 包装ID
     * @return 角色对象信息
     */
    ProdPackage selectPackageById(@Param("prodPackageId") String prodPackageId,@Param("companyId") String companyId);

    /**
     * 删除包装信息
     *
     * @param prodPackageId 包装ID
     * @return 结果
     */
    Boolean deletePackageById(@Param("prodPackageId")String prodPackageId,@Param("companyId") String companyId);

    /**
     * 批量删除包装信息
     *
     * @param prodPackageIds 需要删除的包装ID
     * @return 结果
     */
    Boolean deletePackageByIds(@Param("prodPackageIds") String[] prodPackageIds,@Param("companyId") String companyId);

    /**
     * 修改包装信息
     *
     * @param prodPackage 包装信息
     * @return 结果
     */
    Boolean updatePackage(@Param("prodPackage") ProdPackage prodPackage,@Param("companyId") String companyId);

    /**
     * 新增包装信息
     *
     * @param prodPackage 包装信息
     * @return 结果
     */
    Boolean insertPackage(@Param("prodPackage") ProdPackage prodPackage,@Param("companyId") String companyId);

    /**
     * 校验包装名称
     *
     * @param prodPackageName 包装名称
     * @return 结果
     */
    ProdPackage checkPackageNameUnique(@Param("prodPackageName") String prodPackageName,@Param("companyId") String companyId);

    /**
     * 校验包装编码
     *
     * @param prodPackageCode 包装编码
     * @return 结果
     */
    ProdPackage checkPackageCodeUnique(@Param("prodPackageCode") String prodPackageCode,@Param("companyId") String companyId);

    /**
     * 获取包装列表
     * @return
     */
    List<ProdPackage> getProdPackageList(@Param("companyId") String companyId);
}