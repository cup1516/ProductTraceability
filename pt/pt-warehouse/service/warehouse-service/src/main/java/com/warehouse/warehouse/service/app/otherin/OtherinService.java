package com.warehouse.warehouse.service.app.otherin;

import com.warehouse.warehouse.pojo.dto.otherin.OtherinDTO;
import com.warehouse.warehouse.pojo.dto.otherin.OtherinWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesAndStockDTO;

import java.util.List;

/**
 * 入库表(Otherin)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface OtherinService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OtherinDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OtherinDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param otherinDTO 实例对象
     * @return 对象列表
     */
    List<OtherinDTO> describeAll(OtherinDTO otherinDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param otherinDTO 实例对象
     * @return 对象列表
     */
    List<OtherinDTO> describeFuzzy(OtherinDTO otherinDTO);

    /**
     * 查询未提交的入库单数据
     *
     * @param otherinDTO 实例对象
     * @return 对象列表
     */
    List<OtherinDTO> describeNotCommit(OtherinDTO otherinDTO);

    /**
     * 新增数据
     *
     * @param otherinDTO 实例对象
     * @return 实例对象
     */
    OtherinDTO insertOtherin(OtherinDTO otherinDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<OtherinDTO> saveOtherin(List<OtherinDTO> list);

    /**
     * 修改数据
     *
     * @param otherinDTO 实例对象
     * @return 实例对象
     */
    OtherinDTO updateOtherinByPrimaryKey(OtherinDTO otherinDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteOtherinByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteOtherinByPrimaryKeys(List<String> list);
    /**
     * 生成入库单号
     *
     * @params dto 实例对象
     * @return 实例对象
     */

    OtherinDTO initOrderNo(UserDTO dto);

    List<OtherinWithWarehouseNameDTO> describeMineFuzzy(OtherinDTO otherinDTO, String userId);

    Integer updateWarehouseIdByOtherinNo(OtherinDTO otherinDTO);


    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批入库单数据
     * @params: [ otherinDTO, userId ]
     * @return: java.util.List<com.system.service.dto.OtherinWithWarehouseNameDTO>
     * @createTime: 2020/4/19 12:31
     * @version: 1.0
     * @author: wanpp
     */
    List<OtherinWithWarehouseNameDTO> describeCommissionFuzzy(OtherinDTO otherinDTO, String userId);
    /**
     * @title: approveOtherin
     * @description: 审批出库单(通过和打回)
     * @params: [ otheroutDTO ]
     * @return: java.lang.Boolean
     * @createTime: 2020/4/19 13:37
     * @version: 1.0
     * @author: wanpp
     */
    Boolean approveOtherin(OtherinDTO otherinDTO);



    /**
     * @title: describeShelvesAndStockByWarehouseId
     * @description: 根据仓库id获取货架货物级联信息
     * @params: [ warehouseId ]
     * @return: java.util.List<com.system.service.dto.ShelvesAndStockDTO>
     * @createTime: 2020/4/14 9:07
     * @version: 1.0
     * @author: wanpp
     */
    List<ShelvesAndStockDTO> describeShelvesAndStockByWarehouseId(String warehouseId, String goodsNo);

    /**
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的出库单数据
     * @params: [ otheroutDTO, userId ]
     * @return: java.util.List<com.system.service.dto.otherout.OtheroutWithWarehouseNameDTO>
     * @createTime: 2020/4/20 20:29
     * @version: 1.0
     * @author: wanpp
     */
    List<OtherinWithWarehouseNameDTO> describeAlreadDoneFuzzy(OtherinDTO otherinDTO, String userId);


    OtherinDTO describeByOtherinNo(String otherinNo);
}