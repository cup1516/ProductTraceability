package com.warehouse.warehouse.service.impl.purchase;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.purchase.PurchaseDetailMapper;
import com.warehouse.warehouse.pojo.dto.purchase.PurchaseDetailDTO;
import com.warehouse.warehouse.pojo.po.purchase.PurchaseDetail;
import com.warehouse.warehouse.service.app.purchase.PurchaseDetailService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 采购单子表(PurchaseDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

    @Resource
    private PurchaseDetailMapper purchaseDetailMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PurchaseDetailDTO describeById(String id) {
        PurchaseDetailDTO dto = new PurchaseDetailDTO();
        PurchaseDetail purchaseDetail = this.purchaseDetailMapper.describeById(id);
        if (null != purchaseDetail) {
            BeanUtils.copyProperties(purchaseDetail, dto);
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
    public List<PurchaseDetailDTO> describeAllByLimit(long offset, long limit) {
        List<PurchaseDetail> list = this.purchaseDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PurchaseDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param purchaseDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<PurchaseDetailDTO> describeAll(PurchaseDetailDTO purchaseDetailDTO) {
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        BeanUtils.copyProperties(purchaseDetailDTO, purchaseDetail);
        List<PurchaseDetail> list = this.purchaseDetailMapper.describeAll(purchaseDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PurchaseDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param purchaseDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<PurchaseDetailDTO> describeFuzzy(PurchaseDetailDTO purchaseDetailDTO) {
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        BeanUtils.copyProperties(purchaseDetailDTO, purchaseDetail);
        List<PurchaseDetail> list = this.purchaseDetailMapper.describeFuzzy(purchaseDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), PurchaseDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param purchaseDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public PurchaseDetailDTO insertPurchaseDetail(PurchaseDetailDTO purchaseDetailDTO) {
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        BeanUtils.copyProperties(purchaseDetailDTO, purchaseDetail);
        if (null == purchaseDetail.getId() || "".equals(purchaseDetail.getId())) {
            purchaseDetail.setId(UUID.randomUUID().toString());
        }
        this.purchaseDetailMapper.insertPurchaseDetail(purchaseDetail);
        return purchaseDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<PurchaseDetailDTO> savePurchaseDetail(List<PurchaseDetailDTO> list) {
        list.forEach(purchaseDetailDTO -> {
            if (null == purchaseDetailDTO.getId() || "".equals(purchaseDetailDTO.getId())) {
                purchaseDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.purchaseDetailMapper.savePurchaseDetail(JSON.parseArray(JSON.toJSONString(list), PurchaseDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param purchaseDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public PurchaseDetailDTO updatePurchaseDetailByPrimaryKey(PurchaseDetailDTO purchaseDetailDTO) {
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        BeanUtils.copyProperties(purchaseDetailDTO, purchaseDetail);
        this.purchaseDetailMapper.updatePurchaseDetailByPrimaryKey(purchaseDetail);
        return this.describeById(purchaseDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deletePurchaseDetailByPrimaryKey(String id) {
        return this.purchaseDetailMapper.deletePurchaseDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deletePurchaseDetailByPrimaryKeys(List<String> list) {
        return this.purchaseDetailMapper.deletePurchaseDetailByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(PurchaseDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(PurchaseDetail.class, dto.getOrderBy());
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
    private Example initExample(PurchaseDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(PurchaseDetail.class, dto.getOrderBy());
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