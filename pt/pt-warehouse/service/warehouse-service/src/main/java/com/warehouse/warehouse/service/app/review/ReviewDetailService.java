package com.warehouse.warehouse.service.app.review;


import com.warehouse.warehouse.pojo.dto.review.ReviewDetailDTO;

import java.util.List;

/**
 * (ReviewDetail)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface ReviewDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ReviewDetailDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ReviewDetailDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param reviewDetailDTO 实例对象
     * @return 对象列表
     */
    List<ReviewDetailDTO> describeAll(ReviewDetailDTO reviewDetailDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param reviewDetailDTO 实例对象
     * @return 对象列表
     */
    List<ReviewDetailDTO> describeFuzzy(ReviewDetailDTO reviewDetailDTO);
    
    
    /**
     * 新增数据
     *
     * @param reviewDetailDTO 实例对象
     * @return 实例对象
     */
    ReviewDetailDTO insertReviewDetail(ReviewDetailDTO reviewDetailDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<ReviewDetailDTO> saveReviewDetail(List<ReviewDetailDTO> list);

    /**
     * 修改数据
     *
     * @param reviewDetailDTO 实例对象
     * @return 实例对象
     */
    ReviewDetailDTO updateReviewDetailByPrimaryKey(ReviewDetailDTO reviewDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteReviewDetailByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteReviewDetailByPrimaryKeys(List<String> list);

}