package com.warehouse.warehouse.dao.warehouse.shelves.locator;

import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.Locator;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 货位表(Locator)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface LocatorMapper extends Mapper<Locator> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Locator describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Locator> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param locator 实例对象
     * @return 对象列表
     */
    List<Locator> describeAll(Locator locator);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param locator 实例对象
     * @return 对象列表
     */
    List<Locator> describeFuzzy(Locator locator);

    /**
     * 新增数据
     *
     * @param locator 实例对象
     * @return 影响行数
     */
    Integer insertLocator(Locator locator);

    /**
     * 新增数据
     *
     * @param
     * @return 影响行数
     */
    Integer saveLocator(@Param("list") List<Locator> list);

    /**
     * 修改数据
     *
     * @param locator 实例对象
     * @return 影响行数
     */
    Integer updateLocatorByPrimaryKey(Locator locator);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteLocatorByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteLocatorByPrimaryKeys(@Param("list") List<String> list);

    /**
     * @title: describesLocatorByShelveseIdList
     * @description:  根据 货架id集合 获取货位信息
     * @params: shelvesIdList
     * @return: List<Locator>
     * @createTime: 2020/4/14 9:20
     * @version: 1.0
     * @author: wanpp
     */
    List<Locator> desrcibesLocatorByShelveseIdList(@Param("shelvesIdList") List<String> shelvesIdList);

    /**
     * @title: describesLocatorByShelveseIdList
     * @description:  根据 货架id集合 获取货位信息
     * @params: shelvesIdList
     * @return: List<Locator>
     * @createTime: 2020/4/14 9:20
     * @version: 1.0
     * @author: wanpp
     */
    List<Locator> desrcibesUsableLocatorByShelveseIdList(@Param("shelvesIdList") List<String> shelvesIdList);

    /**
     * @title: describeFuzzyWithWarehouseName
     * @description: 条件分页模糊查询所有数据(携带仓库名称)
     * @params: [locator]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @createTime: 2020/5/10 14:51
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String, Object>> describeFuzzyWithWarehouseName(Locator locator);

    List<Locator> describesAllLocatorsByWarehouseId(String warehouseId);
    List<Locator> describeFullLocatorsByWarehouseId(String warehouseId);
    List<Locator> describeUseLocatorsByWarehouseId(String warehouseId);

}