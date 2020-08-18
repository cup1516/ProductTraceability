package com.warehouse.warehouse.service.app.stock;


import com.warehouse.warehouse.pojo.dto.stock.StockLogDTO;

import java.util.List;

/**
 * 仓储日志表(StockLog)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface StockLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StockLogDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<StockLogDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param stockLogDTO 实例对象
     * @return 对象列表
     */
    List<StockLogDTO> describeAll(StockLogDTO stockLogDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param stockLogDTO 实例对象
     * @return 对象列表
     */
    List<StockLogDTO> describeFuzzy(StockLogDTO stockLogDTO);
    
    
    /**
     * 新增数据
     *
     * @param stockLogDTO 实例对象
     * @return 实例对象
     */
    StockLogDTO insertStockLog(StockLogDTO stockLogDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<StockLogDTO> saveStockLog(List<StockLogDTO> list);

    /**
     * 修改数据
     *
     * @param stockLogDTO 实例对象
     * @return 实例对象
     */
    StockLogDTO updateStockLogByPrimaryKey(StockLogDTO stockLogDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteStockLogByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteStockLogByPrimaryKeys(List<String> list);

}