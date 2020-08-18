package com.warehouse.warehouse.dao.review;

import com.warehouse.warehouse.pojo.po.review.ReviewDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (ReviewDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */

public interface ReviewDetailMapper extends Mapper<ReviewDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ReviewDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ReviewDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param reviewDetail 实例对象
     * @return 对象列表
     */
    List<ReviewDetail> describeAll(ReviewDetail reviewDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param reviewDetail 实例对象
     * @return 对象列表
     */
    List<ReviewDetail> describeFuzzy(ReviewDetail reviewDetail);

    /**
     * 新增数据
     *
     * @param reviewDetail 实例对象
     * @return 影响行数
     */
    Integer insertReviewDetail(ReviewDetail reviewDetail);

    /**
     * 新增数据
     *
     * @param List<reviewDetail> 实例对象
     * @return 影响行数
     */
    Integer saveReviewDetail(@Param("list") List<ReviewDetail> list);

    /**
     * 修改数据
     *
     * @param reviewDetail 实例对象
     * @return 影响行数
     */
    Integer updateReviewDetailByPrimaryKey(ReviewDetail reviewDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteReviewDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteReviewDetailByPrimaryKeys(@Param("list") List<String> list);

}