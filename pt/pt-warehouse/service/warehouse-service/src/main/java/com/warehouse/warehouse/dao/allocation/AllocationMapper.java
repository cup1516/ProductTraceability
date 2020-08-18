package com.warehouse.warehouse.dao.allocation;

import com.warehouse.warehouse.pojo.po.allocation.Allocation;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 调拨单(Allocation)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface AllocationMapper extends Mapper<Allocation> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Allocation describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Allocation> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param allocation 实例对象
     * @return 对象列表
     */
    List<Allocation> describeAll(Allocation allocation);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param allocation 实例对象
     * @return 对象列表
     */
    List<Allocation> describeFuzzy(Allocation allocation);

    /**
     * 新增数据
     *
     * @param allocation 实例对象
     * @return 影响行数
     */
    Integer insertAllocation(Allocation allocation);

    /**
     * 新增数据
     *
     * @param List<allocation> 实例对象
     * @return 影响行数
     */
    Integer saveAllocation(@Param("list") List<Allocation> list);

    /**
     * 修改数据
     *
     * @param allocation 实例对象
     * @return 影响行数
     */
    Integer updateAllocationByPrimaryKey(Allocation allocation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteAllocationByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteAllocationByPrimaryKeys(@Param("list") List<String> list);

    /**
     * 查看
     * @return
     */
    List<Allocation> describeNotCommit(Allocation allocation);

    /**
     *
     * @param allocation
     * @param userId
     * @return
     */
    List<Map<String, Object>> describeCommissionFuzzy(@Param("allocation") Allocation allocation, @Param("userId") String userId);
}