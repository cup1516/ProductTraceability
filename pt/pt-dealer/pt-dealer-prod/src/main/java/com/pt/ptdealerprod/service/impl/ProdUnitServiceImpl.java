package com.pt.ptdealerprod.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptdealerprod.entity.ProdUnit;
import com.pt.ptdealerprod.mapper.ProdUnitMapper;
import com.pt.ptdealerprod.service.ProdUnitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ProdUnitServiceImpl implements ProdUnitService{

    private ProdUnitMapper prodUnitMapper;



    @Override
    public IPage getProdUnitPage(Page page, ProdUnit prodUnit,String companyId) {
        return prodUnitMapper.getProdUnitPage(page,prodUnit,companyId);
    }

    /**
     * 查询节点信息集合
     *
     * @param prodUnit 节点信息
     * @return 节点信息集合
     */
    @Override
    public List<ProdUnit> selectUnitList(ProdUnit prodUnit,String companyId)
    {
        return prodUnitMapper.selectUnitList(prodUnit,companyId);
    }



    /**
     * 查询所有节点
     *
     * @return 节点列表
     */
    @Override
    public List<ProdUnit> selectUnitAll(String companyId)
    {
        return prodUnitMapper.selectUnitAll(companyId);
    }

    /**
     * 通过节点ID查询节点信息
     *
     * @param unitId 节点ID
     * @return 角色对象信息
     */
    @Override
    public ProdUnit selectUnitById(String unitId,String companyId)
    {
        return prodUnitMapper.selectUnitById(unitId,companyId);
    }


    /**
     * 校验节点名称是否唯一
     *
     * @param unit 节点信息
     * @return 结果
     */
    @Override
    public Boolean checkUnitNameUnique(ProdUnit unit,String companyId)
    {
        if(StrUtil.isEmpty(unit.getUnitId())){
            return Boolean.TRUE;
        }
        ProdUnit prodUnit = prodUnitMapper.checkUnitNameUnique(unit.getUnitName(),companyId);

        if (prodUnit != null && !unit.getUnitId().equals(prodUnit.getUnitId()))
        {
            throw new CustomException("修改单位'" + unit.getUnitName() + "'失败，单位名称已存在");
        }
        return Boolean.TRUE;

    }

    /**
     * 校验节点编码是否唯一
     *
     * @param unit 节点信息
     * @return 结果
     */
    @Override
    public Boolean checkUnitCodeUnique(ProdUnit unit,String companyId)
    {
        if(StrUtil.isEmpty(unit.getUnitId())){
            return Boolean.TRUE;
        }
        ProdUnit prodUnit = prodUnitMapper.checkUnitCodeUnique(unit.getUnitCode(),companyId);

        if (prodUnit != null && !unit.getUnitId().equals(prodUnit.getUnitId()))
        {
            throw new CustomException("修改单位'" + unit.getUnitCode() + "'失败，单位名称已存在");
        }
        return Boolean.TRUE;
    }

    /**
     * 通过节点ID查询节点使用数量
     *
     * @param unitId 节点ID
     * @return 结果
     */
    @Override
    public int countProdUnitById(String unitId,String companyId)
    {
//		return sysUserUnitService.countProdUnitById(unitId);
        return 0;
    }

    /**
     * 删除节点信息
     *
     * @param unitId 节点ID
     * @return 结果
     */
    @Override
    public Boolean deleteUnitById(String unitId,String companyId)
    {
        return prodUnitMapper.deleteUnitById(unitId,companyId);
    }

    /**
     * 批量删除节点信息
     *
     * @param unitIds 需要删除的节点ID
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public Boolean deleteUnitByIds(String[] unitIds,String companyId)
    {
        for (String unitId : unitIds)
        {
            if (countProdUnitById(unitId,companyId) > 0)
            {
                return  Boolean.FALSE;
            }
        }
        return prodUnitMapper.deleteUnitByIds(unitIds,companyId);
    }

    /**
     * 新增保存节点信息
     *
     * @param unit 节点信息
     * @return 结果
     */
    @Override
    public Boolean insertUnit(ProdUnit unit,String companyId)
    {
        unit.setUnitId(IdUtils.simpleUUID());
        return prodUnitMapper.insertUnit(unit,companyId);
    }

    /**
     * 修改保存节点信息
     *
     * @param unit 节点信息
     * @return 结果
     */
    @Override
    public Boolean updateUnit(ProdUnit unit,String companyId)
    {
        return prodUnitMapper.updateUnit(unit,companyId);
    }

    @Override
    public List<ProdUnit> getProdUnitList(String companyId) {
        return prodUnitMapper.getProdUnitList(companyId);
    }
}
