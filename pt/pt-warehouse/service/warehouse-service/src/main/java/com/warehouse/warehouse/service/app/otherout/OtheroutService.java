package com.warehouse.warehouse.service.app.otherout;


import com.warehouse.warehouse.pojo.dto.otherout.OtheroutDTO;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesAndStockDTO;

import java.util.List;

/**
 * 出库表(Otherout)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface OtheroutService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OtheroutDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OtheroutDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param otheroutDTO 实例对象
     * @return 对象列表
     */
    List<OtheroutDTO> describeAll(OtheroutDTO otheroutDTO);

    /**
     * 条件模糊查询所有数据
     *
     * @param otheroutDTO 实例对象
     * @return 对象列表
     */
    List<OtheroutDTO> describeFuzzy(OtheroutDTO otheroutDTO);

    /**
     * 查询未提交的出库单数据
     *
     * @param otheroutDTO 实例对象
     * @return 对象列表
     */
    List<OtheroutDTO> describeNotCommit(OtheroutDTO otheroutDTO);

    /**
     * 新增数据
     *
     * @param otheroutDTO 实例对象
     * @return 实例对象
     */
    OtheroutDTO insertOtherout(OtheroutDTO otheroutDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<OtheroutDTO> saveOtherout(List<OtheroutDTO> list);

    /**
     * 修改数据
     *
     * @param otheroutDTO 实例对象
     * @return 实例对象
     */
    OtheroutDTO updateOtheroutByPrimaryKey(OtheroutDTO otheroutDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteOtheroutByPrimaryKey(String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteOtheroutByPrimaryKeys(List<String> list);

    /**
     * 生成出库单号
     *
     * @return 实例对象
     * @params OtheroutDTO 实例对象
     */
    OtheroutDTO initOrderNo(UserDTO dto);

    /**
     * @title: describeShelvesAndStockByWarehouseId
     * @description: 根据仓库id获取货架货物级联信息
     * @params: [ warehouseId ]
     * @return: java.util.List<com.system.service.dto.ShelvesAndStockDTO>
     * @createTime: 2020/4/14 9:07
     * @version: 1.0
     * @author: wanpp
     */
    List<ShelvesAndStockDTO> describeShelvesAndStockByWarehouseId(String warehouseId);

    /**
     * @title: updateWarehouseIdByOtheroutNo
     * @description: 根据OtheroutNo修改数据的仓库信息
     * @params: [ otheroutDTO ]
     * @return: java.lang.Integer
     * @createTime: 2020/4/18 18:57
     * @version: 1.0
     * @author: wanpp
     */
    Integer updateWarehouseIdByOtheroutNo(OtheroutDTO otheroutDTO);

    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批出库单数据
     * @params: [ otheroutDTO, userId ]
     * @return: java.util.List<com.system.service.dto.OtheroutWithWarehouseNameDTO>
     * @createTime: 2020/4/19 12:31
     * @version: 1.0
     * @author: wanpp
     */
    List<OtheroutWithWarehouseNameDTO> describeCommissionFuzzy(OtheroutDTO otheroutDTO, String userId);

    /**
     * @title: approveOtherout
     * @description: 审批出库单(通过和打回)
     * @params: [ otheroutDTO ]
     * @return: java.lang.Boolean
     * @createTime: 2020/4/19 13:37
     * @version: 1.0
     * @author: wanpp
     */
    Boolean approveOtherout(OtheroutDTO otheroutDTO);

    /**
     * @title: describeMineFuzzy
     * @description: 条件分页模糊查询当前用户提交出库单数据
     * @params: [ otheroutDTO, userId ]
     * @return: java.util.List<com.system.service.dto.otherout.OtheroutWithWarehouseNameDTO>
     * @createTime: 2020/4/19 17:42
     * @version: 1.0
     * @author: wanpp
     */
    List<OtheroutWithWarehouseNameDTO> describeMineFuzzy(OtheroutDTO otheroutDTO, String userId);

    /**
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的出库单数据
     * @params: [ otheroutDTO, userId ]
     * @return: java.util.List<com.system.service.dto.otherout.OtheroutWithWarehouseNameDTO>
     * @createTime: 2020/4/20 20:29
     * @version: 1.0
     * @author: wanpp
     */
    List<OtheroutWithWarehouseNameDTO> describeAlreadDoneFuzzy(OtheroutDTO otheroutDTO, String userId);
}