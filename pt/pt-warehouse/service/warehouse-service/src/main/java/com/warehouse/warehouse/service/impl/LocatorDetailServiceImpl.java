package com.warehouse.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.LocatorDetail;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDetailDTO;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorDetailMapper;
import com.warehouse.warehouse.service.LocatorDetailService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * (LocatorDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-06-02 21:20:32
 */
@Service("locatorDetailService")
public class LocatorDetailServiceImpl implements LocatorDetailService {

    @Resource
    private LocatorDetailMapper locatorDetailMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public LocatorDetailDTO describeById(String id) {
        LocatorDetailDTO dto = new LocatorDetailDTO();
        LocatorDetail locatorDetail = this.locatorDetailMapper.describeById(id);
        if (null != locatorDetail) {
            BeanUtils.copyProperties(locatorDetail, dto);
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
    public List<LocatorDetailDTO> describeAllByLimit(long offset, long limit) {
        List<LocatorDetail> list = this.locatorDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), LocatorDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param locatorDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<LocatorDetailDTO> describeAll(LocatorDetailDTO locatorDetailDTO) {
        LocatorDetail locatorDetail = new LocatorDetail();
        BeanUtils.copyProperties(locatorDetailDTO, locatorDetail);
        List<LocatorDetail> list = this.locatorDetailMapper.describeAll(locatorDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), LocatorDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param locatorDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<LocatorDetailDTO> describeFuzzy(LocatorDetailDTO locatorDetailDTO) {
        LocatorDetail locatorDetail = new LocatorDetail();
        BeanUtils.copyProperties(locatorDetailDTO, locatorDetail);
        List<LocatorDetail> list = this.locatorDetailMapper.describeFuzzy(locatorDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), LocatorDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param locatorDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public LocatorDetailDTO insertLocatorDetail(LocatorDetailDTO locatorDetailDTO) {
        LocatorDetail locatorDetail = new LocatorDetail();
        BeanUtils.copyProperties(locatorDetailDTO, locatorDetail);
        if (null == locatorDetail.getId() || "".equals(locatorDetail.getId())) {
            locatorDetail.setId(UUID.randomUUID().toString());
        }
        this.locatorDetailMapper.insertLocatorDetail(locatorDetail);
        return locatorDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<LocatorDetailDTO> saveLocatorDetail(List<LocatorDetailDTO> list) {
        list.forEach(locatorDetailDTO -> {
            if (null == locatorDetailDTO.getId() || "".equals(locatorDetailDTO.getId())) {
                locatorDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.locatorDetailMapper.saveLocatorDetail(JSON.parseArray(JSON.toJSONString(list), LocatorDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param locatorDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public LocatorDetailDTO updateLocatorDetailByPrimaryKey(LocatorDetailDTO locatorDetailDTO) {
        LocatorDetail locatorDetail = new LocatorDetail();
        BeanUtils.copyProperties(locatorDetailDTO, locatorDetail);
        this.locatorDetailMapper.updateLocatorDetailByPrimaryKey(locatorDetail);
        return this.describeById(locatorDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteLocatorDetailByPrimaryKey(String id) {
        return this.locatorDetailMapper.deleteLocatorDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteLocatorDetailByPrimaryKeys(List<String> list) {
        return this.locatorDetailMapper.deleteLocatorDetailByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(LocatorDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(LocatorDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andLike("locatorId", "%" + dto.getLocatorId() + "%");
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andLike("locatorName", "%" + dto.getLocatorName() + "%");
            }
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andLike("locatorCode", "%" + dto.getLocatorCode() + "%");
            }
            if (null != dto.getLength()) {
                criteriaName.andLike("length", "%" + dto.getLength() + "%");
            }
            if (null != dto.getWidth()) {
                criteriaName.andLike("width", "%" + dto.getWidth() + "%");
            }
            if (null != dto.getHeight()) {
                criteriaName.andLike("height", "%" + dto.getHeight() + "%");
            }
            if (null != dto.getWeight()) {
                criteriaName.andLike("weight", "%" + dto.getWeight() + "%");
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andLike("creator", "%" + dto.getCreator() + "%");
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andLike("createId", "%" + dto.getCreateId() + "%");
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
            if (null != dto.getCapacity()) {
                criteriaName.andLike("capacity", "%" + dto.getCapacity() + "%");
            }
            if (null != dto.getCharge()) {
                criteriaName.andLike("charge", "%" + dto.getCharge() + "%");
            }
            if (null != dto.getType() && !"".equals(dto.getType())) {
                criteriaName.andLike("type", "%" + dto.getType() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getWarehouseName() && !"".equals(dto.getWarehouseName())) {
                criteriaName.andLike("warehouseName", "%" + dto.getWarehouseName() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
            }
            if (null != dto.getShelfName() && !"".equals(dto.getShelfName())) {
                criteriaName.andLike("shelfName", "%" + dto.getShelfName() + "%");
            }
            if (null != dto.getShelfId() && !"".equals(dto.getShelfId())) {
                criteriaName.andLike("shelfId", "%" + dto.getShelfId() + "%");
            }
            if (null != dto.getGoodName() && !"".equals(dto.getGoodName())) {
                criteriaName.andLike("goodName", "%" + dto.getGoodName() + "%");
            }
            if (null != dto.getGoodNo() && !"".equals(dto.getGoodNo())) {
                criteriaName.andLike("goodNo", "%" + dto.getGoodNo() + "%");
            }
            if (null != dto.getGoodNum()) {
                criteriaName.andLike("goodNum", "%" + dto.getGoodNum() + "%");
            }
            if (null != dto.getGoodPrice()) {
                criteriaName.andLike("goodPrice", "%" + dto.getGoodPrice() + "%");
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
    private Example initExample(LocatorDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(LocatorDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andEqualTo("locatorId", dto.getLocatorId());
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andEqualTo("locatorName", dto.getLocatorName());
            }
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andEqualTo("locatorCode", dto.getLocatorCode());
            }
            if (null != dto.getLength()) {
                criteriaName.andEqualTo("length", dto.getLength());
            }
            if (null != dto.getWidth()) {
                criteriaName.andEqualTo("width", dto.getWidth());
            }
            if (null != dto.getHeight()) {
                criteriaName.andEqualTo("height", dto.getHeight());
            }
            if (null != dto.getWeight()) {
                criteriaName.andEqualTo("weight", dto.getWeight());
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andEqualTo("creator", dto.getCreator());
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andEqualTo("createId", dto.getCreateId());
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
            if (null != dto.getCapacity()) {
                criteriaName.andEqualTo("capacity", dto.getCapacity());
            }
            if (null != dto.getCharge()) {
                criteriaName.andEqualTo("charge", dto.getCharge());
            }
            if (null != dto.getType() && !"".equals(dto.getType())) {
                criteriaName.andEqualTo("type", dto.getType());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getWarehouseName() && !"".equals(dto.getWarehouseName())) {
                criteriaName.andEqualTo("warehouseName", dto.getWarehouseName());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
            }
            if (null != dto.getShelfName() && !"".equals(dto.getShelfName())) {
                criteriaName.andEqualTo("shelfName", dto.getShelfName());
            }
            if (null != dto.getShelfId() && !"".equals(dto.getShelfId())) {
                criteriaName.andEqualTo("shelfId", dto.getShelfId());
            }
            if (null != dto.getGoodName() && !"".equals(dto.getGoodName())) {
                criteriaName.andEqualTo("goodName", dto.getGoodName());
            }
            if (null != dto.getGoodNo() && !"".equals(dto.getGoodNo())) {
                criteriaName.andEqualTo("goodNo", dto.getGoodNo());
            }
            if (null != dto.getGoodNum()) {
                criteriaName.andEqualTo("goodNum", dto.getGoodNum());
            }
            if (null != dto.getGoodPrice()) {
                criteriaName.andEqualTo("goodPrice", dto.getGoodPrice());
            }
        }
        return example;
    }
    
}