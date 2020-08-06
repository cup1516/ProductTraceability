package com.pt.ptdealerprod.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerprod.entity.ProdType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wl
 */
@Mapper
public interface ProdTypeMapper {
    IPage<List<ProdType>> getProdTypePage(Page page, @Param("query") ProdType prodType,@Param("companyId") String companyId);

    /**
     * 查询类型数据集合
     *
     * @param type 类型信息
     * @return 类型数据集合
     */
    List<ProdType> selectTypeList(@Param("type")ProdType type,@Param("companyId") String companyId);

    /**
     * 查询所有类型
     *
     * @return 类型列表
     */
    List<ProdType> selectTypeAll(@Param("companyId") String companyId);

    /**
     * 通过类型ID查询类型信息
     *
     * @param typeId 类型ID
     * @return 角色对象信息
     */
    ProdType selectTypeById(@Param("typeId")String typeId,@Param("companyId") String companyId);

    /**
     * 删除类型信息
     *
     * @param typeId 类型ID
     * @return 结果
     */
    Boolean deleteTypeById(@Param("typeId")String typeId,@Param("companyId") String companyId);

    /**
     * 批量删除类型信息
     *
     * @param typeIds 需要删除的类型ID
     * @return 结果
     */
    Boolean deleteTypeByIds(@Param("typeIds")String[] typeIds,@Param("companyId") String companyId);

    /**
     * 修改类型信息
     *
     * @param type 类型信息
     * @return 结果
     */
    Boolean updateType(@Param("type")ProdType type,@Param("companyId") String companyId);

    /**
     * 新增类型信息
     *
     * @param type 类型信息
     * @return 结果
     */
    Boolean insertType(@Param("type")ProdType type,@Param("companyId") String companyId);

    /**
     * 校验类型名称
     *
     * @param typeName 类型名称
     * @return 结果
     */
    ProdType checkTypeNameUnique(@Param("typeName")String typeName,@Param("companyId") String companyId);

    /**
     * 校验类型编码
     *
     * @param typeCode 类型编码
     * @return 结果
     */
    ProdType checkTypeCodeUnique(@Param("typeCode") String typeCode,@Param("companyId") String companyId);

    /**
     * 获取类型列表
     * @return
     */
    List<ProdType> getProdTypeList(@Param("companyId") String companyId);
}