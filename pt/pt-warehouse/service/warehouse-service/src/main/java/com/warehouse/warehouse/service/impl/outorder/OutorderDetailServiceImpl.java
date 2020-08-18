package com.warehouse.warehouse.service.impl.outorder;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.outorder.OutorderDetailMapper;
import com.warehouse.warehouse.pojo.dto.outorder.OutorderDetailDTO;
import com.warehouse.warehouse.pojo.po.outorder.OutorderDetail;
import com.warehouse.warehouse.service.app.outorder.OutorderDetailService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 出库收据表子表(OutorderDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("outorderDetailService")
public class OutorderDetailServiceImpl implements OutorderDetailService {

    @Resource
    private OutorderDetailMapper outorderDetailMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OutorderDetailDTO describeById(String id) {
        OutorderDetailDTO dto = new OutorderDetailDTO();
        OutorderDetail outorderDetail = this.outorderDetailMapper.describeById(id);
        if (null != outorderDetail) {
            BeanUtils.copyProperties(outorderDetail, dto);
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
    public List<OutorderDetailDTO> describeAllByLimit(long offset, long limit) {
        List<OutorderDetail> list = this.outorderDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutorderDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param outorderDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OutorderDetailDTO> describeAll(OutorderDetailDTO outorderDetailDTO) {
        OutorderDetail outorderDetail = new OutorderDetail();
        BeanUtils.copyProperties(outorderDetailDTO, outorderDetail);
        List<OutorderDetail> list = this.outorderDetailMapper.describeAll(outorderDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutorderDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param outorderDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OutorderDetailDTO> describeFuzzy(OutorderDetailDTO outorderDetailDTO) {
        OutorderDetail outorderDetail = new OutorderDetail();
        BeanUtils.copyProperties(outorderDetailDTO, outorderDetail);
        List<OutorderDetail> list = this.outorderDetailMapper.describeFuzzy(outorderDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutorderDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param outorderDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OutorderDetailDTO insertOutorderDetail(OutorderDetailDTO outorderDetailDTO) {
        OutorderDetail outorderDetail = new OutorderDetail();
        BeanUtils.copyProperties(outorderDetailDTO, outorderDetail);
        if (null == outorderDetail.getId() || "".equals(outorderDetail.getId())) {
            outorderDetail.setId(UUID.randomUUID().toString());
        }
        this.outorderDetailMapper.insertOutorderDetail(outorderDetail);
        return outorderDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<OutorderDetailDTO> saveOutorderDetail(List<OutorderDetailDTO> list) {
        list.forEach(outorderDetailDTO -> {
            if (null == outorderDetailDTO.getId() || "".equals(outorderDetailDTO.getId())) {
                outorderDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.outorderDetailMapper.saveOutorderDetail(JSON.parseArray(JSON.toJSONString(list), OutorderDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param outorderDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OutorderDetailDTO updateOutorderDetailByPrimaryKey(OutorderDetailDTO outorderDetailDTO) {
        OutorderDetail outorderDetail = new OutorderDetail();
        BeanUtils.copyProperties(outorderDetailDTO, outorderDetail);
        this.outorderDetailMapper.updateOutorderDetailByPrimaryKey(outorderDetail);
        return this.describeById(outorderDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteOutorderDetailByPrimaryKey(String id) {
        return this.outorderDetailMapper.deleteOutorderDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteOutorderDetailByPrimaryKeys(List<String> list) {
        return this.outorderDetailMapper.deleteOutorderDetailByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(OutorderDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OutorderDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andLike("pid", "%" + dto.getPid() + "%");
            }
            if (null != dto.getStockLogId() && !"".equals(dto.getStockLogId())) {
                criteriaName.andLike("stockLogId", "%" + dto.getStockLogId() + "%");
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
    private Example initExample(OutorderDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OutorderDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andEqualTo("pid", dto.getPid());
            }
            if (null != dto.getStockLogId() && !"".equals(dto.getStockLogId())) {
                criteriaName.andEqualTo("stockLogId", dto.getStockLogId());
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