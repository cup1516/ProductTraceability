package com.pt.ptdealerprod.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptdealerprod.entity.ProdUnit;
import com.pt.ptdealerprod.mapper.ProdUnitMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.pt.ptdealerprod.entity.ProdUnit;
import java.util.List;
import com.pt.ptdealerprod.mapper.ProdUnitMapper;
import com.pt.ptdealerprod.service.ProdUnitService;
@Service
@AllArgsConstructor
public class ProdUnitServiceImpl implements ProdUnitService{

    private ProdUnitMapper prodUnitMapper;

    @Override
    public Boolean saveUnit(ProdUnit prodUnit) {
        prodUnit.setUnitId(IdUtils.simpleUUID());
        prodUnitMapper.insertUnit(prodUnit);
        return Boolean.TRUE;
    }

    @Override
    public IPage getProdUnitPage(Page page, ProdUnit prodUnit) {
        return prodUnitMapper.getProdUnitPage(page,prodUnit);
    }

    /**
     * 查询节点信息集合
     *
     * @param prodUnit 节点信息
     * @return 节点信息集合
     */
    @Override
    public List<ProdUnit> selectUnitList(ProdUnit prodUnit)
    {
        return prodUnitMapper.selectUnitList(prodUnit);
    }



    /**
     * 查询所有节点
     *
     * @return 节点列表
     */
    @Override
    public List<ProdUnit> selectUnitAll()
    {
        return prodUnitMapper.selectUnitAll();
    }

    /**
     * 通过节点ID查询节点信息
     *
     * @param unitId 节点ID
     * @return 角色对象信息
     */
    @Override
    public ProdUnit selectUnitById(String unitId)
    {
        return prodUnitMapper.selectUnitById(unitId);
    }


    /**
     * 校验节点名称是否唯一
     *
     * @param unit 节点信息
     * @return 结果
     */
    @Override
    public Boolean checkUnitNameUnique(ProdUnit unit)
    {
        if(StrUtil.isEmpty(unit.getUnitId())){
            return Boolean.TRUE;
        }
        ProdUnit prodUnit = prodUnitMapper.checkUnitNameUnique(unit.getUnitName());

        if (prodUnit != null && !prodUnit.getUnitId().equals(prodUnit.getUnitId()))
        {
            return Boolean.FALSE;
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
    public Boolean checkUnitCodeUnique(ProdUnit unit)
    {
        if(StrUtil.isEmpty(unit.getUnitId())){
            return Boolean.TRUE;
        }
        ProdUnit prodUnit = prodUnitMapper.checkUnitCodeUnique(unit.getUnitName());

        if (prodUnit != null && !prodUnit.getUnitId().equals(prodUnit.getUnitId()))
        {
            return Boolean.FALSE;
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
    public int countProdUnitById(String unitId)
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
    public Boolean deleteUnitById(String unitId)
    {
        return prodUnitMapper.deleteUnitById(unitId);
    }

    /**
     * 批量删除节点信息
     *
     * @param unitIds 需要删除的节点ID
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public Boolean deleteUnitByIds(String[] unitIds)
    {
        for (String unitId : unitIds)
        {
            if (countProdUnitById(unitId) > 0)
            {
                return  Boolean.FALSE;
            }
        }
        return prodUnitMapper.deleteUnitByIds(unitIds);
    }

    /**
     * 新增保存节点信息
     *
     * @param unit 节点信息
     * @return 结果
     */
    @Override
    public Boolean insertUnit(ProdUnit unit)
    {
        unit.setUnitId(IdUtils.simpleUUID());
        return prodUnitMapper.insertUnit(unit);
    }

    /**
     * 修改保存节点信息
     *
     * @param unit 节点信息
     * @return 结果
     */
    @Override
    public Boolean updateUnit(ProdUnit unit)
    {
        return prodUnitMapper.updateUnit(unit);
    }

    @Override
    public List<ProdUnit> getProdUnitList() {
        return prodUnitMapper.getProdUnitList();
    }
}
