package com.pt.ptdealerprod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.entity.ProdUnit;
import com.pt.ptdealerprod.entity.ProdUnit;
import java.util.List;
/**
 * @author wl
 */
public interface ProdUnitService{
    Boolean saveUnit(ProdUnit prodUnit);

    IPage getProdUnitPage(Page page, ProdUnit prodUnit);

    /**
     * 查询单位信息集合
     *
     * @param prodUnit 单位信息
     * @return 单位列表
     */
    List<ProdUnit> selectUnitList(ProdUnit prodUnit);

    /**
     * 查询所有单位
     *
     * @return 单位列表
     */
    List<ProdUnit> selectUnitAll();

    /**
     * 通过单位ID查询单位信息
     *
     * @param unitId 单位ID
     * @return 角色对象信息
     */
    ProdUnit selectUnitById(String unitId);

    /**
     * 校验单位名称
     *
     * @param prodUnit 单位信息
     * @return 结果
     */
    Boolean checkUnitNameUnique(ProdUnit prodUnit);

    /**
     * 校验单位编码
     *
     * @param prodUnit 单位信息
     * @return 结果
     */
    Boolean checkUnitCodeUnique(ProdUnit prodUnit);

    /**
     * 通过单位ID查询单位使用数量
     *
     * @param unitId 单位ID
     * @return 结果
     */
    int countProdUnitById(String unitId);

    /**
     * 删除单位信息
     *
     * @param unitId 单位ID
     * @return 结果
     */
    Boolean deleteUnitById(String unitId);

    /**
     * 批量删除单位信息
     *
     * @param unitIds 需要删除的单位ID
     * @return 结果
     * @throws Exception 异常
     */
    Boolean deleteUnitByIds(String[] unitIds);

    /**
     * 新增保存单位信息
     *
     * @param prodUnit 单位信息
     * @return 结果
     */
    Boolean insertUnit(ProdUnit prodUnit);

    /**
     * 修改保存单位信息
     *
     * @param prodUnit 单位信息
     * @return 结果
     */
    Boolean updateUnit(ProdUnit prodUnit);

    /**
     * 获取单位列表
     * @return
     */
    List<ProdUnit> getProdUnitList();

}
