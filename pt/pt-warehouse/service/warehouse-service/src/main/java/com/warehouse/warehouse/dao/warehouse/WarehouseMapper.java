package com.warehouse.warehouse.dao.warehouse;

import com.warehouse.warehouse.pojo.po.warehouse.Warehouse;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 仓库表(Warehouse)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */

public interface WarehouseMapper extends Mapper<Warehouse> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Warehouse describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Warehouse> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param warehouse 实例对象
     * @return 对象列表
     */
    List<Warehouse> describeAll(Warehouse warehouse);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param warehouse 实例对象
     * @return 对象列表
     */
    List<Warehouse> describeFuzzy(Warehouse warehouse);

    /**
     * 新增数据
     *
     * @param warehouse 实例对象
     * @return 影响行数
     */
    Integer insertWarehouse(Warehouse warehouse);

    /**
     * 新增数据
     *
     * @param List<warehouse> 实例对象
     * @return 影响行数
     */
    Integer saveWarehouse(@Param("list") List<Warehouse> list);

    /**
     * 修改数据
     *
     * @param warehouse 实例对象
     * @return 影响行数
     */
    Integer updateWarehouseByPrimaryKey(Warehouse warehouse);

    /**
     * @title: getAllStoreByPrincipalId
     * @description: 根据负责人id查询仓库信息
     * @params:  [ principalId ]
     * @return: java.util.List<com.dao.entity.Warehouse>
     * @createTime: 2020/4/14 22:36
     * @version: 1.0
     * @author: wanpp
     */
    List<Warehouse> getAllStoreByPrincipalId(@Param("principalId") String principalId, @Param("companyId") String companyId);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteWarehouseByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteWarehouseByPrimaryKeys(@Param("list") List<String> list);

}
