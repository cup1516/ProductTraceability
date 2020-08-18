package com.warehouse.warehouse.service.app.stock;


import com.warehouse.warehouse.pojo.dto.stock.StockDTO;

import java.util.List;

/**
 * 仓储表(Stock)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface StockService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StockDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<StockDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param stockDTO 实例对象
     * @return 对象列表
     */
    List<StockDTO> describeAll(StockDTO stockDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param stockDTO 实例对象
     * @return 对象列表
     */
    List<StockDTO> describeFuzzy(StockDTO stockDTO);
    
    
    /**
     * 新增数据
     *
     * @param stockDTO 实例对象
     * @return 实例对象
     */
    StockDTO insertStock(StockDTO stockDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<StockDTO> saveStock(List<StockDTO> list);

    /**
     * 修改数据
     *
     * @param stockDTO 实例对象
     * @return 实例对象
     */
    StockDTO updateStockByPrimaryKey(StockDTO stockDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteStockByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteStockByPrimaryKeys(List<String> list);

}