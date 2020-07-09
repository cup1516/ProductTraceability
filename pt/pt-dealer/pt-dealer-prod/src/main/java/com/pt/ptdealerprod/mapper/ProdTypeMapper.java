package com.pt.ptdealerprod.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.entity.ProdType;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wl
 */
@Mapper
public interface ProdTypeMapper {
    IPage<List<ProdType>> getProdTypePage(Page page, @Param("query") ProdType prodType);

    /**
     * 查询类型数据集合
     *
     * @param type 类型信息
     * @return 类型数据集合
     */
    List<ProdType> selectTypeList(ProdType type);

    /**
     * 查询所有类型
     *
     * @return 类型列表
     */
    List<ProdType> selectTypeAll();

    /**
     * 通过类型ID查询类型信息
     *
     * @param typeId 类型ID
     * @return 角色对象信息
     */
    ProdType selectTypeById(String typeId);

    /**
     * 删除类型信息
     *
     * @param typeId 类型ID
     * @return 结果
     */
    Boolean deleteTypeById(String typeId);

    /**
     * 批量删除类型信息
     *
     * @param typeIds 需要删除的类型ID
     * @return 结果
     */
    Boolean deleteTypeByIds(String[] typeIds);

    /**
     * 修改类型信息
     *
     * @param type 类型信息
     * @return 结果
     */
    Boolean updateType(ProdType type);

    /**
     * 新增类型信息
     *
     * @param type 类型信息
     * @return 结果
     */
    Boolean insertType(ProdType type);

    /**
     * 校验类型名称
     *
     * @param typeName 类型名称
     * @return 结果
     */
    ProdType checkTypeNameUnique(String typeName);

    /**
     * 校验类型编码
     *
     * @param typeCode 类型编码
     * @return 结果
     */
    ProdType checkTypeCodeUnique(String typeCode);

    /**
     * 获取类型列表
     * @return
     */
    List<ProdType> getProdTypeList();
}