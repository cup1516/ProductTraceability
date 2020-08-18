package com.warehouse.warehouse.service.impl.review;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.review.ReviewMapper;
import com.warehouse.warehouse.pojo.dto.review.ReviewDTO;
import com.warehouse.warehouse.pojo.po.review.Review;
import com.warehouse.warehouse.service.app.review.ReviewService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * (Review)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private ReviewMapper reviewMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ReviewDTO describeById(String id) {
        ReviewDTO dto = new ReviewDTO();
        Review review = this.reviewMapper.describeById(id);
        if (null != review) {
            BeanUtils.copyProperties(review, dto);
        }
        return dto;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ReviewDTO> describeAllByLimit(long offset, long limit) {
        List<Review> list = this.reviewMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReviewDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param reviewDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ReviewDTO> describeAll(ReviewDTO reviewDTO) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewDTO, review);
        List<Review> list = this.reviewMapper.describeAll(review);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReviewDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param reviewDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ReviewDTO> describeFuzzy(ReviewDTO reviewDTO) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewDTO, review);
        List<Review> list = this.reviewMapper.describeFuzzy(review);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReviewDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param reviewDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ReviewDTO insertReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewDTO, review);
        if (null == review.getId() || "".equals(review.getId())) {
            review.setId(UUID.randomUUID().toString());
        }
        this.reviewMapper.insertReview(review);
        return reviewDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<ReviewDTO> saveReview(List<ReviewDTO> list) {
        list.forEach(reviewDTO -> {
            if (null == reviewDTO.getId() || "".equals(reviewDTO.getId())) {
                reviewDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.reviewMapper.saveReview(JSON.parseArray(JSON.toJSONString(list), Review.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param reviewDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ReviewDTO updateReviewByPrimaryKey(ReviewDTO reviewDTO) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewDTO, review);
        this.reviewMapper.updateReviewByPrimaryKey(review);
        return this.describeById(review.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteReviewByPrimaryKey(String id) {
        return this.reviewMapper.deleteReviewByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteReviewByPrimaryKeys(List<String> list) {
        return this.reviewMapper.deleteReviewByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(ReviewDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Review.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getOutorderId() && !"".equals(dto.getOutorderId())) {
                criteriaName.andLike("outorderId", "%" + dto.getOutorderId() + "%");
            }
            if (null != dto.getOutorderNo() && !"".equals(dto.getOutorderNo())) {
                criteriaName.andLike("outorderNo", "%" + dto.getOutorderNo() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andLike("createId", "%" + dto.getCreateId() + "%");
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andLike("creator", "%" + dto.getCreator() + "%");
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andLike("createTime", "%" + dto.getCreateTime() + "%");
            }
        }
        return example;
    }
    
    /**
     * 初始化 精确查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initExample(ReviewDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Review.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getOutorderId() && !"".equals(dto.getOutorderId())) {
                criteriaName.andEqualTo("outorderId", dto.getOutorderId());
            }
            if (null != dto.getOutorderNo() && !"".equals(dto.getOutorderNo())) {
                criteriaName.andEqualTo("outorderNo", dto.getOutorderNo());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andEqualTo("createId", dto.getCreateId());
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andEqualTo("creator", dto.getCreator());
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andEqualTo("createTime", dto.getCreateTime());
            }
        }
        return example;
    }
    
}