package com.warehouse.warehouse.dao.receive;

import com.warehouse.warehouse.pojo.po.receive.ReceiveDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 接货表子表(ReceiveDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */

public interface ReceiveDetailMapper extends Mapper<ReceiveDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ReceiveDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ReceiveDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param receiveDetail 实例对象
     * @return 对象列表
     */
    List<ReceiveDetail> describeAll(ReceiveDetail receiveDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param receiveDetail 实例对象
     * @return 对象列表
     */
    List<ReceiveDetail> describeFuzzy(ReceiveDetail receiveDetail);

    /**
     * 新增数据
     *
     * @param receiveDetail 实例对象
     * @return 影响行数
     */
    Integer insertReceiveDetail(ReceiveDetail receiveDetail);

    /**
     * 新增数据
     *
     * @param List<receiveDetail> 实例对象
     * @return 影响行数
     */
    Integer saveReceiveDetail(@Param("list") List<ReceiveDetail> list);

    /**
     * 修改数据
     *
     * @param receiveDetail 实例对象
     * @return 影响行数
     */
    Integer updateReceiveDetailByPrimaryKey(ReceiveDetail receiveDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteReceiveDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteReceiveDetailByPrimaryKeys(@Param("list") List<String> list);

}