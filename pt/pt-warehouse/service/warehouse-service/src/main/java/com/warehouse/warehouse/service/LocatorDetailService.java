package com.warehouse.warehouse.service;

import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDetailDTO;

import java.util.List;

/**
 * (LocatorDetail)表服务接口
 *
 * @author makejava
 * @since 2020-06-02 21:20:32
 */
public interface LocatorDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LocatorDetailDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<LocatorDetailDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param locatorDetailDTO 实例对象
     * @return 对象列表
     */
    List<LocatorDetailDTO> describeAll(LocatorDetailDTO locatorDetailDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param locatorDetailDTO 实例对象
     * @return 对象列表
     */
    List<LocatorDetailDTO> describeFuzzy(LocatorDetailDTO locatorDetailDTO);
    
    
    /**
     * 新增数据
     *
     * @param locatorDetailDTO 实例对象
     * @return 实例对象
     */
    LocatorDetailDTO insertLocatorDetail(LocatorDetailDTO locatorDetailDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<LocatorDetailDTO> saveLocatorDetail(List<LocatorDetailDTO> list);

    /**
     * 修改数据
     *
     * @param locatorDetailDTO 实例对象
     * @return 实例对象
     */
    LocatorDetailDTO updateLocatorDetailByPrimaryKey(LocatorDetailDTO locatorDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteLocatorDetailByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteLocatorDetailByPrimaryKeys(List<String> list);

}