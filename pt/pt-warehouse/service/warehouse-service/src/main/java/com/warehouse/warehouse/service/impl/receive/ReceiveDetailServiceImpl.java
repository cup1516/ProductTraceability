package com.warehouse.warehouse.service.impl.receive;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.receive.ReceiveDetailMapper;
import com.warehouse.warehouse.pojo.dto.receive.ReceiveDetailDTO;
import com.warehouse.warehouse.pojo.po.receive.ReceiveDetail;
import com.warehouse.warehouse.service.app.receive.ReceiveDetailService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 接货表子表(ReceiveDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("receiveDetailService")
public class ReceiveDetailServiceImpl implements ReceiveDetailService {

    @Resource
    private ReceiveDetailMapper receiveDetailMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ReceiveDetailDTO describeById(String id) {
        ReceiveDetailDTO dto = new ReceiveDetailDTO();
        ReceiveDetail receiveDetail = this.receiveDetailMapper.describeById(id);
        if (null != receiveDetail) {
            BeanUtils.copyProperties(receiveDetail, dto);
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
    public List<ReceiveDetailDTO> describeAllByLimit(long offset, long limit) {
        List<ReceiveDetail> list = this.receiveDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReceiveDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param receiveDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ReceiveDetailDTO> describeAll(ReceiveDetailDTO receiveDetailDTO) {
        ReceiveDetail receiveDetail = new ReceiveDetail();
        BeanUtils.copyProperties(receiveDetailDTO, receiveDetail);
        List<ReceiveDetail> list = this.receiveDetailMapper.describeAll(receiveDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReceiveDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param receiveDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ReceiveDetailDTO> describeFuzzy(ReceiveDetailDTO receiveDetailDTO) {
        ReceiveDetail receiveDetail = new ReceiveDetail();
        BeanUtils.copyProperties(receiveDetailDTO, receiveDetail);
        List<ReceiveDetail> list = this.receiveDetailMapper.describeFuzzy(receiveDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReceiveDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param receiveDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ReceiveDetailDTO insertReceiveDetail(ReceiveDetailDTO receiveDetailDTO) {
        ReceiveDetail receiveDetail = new ReceiveDetail();
        BeanUtils.copyProperties(receiveDetailDTO, receiveDetail);
        if (null == receiveDetail.getId() || "".equals(receiveDetail.getId())) {
            receiveDetail.setId(UUID.randomUUID().toString());
        }
        this.receiveDetailMapper.insertReceiveDetail(receiveDetail);
        return receiveDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<ReceiveDetailDTO> saveReceiveDetail(List<ReceiveDetailDTO> list) {
        list.forEach(receiveDetailDTO -> {
            if (null == receiveDetailDTO.getId() || "".equals(receiveDetailDTO.getId())) {
                receiveDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.receiveDetailMapper.saveReceiveDetail(JSON.parseArray(JSON.toJSONString(list), ReceiveDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param receiveDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ReceiveDetailDTO updateReceiveDetailByPrimaryKey(ReceiveDetailDTO receiveDetailDTO) {
        ReceiveDetail receiveDetail = new ReceiveDetail();
        BeanUtils.copyProperties(receiveDetailDTO, receiveDetail);
        this.receiveDetailMapper.updateReceiveDetailByPrimaryKey(receiveDetail);
        return this.describeById(receiveDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteReceiveDetailByPrimaryKey(String id) {
        return this.receiveDetailMapper.deleteReceiveDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteReceiveDetailByPrimaryKeys(List<String> list) {
        return this.receiveDetailMapper.deleteReceiveDetailByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(ReceiveDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(ReceiveDetail.class, dto.getOrderBy());
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
    private Example initExample(ReceiveDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(ReceiveDetail.class, dto.getOrderBy());
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