package com.warehouse.warehouse.service.app.outorder;


import com.warehouse.warehouse.pojo.dto.outorder.OutorderDetailDTO;

import java.util.List;

/**
 * 出库收据表子表(OutorderDetail)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface OutorderDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutorderDetailDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OutorderDetailDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param outorderDetailDTO 实例对象
     * @return 对象列表
     */
    List<OutorderDetailDTO> describeAll(OutorderDetailDTO outorderDetailDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param outorderDetailDTO 实例对象
     * @return 对象列表
     */
    List<OutorderDetailDTO> describeFuzzy(OutorderDetailDTO outorderDetailDTO);
    
    
    /**
     * 新增数据
     *
     * @param outorderDetailDTO 实例对象
     * @return 实例对象
     */
    OutorderDetailDTO insertOutorderDetail(OutorderDetailDTO outorderDetailDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<OutorderDetailDTO> saveOutorderDetail(List<OutorderDetailDTO> list);

    /**
     * 修改数据
     *
     * @param outorderDetailDTO 实例对象
     * @return 实例对象
     */
    OutorderDetailDTO updateOutorderDetailByPrimaryKey(OutorderDetailDTO outorderDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteOutorderDetailByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteOutorderDetailByPrimaryKeys(List<String> list);

}