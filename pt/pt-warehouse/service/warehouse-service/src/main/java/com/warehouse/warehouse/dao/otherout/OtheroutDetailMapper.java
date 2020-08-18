package com.warehouse.warehouse.dao.otherout;

import com.warehouse.warehouse.pojo.po.otherout.OtheroutDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 出库子表(OtheroutDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-18 16:29:04
 */

public interface OtheroutDetailMapper extends Mapper<OtheroutDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OtheroutDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OtheroutDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param otheroutDetail 实例对象
     * @return 对象列表
     */
    List<OtheroutDetail> describeAll(OtheroutDetail otheroutDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param otheroutDetail 实例对象
     * @return 对象列表
     */
    List<OtheroutDetail> describeFuzzy(OtheroutDetail otheroutDetail);

    /**
     * 新增数据
     *
     * @param otheroutDetail 实例对象
     * @return 影响行数
     */
    Integer insertOtheroutDetail(OtheroutDetail otheroutDetail);

    /**
     * 新增数据
     *
     * @param List<otheroutDetail> 实例对象
     * @return 影响行数
     */
    Integer saveOtheroutDetail(@Param("list") List<OtheroutDetail> list);

    /**
     * 修改数据
     *
     * @param otheroutDetail 实例对象
     * @return 影响行数
     */
    Integer updateOtheroutDetailByPrimaryKey(OtheroutDetail otheroutDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteOtheroutDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteOtheroutDetailByPrimaryKeys(@Param("list") List<String> list);

}