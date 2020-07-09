package com.pt.ptdealerprod.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.entity.ProdUnit;
import com.pt.ptdealerprod.entity.ProdUnit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProdUnitMapper {
    IPage<List<ProdUnit>> getProdUnitPage(Page page, @Param("query") ProdUnit prodUnit);

    /**
     * 查询单位数据集合
     *
     * @param type 单位信息
     * @return 单位数据集合
     */
    List<ProdUnit> selectUnitList(ProdUnit type);

    /**
     * 查询所有单位
     *
     * @return 单位列表
     */
    List<ProdUnit> selectUnitAll();

    /**
     * 通过单位ID查询单位信息
     *
     * @param typeId 单位ID
     * @return 角色对象信息
     */
    ProdUnit selectUnitById(String typeId);

    /**
     * 删除单位信息
     *
     * @param typeId 单位ID
     * @return 结果
     */
    Boolean deleteUnitById(String typeId);

    /**
     * 批量删除单位信息
     *
     * @param typeIds 需要删除的单位ID
     * @return 结果
     */
    Boolean deleteUnitByIds(String[] typeIds);

    /**
     * 修改单位信息
     *
     * @param type 单位信息
     * @return 结果
     */
    Boolean updateUnit(ProdUnit type);

    /**
     * 新增单位信息
     *
     * @param type 单位信息
     * @return 结果
     */
    Boolean insertUnit(ProdUnit type);

    /**
     * 校验单位名称
     *
     * @param typeName 单位名称
     * @return 结果
     */
    ProdUnit checkUnitNameUnique(String typeName);

    /**
     * 校验单位编码
     *
     * @param typeCode 单位编码
     * @return 结果
     */
    ProdUnit checkUnitCodeUnique(String typeCode);

    /**
     * 获取单位列表
     * @return
     */
    List<ProdUnit> getProdUnitList();
}