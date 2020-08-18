package com.warehouse.warehouse.dao.puton;

import com.warehouse.warehouse.pojo.po.puton.Puton;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Puton)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface PutonMapper extends Mapper<Puton> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Puton describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Puton> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param puton 实例对象
     * @return 对象列表
     */
    List<Puton> describeAll(Puton puton);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param puton 实例对象
     * @return 对象列表
     */
    List<Puton> describeFuzzy(Puton puton);

    /**
     * 新增数据
     *
     * @param puton 实例对象
     * @return 影响行数
     */
    Integer insertPuton(Puton puton);

    /**
     * 新增数据
     *
     * @param List<puton> 实例对象
     * @return 影响行数
     */
    Integer savePuton(@Param("list") List<Puton> list);

    /**
     * 修改数据
     *
     * @param puton 实例对象
     * @return 影响行数
     */
    Integer updatePutonByPrimaryKey(Puton puton);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deletePutonByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deletePutonByPrimaryKeys(@Param("list") List<String> list);

}