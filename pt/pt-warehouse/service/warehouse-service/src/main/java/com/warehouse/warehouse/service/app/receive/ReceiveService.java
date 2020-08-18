package com.warehouse.warehouse.service.app.receive;


import com.warehouse.warehouse.pojo.dto.receive.ReceiveDTO;

import java.util.List;

/**
 * 接货表(Receive)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface ReceiveService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ReceiveDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ReceiveDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param receiveDTO 实例对象
     * @return 对象列表
     */
    List<ReceiveDTO> describeAll(ReceiveDTO receiveDTO);

    /**
     * 条件模糊查询所有数据
     *
     * @param receiveDTO 实例对象
     * @return 对象列表
     */
    List<ReceiveDTO> describeFuzzy(ReceiveDTO receiveDTO);


    /**
     * 新增数据
     *
     * @param receiveDTO 实例对象
     * @return 实例对象
     */
    ReceiveDTO insertReceive(ReceiveDTO receiveDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<ReceiveDTO> saveReceive(List<ReceiveDTO> list);

    /**
     * 修改数据
     *
     * @param receiveDTO 实例对象
     * @return 实例对象
     */
    ReceiveDTO updateReceiveByPrimaryKey(ReceiveDTO receiveDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteReceiveByPrimaryKey(String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteReceiveByPrimaryKeys(List<String> list);

}