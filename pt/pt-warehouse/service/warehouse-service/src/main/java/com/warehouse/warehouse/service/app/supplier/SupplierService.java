package com.warehouse.warehouse.service.app.supplier;


import com.warehouse.warehouse.pojo.dto.supplier.SupplierDTO;

import java.util.List;

/**
 * 供应商表(Supplier)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface SupplierService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SupplierDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SupplierDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param supplierDTO 实例对象
     * @return 对象列表
     */
    List<SupplierDTO> describeAll(SupplierDTO supplierDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param supplierDTO 实例对象
     * @return 对象列表
     */
    List<SupplierDTO> describeFuzzy(SupplierDTO supplierDTO);
    
    
    /**
     * 新增数据
     *
     * @param supplierDTO 实例对象
     * @return 实例对象
     */
    SupplierDTO insertSupplier(SupplierDTO supplierDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<SupplierDTO> saveSupplier(List<SupplierDTO> list);

    /**
     * 修改数据
     *
     * @param supplierDTO 实例对象
     * @return 实例对象
     */
    SupplierDTO updateSupplierByPrimaryKey(SupplierDTO supplierDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteSupplierByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteSupplierByPrimaryKeys(List<String> list);



    public List<SupplierDTO> findAllSupplier();

}