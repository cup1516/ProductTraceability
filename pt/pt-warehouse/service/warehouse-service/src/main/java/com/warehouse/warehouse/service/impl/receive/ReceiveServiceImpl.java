package com.warehouse.warehouse.service.impl.receive;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.receive.ReceiveMapper;
import com.warehouse.warehouse.pojo.dto.receive.ReceiveDTO;
import com.warehouse.warehouse.pojo.po.receive.Receive;
import com.warehouse.warehouse.service.app.receive.ReceiveService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 接货表(Receive)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {

    @Resource
    private ReceiveMapper receiveMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ReceiveDTO describeById(String id) {
        ReceiveDTO dto = new ReceiveDTO();
        Receive receive = this.receiveMapper.describeById(id);
        if (null != receive) {
            BeanUtils.copyProperties(receive, dto);
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
    public List<ReceiveDTO> describeAllByLimit(long offset, long limit) {
        List<Receive> list = this.receiveMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReceiveDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param receiveDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ReceiveDTO> describeAll(ReceiveDTO receiveDTO) {
        Receive receive = new Receive();
        BeanUtils.copyProperties(receiveDTO, receive);
        List<Receive> list = this.receiveMapper.describeAll(receive);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReceiveDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param receiveDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ReceiveDTO> describeFuzzy(ReceiveDTO receiveDTO) {
        Receive receive = new Receive();
        BeanUtils.copyProperties(receiveDTO, receive);
        List<Receive> list = this.receiveMapper.describeFuzzy(receive);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ReceiveDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param receiveDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ReceiveDTO insertReceive(ReceiveDTO receiveDTO) {
        Receive receive = new Receive();
        BeanUtils.copyProperties(receiveDTO, receive);
        if (null == receive.getId() || "".equals(receive.getId())) {
            receive.setId(UUID.randomUUID().toString());
        }
        this.receiveMapper.insertReceive(receive);
        return receiveDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<ReceiveDTO> saveReceive(List<ReceiveDTO> list) {
        list.forEach(receiveDTO -> {
            if (null == receiveDTO.getId() || "".equals(receiveDTO.getId())) {
                receiveDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.receiveMapper.saveReceive(JSON.parseArray(JSON.toJSONString(list), Receive.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param receiveDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ReceiveDTO updateReceiveByPrimaryKey(ReceiveDTO receiveDTO) {
        Receive receive = new Receive();
        BeanUtils.copyProperties(receiveDTO, receive);
        this.receiveMapper.updateReceiveByPrimaryKey(receive);
        return this.describeById(receive.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteReceiveByPrimaryKey(String id) {
        return this.receiveMapper.deleteReceiveByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteReceiveByPrimaryKeys(List<String> list) {
        return this.receiveMapper.deleteReceiveByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(ReceiveDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Receive.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getReceiveNo() && !"".equals(dto.getReceiveNo())) {
                criteriaName.andLike("receiveNo", "%" + dto.getReceiveNo() + "%");
            }
            if (null != dto.getPurchaseId() && !"".equals(dto.getPurchaseId())) {
                criteriaName.andLike("purchaseId", "%" + dto.getPurchaseId() + "%");
            }
            if (null != dto.getPurchaseNo() && !"".equals(dto.getPurchaseNo())) {
                criteriaName.andLike("purchaseNo", "%" + dto.getPurchaseNo() + "%");
            }
            if (null != dto.getAllocationId() && !"".equals(dto.getAllocationId())) {
                criteriaName.andLike("allocationId", "%" + dto.getAllocationId() + "%");
            }
            if (null != dto.getAllocationNo() && !"".equals(dto.getAllocationNo())) {
                criteriaName.andLike("allocationNo", "%" + dto.getAllocationNo() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getRemarks() && !"".equals(dto.getRemarks())) {
                criteriaName.andLike("remarks", "%" + dto.getRemarks() + "%");
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
            if (null != dto.getUpdateId() && !"".equals(dto.getUpdateId())) {
                criteriaName.andLike("updateId", "%" + dto.getUpdateId() + "%");
            }
            if (null != dto.getUpdator() && !"".equals(dto.getUpdator())) {
                criteriaName.andLike("updator", "%" + dto.getUpdator() + "%");
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andLike("updateTime", "%" + dto.getUpdateTime() + "%");
            }
            if (null != dto.getAuditId() && !"".equals(dto.getAuditId())) {
                criteriaName.andLike("auditId", "%" + dto.getAuditId() + "%");
            }
            if (null != dto.getAuditor() && !"".equals(dto.getAuditor())) {
                criteriaName.andLike("auditor", "%" + dto.getAuditor() + "%");
            }
            if (null != dto.getAuditTime()) {
                criteriaName.andLike("auditTime", "%" + dto.getAuditTime() + "%");
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
    private Example initExample(ReceiveDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Receive.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getReceiveNo() && !"".equals(dto.getReceiveNo())) {
                criteriaName.andEqualTo("receiveNo", dto.getReceiveNo());
            }
            if (null != dto.getPurchaseId() && !"".equals(dto.getPurchaseId())) {
                criteriaName.andEqualTo("purchaseId", dto.getPurchaseId());
            }
            if (null != dto.getPurchaseNo() && !"".equals(dto.getPurchaseNo())) {
                criteriaName.andEqualTo("purchaseNo", dto.getPurchaseNo());
            }
            if (null != dto.getAllocationId() && !"".equals(dto.getAllocationId())) {
                criteriaName.andEqualTo("allocationId", dto.getAllocationId());
            }
            if (null != dto.getAllocationNo() && !"".equals(dto.getAllocationNo())) {
                criteriaName.andEqualTo("allocationNo", dto.getAllocationNo());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getRemarks() && !"".equals(dto.getRemarks())) {
                criteriaName.andEqualTo("remarks", dto.getRemarks());
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
            if (null != dto.getUpdateId() && !"".equals(dto.getUpdateId())) {
                criteriaName.andEqualTo("updateId", dto.getUpdateId());
            }
            if (null != dto.getUpdator() && !"".equals(dto.getUpdator())) {
                criteriaName.andEqualTo("updator", dto.getUpdator());
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andEqualTo("updateTime", dto.getUpdateTime());
            }
            if (null != dto.getAuditId() && !"".equals(dto.getAuditId())) {
                criteriaName.andEqualTo("auditId", dto.getAuditId());
            }
            if (null != dto.getAuditor() && !"".equals(dto.getAuditor())) {
                criteriaName.andEqualTo("auditor", dto.getAuditor());
            }
            if (null != dto.getAuditTime()) {
                criteriaName.andEqualTo("auditTime", dto.getAuditTime());
            }
        }
        return example;
    }
    
}