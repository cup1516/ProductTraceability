package com.warehouse.warehouse.service.app.dispatch;

import com.warehouse.warehouse.pojo.dto.dispatch.DispatchDTO;
import com.warehouse.warehouse.pojo.dto.dispatch.DispatchWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;

import java.util.List;

/**
 * 报损表(Dispatch)表服务接口
 *
 * @author makejava
 * @since 2020-05-01 19:49:56
 */
public interface DispatchService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DispatchDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DispatchDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param dispatchDTO 实例对象
     * @return 对象列表
     */
    List<DispatchDTO> describeAll(DispatchDTO dispatchDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param dispatchDTO 实例对象
     * @return 对象列表
     */
    List<DispatchDTO> describeFuzzy(DispatchDTO dispatchDTO);
    
    
    /**
     * 新增数据
     *
     * @param dispatchDTO 实例对象
     * @return 实例对象
     */
    DispatchDTO insertDispatch(DispatchDTO dispatchDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<DispatchDTO> saveDispatch(List<DispatchDTO> list);

    /**
     * 修改数据
     *
     * @param dispatchDTO 实例对象
     * @return 实例对象
     */
    DispatchDTO updateDispatchByPrimaryKey(DispatchDTO dispatchDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteDispatchByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteDispatchByPrimaryKeys(List<String> list);

    /**
     * @title: initOrderNo
     * @description: 生成报损单，初始报损单信息
     * @params: [ dto ]
     * @return: com.system.service.dto.DispatchDTO
     * @createTime: 2020/5/1 19:58
     * @version: 1.0
     * @author: wanpp
     */
    DispatchDTO initOrderNo(UserDTO dto);

    /**
     * @title: describeNotCommit
     * @description: 查询未提交的报损单数据
     * @params: [ dispatchDTO ]
     * @return: java.util.List<com.system.service.dto.DispatchDTO>
     * @createTime: 2020/5/1 20:28
     * @version: 1.0
     * @author: wanpp
     */
    List<DispatchDTO> describeNotCommit(DispatchDTO dispatchDTO);

    /**
     * @title: updateWarehouseIdByDispatchNo
     * @description: 根据DispatchNo修改数据的仓库信息
     * @params: [ dispatchDTO ]
     * @return: java.lang.Integer
     * @createTime: 2020/5/1 22:10
     * @version: 1.0
     * @author: wanpp
     */
    Integer updateWarehouseIdByDispatchNo(DispatchDTO dispatchDTO);

    /**
     * @title: describeMineFuzzy
     * @description: 条件模糊查询当前用户提交报损单数据
     * @params: [ dispatchDTO, userId ]
     * @return: java.util.List<com.system.service.dto.dispatch.DispatchWithWarehouseNameDTO>
     * @createTime: 2020/5/2 9:06
     * @version: 1.0
     * @author: wanpp
     */
    List<DispatchWithWarehouseNameDTO> describeMineFuzzy(DispatchDTO dispatchDTO, String userId);

    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批报损单数据
     * @params: [ dispatchDTO, userId ]
     * @return: java.util.List<com.system.service.dto.dispatch.DispatchWithWarehouseNameDTO>
     * @createTime: 2020/5/2 11:39
     * @version: 1.0
     * @author: wanpp
     */
    List<DispatchWithWarehouseNameDTO> describeCommissionFuzzy(DispatchDTO dispatchDTO, String userId);

    /**
     * @title: approveDispatch
     * @description: 审批报损单(通过和打回)
     * @params: [ dispatchDTO ]
     * @return: java.lang.Boolean
     * @createTime: 2020/5/2 11:55
     * @version: 1.0
     * @author: wanpp
     */
    Boolean approveDispatch(DispatchDTO dispatchDTO);

    /**
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的报损单数据
     * @params: [ dispatchDTO, userId ]
     * @return: java.util.List<com.system.service.dto.dispatch.DispatchWithWarehouseNameDTO>
     * @createTime: 2020/5/2 12:54
     * @version: 1.0
     * @author: wanpp
     */
    List<DispatchWithWarehouseNameDTO> describeAlreadDoneFuzzy(DispatchDTO dispatchDTO, String userId);
}