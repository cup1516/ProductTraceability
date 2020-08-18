package com.warehouse.warehouse.dao.outorder;

import com.warehouse.warehouse.pojo.po.outorder.OutorderDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 出库收据表子表(OutorderDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface OutorderDetailMapper extends Mapper<OutorderDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutorderDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OutorderDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param outorderDetail 实例对象
     * @return 对象列表
     */
    List<OutorderDetail> describeAll(OutorderDetail outorderDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param outorderDetail 实例对象
     * @return 对象列表
     */
    List<OutorderDetail> describeFuzzy(OutorderDetail outorderDetail);

    /**
     * 新增数据
     *
     * @param outorderDetail 实例对象
     * @return 影响行数
     */
    Integer insertOutorderDetail(OutorderDetail outorderDetail);

    /**
     * 新增数据
     *
     * @param List<outorderDetail> 实例对象
     * @return 影响行数
     */
    Integer saveOutorderDetail(@Param("list") List<OutorderDetail> list);

    /**
     * 修改数据
     *
     * @param outorderDetail 实例对象
     * @return 影响行数
     */
    Integer updateOutorderDetailByPrimaryKey(OutorderDetail outorderDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteOutorderDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteOutorderDetailByPrimaryKeys(@Param("list") List<String> list);

}