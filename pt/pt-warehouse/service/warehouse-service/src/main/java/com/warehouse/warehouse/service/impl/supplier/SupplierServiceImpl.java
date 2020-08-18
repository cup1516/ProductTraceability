package com.warehouse.warehouse.service.impl.supplier;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.supplier.SupplierMapper;
import com.warehouse.warehouse.pojo.dto.supplier.SupplierDTO;
import com.warehouse.warehouse.pojo.po.supplier.Supplier;
import com.warehouse.warehouse.service.app.supplier.SupplierService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 供应商表(Supplier)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierMapper supplierMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SupplierDTO describeById(String id) {
        SupplierDTO dto = new SupplierDTO();
        Supplier supplier = this.supplierMapper.describeById(id);
        if (null != supplier) {
            BeanUtils.copyProperties(supplier, dto);
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
    public List<SupplierDTO> describeAllByLimit(long offset, long limit) {
        List<Supplier> list = this.supplierMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), SupplierDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param supplierDTO 对象
     * @return 对象列表
     */
    @Override
    public List<SupplierDTO> describeAll(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        List<Supplier> list = this.supplierMapper.describeAll(supplier);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), SupplierDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param supplierDTO 对象
     * @return 对象列表
     */
    @Override
    public List<SupplierDTO> describeFuzzy(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        List<Supplier> list = this.supplierMapper.describeFuzzy(supplier);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), SupplierDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param supplierDTO 实例对象
     * @return 实例对象
     */
    @Override
    public SupplierDTO insertSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        if (null == supplier.getId() || "".equals(supplier.getId())) {
            supplier.setId(UUID.randomUUID().toString());
        }
        this.supplierMapper.insertSupplier(supplier);
        return supplierDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<SupplierDTO> saveSupplier(List<SupplierDTO> list) {
        list.forEach(supplierDTO -> {
            if (null == supplierDTO.getId() || "".equals(supplierDTO.getId())) {
                supplierDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.supplierMapper.saveSupplier(JSON.parseArray(JSON.toJSONString(list), Supplier.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param supplierDTO 实例对象
     * @return 实例对象
     */
    @Override
    public SupplierDTO updateSupplierByPrimaryKey(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        this.supplierMapper.updateSupplierByPrimaryKey(supplier);
        return this.describeById(supplier.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteSupplierByPrimaryKey(String id) {
        return this.supplierMapper.deleteSupplierByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteSupplierByPrimaryKeys(List<String> list) {
        return this.supplierMapper.deleteSupplierByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(SupplierDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Supplier.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andLike("name", "%" + dto.getName() + "%");
            }
            if (null != dto.getContact() && !"".equals(dto.getContact())) {
                criteriaName.andLike("contact", "%" + dto.getContact() + "%");
            }
            if (null != dto.getPhone() && !"".equals(dto.getPhone())) {
                criteriaName.andLike("phone", "%" + dto.getPhone() + "%");
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
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
        }
        return example;
    }
    
    /**
     * 初始化 精确查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initExample(SupplierDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Supplier.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andEqualTo("name", dto.getName());
            }
            if (null != dto.getContact() && !"".equals(dto.getContact())) {
                criteriaName.andEqualTo("contact", dto.getContact());
            }
            if (null != dto.getPhone() && !"".equals(dto.getPhone())) {
                criteriaName.andEqualTo("phone", dto.getPhone());
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
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
        }
        return example;
    }



    @Override
    public List<SupplierDTO> findAllSupplier() {
        List<Supplier> supplierList = supplierMapper.findAllSupplier();
        if (null != supplierList && supplierList.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(supplierList), SupplierDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
}