package com.warehouse.warehouse.service.app.warehouse;


import com.warehouse.warehouse.pojo.dto.warehouse.WarehouseDTO;

import java.util.List;

/**
 * 仓库表(Warehouse)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface WarehouseService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WarehouseDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WarehouseDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param warehouseDTO 实例对象
     * @return 对象列表
     */
    List<WarehouseDTO> describeAll(WarehouseDTO warehouseDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param warehouseDTO 实例对象
     * @return 对象列表
     */
    List<WarehouseDTO> describeFuzzy(WarehouseDTO warehouseDTO);
    
	/**
     * 根据负责人id查询所有仓库
     *
     * @param warehouseDTO 实例对象
     * @return 对象列表
     */
	 List<WarehouseDTO> getAllStoreByPrincipalId(String rincipalId,String companyId);
    
    /**
     * 新增数据
     *
     * @param warehouseDTO 实例对象
     * @return 实例对象
     */
    WarehouseDTO insertWarehouse(WarehouseDTO warehouseDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<WarehouseDTO> saveWarehouse(List<WarehouseDTO> list);

    /**
     * 修改数据
     *
     * @param warehouseDTO 实例对象
     * @return 实例对象
     */
    WarehouseDTO updateWarehouseByPrimaryKey(WarehouseDTO warehouseDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteWarehouseByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteWarehouseByPrimaryKeys(List<String> list);
    String getStorageByWarehouseId(String id);
}