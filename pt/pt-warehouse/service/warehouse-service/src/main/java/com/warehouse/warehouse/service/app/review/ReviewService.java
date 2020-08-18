package com.warehouse.warehouse.service.app.review;


import com.warehouse.warehouse.pojo.dto.review.ReviewDTO;

import java.util.List;

/**
 * (Review)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface ReviewService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ReviewDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ReviewDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param reviewDTO 实例对象
     * @return 对象列表
     */
    List<ReviewDTO> describeAll(ReviewDTO reviewDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param reviewDTO 实例对象
     * @return 对象列表
     */
    List<ReviewDTO> describeFuzzy(ReviewDTO reviewDTO);
    
    
    /**
     * 新增数据
     *
     * @param reviewDTO 实例对象
     * @return 实例对象
     */
    ReviewDTO insertReview(ReviewDTO reviewDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<ReviewDTO> saveReview(List<ReviewDTO> list);

    /**
     * 修改数据
     *
     * @param reviewDTO 实例对象
     * @return 实例对象
     */
    ReviewDTO updateReviewByPrimaryKey(ReviewDTO reviewDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteReviewByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteReviewByPrimaryKeys(List<String> list);

}