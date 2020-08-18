package com.warehouse.warehouse.dao.dispatch;

import com.warehouse.warehouse.pojo.po.dispatch.DispatchDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 报损子表(DispatchDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-01 19:48:49
 */

public interface DispatchDetailMapper extends Mapper<DispatchDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DispatchDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DispatchDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dispatchDetail 实例对象
     * @return 对象列表
     */
    List<DispatchDetail> describeAll(DispatchDetail dispatchDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param dispatchDetail 实例对象
     * @return 对象列表
     */
    List<DispatchDetail> describeFuzzy(DispatchDetail dispatchDetail);

    /**
     * 新增数据
     *
     * @param dispatchDetail 实例对象
     * @return 影响行数
     */
    Integer insertDispatchDetail(DispatchDetail dispatchDetail);

    /**
     * 新增数据
     *
     * @param List<dispatchDetail> 实例对象
     * @return 影响行数
     */
    Integer saveDispatchDetail(@Param("list") List<DispatchDetail> list);

    /**
     * 修改数据
     *
     * @param dispatchDetail 实例对象
     * @return 影响行数
     */
    Integer updateDispatchDetailByPrimaryKey(DispatchDetail dispatchDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteDispatchDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteDispatchDetailByPrimaryKeys(@Param("list") List<String> list);

}