package com.pt.ptdealerprod.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.entity.ProdUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProdUnitMapper {
    IPage<List<ProdUnit>> getProdUnitPage(Page page, @Param("query") ProdUnit prodUnit,@Param("companyId") String companyId);

    /**
     * 查询单位数据集合
     *
     * @param unit 单位信息
     * @return 单位数据集合
     */
    List<ProdUnit> selectUnitList(@Param("unit")ProdUnit unit,@Param("companyId") String companyId);

    /**
     * 查询所有单位
     *
     * @return 单位列表
     */
    List<ProdUnit> selectUnitAll(@Param("companyId") String companyId);

    /**
     * 通过单位ID查询单位信息
     *
     * @param unitId 单位ID
     * @return 角色对象信息
     */
    ProdUnit selectUnitById(@Param("unitId")String unitId,@Param("companyId") String companyId);

    /**
     * 删除单位信息
     *
     * @param unitId 单位ID
     * @return 结果
     */
    Boolean deleteUnitById(@Param("unitId")String unitId,@Param("companyId") String companyId);

    /**
     * 批量删除单位信息
     *
     * @param unitIds 需要删除的单位ID
     * @return 结果
     */
    Boolean deleteUnitByIds(@Param("unitIds")String[] unitIds,@Param("companyId") String companyId);

    /**
     * 修改单位信息
     *
     * @param unit 单位信息
     * @return 结果
     */
    Boolean updateUnit(@Param("unit") ProdUnit unit,@Param("companyId") String companyId);

    /**
     * 新增单位信息
     *
     * @param unit 单位信息
     * @return 结果
     */
    Boolean insertUnit(@Param("unit") ProdUnit unit,@Param("companyId") String companyId);

    /**
     * 校验单位名称
     *
     * @param unitName 单位名称
     * @return 结果
     */
    ProdUnit checkUnitNameUnique(@Param("unitName") String unitName,@Param("companyId") String companyId);

    /**
     * 校验单位编码
     *
     * @param unitCode 单位编码
     * @return 结果
     */
    ProdUnit checkUnitCodeUnique(@Param("unitCode") String unitCode,@Param("companyId") String companyId);

    /**
     * 获取单位列表
     * @return
     */
    List<ProdUnit> getProdUnitList(@Param("companyId") String companyId);
}