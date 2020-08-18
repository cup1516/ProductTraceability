package com.warehouse.warehouse.service.app.inorder;


import com.warehouse.warehouse.pojo.dto.inorder.InOrderDTO;
import com.warehouse.warehouse.pojo.dto.otherin.OtherinDTO;

import java.util.List;

/**
 * (InOrder)表服务接口
 *
 * @author makejava
 * @since 2020-05-10 19:12:20
 */
public interface InOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InOrderDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<InOrderDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param inOrderDTO 实例对象
     * @return 对象列表
     */
    List<InOrderDTO> describeAll(InOrderDTO inOrderDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param inOrderDTO 实例对象
     * @return 对象列表
     */
    List<InOrderDTO> describeFuzzy(InOrderDTO inOrderDTO);
    
    
    /**
     * 新增数据
     *
     * @param inOrderDTO 实例对象
     * @return 实例对象
     */
    InOrderDTO insertInOrder(OtherinDTO otherinDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<InOrderDTO> saveInOrder(List<InOrderDTO> list);

    /**
     * 修改数据
     *
     * @param inOrderDTO 实例对象
     * @return 实例对象
     */
    InOrderDTO updateInOrderByPrimaryKey(InOrderDTO inOrderDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteInOrderByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteInOrderByPrimaryKeys(List<String> list);


    List<InOrderDTO> describeFuzzyCompany(InOrderDTO inOrderDTO);

}