package com.warehouse.warehouse.service.app.otherout;

import com.warehouse.warehouse.pojo.dto.otherout.InsertOtheroutDetailByStockIdDTO;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutDetailDTO;

import java.util.List;

/**
 * 出库子表(OtheroutDetail)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface OtheroutDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OtheroutDetailDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OtheroutDetailDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param otheroutDetailDTO 实例对象
     * @return 对象列表
     */
    List<OtheroutDetailDTO> describeAll(OtheroutDetailDTO otheroutDetailDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param otheroutDetailDTO 实例对象
     * @return 对象列表
     */
    List<OtheroutDetailDTO> describeFuzzy(OtheroutDetailDTO otheroutDetailDTO);
    
    
    /**
     * 新增数据
     *
     * @param otheroutDetailDTO 实例对象
     * @return 实例对象
     */
    OtheroutDetailDTO insertOtheroutDetail(OtheroutDetailDTO otheroutDetailDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<OtheroutDetailDTO> saveOtheroutDetail(List<OtheroutDetailDTO> list);

    /**
     * 修改数据
     *
     * @param otheroutDetailDTO 实例对象
     * @return 实例对象
     */
    OtheroutDetailDTO updateOtheroutDetailByPrimaryKey(OtheroutDetailDTO otheroutDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteOtheroutDetailByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteOtheroutDetailByPrimaryKeys(List<String> list);

    /**
     * @title: insertByStockId
     * @description: 通过仓储表编号新增数据
     * @params: [ dto ]
     * @return: java.lang.Integer
     * @createTime: 2020/4/18 15:02
     * @version: 1.0
     * @author: wanpp
     */
    Integer insertByStockId(InsertOtheroutDetailByStockIdDTO dto);

    /**
     * @title: updateByStockId
     * @description: 通过仓储表编号更新数据
     * @params: [ dto ]
     * @return: java.lang.Integer
     * @createTime: 2020/4/18 15:02
     * @version: 1.0
     * @author: wanpp
     */
    Integer updateByStockId(InsertOtheroutDetailByStockIdDTO dto);

    /**
     * @title: deleteAllByPid
     * @description: 通过出库编号清空所有出库详情数据
     * @params: [ orderNo ]
     * @return: java.lang.Integer
     * @createTime: 2020/4/18 16:59
     * @version: 1.0
     * @author: wanpp
     */
    Integer deleteAllByPid(String orderNo);
}