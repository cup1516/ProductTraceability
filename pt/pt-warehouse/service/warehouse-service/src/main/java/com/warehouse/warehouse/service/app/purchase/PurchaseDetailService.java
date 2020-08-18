package com.warehouse.warehouse.service.app.purchase;


import com.warehouse.warehouse.pojo.dto.purchase.PurchaseDetailDTO;

import java.util.List;

/**
 * 采购单子表(PurchaseDetail)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface PurchaseDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PurchaseDetailDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<PurchaseDetailDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param purchaseDetailDTO 实例对象
     * @return 对象列表
     */
    List<PurchaseDetailDTO> describeAll(PurchaseDetailDTO purchaseDetailDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param purchaseDetailDTO 实例对象
     * @return 对象列表
     */
    List<PurchaseDetailDTO> describeFuzzy(PurchaseDetailDTO purchaseDetailDTO);
    
    
    /**
     * 新增数据
     *
     * @param purchaseDetailDTO 实例对象
     * @return 实例对象
     */
    PurchaseDetailDTO insertPurchaseDetail(PurchaseDetailDTO purchaseDetailDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<PurchaseDetailDTO> savePurchaseDetail(List<PurchaseDetailDTO> list);

    /**
     * 修改数据
     *
     * @param purchaseDetailDTO 实例对象
     * @return 实例对象
     */
    PurchaseDetailDTO updatePurchaseDetailByPrimaryKey(PurchaseDetailDTO purchaseDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deletePurchaseDetailByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deletePurchaseDetailByPrimaryKeys(List<String> list);

}