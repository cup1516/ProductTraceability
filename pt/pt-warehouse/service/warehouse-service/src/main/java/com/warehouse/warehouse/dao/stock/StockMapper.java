package com.warehouse.warehouse.dao.stock;

import com.warehouse.warehouse.pojo.po.stock.Stock;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 仓储表(Stock)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-12 20:36:32
 */

public interface StockMapper extends Mapper<Stock> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Stock describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Stock> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param stock 实例对象
     * @return 对象列表
     */
    List<Stock> describeAll(Stock stock);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param stock 实例对象
     * @return 对象列表
     */
    List<Stock> describeFuzzy(Stock stock);

    /**
     * 新增数据
     *
     * @param stock 实例对象
     * @return 影响行数
     */
    Integer insertStock(Stock stock);

    /**
     * 新增数据
     *
     * @param List<stock> 实例对象
     * @return 影响行数
     */
    Integer saveStock(@Param("list") List<Stock> list);

    /**
     * 修改数据
     *
     * @param stock 实例对象
     * @return 影响行数
     */
    Integer updateStockByPrimaryKey(Stock stock);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteStockByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteStockByPrimaryKeys(@Param("list") List<String> list);

    /**
     * @title: describesStockByShelveseIdList
     * @description:  根据 货架id集合 获取货位信息
     * @params: shelvesIdList
     * @return: List<Stock>
     * @createTime: 2020/4/14 9:20
     * @version: 1.0
     * @author: wanpp
     */
    List<Stock> describesStockByShelveseIdList(@Param("shelvesIdList") List<String> shelvesIdList);
    /**
     通过货架id查找对应的stock
     */
     List<Stock> findByLocatorId(@Param("locatorId") String locatorId);

    Integer updateBatchStockListInCqty(@Param("list") List<Stock> list);
}