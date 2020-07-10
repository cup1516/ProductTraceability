package com.pt.ptdealerprod.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.pt.ptdealerprod.entity.ProdType;
import com.pt.ptdealerprod.mapper.ProdTypeMapper;
import com.pt.ptdealerprod.service.ProdTypeService;
@Service
@AllArgsConstructor
public class ProdTypeServiceImpl implements ProdTypeService{


    private ProdTypeMapper prodTypeMapper;

    @Override
    public Boolean saveType(ProdType prodType) {
        prodType.setTypeId(IdUtils.simpleUUID());
        prodTypeMapper.insertType(prodType);
        return Boolean.TRUE;
    }

    @Override
    public IPage getProdTypePage(Page page, ProdType prodType) {
        return prodTypeMapper.getProdTypePage(page,prodType);
    }

    /**
     * 查询节点信息集合
     *
     * @param prodType 节点信息
     * @return 节点信息集合
     */
    @Override
    public List<ProdType> selectTypeList(ProdType prodType)
    {
        return prodTypeMapper.selectTypeList(prodType);
    }



    /**
     * 查询所有节点
     *
     * @return 节点列表
     */
    @Override
    public List<ProdType> selectTypeAll()
    {
        return prodTypeMapper.selectTypeAll();
    }

    /**
     * 通过节点ID查询节点信息
     *
     * @param typeId 节点ID
     * @return 角色对象信息
     */
    @Override
    public ProdType selectTypeById(String typeId)
    {
        return prodTypeMapper.selectTypeById(typeId);
    }


    /**
     * 校验节点名称是否唯一
     *
     * @param type 节点信息
     * @return 结果
     */
    @Override
    public Boolean checkTypeNameUnique(ProdType type)
    {
        if(StrUtil.isEmpty(type.getTypeId())){
            return Boolean.TRUE;
        }
        ProdType prodType = prodTypeMapper.checkTypeNameUnique(type.getTypeName());

        if (prodType != null && !prodType.getTypeId().equals(prodType.getTypeId()))
        {
            return Boolean.FALSE;
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
    public Boolean checkTypeCodeUnique(ProdType type)
    {
        if(StrUtil.isEmpty(type.getTypeId())){
            return Boolean.TRUE;
        }
        ProdType prodType = prodTypeMapper.checkTypeCodeUnique(type.getTypeName());

        if (prodType != null && !prodType.getTypeId().equals(prodType.getTypeId()))
        {
            return Boolean.FALSE;
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
    public int countProdTypeById(String typeId)
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
    public Boolean deleteTypeById(String typeId)
    {
        return prodTypeMapper.deleteTypeById(typeId);
    }

    /**
     * 批量删除节点信息
     *
     * @param typeIds 需要删除的节点ID
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public Boolean deleteTypeByIds(String[] typeIds)
    {
        for (String typeId : typeIds)
        {
            if (countProdTypeById(typeId) > 0)
            {
                return  Boolean.FALSE;
            }
        }
        return prodTypeMapper.deleteTypeByIds(typeIds);
    }

    /**
     * 新增保存节点信息
     *
     * @param type 节点信息
     * @return 结果
     */
    @Override
    public Boolean insertType(ProdType type)
    {
        type.setTypeId(IdUtils.simpleUUID());
        return prodTypeMapper.insertType(type);
    }

    /**
     * 修改保存节点信息
     *
     * @param type 节点信息
     * @return 结果
     */
    @Override
    public Boolean updateType(ProdType type)
    {
        return prodTypeMapper.updateType(type);
    }

    @Override
    public List<ProdType> getProdTypeList() {
        return prodTypeMapper.getProdTypeList();
    }

}
