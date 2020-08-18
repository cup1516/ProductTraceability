package com.warehouse.warehouse.dao.inorder;

import com.warehouse.warehouse.pojo.po.inorder.InOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (InOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-10 19:10:00
 */

public interface InOrderMapper extends Mapper<InOrder> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InOrder describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<InOrder> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param inOrder 实例对象
     * @return 对象列表
     */
    List<InOrder> describeAll(InOrder inOrder);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param inOrder 实例对象
     * @return 对象列表
     */
    List<InOrder> describeFuzzy(InOrder inOrder);

    /**
     * 新增数据
     *
     * @param inOrder 实例对象
     * @return 影响行数
     */
    Integer insertInOrder(InOrder inOrder);

    /**
     * 新增数据
     *
     * @param List<inOrder> 实例对象
     * @return 影响行数
     */
    Integer saveInOrder(@Param("list") List<InOrder> list);

    /**
     * 修改数据
     *
     * @param inOrder 实例对象
     * @return 影响行数
     */
    Integer updateInOrderByPrimaryKey(InOrder inOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteInOrderByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteInOrderByPrimaryKeys(@Param("list") List<String> list);



    List<InOrder> describeFuzzyCompany(InOrder inOrder);

}