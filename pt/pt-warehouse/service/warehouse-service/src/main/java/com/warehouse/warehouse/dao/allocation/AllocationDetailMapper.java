package com.warehouse.warehouse.dao.allocation;

import com.warehouse.warehouse.pojo.po.allocation.AllocationDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 调拨单子表(AllocationDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface AllocationDetailMapper extends Mapper<AllocationDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AllocationDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AllocationDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param allocationDetail 实例对象
     * @return 对象列表
     */
    List<AllocationDetail> describeAll(AllocationDetail allocationDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param allocationDetail 实例对象
     * @return 对象列表
     */
    List<AllocationDetail> describeFuzzy(AllocationDetail allocationDetail);

    /**
     * 新增数据
     *
     * @param allocationDetail 实例对象
     * @return 影响行数
     */
    Integer insertAllocationDetail(AllocationDetail allocationDetail);

    /**
     * 新增数据
     *
     * @param List<allocationDetail> 实例对象
     * @return 影响行数
     */
    Integer saveAllocationDetail(@Param("list") List<AllocationDetail> list);

    /**
     * 修改数据
     *
     * @param allocationDetail 实例对象
     * @return 影响行数
     */
    Integer updateAllocationDetailByPrimaryKey(AllocationDetail allocationDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteAllocationDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteAllocationDetailByPrimaryKeys(@Param("list") List<String> list);

    /**
     * 根据主表ID，查询所有的调拨项
     * @param pid
     * @return
     */
    List<AllocationDetail> findAllocationDetailListByPid(String pid);

}