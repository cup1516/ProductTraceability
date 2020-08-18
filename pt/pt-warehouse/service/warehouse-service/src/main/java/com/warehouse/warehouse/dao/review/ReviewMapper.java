package com.warehouse.warehouse.dao.review;

import com.warehouse.warehouse.pojo.po.review.Review;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Review)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */

public interface ReviewMapper extends Mapper<Review> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Review describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Review> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param review 实例对象
     * @return 对象列表
     */
    List<Review> describeAll(Review review);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param review 实例对象
     * @return 对象列表
     */
    List<Review> describeFuzzy(Review review);

    /**
     * 新增数据
     *
     * @param review 实例对象
     * @return 影响行数
     */
    Integer insertReview(Review review);

    /**
     * 新增数据
     *
     * @param List<review> 实例对象
     * @return 影响行数
     */
    Integer saveReview(@Param("list") List<Review> list);

    /**
     * 修改数据
     *
     * @param review 实例对象
     * @return 影响行数
     */
    Integer updateReviewByPrimaryKey(Review review);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteReviewByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteReviewByPrimaryKeys(@Param("list") List<String> list);

}