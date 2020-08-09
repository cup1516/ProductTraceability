package com.pt.ptdealerprod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.entity.ProdType;

import java.util.List;

/**
 * @author wl
 */
public interface ProdTypeService{


    IPage getProdTypePage(Page page, ProdType prodType,String companyId);

    /**
     * 查询类型信息集合
     *
     * @param prodType 类型信息
     * @return 类型列表
     */
    List<ProdType> selectTypeList(ProdType prodType,String companyId);

    /**
     * 查询所有类型
     *
     * @return 类型列表
     */
    List<ProdType> selectTypeAll(String companyId);

    /**
     * 通过类型ID查询类型信息
     *
     * @param typeId 类型ID
     * @return 角色对象信息
     */
    ProdType selectTypeById(String typeId,String companyId);

    /**
     * 校验类型名称
     *
     * @param prodType 类型信息
     * @return 结果
     */
    Boolean checkTypeNameUnique(ProdType prodType,String companyId);

    /**
     * 校验类型编码
     *
     * @param prodType 类型信息
     * @return 结果
     */
    Boolean checkTypeCodeUnique(ProdType prodType,String companyId);

    /**
     * 通过类型ID查询类型使用数量
     *
     * @param typeId 类型ID
     * @return 结果
     */
    int countProdTypeById(String typeId,String companyId);

    /**
     * 删除类型信息
     *
     * @param typeId 类型ID
     * @return 结果
     */
    Boolean deleteTypeById(String typeId,String companyId);

    /**
     * 批量删除类型信息
     *
     * @param typeIds 需要删除的类型ID
     * @return 结果
     * @throws Exception 异常
     */
    Boolean deleteTypeByIds(String[] typeIds,String companyId);

    /**
     * 新增保存类型信息
     *
     * @param prodType 类型信息
     * @return 结果
     */
    Boolean insertType(ProdType prodType,String companyId);

    /**
     * 修改保存类型信息
     *
     * @param prodType 类型信息
     * @return 结果
     */
    Boolean updateType(ProdType prodType,String companyId);

    /**
     * 获取类型列表
     * @return
     */
    List<ProdType> getProdTypeList(String companyId);

    List<ProdType> getProdTypeListByName(String companyId,String typeName);
}
