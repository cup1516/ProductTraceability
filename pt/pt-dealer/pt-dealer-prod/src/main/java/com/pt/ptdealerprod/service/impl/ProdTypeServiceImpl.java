package com.pt.ptdealerprod.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptdealerprod.entity.ProdType;
import com.pt.ptdealerprod.mapper.ProdTypeMapper;
import com.pt.ptdealerprod.service.ProdTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ProdTypeServiceImpl implements ProdTypeService{


    private ProdTypeMapper prodTypeMapper;


    @Override
    public IPage getProdTypePage(Page page, ProdType prodType,String companyId) {
        return prodTypeMapper.getProdTypePage(page,prodType,companyId);
    }

    /**
     * 查询节点信息集合
     *
     * @param prodType 节点信息
     * @return 节点信息集合
     */
    @Override
    public List<ProdType> selectTypeList(ProdType prodType,String companyId)
    {
        return prodTypeMapper.selectTypeList(prodType,companyId);
    }



    /**
     * 查询所有节点
     *
     * @return 节点列表
     */
    @Override
    public List<ProdType> selectTypeAll(String companyId)
    {
        return prodTypeMapper.selectTypeAll(companyId);
    }

    /**
     * 通过节点ID查询节点信息
     *
     * @param typeId 节点ID
     * @return 角色对象信息
     */
    @Override
    public ProdType selectTypeById(String typeId,String companyId)
    {
        return prodTypeMapper.selectTypeById(typeId,companyId);
    }


    /**
     * 校验节点名称是否唯一
     *
     * @param type 节点信息
     * @return 结果
     */
    @Override
    public Boolean checkTypeNameUnique(ProdType type,String companyId)
    {
        if(StrUtil.isEmpty(type.getTypeId())){
            return Boolean.TRUE;
        }
        ProdType prodType = prodTypeMapper.checkTypeNameUnique(type.getTypeName(),companyId);

        if (prodType != null && !type.getTypeId().equals(prodType.getTypeId()))
        {
            throw new CustomException("修改类型'" + type.getTypeName() + "'失败，类型名称已存在");
        }
        return Boolean.TRUE;

    }

    /**
     * 校验节点编码是否唯一
     *
     * @param type 节点信息
     * @return 结果
     */
    @Override
    public Boolean checkTypeCodeUnique(ProdType type,String companyId)
    {
        if(StrUtil.isEmpty(type.getTypeId())){
            return Boolean.TRUE;
        }
        ProdType prodType = prodTypeMapper.checkTypeCodeUnique(type.getTypeCode(),companyId);

        if (prodType != null && !type.getTypeId().equals(prodType.getTypeId()))
        {
            throw new CustomException("修改类型'" + type.getTypeCode() + "'失败，类型编码已存在");
        }
        return Boolean.TRUE;
    }

    /**
     * 通过节点ID查询节点使用数量
     *
     * @param typeId 节点ID
     * @return 结果
     */
    @Override
    public int countProdTypeById(String typeId,String companyId)
    {
//		return sysUserTypeService.countProdTypeById(typeId);
        return 0;
    }

    /**
     * 删除节点信息
     *
     * @param typeId 节点ID
     * @return 结果
     */
    @Override
    public Boolean deleteTypeById(String typeId,String companyId)
    {
        return prodTypeMapper.deleteTypeById(typeId,companyId);
    }

    /**
     * 批量删除节点信息
     *
     * @param typeIds 需要删除的节点ID
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public Boolean deleteTypeByIds(String[] typeIds,String companyId)
    {
        for (String typeId : typeIds)
        {
            if (countProdTypeById(typeId,companyId) > 0)
            {
                return  Boolean.FALSE;
            }
        }
        return prodTypeMapper.deleteTypeByIds(typeIds,companyId);
    }

    /**
     * 新增保存节点信息
     *
     * @param type 节点信息
     * @return 结果
     */
    @Override
    public Boolean insertType(ProdType type,String companyId)
    {
        type.setTypeId(IdUtils.simpleUUID());
        return prodTypeMapper.insertType(type,companyId);
    }

    /**
     * 修改保存节点信息
     *
     * @param type 节点信息
     * @return 结果
     */
    @Override
    public Boolean updateType(ProdType type,String companyId)
    {
        return prodTypeMapper.updateType(type,companyId);
    }

    @Override
    public List<ProdType> getProdTypeList(String companyId) {
        return prodTypeMapper.getProdTypeList(companyId);
    }
    @Override
    public List<ProdType> getProdTypeListByName(String companyId,String typeName) {
        return prodTypeMapper.getProdTypeListByName(companyId,typeName);
    }
}
