package com.warehouse.warehouse.service.app.allocation;

import com.warehouse.warehouse.pojo.dto.allocation.AllocationDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;

import java.util.List;

/**
 * 调拨单(Allocation)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface AllocationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AllocationDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AllocationDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param allocationDTO 实例对象
     * @return 对象列表
     */
    List<AllocationDTO> describeAll(AllocationDTO allocationDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param allocationDTO 实例对象
     * @return 对象列表
     */
    List<AllocationDTO> describeFuzzy(AllocationDTO allocationDTO);
    
    
    /**
     * 新增数据
     *
     * @param allocationDTO 实例对象
     * @return 实例对象
     */
    AllocationDTO insertAllocation(AllocationDTO allocationDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<AllocationDTO> saveAllocation(List<AllocationDTO> list);

    /**
     * 修改数据
     *
     * @param allocationDTO 实例对象
     * @return 实例对象
     */
    AllocationDTO updateAllocationByPrimaryKey(AllocationDTO allocationDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteAllocationByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteAllocationByPrimaryKeys(List<String> list);

    /**
     * 查询未提交的工单
     * @param allocationDTO
     * @return
     */
    List<AllocationDTO> describeNotCommit(AllocationDTO allocationDTO);

    /**
     * 初始化单号
     * @param dto
     * @return
     */
    public AllocationDTO initOrderNo(UserDTO dto);

    /**
     * 修改数据
     *
     * @param allocationDTO 实例对象
     * @return 实例对象
     */
    Boolean updateAllocationByAllocationNo(AllocationDTO allocationDTO);

    /**
     * 条件分页模糊查询当前用户待审批出库单数据
     * @param allocationDTO
     * @param userId
     * @return
     */
    List<AllocationDTO> describeCommissionFuzzy(AllocationDTO allocationDTO, String userId);

    /**
     * 审批调拨单
     * @param allocationDTO
     * @return
     */
    Boolean approveAllocation(AllocationDTO allocationDTO);

}