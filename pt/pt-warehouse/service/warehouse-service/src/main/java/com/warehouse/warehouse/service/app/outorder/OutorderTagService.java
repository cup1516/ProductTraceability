package com.warehouse.warehouse.service.app.outorder;


import com.warehouse.warehouse.pojo.dto.outorder.OutorderTagDTO;

import java.util.List;

/**
 * (OutorderTag)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface OutorderTagService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutorderTagDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OutorderTagDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param outorderTagDTO 实例对象
     * @return 对象列表
     */
    List<OutorderTagDTO> describeAll(OutorderTagDTO outorderTagDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param outorderTagDTO 实例对象
     * @return 对象列表
     */
    List<OutorderTagDTO> describeFuzzy(OutorderTagDTO outorderTagDTO);
    
    
    /**
     * 新增数据
     *
     * @param outorderTagDTO 实例对象
     * @return 实例对象
     */
    OutorderTagDTO insertOutorderTag(OutorderTagDTO outorderTagDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<OutorderTagDTO> saveOutorderTag(List<OutorderTagDTO> list);

    /**
     * 修改数据
     *
     * @param outorderTagDTO 实例对象
     * @return 实例对象
     */
    OutorderTagDTO updateOutorderTagByPrimaryKey(OutorderTagDTO outorderTagDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteOutorderTagByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteOutorderTagByPrimaryKeys(List<String> list);

}