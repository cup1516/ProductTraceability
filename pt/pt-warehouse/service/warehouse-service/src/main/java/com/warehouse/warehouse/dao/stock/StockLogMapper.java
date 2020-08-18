package com.warehouse.warehouse.dao.stock;

import com.warehouse.warehouse.pojo.po.stock.StockLog;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 仓储日志表(StockLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-12 20:36:32
 */

public interface StockLogMapper extends Mapper<StockLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StockLog describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<StockLog> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param stockLog 实例对象
     * @return 对象列表
     */
    List<StockLog> describeAll(StockLog stockLog);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param stockLog 实例对象
     * @return 对象列表
     */
    List<StockLog> describeFuzzy(StockLog stockLog);

    /**
     * 新增数据
     *
     * @param stockLog 实例对象
     * @return 影响行数
     */
    Integer insertStockLog(StockLog stockLog);

    /**
     * 新增数据
     *
     * @param List<stockLog> 实例对象
     * @return 影响行数
     */
    Integer saveStockLog(@Param("list") List<StockLog> list);

    /**
     * 修改数据
     *
     * @param stockLog 实例对象
     * @return 影响行数
     */
    Integer updateStockLogByPrimaryKey(StockLog stockLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteStockLogByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteStockLogByPrimaryKeys(@Param("list") List<String> list);

}