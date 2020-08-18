package com.warehouse.warehouse.service.impl.allocation;

import com.alibaba.fastjson.JSON;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.allocation.AllocationDetailMapper;
import com.warehouse.warehouse.dao.allocation.AllocationMapper;
import com.warehouse.warehouse.pojo.dto.allocation.AllocationDetailDTO;
import com.warehouse.warehouse.pojo.po.allocation.Allocation;
import com.warehouse.warehouse.pojo.po.allocation.AllocationDetail;
import com.warehouse.warehouse.service.app.allocation.AllocationDetailService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 调拨单子表(AllocationDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("allocationDetailService")
public class AllocationDetailServiceImpl implements AllocationDetailService {

    @Resource
    private AllocationDetailMapper allocationDetailMapper;

    @Resource
    private AllocationMapper allocationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AllocationDetailDTO describeById(String id) {
        AllocationDetailDTO dto = new AllocationDetailDTO();
        AllocationDetail allocationDetail = this.allocationDetailMapper.describeById(id);
        if (null != allocationDetail) {
            BeanUtils.copyProperties(allocationDetail, dto);
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
    public List<AllocationDetailDTO> describeAllByLimit(long offset, long limit) {
        List<AllocationDetail> list = this.allocationDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), AllocationDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param allocationDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<AllocationDetailDTO> describeAll(AllocationDetailDTO allocationDetailDTO) {
        AllocationDetail allocationDetail = new AllocationDetail();
        allocationDetail.setCommodityBar(SecurityUtils.getCompanyId());
        BeanUtils.copyProperties(allocationDetailDTO, allocationDetail);
        List<AllocationDetail> list = this.allocationDetailMapper.describeAll(allocationDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), AllocationDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param allocationDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<AllocationDetailDTO> describeFuzzy(AllocationDetailDTO allocationDetailDTO) {
        AllocationDetail allocationDetail = new AllocationDetail();
        allocationDetail.setCompanyId(SecurityUtils.getCompanyId());
        BeanUtils.copyProperties(allocationDetailDTO, allocationDetail);
        List<AllocationDetail> list = this.allocationDetailMapper.describeFuzzy(allocationDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), AllocationDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param allocationDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public AllocationDetailDTO insertAllocationDetail(AllocationDetailDTO allocationDetailDTO) {
       allocationDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        AllocationDetail allocationDetail = new AllocationDetail();
        BeanUtils.copyProperties(allocationDetailDTO, allocationDetail);
        if (null == allocationDetail.getId() || "".equals(allocationDetail.getId())) {
            allocationDetail.setId(UUID.randomUUID().toString());
        }
        allocationDetail.setCreateTime(new Date());
        this.allocationDetailMapper.insertAllocationDetail(allocationDetail);
        BeanUtils.copyProperties(allocationDetail, allocationDetailDTO);
        return allocationDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<AllocationDetailDTO> saveAllocationDetail(List<AllocationDetailDTO> list) {
        list.forEach(allocationDetailDTO -> {
            if (null == allocationDetailDTO.getId() || "".equals(allocationDetailDTO.getId())) {
                allocationDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.allocationDetailMapper.saveAllocationDetail(JSON.parseArray(JSON.toJSONString(list), AllocationDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param allocationDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public AllocationDetailDTO updateAllocationDetailByPrimaryKey(AllocationDetailDTO allocationDetailDTO) {
        allocationDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        AllocationDetail allocationDetail = new AllocationDetail();
        BeanUtils.copyProperties(allocationDetailDTO, allocationDetail);
        this.allocationDetailMapper.updateAllocationDetailByPrimaryKey(allocationDetail);
        return this.describeById(allocationDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteAllocationDetailByPrimaryKey(String id) {
        return this.allocationDetailMapper.deleteAllocationDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteAllocationDetailByPrimaryKeys(List<String> list) {
        return this.allocationDetailMapper.deleteAllocationDetailByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(AllocationDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(AllocationDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andLike("pid", "%" + dto.getPid() + "%");
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andLike("locatorId", "%" + dto.getLocatorId() + "%");
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
            if (null != dto.getLockStockId() && !"".equals(dto.getLockStockId())) {
                criteriaName.andLike("lockStockId", "%" + dto.getLockStockId() + "%");
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
    private Example initExample(AllocationDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(AllocationDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andEqualTo("pid", dto.getPid());
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andEqualTo("locatorId", dto.getLocatorId());
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
            if (null != dto.getLockStockId() && !"".equals(dto.getLockStockId())) {
                criteriaName.andEqualTo("lockStockId", dto.getLockStockId());
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


    @Override
    public List<AllocationDetailDTO> findByAllocationId(String allocationId){
        Example example = new Example(AllocationDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid",allocationId);
        List<AllocationDetail> allocationDetails = allocationDetailMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(allocationDetails)){
            return JSON.parseArray(JSON.toJSONString(allocationDetails),AllocationDetailDTO.class);
        }
        return new ArrayList<>();
    }

    /**
     * 通过出库编号清空所有出库详情数据
     * @param orderNo
     * @return
     */
    @Override
    public Integer deleteAllByPid(String orderNo) {
        // 数据请空前前，需要处理入库单仓库信息
        Allocation allocation = new Allocation();
        allocation.setAllocationNo(orderNo);
        Allocation allocationResult = allocationMapper.selectOne(allocation);
        allocationResult.setWarehouseId("");
        allocationMapper.updateByPrimaryKey(allocationResult);
        AllocationDetail allocationDetail = new AllocationDetail();
        allocationDetail.setPid(orderNo);
        return  allocationDetailMapper.delete(allocationDetail);
    }
}