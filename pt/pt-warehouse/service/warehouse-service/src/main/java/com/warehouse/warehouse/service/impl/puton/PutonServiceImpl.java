package com.warehouse.warehouse.service.impl.puton;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.puton.PutonMapper;
import com.warehouse.warehouse.pojo.dto.puton.PutonDTO;
import com.warehouse.warehouse.pojo.po.puton.Puton;
import com.warehouse.warehouse.service.app.puton.PutonService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * (Puton)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("putonService")
public class PutonServiceImpl implements PutonService {

    @Resource
    private PutonMapper putonMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PutonDTO describeById(String id) {
        PutonDTO dto = new PutonDTO();
        Puton puton = this.putonMapper.describeById(id);
        if (null != puton) {
            BeanUtils.copyProperties(puton, dto);
        }
        return dto;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<PutonDTO> describeAllByLimit(long offset, long limit) {
        List<Puton> list = this.putonMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PutonDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param putonDTO 对象
     * @return 对象列表
     */
    @Override
    public List<PutonDTO> describeAll(PutonDTO putonDTO) {
        Puton puton = new Puton();
        BeanUtils.copyProperties(putonDTO, puton);
        List<Puton> list = this.putonMapper.describeAll(puton);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PutonDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param putonDTO 对象
     * @return 对象列表
     */
    @Override
    public List<PutonDTO> describeFuzzy(PutonDTO putonDTO) {
        Puton puton = new Puton();
        BeanUtils.copyProperties(putonDTO, puton);
        List<Puton> list = this.putonMapper.describeFuzzy(puton);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PutonDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param putonDTO 实例对象
     * @return 实例对象
     */
    @Override
    public PutonDTO insertPuton(PutonDTO putonDTO) {
        Puton puton = new Puton();
        BeanUtils.copyProperties(putonDTO, puton);
        if (null == puton.getId() || "".equals(puton.getId())) {
            puton.setId(UUID.randomUUID().toString());
        }
        this.putonMapper.insertPuton(puton);
        return putonDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<PutonDTO> savePuton(List<PutonDTO> list) {
        list.forEach(putonDTO -> {
            if (null == putonDTO.getId() || "".equals(putonDTO.getId())) {
                putonDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.putonMapper.savePuton(JSON.parseArray(JSON.toJSONString(list), Puton.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param putonDTO 实例对象
     * @return 实例对象
     */
    @Override
    public PutonDTO updatePutonByPrimaryKey(PutonDTO putonDTO) {
        Puton puton = new Puton();
        BeanUtils.copyProperties(putonDTO, puton);
        this.putonMapper.updatePutonByPrimaryKey(puton);
        return this.describeById(puton.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deletePutonByPrimaryKey(String id) {
        return this.putonMapper.deletePutonByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deletePutonByPrimaryKeys(List<String> list) {
        return this.putonMapper.deletePutonByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(PutonDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Puton.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getPutonNo() && !"".equals(dto.getPutonNo())) {
                criteriaName.andLike("putonNo", "%" + dto.getPutonNo() + "%");
            }
            if (null != dto.getCommodityId() && !"".equals(dto.getCommodityId())) {
                criteriaName.andLike("commodityId", "%" + dto.getCommodityId() + "%");
            }
            if (null != dto.getCommodityBar() && !"".equals(dto.getCommodityBar())) {
                criteriaName.andLike("commodityBar", "%" + dto.getCommodityBar() + "%");
            }
            if (null != dto.getCommodityName() && !"".equals(dto.getCommodityName())) {
                criteriaName.andLike("commodityName", "%" + dto.getCommodityName() + "%");
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andLike("locatorId", "%" + dto.getLocatorId() + "%");
            }
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andLike("locatorCode", "%" + dto.getLocatorCode() + "%");
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andLike("locatorName", "%" + dto.getLocatorName() + "%");
            }
            if (null != dto.getOldlocatorId() && !"".equals(dto.getOldlocatorId())) {
                criteriaName.andLike("oldlocatorId", "%" + dto.getOldlocatorId() + "%");
            }
            if (null != dto.getOldlocatorCode() && !"".equals(dto.getOldlocatorCode())) {
                criteriaName.andLike("oldlocatorCode", "%" + dto.getOldlocatorCode() + "%");
            }
            if (null != dto.getOldlocatorName() && !"".equals(dto.getOldlocatorName())) {
                criteriaName.andLike("oldlocatorName", "%" + dto.getOldlocatorName() + "%");
            }
            if (null != dto.getQty()) {
                criteriaName.andLike("qty", "%" + dto.getQty() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andLike("createId", "%" + dto.getCreateId() + "%");
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andLike("creator", "%" + dto.getCreator() + "%");
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andLike("createTime", "%" + dto.getCreateTime() + "%");
            }
            if (null != dto.getUpdateId() && !"".equals(dto.getUpdateId())) {
                criteriaName.andLike("updateId", "%" + dto.getUpdateId() + "%");
            }
            if (null != dto.getUpdator() && !"".equals(dto.getUpdator())) {
                criteriaName.andLike("updator", "%" + dto.getUpdator() + "%");
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andLike("updateTime", "%" + dto.getUpdateTime() + "%");
            }
            if (null != dto.getAuditId() && !"".equals(dto.getAuditId())) {
                criteriaName.andLike("auditId", "%" + dto.getAuditId() + "%");
            }
            if (null != dto.getAuditor() && !"".equals(dto.getAuditor())) {
                criteriaName.andLike("auditor", "%" + dto.getAuditor() + "%");
            }
            if (null != dto.getAuditTime()) {
                criteriaName.andLike("auditTime", "%" + dto.getAuditTime() + "%");
            }
        }
        return example;
    }
    
    /**
     * 初始化 精确查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initExample(PutonDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Puton.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getPutonNo() && !"".equals(dto.getPutonNo())) {
                criteriaName.andEqualTo("putonNo", dto.getPutonNo());
            }
            if (null != dto.getCommodityId() && !"".equals(dto.getCommodityId())) {
                criteriaName.andEqualTo("commodityId", dto.getCommodityId());
            }
            if (null != dto.getCommodityBar() && !"".equals(dto.getCommodityBar())) {
                criteriaName.andEqualTo("commodityBar", dto.getCommodityBar());
            }
            if (null != dto.getCommodityName() && !"".equals(dto.getCommodityName())) {
                criteriaName.andEqualTo("commodityName", dto.getCommodityName());
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andEqualTo("locatorId", dto.getLocatorId());
            }
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andEqualTo("locatorCode", dto.getLocatorCode());
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andEqualTo("locatorName", dto.getLocatorName());
            }
            if (null != dto.getOldlocatorId() && !"".equals(dto.getOldlocatorId())) {
                criteriaName.andEqualTo("oldlocatorId", dto.getOldlocatorId());
            }
            if (null != dto.getOldlocatorCode() && !"".equals(dto.getOldlocatorCode())) {
                criteriaName.andEqualTo("oldlocatorCode", dto.getOldlocatorCode());
            }
            if (null != dto.getOldlocatorName() && !"".equals(dto.getOldlocatorName())) {
                criteriaName.andEqualTo("oldlocatorName", dto.getOldlocatorName());
            }
            if (null != dto.getQty()) {
                criteriaName.andEqualTo("qty", dto.getQty());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andEqualTo("createId", dto.getCreateId());
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andEqualTo("creator", dto.getCreator());
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andEqualTo("createTime", dto.getCreateTime());
            }
            if (null != dto.getUpdateId() && !"".equals(dto.getUpdateId())) {
                criteriaName.andEqualTo("updateId", dto.getUpdateId());
            }
            if (null != dto.getUpdator() && !"".equals(dto.getUpdator())) {
                criteriaName.andEqualTo("updator", dto.getUpdator());
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andEqualTo("updateTime", dto.getUpdateTime());
            }
            if (null != dto.getAuditId() && !"".equals(dto.getAuditId())) {
                criteriaName.andEqualTo("auditId", dto.getAuditId());
            }
            if (null != dto.getAuditor() && !"".equals(dto.getAuditor())) {
                criteriaName.andEqualTo("auditor", dto.getAuditor());
            }
            if (null != dto.getAuditTime()) {
                criteriaName.andEqualTo("auditTime", dto.getAuditTime());
            }
        }
        return example;
    }
    
}