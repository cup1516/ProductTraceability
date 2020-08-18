package com.warehouse.warehouse.service.impl.purchase;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.purchase.PurchaseMapper;
import com.warehouse.warehouse.pojo.dto.purchase.PurchaseDTO;
import com.warehouse.warehouse.pojo.po.purchase.Purchase;
import com.warehouse.warehouse.service.app.purchase.PurchaseService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 采购表(Purchase)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PurchaseDTO describeById(String id) {
        PurchaseDTO dto = new PurchaseDTO();
        Purchase purchase = this.purchaseMapper.describeById(id);
        if (null != purchase) {
            BeanUtils.copyProperties(purchase, dto);
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
    public List<PurchaseDTO> describeAllByLimit(long offset, long limit) {
        List<Purchase> list = this.purchaseMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PurchaseDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param purchaseDTO 对象
     * @return 对象列表
     */
    @Override
    public List<PurchaseDTO> describeAll(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseDTO, purchase);
        List<Purchase> list = this.purchaseMapper.describeAll(purchase);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PurchaseDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param purchaseDTO 对象
     * @return 对象列表
     */
    @Override
    public List<PurchaseDTO> describeFuzzy(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseDTO, purchase);
        List<Purchase> list = this.purchaseMapper.describeFuzzy(purchase);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PurchaseDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param purchaseDTO 实例对象
     * @return 实例对象
     */
    @Override
    public PurchaseDTO insertPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseDTO, purchase);
        if (null == purchase.getId() || "".equals(purchase.getId())) {
            purchase.setId(UUID.randomUUID().toString());
        }
        this.purchaseMapper.insertPurchase(purchase);
        return purchaseDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<PurchaseDTO> savePurchase(List<PurchaseDTO> list) {
        list.forEach(purchaseDTO -> {
            if (null == purchaseDTO.getId() || "".equals(purchaseDTO.getId())) {
                purchaseDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.purchaseMapper.savePurchase(JSON.parseArray(JSON.toJSONString(list), Purchase.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param purchaseDTO 实例对象
     * @return 实例对象
     */
    @Override
    public PurchaseDTO updatePurchaseByPrimaryKey(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseDTO, purchase);
        this.purchaseMapper.updatePurchaseByPrimaryKey(purchase);
        return this.describeById(purchase.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deletePurchaseByPrimaryKey(String id) {
        return this.purchaseMapper.deletePurchaseByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deletePurchaseByPrimaryKeys(List<String> list) {
        return this.purchaseMapper.deletePurchaseByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(PurchaseDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Purchase.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getPurchaseNo() && !"".equals(dto.getPurchaseNo())) {
                criteriaName.andLike("purchaseNo", "%" + dto.getPurchaseNo() + "%");
            }
            if (null != dto.getSupplierId() && !"".equals(dto.getSupplierId())) {
                criteriaName.andLike("supplierId", "%" + dto.getSupplierId() + "%");
            }
            if (null != dto.getSupplierName() && !"".equals(dto.getSupplierName())) {
                criteriaName.andLike("supplierName", "%" + dto.getSupplierName() + "%");
            }
            if (null != dto.getCommodityNum()) {
                criteriaName.andLike("commodityNum", "%" + dto.getCommodityNum() + "%");
            }
            if (null != dto.getEstimateArrivalTime()) {
                criteriaName.andLike("estimateArrivalTime", "%" + dto.getEstimateArrivalTime() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
            }
            if (null != dto.getWarehouseName() && !"".equals(dto.getWarehouseName())) {
                criteriaName.andLike("warehouseName", "%" + dto.getWarehouseName() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
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
    private Example initExample(PurchaseDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Purchase.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getPurchaseNo() && !"".equals(dto.getPurchaseNo())) {
                criteriaName.andEqualTo("purchaseNo", dto.getPurchaseNo());
            }
            if (null != dto.getSupplierId() && !"".equals(dto.getSupplierId())) {
                criteriaName.andEqualTo("supplierId", dto.getSupplierId());
            }
            if (null != dto.getSupplierName() && !"".equals(dto.getSupplierName())) {
                criteriaName.andEqualTo("supplierName", dto.getSupplierName());
            }
            if (null != dto.getCommodityNum()) {
                criteriaName.andEqualTo("commodityNum", dto.getCommodityNum());
            }
            if (null != dto.getEstimateArrivalTime()) {
                criteriaName.andEqualTo("estimateArrivalTime", dto.getEstimateArrivalTime());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
            }
            if (null != dto.getWarehouseName() && !"".equals(dto.getWarehouseName())) {
                criteriaName.andEqualTo("warehouseName", dto.getWarehouseName());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
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