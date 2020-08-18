package com.warehouse.warehouse.dao.supplier;

import com.warehouse.warehouse.pojo.po.supplier.Supplier;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 供应商表(Supplier)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */

public interface SupplierMapper extends Mapper<Supplier> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Supplier describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Supplier> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param supplier 实例对象
     * @return 对象列表
     */
    List<Supplier> describeAll(Supplier supplier);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param supplier 实例对象
     * @return 对象列表
     */
    List<Supplier> describeFuzzy(Supplier supplier);

    /**
     * 新增数据
     *
     * @param supplier 实例对象
     * @return 影响行数
     */
    Integer insertSupplier(Supplier supplier);

    /**
     * 新增数据
     *
     * @param List<supplier> 实例对象
     * @return 影响行数
     */
    Integer saveSupplier(@Param("list") List<Supplier> list);

    /**
     * 修改数据
     *
     * @param supplier 实例对象
     * @return 影响行数
     */
    Integer updateSupplierByPrimaryKey(Supplier supplier);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteSupplierByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteSupplierByPrimaryKeys(@Param("list") List<String> list);


    List<Supplier> findAllSupplier();

}