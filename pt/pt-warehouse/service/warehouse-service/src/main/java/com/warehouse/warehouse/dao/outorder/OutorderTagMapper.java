package com.warehouse.warehouse.dao.outorder;

import com.warehouse.warehouse.pojo.po.outorder.OutorderTag;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (OutorderTag)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface OutorderTagMapper extends Mapper<OutorderTag> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutorderTag describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OutorderTag> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param outorderTag 实例对象
     * @return 对象列表
     */
    List<OutorderTag> describeAll(OutorderTag outorderTag);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param outorderTag 实例对象
     * @return 对象列表
     */
    List<OutorderTag> describeFuzzy(OutorderTag outorderTag);

    /**
     * 新增数据
     *
     * @param outorderTag 实例对象
     * @return 影响行数
     */
    Integer insertOutorderTag(OutorderTag outorderTag);

    /**
     * 新增数据
     *
     * @param List<outorderTag> 实例对象
     * @return 影响行数
     */
    Integer saveOutorderTag(@Param("list") List<OutorderTag> list);

    /**
     * 修改数据
     *
     * @param outorderTag 实例对象
     * @return 影响行数
     */
    Integer updateOutorderTagByPrimaryKey(OutorderTag outorderTag);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteOutorderTagByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteOutorderTagByPrimaryKeys(@Param("list") List<String> list);

}