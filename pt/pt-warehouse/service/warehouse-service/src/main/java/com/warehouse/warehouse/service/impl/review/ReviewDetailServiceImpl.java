package com.warehouse.warehouse.service.impl.review;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.review.ReviewDetailMapper;
import com.warehouse.warehouse.pojo.dto.review.ReviewDetailDTO;
import com.warehouse.warehouse.pojo.po.review.ReviewDetail;
import com.warehouse.warehouse.service.app.review.ReviewDetailService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * (ReviewDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("reviewDetailService")
public class ReviewDetailServiceImpl implements ReviewDetailService {

    @Resource
    private ReviewDetailMapper reviewDetailMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ReviewDetailDTO describeById(String id) {
        ReviewDetailDTO dto = new ReviewDetailDTO();
        ReviewDetail reviewDetail = this.reviewDetailMapper.describeById(id);
        if (null != reviewDetail) {
            BeanUtils.copyProperties(reviewDetail, dto);
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
    public List<ReviewDetailDTO> describeAllByLimit(long offset, long limit) {
        List<ReviewDetail> list = this.reviewDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReviewDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param reviewDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ReviewDetailDTO> describeAll(ReviewDetailDTO reviewDetailDTO) {
        ReviewDetail reviewDetail = new ReviewDetail();
        BeanUtils.copyProperties(reviewDetailDTO, reviewDetail);
        List<ReviewDetail> list = this.reviewDetailMapper.describeAll(reviewDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReviewDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param reviewDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ReviewDetailDTO> describeFuzzy(ReviewDetailDTO reviewDetailDTO) {
        ReviewDetail reviewDetail = new ReviewDetail();
        BeanUtils.copyProperties(reviewDetailDTO, reviewDetail);
        List<ReviewDetail> list = this.reviewDetailMapper.describeFuzzy(reviewDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReviewDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param reviewDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ReviewDetailDTO insertReviewDetail(ReviewDetailDTO reviewDetailDTO) {
        ReviewDetail reviewDetail = new ReviewDetail();
        BeanUtils.copyProperties(reviewDetailDTO, reviewDetail);
        if (null == reviewDetail.getId() || "".equals(reviewDetail.getId())) {
            reviewDetail.setId(UUID.randomUUID().toString());
        }
        this.reviewDetailMapper.insertReviewDetail(reviewDetail);
        return reviewDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<ReviewDetailDTO> saveReviewDetail(List<ReviewDetailDTO> list) {
        list.forEach(reviewDetailDTO -> {
            if (null == reviewDetailDTO.getId() || "".equals(reviewDetailDTO.getId())) {
                reviewDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.reviewDetailMapper.saveReviewDetail(JSON.parseArray(JSON.toJSONString(list), ReviewDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param reviewDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ReviewDetailDTO updateReviewDetailByPrimaryKey(ReviewDetailDTO reviewDetailDTO) {
        ReviewDetail reviewDetail = new ReviewDetail();
        BeanUtils.copyProperties(reviewDetailDTO, reviewDetail);
        this.reviewDetailMapper.updateReviewDetailByPrimaryKey(reviewDetail);
        return this.describeById(reviewDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteReviewDetailByPrimaryKey(String id) {
        return this.reviewDetailMapper.deleteReviewDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteReviewDetailByPrimaryKeys(List<String> list) {
        return this.reviewDetailMapper.deleteReviewDetailByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(ReviewDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(ReviewDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andLike("pid", "%" + dto.getPid() + "%");
            }
            if (null != dto.getCommodityId() && !"".equals(dto.getCommodityId())) {
                criteriaName.andLike("commodityId", "%" + dto.getCommodityId() + "%");
            }
            if (null != dto.getCommodityBar() && !"".equals(dto.getCommodityBar())) {
                criteriaName.andLike("commodityBar", "%" + dto.getCommodityBar() + "%");
            }
            if (null != dto.getCommodityName() && !"".equals(dto.getCommodityName())) {
                criteriaName.andLike("commodityName", "%" + dto.getCommodityName() + "%");
            }
            if (null != dto.getQty()) {
                criteriaName.andLike("qty", "%" + dto.getQty() + "%");
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
            if (null != dto.getUpdateId() && !"".equals(dto.getUpdateId())) {
                criteriaName.andLike("updateId", "%" + dto.getUpdateId() + "%");
            }
            if (null != dto.getUpdator() && !"".equals(dto.getUpdator())) {
                criteriaName.andLike("updator", "%" + dto.getUpdator() + "%");
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andLike("updateTime", "%" + dto.getUpdateTime() + "%");
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
    private Example initExample(ReviewDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(ReviewDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andEqualTo("pid", dto.getPid());
            }
            if (null != dto.getCommodityId() && !"".equals(dto.getCommodityId())) {
                criteriaName.andEqualTo("commodityId", dto.getCommodityId());
            }
            if (null != dto.getCommodityBar() && !"".equals(dto.getCommodityBar())) {
                criteriaName.andEqualTo("commodityBar", dto.getCommodityBar());
            }
            if (null != dto.getCommodityName() && !"".equals(dto.getCommodityName())) {
                criteriaName.andEqualTo("commodityName", dto.getCommodityName());
            }
            if (null != dto.getQty()) {
                criteriaName.andEqualTo("qty", dto.getQty());
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
            if (null != dto.getUpdateId() && !"".equals(dto.getUpdateId())) {
                criteriaName.andEqualTo("updateId", dto.getUpdateId());
            }
            if (null != dto.getUpdator() && !"".equals(dto.getUpdator())) {
                criteriaName.andEqualTo("updator", dto.getUpdator());
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andEqualTo("updateTime", dto.getUpdateTime());
            }
        }
        return example;
    }
    
}