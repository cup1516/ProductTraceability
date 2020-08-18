package com.warehouse.warehouse.service.app.outorder;

import com.warehouse.warehouse.pojo.dto.otherout.OtheroutDTO;
import com.warehouse.warehouse.pojo.dto.outorder.OutOrderDTO;

import java.util.List;

/**
 * (OutOrder)表服务接口
 *
 * @author makejava
 * @since 2020-05-10 19:12:20
 */
public interface OutOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutOrderDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OutOrderDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param outOrderDTO 实例对象
     * @return 对象列表
     */
    List<OutOrderDTO> describeAll(OutOrderDTO outOrderDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param outOrderDTO 实例对象
     * @return 对象列表
     */
    List<OutOrderDTO> describeFuzzy(OutOrderDTO outOrderDTO);
    
    
    /**
     * 新增数据
     *
     * @param outOrderDTO 实例对象
     * @return 实例对象
     */
    OutOrderDTO insertOutOrder(OtheroutDTO otheroutDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<OutOrderDTO> saveOutOrder(List<OutOrderDTO> list);

    /**
     * 修改数据
     *
     * @param outOrderDTO 实例对象
     * @return 实例对象
     */
    OutOrderDTO updateOutOrderByPrimaryKey(OutOrderDTO outOrderDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteOutOrderByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteOutOrderByPrimaryKeys(List<String> list);

    List<OutOrderDTO> describeFuzzyCompany(OutOrderDTO outOrderDTO);

}