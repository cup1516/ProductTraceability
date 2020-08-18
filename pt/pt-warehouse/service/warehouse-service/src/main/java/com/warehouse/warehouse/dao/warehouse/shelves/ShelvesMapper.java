package com.warehouse.warehouse.dao.warehouse.shelves;

import com.warehouse.warehouse.pojo.po.warehouse.shelves.Shelves;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 货架表(Shelves)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-12 20:32:33
 */

public interface ShelvesMapper extends Mapper<Shelves> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Shelves describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Shelves> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param shelves 实例对象
     * @return 对象列表
     */
    List<Shelves> describeAll(Shelves shelves);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param shelves 实例对象
     * @return 对象列表
     */
    List<Shelves> describeFuzzy(Shelves shelves);

    /**
     * 新增数据
     *
     * @param shelves 实例对象
     * @return 影响行数
     */
    Integer insertShelves(Shelves shelves);

    /**
     * 新增数据
     *
     * @param List<shelves> 实例对象
     * @return 影响行数
     */
    Integer saveShelves(@Param("list") List<Shelves> list);

    /**
     * 修改数据
     *
     * @param shelves 实例对象
     * @return 影响行数
     */
    Integer updateShelvesByPrimaryKey(Shelves shelves);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteShelvesByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteShelvesByPrimaryKeys(@Param("list") List<String> list);



    List<Shelves> describesAllShelvesByWarehouseId(@Param("warehouseId") String warehouseId);


    List<Shelves> describesAllUsableShelvesByWarehouseId(@Param("warehouseId") String warehouseId);

    Shelves describeByLocatorId(@Param("locatorId") String locatorId);

    /**
     * @title: describeAllMineGroupByWarehouse
     * @description: 查询用户所有数据(级联)
     * @params: [userId]
     * @return: java.util.List<com.dao.entity.Shelves>
     * @createTime: 2020/5/10 15:43
     * @version: 1.0
     * @author: wanpp
     */
    List<Shelves> describeAllMineGroupByWarehouse(@Param("userId") String userId);
}