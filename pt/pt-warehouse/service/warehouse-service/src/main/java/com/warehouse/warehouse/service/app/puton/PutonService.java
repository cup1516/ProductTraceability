package com.warehouse.warehouse.service.app.puton;


import com.warehouse.warehouse.pojo.dto.puton.PutonDTO;

import java.util.List;

/**
 * (Puton)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface PutonService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PutonDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<PutonDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param putonDTO 实例对象
     * @return 对象列表
     */
    List<PutonDTO> describeAll(PutonDTO putonDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param putonDTO 实例对象
     * @return 对象列表
     */
    List<PutonDTO> describeFuzzy(PutonDTO putonDTO);
    
    
    /**
     * 新增数据
     *
     * @param putonDTO 实例对象
     * @return 实例对象
     */
    PutonDTO insertPuton(PutonDTO putonDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<PutonDTO> savePuton(List<PutonDTO> list);

    /**
     * 修改数据
     *
     * @param putonDTO 实例对象
     * @return 实例对象
     */
    PutonDTO updatePutonByPrimaryKey(PutonDTO putonDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deletePutonByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deletePutonByPrimaryKeys(List<String> list);

}