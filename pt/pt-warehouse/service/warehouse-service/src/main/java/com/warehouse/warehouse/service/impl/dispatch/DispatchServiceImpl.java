package com.warehouse.warehouse.service.impl.dispatch;

import com.alibaba.fastjson.JSON;
import com.common.utils.InitOrderNo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.dispatch.DispatchDetailMapper;
import com.warehouse.warehouse.dao.dispatch.DispatchMapper;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.pojo.dto.dispatch.DispatchDTO;
import com.warehouse.warehouse.pojo.dto.dispatch.DispatchWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.pojo.po.dispatch.Dispatch;
import com.warehouse.warehouse.pojo.po.dispatch.DispatchDetail;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.service.app.dispatch.DispatchService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 报损表(Dispatch)表服务实现类
 *
 * @author makejava
 * @since 2020-05-01 19:49:56
 */
@Service("dispatchService")
public class DispatchServiceImpl implements DispatchService {

    @Resource
    private DispatchMapper dispatchMapper;

    @Resource
    private DispatchDetailMapper dispatchDetailMapper;

    @Resource
    private StockMapper stockMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DispatchDTO describeById(String id) {
        DispatchDTO dto = new DispatchDTO();
        Dispatch dispatch = this.dispatchMapper.describeById(id);
        if (null != dispatch) {
            BeanUtils.copyProperties(dispatch, dto);
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
    public List<DispatchDTO> describeAllByLimit(long offset, long limit) {
        List<Dispatch> list = this.dispatchMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param dispatchDTO 对象
     * @return 对象列表
     */
    @Override
    public List<DispatchDTO> describeAll(DispatchDTO dispatchDTO) {
        Dispatch dispatch = new Dispatch();
        BeanUtils.copyProperties(dispatchDTO, dispatch);
        List<Dispatch> list = this.dispatchMapper.describeAll(dispatch);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param dispatchDTO 对象
     * @return 对象列表
     */
    @Override
    public List<DispatchDTO> describeFuzzy(DispatchDTO dispatchDTO) {
        Dispatch dispatch = new Dispatch();
        BeanUtils.copyProperties(dispatchDTO, dispatch);
        List<Dispatch> list = this.dispatchMapper.describeFuzzy(dispatch);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param dispatchDTO 实例对象
     * @return 实例对象
     */
    @Override
    public DispatchDTO insertDispatch(DispatchDTO dispatchDTO) {
        Dispatch dispatch = new Dispatch();
        BeanUtils.copyProperties(dispatchDTO, dispatch);
        if (null == dispatch.getId() || "".equals(dispatch.getId())) {
            dispatch.setId(UUID.randomUUID().toString());
        }
        this.dispatchMapper.insertDispatch(dispatch);
        return dispatchDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<DispatchDTO> saveDispatch(List<DispatchDTO> list) {
        list.forEach(dispatchDTO -> {
            if (null == dispatchDTO.getId() || "".equals(dispatchDTO.getId())) {
                dispatchDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.dispatchMapper.saveDispatch(JSON.parseArray(JSON.toJSONString(list), Dispatch.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param dispatchDTO 实例对象
     * @return 实例对象
     */
    @Override
    public DispatchDTO updateDispatchByPrimaryKey(DispatchDTO dispatchDTO) {
        Dispatch dispatch = new Dispatch();
        BeanUtils.copyProperties(dispatchDTO, dispatch);
        dispatch.setUpdateTime(new Date());
        this.dispatchMapper.updateDispatchByPrimaryKey(dispatch);
        return this.describeById(dispatch.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteDispatchByPrimaryKey(String id) {
        return this.dispatchMapper.deleteDispatchByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteDispatchByPrimaryKeys(List<String> list) {
        return this.dispatchMapper.deleteDispatchByPrimaryKeys(list) == list.size();
    }

    /**
     * @title: initOrderNo
     * @description: 生成报损单，初始报损单信息
     * @params: [ dto ]
     * @return: com.system.service.dto.DispatchDTO
     * @createTime: 2020/5/1 19:59
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public DispatchDTO initOrderNo(UserDTO dto) {
        Dispatch dispatch = new Dispatch();
        if (null != dto.getUserId() && !"".equals(dto.getUserId())) {
            dispatch.setCreateId(dto.getUserId());
        } else {
            throw new RuntimeException("用户不能为空");
        }
        if (null != dto.getUsername() && !"".equals(dto.getUsername())) {
            dispatch.setCreator(dto.getUsername());
        }
        String orderNo = InitOrderNo.getNowFormatDate();
        dispatch.setId(UUID.randomUUID().toString());
        dispatch.setDispatchNo(orderNo);
        dispatch.setState("3");
        dispatch.setCompanyId(SecurityUtils.getCompanyId());
        dispatch.setCreateTime(new Date());
        this.dispatchMapper.insertDispatch(dispatch);
        DispatchDTO returnDTO = new DispatchDTO();
        BeanUtils.copyProperties(dispatch, returnDTO);
        return returnDTO;
    }

    /**
     * @title: describeNotCommit
     * @description: 查询未提交的报损单数据
     * @params: [ dispatchDTO ]
     * @return: java.util.List<com.system.service.dto.DispatchDTO>
     * @createTime: 2020/5/1 20:29
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<DispatchDTO> describeNotCommit(DispatchDTO dispatchDTO) {
        Dispatch dispatch = new Dispatch();
        BeanUtils.copyProperties(dispatchDTO, dispatch);
        List<Dispatch> list = this.dispatchMapper.describeNotCommit(dispatch);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @title: updateWarehouseIdByDispatchNo
     * @description: 根据DispatchNo修改数据的仓库信息
     * @params: [ dispatchDTO ]
     * @return: java.lang.Integer
     * @createTime: 2020/5/1 22:11
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Integer updateWarehouseIdByDispatchNo(DispatchDTO dispatchDTO) {
        try {
            Dispatch queryDispatchParam = new Dispatch();
            queryDispatchParam.setDispatchNo(dispatchDTO.getDispatchNo());
            Dispatch queryDispatchResult = dispatchMapper.selectOne(queryDispatchParam);
            queryDispatchResult.setWarehouseId(dispatchDTO.getWarehouseId());
            Integer integer = dispatchMapper.updateDispatchByPrimaryKey(queryDispatchResult);
            return integer;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @title: describeMineFuzzy
     * @description: 条件模糊查询当前用户提交报损单数据
     * @params: [dispatchDTO, userId]
     * @return: java.util.List<com.system.service.dto.dispatch.DispatchWithWarehouseNameDTO>
     * @createTime: 2020/5/2 9:06
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<DispatchWithWarehouseNameDTO> describeMineFuzzy(DispatchDTO dispatchDTO, String userId) {
        Dispatch dispatch = new Dispatch();
        BeanUtils.copyProperties(dispatchDTO, dispatch);
        List<Map<String, Object>> list = this.dispatchMapper.describeMineFuzzy(dispatch, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批报损单数据
     * @params: [ dispatchDTO, userId ]
     * @return: java.util.List<com.system.service.dto.dispatch.DispatchWithWarehouseNameDTO>
     * @createTime: 2020/5/2 11:39
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<DispatchWithWarehouseNameDTO> describeCommissionFuzzy(DispatchDTO dispatchDTO, String userId) {
        Dispatch dispatch = new Dispatch();
        BeanUtils.copyProperties(dispatchDTO, dispatch);
        List<Map<String, Object>> list = this.dispatchMapper.describeCommissionFuzzy(dispatch, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @title: approveDispatch
     * @description: 审批报损单(通过4和打回5)
     * @params: [ dispatchDTO ]
     * @return: java.lang.Boolean
     * @createTime: 2020/5/2 11:56
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Boolean approveDispatch(DispatchDTO dispatchDTO) {
        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(dispatchDTO.getId());
        if(dispatch == null) {
            return false;
        }
        // 5、将出库主表的信息存库
        String state = dispatchDTO.getState();
        dispatch.setState(state);
        dispatch.setAuditTime(new Date());
        if (dispatchMapper.updateDispatchByPrimaryKey(dispatch) > 0) {
            // 说明修改成功，根据state作相应的处理  TODO 后期这些状态值可以考虑数据字典提取，避免写死了
            if("4".equals(state)){
                // 审核通过，减库存
                // 查询报损单子表数据
                Example example = new Example(DispatchDetail.class);
                Example.Criteria criteria = example.createCriteria();
                // TODO 这行代码你们审核下，是不是pid放主表的dispatch_no字段
                criteria.andEqualTo("pid",dispatchDTO.getDispatchNo());
                List<DispatchDetail>  dispatchDetails = dispatchDetailMapper.selectByExample(example);
                System.out.println(dispatchDetails+"YYYYYYYYYYYYYYYYYY");
                if(CollectionUtils.isEmpty(dispatchDetails)){
                    // 报损子表没有记录，何谈减库存
                    throw new RuntimeException("数据异常,请联系管理员");
                }
                for (DispatchDetail dispatchDetail : dispatchDetails) {
                    // 获取每条记录的仓储数据ID
                    String stockId = dispatchDetail.getStockId();

                    Stock stock = stockMapper.selectByPrimaryKey(stockId);

                    if(stock != null) {
                        stock.setCqty(stock.getCqty() - dispatchDetail.getQty());
                        Integer rowCount = stockMapper.updateStockByPrimaryKey(stock);
                        if(rowCount < 0) {
                           throw new RuntimeException("减库存发生异常,请联系管理员");
                        }
                    }else {
                        // 未找到仓储表数据，说明没关联，无法减库存
                        throw new RuntimeException("数据异常1,请联系管理员");
                    }
                    // 审批报损单到这里才算真正成功
                    return true;
                }
            }else if("5".equals(state)) {
                // 审核不通过，打回
                return false;
            }
        }
        return false;
    }

    /**
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的报损单数据
     * @params: [ dispatchDTO, userId ]
     * @return: java.util.List<com.system.service.dto.dispatch.DispatchWithWarehouseNameDTO>
     * @createTime: 2020/5/2 12:55
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<DispatchWithWarehouseNameDTO> describeAlreadDoneFuzzy(DispatchDTO dispatchDTO, String userId) {
        Dispatch dispatch = new Dispatch();
        BeanUtils.copyProperties(dispatchDTO, dispatch);
        List<Map<String, Object>> list = this.dispatchMapper.describeAlreadDoneFuzzy(dispatch, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(DispatchDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Dispatch.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getDispatchNo() && !"".equals(dto.getDispatchNo())) {
                criteriaName.andLike("dispatchNo", "%" + dto.getDispatchNo() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
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
    private Example initExample(DispatchDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Dispatch.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getDispatchNo() && !"".equals(dto.getDispatchNo())) {
                criteriaName.andEqualTo("dispatchNo", dto.getDispatchNo());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
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