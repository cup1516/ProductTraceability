package com.warehouse.warehouse.service.impl.dispatch;

import com.alibaba.fastjson.JSON;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.dispatch.DispatchDetailMapper;
import com.warehouse.warehouse.dao.dispatch.DispatchMapper;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.pojo.dto.dispatch.DispatchDetailDTO;
import com.warehouse.warehouse.pojo.dto.dispatch.InsertDispatchDetailByStockIdDTO;
import com.warehouse.warehouse.pojo.po.dispatch.Dispatch;
import com.warehouse.warehouse.pojo.po.dispatch.DispatchDetail;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.service.app.dispatch.DispatchDetailService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 报损子表(DispatchDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-05-01 19:49:56
 */
@Service("dispatchDetailService")
public class DispatchDetailServiceImpl implements DispatchDetailService {

    private static final Logger logger = LogManager.getLogger(DispatchDetailServiceImpl.class);

    @Resource
    private DispatchDetailMapper dispatchDetailMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private DispatchMapper dispatchMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DispatchDetailDTO describeById(String id) {
        DispatchDetailDTO dto = new DispatchDetailDTO();
        DispatchDetail dispatchDetail = this.dispatchDetailMapper.describeById(id);
        if (null != dispatchDetail) {
            BeanUtils.copyProperties(dispatchDetail, dto);
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
    public List<DispatchDetailDTO> describeAllByLimit(long offset, long limit) {
        List<DispatchDetail> list = this.dispatchDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param dispatchDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<DispatchDetailDTO> describeAll(DispatchDetailDTO dispatchDetailDTO) {
        dispatchDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        DispatchDetail dispatchDetail = new DispatchDetail();
        BeanUtils.copyProperties(dispatchDetailDTO, dispatchDetail);
        List<DispatchDetail> list = this.dispatchDetailMapper.describeAll(dispatchDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param dispatchDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<DispatchDetailDTO> describeFuzzy(DispatchDetailDTO dispatchDetailDTO) {
        DispatchDetail dispatchDetail = new DispatchDetail();
        dispatchDetail.setCompanyId(SecurityUtils.getCompanyId());
        BeanUtils.copyProperties(dispatchDetailDTO, dispatchDetail);
        List<DispatchDetail> list = this.dispatchDetailMapper.describeFuzzy(dispatchDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), DispatchDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param dispatchDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public DispatchDetailDTO insertDispatchDetail(DispatchDetailDTO dispatchDetailDTO) {
        dispatchDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        DispatchDetail dispatchDetail = new DispatchDetail();
        BeanUtils.copyProperties(dispatchDetailDTO, dispatchDetail);
        if (null == dispatchDetail.getId() || "".equals(dispatchDetail.getId())) {
            dispatchDetail.setId(UUID.randomUUID().toString());
        }
        this.dispatchDetailMapper.insertDispatchDetail(dispatchDetail);
        return dispatchDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<DispatchDetailDTO> saveDispatchDetail(List<DispatchDetailDTO> list) {
        list.forEach(dispatchDetailDTO -> {
            if (null == dispatchDetailDTO.getId() || "".equals(dispatchDetailDTO.getId())) {
                dispatchDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.dispatchDetailMapper.saveDispatchDetail(JSON.parseArray(JSON.toJSONString(list), DispatchDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param dispatchDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public DispatchDetailDTO updateDispatchDetailByPrimaryKey(DispatchDetailDTO dispatchDetailDTO) {
        dispatchDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        DispatchDetail dispatchDetail = new DispatchDetail();
        BeanUtils.copyProperties(dispatchDetailDTO, dispatchDetail);
        this.dispatchDetailMapper.updateDispatchDetailByPrimaryKey(dispatchDetail);
        return this.describeById(dispatchDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteDispatchDetailByPrimaryKey(String id) {
        return this.dispatchDetailMapper.deleteDispatchDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteDispatchDetailByPrimaryKeys(List<String> list) {
        return this.dispatchDetailMapper.deleteDispatchDetailByPrimaryKeys(list) == list.size();
    }

    /**
     * @title: insertByStockId
     * @description: 通过仓储表编号插入数据
     * @params: [ dto ]
     * @return: java.lang.Integer
     * @createTime: 2020/5/1 22:39
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Integer insertByStockId(InsertDispatchDetailByStockIdDTO dto) {
        Integer returnData = 0;
        try{
            Stock stock = stockMapper.selectByPrimaryKey(dto.getStockId());
            DispatchDetail detail = new DispatchDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setPid(dto.getPid());
            detail.setLocatorId(stock.getLocatorId());
            detail.setLocatorCode(stock.getLocatorCode());
            detail.setLocatorName(stock.getLocatorName());
            detail.setCommodityId(stock.getCommodityId());
            detail.setCommodityBar(stock.getCommodityBar());
            detail.setCommodityName(stock.getCommodityName());
            detail.setQty(dto.getNum());
            detail.setCreateId(dto.getCreateId());
            detail.setCreator(dto.getCreator());
            detail.setCreateTime(new Date());
            detail.setUpdateId(dto.getUpdateId());
            detail.setCompanyId(SecurityUtils.getCompanyId());
            detail.setUpdator(dto.getUpdator());
            detail.setUpdateTime(new Date());
            detail.setStockId(dto.getStockId());
            detail.setCompanyId(dto.getCompanyId());
            // todo,带入关联人员和入库单信息
            // detail.setOtherinId();
            returnData = dispatchDetailMapper.insertDispatchDetail(detail);
        }catch (Exception e){
            logger.error("通过仓储表编号新增数据异常，异常为：{}",JSON.toJSONString(e));
        }
        return returnData;
    }

    /**
     * @title: updateByStockId
     * @description: 通过货位编号修改数据
     * @params: [ dto ]
     * @return: java.lang.Integer
     * @createTime: 2020/5/1 22:46
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Integer updateByStockId(InsertDispatchDetailByStockIdDTO dto) {
        Integer returnData = 0;
        try{
            Stock stock = stockMapper.selectByPrimaryKey(dto.getStockId());
            DispatchDetail detail = new DispatchDetail();
            detail.setPid(dto.getPid());
            detail.setLocatorId(stock.getLocatorId());
            detail.setLocatorCode(stock.getLocatorCode());
            detail.setLocatorName(stock.getLocatorName());
            detail.setCommodityId(stock.getCommodityId());
            detail.setCommodityBar(stock.getCommodityBar());
            detail.setCommodityName(stock.getCommodityName());
            detail.setQty(dto.getNum());
            detail.setCreateId(dto.getCreateId());
            detail.setCreator(dto.getCreator());
            detail.setCreateTime(new Date());
            detail.setUpdateId(dto.getUpdateId());
            detail.setCompanyId(SecurityUtils.getCompanyId());
            detail.setUpdator(dto.getUpdator());
            detail.setUpdateTime(new Date());
            detail.setStockId(dto.getStockId());
            detail.setId(dto.getDetailId());
            // todo,带入关联人员和入库单信息
            // detail.setOtherinId();
            returnData = dispatchDetailMapper.updateDispatchDetailByPrimaryKey(detail);
        }catch (Exception e){
            logger.error("通过仓储表编号更新数据异常，异常为：{}",JSON.toJSONString(e));
        }
        return returnData;
    }

    /**
     * @title: deleteAllByPid
     * @description: 通过报损编号清空所有报损详情数据
     * @params: [ orderNo ]
     * @return: java.lang.Integer
     * @createTime: 2020/5/2 7:50
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Integer deleteAllByPid(String orderNo) {
        // 数据情况前，需要处理入库单仓库信息
        Dispatch dispatch = new Dispatch();
        dispatch.setDispatchNo(orderNo);
        Dispatch dispatchResult = dispatchMapper.selectOne(dispatch);
        dispatchResult.setWarehouseId("");
        dispatchMapper.updateDispatchByPrimaryKey(dispatchResult);
        DispatchDetail dispatchDetail = new DispatchDetail();
        dispatchDetail.setPid(orderNo);
        int delete = dispatchDetailMapper.delete(dispatchDetail);
        return delete;
    }

    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(DispatchDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(DispatchDetail.class, dto.getOrderBy());
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
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andLike("locatorCode", "%" + dto.getLocatorCode() + "%");
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andLike("locatorName", "%" + dto.getLocatorName() + "%");
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
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
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
            if (null != dto.getStockId() && !"".equals(dto.getStockId())) {
                criteriaName.andLike("stockId", "%" + dto.getStockId() + "%");
            }
            if (null != dto.getOtherinCustomerId() && !"".equals(dto.getOtherinCustomerId())) {
                criteriaName.andLike("otherinCustomerId", "%" + dto.getOtherinCustomerId() + "%");
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
    private Example initExample(DispatchDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(DispatchDetail.class, dto.getOrderBy());
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
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andEqualTo("locatorCode", dto.getLocatorCode());
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andEqualTo("locatorName", dto.getLocatorName());
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
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
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
            if (null != dto.getStockId() && !"".equals(dto.getStockId())) {
                criteriaName.andEqualTo("stockId", dto.getStockId());
            }
            if (null != dto.getOtherinCustomerId() && !"".equals(dto.getOtherinCustomerId())) {
                criteriaName.andEqualTo("otherinCustomerId", dto.getOtherinCustomerId());
            }
        }
        return example;
    }
    
}