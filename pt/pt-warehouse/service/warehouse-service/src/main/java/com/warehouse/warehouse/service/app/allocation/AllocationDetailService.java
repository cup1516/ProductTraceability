package com.warehouse.warehouse.service.app.allocation;



import com.warehouse.warehouse.pojo.dto.allocation.AllocationDetailDTO;

import java.util.List;

/**
 * 调拨单子表(AllocationDetail)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface AllocationDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AllocationDetailDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AllocationDetailDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param allocationDetailDTO 实例对象
     * @return 对象列表
     */
    List<AllocationDetailDTO> describeAll(AllocationDetailDTO allocationDetailDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param allocationDetailDTO 实例对象
     * @return 对象列表
     */
    List<AllocationDetailDTO> describeFuzzy(AllocationDetailDTO allocationDetailDTO);
    
    
    /**
     * 新增数据
     *
     * @param allocationDetailDTO 实例对象
     * @return 实例对象
     */
    AllocationDetailDTO insertAllocationDetail(AllocationDetailDTO allocationDetailDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<AllocationDetailDTO> saveAllocationDetail(List<AllocationDetailDTO> list);

    /**
     * 修改数据
     *
     * @param allocationDetailDTO 实例对象
     * @return 实例对象
     */
    AllocationDetailDTO updateAllocationDetailByPrimaryKey(AllocationDetailDTO allocationDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteAllocationDetailByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteAllocationDetailByPrimaryKeys(List<String> list);

    /**
     * 查询调拨单的调拨项详情
     * @param allocationId
     * @return
     */
    List<AllocationDetailDTO> findByAllocationId(String allocationId);

    /**
     * 通过调拨编号清空所有调拨详情数据
     * @param orderNo
     * @return
     */
    Integer deleteAllByPid(String orderNo);

}