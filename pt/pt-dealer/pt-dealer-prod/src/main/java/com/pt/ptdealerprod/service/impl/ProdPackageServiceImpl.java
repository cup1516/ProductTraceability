package com.pt.ptdealerprod.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptdealerprod.dto.PackageDto;
import com.pt.ptdealerprod.entity.ProdPackage;
import com.pt.ptdealerprod.entity.ProdPackageUnit;
import com.pt.ptdealerprod.mapper.ProdPackageMapper;
import com.pt.ptdealerprod.mapper.ProdPackageUnitMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.pt.ptdealerprod.mapper.ProdPackageMapper;
import com.pt.ptdealerprod.entity.ProdPackage;
import com.pt.ptdealerprod.service.ProdPackageService;
@Service
@AllArgsConstructor
public class ProdPackageServiceImpl implements ProdPackageService{

    private ProdPackageMapper prodPackageMapper;
    private ProdPackageUnitMapper prodPackageUnitMapper;

    @Override
    public Boolean savePackage(ProdPackage prodPackage) {
        prodPackage.setPackageId(IdUtils.simpleUUID());
        prodPackageMapper.insertPackage(prodPackage);
        return Boolean.TRUE;
    }

    @Override
    public IPage getProdPackagePage(Page page, ProdPackage prodPackage) {
        return prodPackageMapper.getProdPackagePage(page,prodPackage);
    }

    /**
     * 查询节点信息集合
     *
     * @param prodPackage 节点信息
     * @return 节点信息集合
     */
    @Override
    public List<ProdPackage> selectPackageList(ProdPackage prodPackage)
    {
        return prodPackageMapper.selectPackageList(prodPackage);
    }



    /**
     * 查询所有节点
     *
     * @return 节点列表
     */
    @Override
    public List<ProdPackage> selectPackageAll()
    {
        return prodPackageMapper.selectPackageAll();
    }

    /**
     * 通过节点ID查询节点信息
     *
     * @param prodPackageId 节点ID
     * @return 角色对象信息
     */
    @Override
    public ProdPackage selectPackageById(String prodPackageId)
    {
        return prodPackageMapper.selectPackageById(prodPackageId);
    }


    /**
     * 校验节点名称是否唯一
     *
     * @param prodPackage 节点信息
     * @return 结果
     */
    @Override
    public Boolean checkPackageNameUnique(ProdPackage prodPackage)
    {
        if(StrUtil.isEmpty(prodPackage.getPackageId())){
            return Boolean.TRUE;
        }
        ProdPackage prodPackage_ = prodPackageMapper.checkPackageNameUnique(prodPackage.getPackageName());

        if (prodPackage != null && !prodPackage.getPackageId().equals(prodPackage_.getPackageId()))
        {
            throw new CustomException("新增包装'" + prodPackage.getPackageName() + "'失败，包装名称已存在");
        }
        return Boolean.TRUE;

    }

    /**
     * 校验节点编码是否唯一
     *
     * @param prodPackage 节点信息
     * @return 结果
     */
    @Override
    public Boolean checkPackageCodeUnique(ProdPackage prodPackage)
    {
        if(StrUtil.isEmpty(prodPackage.getPackageId())){
            return Boolean.TRUE;
        }
        ProdPackage prodPackage_ = prodPackageMapper.checkPackageCodeUnique(prodPackage.getPackageCode());

        if (prodPackage != null && !prodPackage.getPackageId().equals(prodPackage_.getPackageId()))
        {
            throw new CustomException("新增包装'" + prodPackage.getPackageName() + "'失败，包装编号已存在");
        }
        return Boolean.TRUE;
    }

    /**
     * 通过节点ID查询节点使用数量
     *
     * @param prodPackageId 节点ID
     * @return 结果
     */
    @Override
    public int countProdPackageById(String prodPackageId)
    {
//		return sysUserPackageService.countProdPackageById(prodPackageId);
        return 0;
    }

    /**
     * 删除节点信息
     *
     * @param prodPackageId 节点ID
     * @return 结果
     */
    @Override
    public Boolean deletePackageById(String prodPackageId)
    {
        return prodPackageMapper.deletePackageById(prodPackageId);
    }

    /**
     * 批量删除节点信息
     *
     * @param prodPackageIds 需要删除的节点ID
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public Boolean deletePackageByIds(String[] prodPackageIds)
    {
        for (String prodPackageId : prodPackageIds)
        {
            prodPackageUnitMapper.deletePackageUnit(prodPackageId);
//            if (countProdPackageById(prodPackageId) > 0)
//            {
//                return  Boolean.FALSE;
//            }
        }
        prodPackageMapper.deletePackageByIds(prodPackageIds);
        return Boolean.TRUE;
    }

    /**
     * 新增保存包装信息
     *
     * @param packageDto 包装信息
     * @return 结果
     */
    @Override
    public Boolean insertPackage(PackageDto packageDto)
    {
        packageDto.setPackageId(IdUtils.simpleUUID());
        prodPackageMapper.insertPackage(packageDto);
        List<ProdPackageUnit> processUnits = packageDto.getPackageUnits();
        processUnits.stream().forEach(procPackageUnit -> procPackageUnit.setPackageId(packageDto.getPackageId()));
        prodPackageUnitMapper.batchPackageUnit(processUnits);
        return Boolean.TRUE;
    }

    /**
     * 修改保存包装信息
     *
     * @param packageDto 包装信息
     * @return 结果
     */
    @Override
    public Boolean updatePackage(PackageDto packageDto)
    {
        List<ProdPackageUnit> processUnits = packageDto.getPackageUnits();
        processUnits.stream().forEach(procPackageUnit -> procPackageUnit.setPackageId(packageDto.getPackageId()));
        prodPackageUnitMapper.deletePackageUnit(packageDto.getPackageId());
        if(packageDto.getPackageUnits().size()>0){
            prodPackageUnitMapper.batchPackageUnit(processUnits);
        }
        return prodPackageMapper.updatePackage(packageDto);
    }


    @Override
    public List<ProdPackage> getProdPackageList() {
        return prodPackageMapper.getProdPackageList();
    }
}
