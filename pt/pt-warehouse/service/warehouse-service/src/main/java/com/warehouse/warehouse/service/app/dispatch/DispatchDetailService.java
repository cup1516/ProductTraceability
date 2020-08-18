package com.warehouse.warehouse.service.app.dispatch;


import com.warehouse.warehouse.pojo.dto.dispatch.DispatchDetailDTO;
import com.warehouse.warehouse.pojo.dto.dispatch.InsertDispatchDetailByStockIdDTO;

import java.util.List;

/**
 * 报损子表(DispatchDetail)表服务接口
 *
 * @author makejava
 * @since 2020-05-01 19:49:56
 */
public interface DispatchDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DispatchDetailDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DispatchDetailDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param dispatchDetailDTO 实例对象
     * @return 对象列表
     */
    List<DispatchDetailDTO> describeAll(DispatchDetailDTO dispatchDetailDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param dispatchDetailDTO 实例对象
     * @return 对象列表
     */
    List<DispatchDetailDTO> describeFuzzy(DispatchDetailDTO dispatchDetailDTO);
    
    
    /**
     * 新增数据
     *
     * @param dispatchDetailDTO 实例对象
     * @return 实例对象
     */
    DispatchDetailDTO insertDispatchDetail(DispatchDetailDTO dispatchDetailDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<DispatchDetailDTO> saveDispatchDetail(List<DispatchDetailDTO> list);

    /**
     * 修改数据
     *
     * @param dispatchDetailDTO 实例对象
     * @return 实例对象
     */
    DispatchDetailDTO updateDispatchDetailByPrimaryKey(DispatchDetailDTO dispatchDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteDispatchDetailByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteDispatchDetailByPrimaryKeys(List<String> list);

    /**
     * @title: insertByStockId
     * @description: 通过仓储表编号插入数据
     * @params: [ dto ]
     * @return: java.lang.Integer
     * @createTime: 2020/5/1 22:39
     * @version: 1.0
     * @author: wanpp
     */
    Integer insertByStockId(InsertDispatchDetailByStockIdDTO dto);

    /**
     * @title: updateByStockId
     * @description: 通过货位编号修改数据
     * @params: [ dto ]
     * @return: java.lang.Integer
     * @createTime: 2020/5/1 22:46
     * @version: 1.0
     * @author: wanpp
     */
    Integer updateByStockId(InsertDispatchDetailByStockIdDTO dto);

    /**
     * @title: deleteAllByPid
     * @description: 通过报损编号清空所有报损详情数据
     * @params: [ orderNo ]
     * @return: java.lang.Integer
     * @createTime: 2020/5/2 7:48
     * @version: 1.0
     * @author: wanpp
     */
    Integer deleteAllByPid(String orderNo);
}