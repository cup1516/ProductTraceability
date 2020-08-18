package com.warehouse.warehouse.dao.outorder;

import com.warehouse.warehouse.pojo.po.outorder.OutOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (OutOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-10 19:10:00
 */

public interface OutOrderMapper extends Mapper<OutOrder> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutOrder describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OutOrder> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param outOrder 实例对象
     * @return 对象列表
     */
    List<OutOrder> describeAll(OutOrder outOrder);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param outOrder 实例对象
     * @return 对象列表
     */
    List<OutOrder> describeFuzzy(OutOrder outOrder);

    /**
     * 新增数据
     *
     * @param outOrder 实例对象
     * @return 影响行数
     */
    Integer insertOutOrder(OutOrder outOrder);

    /**
     * 新增数据
     *
     * @param List<outOrder> 实例对象
     * @return 影响行数
     */
    Integer saveOutOrder(@Param("list") List<OutOrder> list);

    /**
     * 修改数据
     *
     * @param outOrder 实例对象
     * @return 影响行数
     */
    Integer updateOutOrderByPrimaryKey(OutOrder outOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteOutOrderByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteOutOrderByPrimaryKeys(@Param("list") List<String> list);



    List<OutOrder> describeFuzzyCompany(OutOrder outOrder);
}