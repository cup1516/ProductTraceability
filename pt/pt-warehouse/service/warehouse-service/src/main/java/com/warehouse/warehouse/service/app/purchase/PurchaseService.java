package com.warehouse.warehouse.service.app.purchase;


import com.warehouse.warehouse.pojo.dto.purchase.PurchaseDTO;

import java.util.List;

/**
 * 采购表(Purchase)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface PurchaseService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PurchaseDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<PurchaseDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param purchaseDTO 实例对象
     * @return 对象列表
     */
    List<PurchaseDTO> describeAll(PurchaseDTO purchaseDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param purchaseDTO 实例对象
     * @return 对象列表
     */
    List<PurchaseDTO> describeFuzzy(PurchaseDTO purchaseDTO);
    
    
    /**
     * 新增数据
     *
     * @param purchaseDTO 实例对象
     * @return 实例对象
     */
    PurchaseDTO insertPurchase(PurchaseDTO purchaseDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<PurchaseDTO> savePurchase(List<PurchaseDTO> list);

    /**
     * 修改数据
     *
     * @param purchaseDTO 实例对象
     * @return 实例对象
     */
    PurchaseDTO updatePurchaseByPrimaryKey(PurchaseDTO purchaseDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deletePurchaseByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deletePurchaseByPrimaryKeys(List<String> list);

}