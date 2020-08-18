package com.warehouse.warehouse.service.impl.stock;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.stock.StockLogMapper;
import com.warehouse.warehouse.pojo.dto.stock.StockLogDTO;
import com.warehouse.warehouse.pojo.po.stock.StockLog;
import com.warehouse.warehouse.service.app.stock.StockLogService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 仓储日志表(StockLog)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("stockLogService")
public class StockLogServiceImpl implements StockLogService {

    @Resource
    private StockLogMapper stockLogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StockLogDTO describeById(String id) {
        StockLogDTO dto = new StockLogDTO();
        StockLog stockLog = this.stockLogMapper.describeById(id);
        if (null != stockLog) {
            BeanUtils.copyProperties(stockLog, dto);
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
    public List<StockLogDTO> describeAllByLimit(long offset, long limit) {
        List<StockLog> list = this.stockLogMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), StockLogDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param stockLogDTO 对象
     * @return 对象列表
     */
    @Override
    public List<StockLogDTO> describeAll(StockLogDTO stockLogDTO) {
        StockLog stockLog = new StockLog();
        BeanUtils.copyProperties(stockLogDTO, stockLog);
        List<StockLog> list = this.stockLogMapper.describeAll(stockLog);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), StockLogDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param stockLogDTO 对象
     * @return 对象列表
     */
    @Override
    public List<StockLogDTO> describeFuzzy(StockLogDTO stockLogDTO) {
        StockLog stockLog = new StockLog();
        BeanUtils.copyProperties(stockLogDTO, stockLog);
        List<StockLog> list = this.stockLogMapper.describeFuzzy(stockLog);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), StockLogDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param stockLogDTO 实例对象
     * @return 实例对象
     */
    @Override
    public StockLogDTO insertStockLog(StockLogDTO stockLogDTO) {
        StockLog stockLog = new StockLog();
        BeanUtils.copyProperties(stockLogDTO, stockLog);
        if (null == stockLog.getId() || "".equals(stockLog.getId())) {
            stockLog.setId(UUID.randomUUID().toString());
        }
        this.stockLogMapper.insertStockLog(stockLog);
        return stockLogDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<StockLogDTO> saveStockLog(List<StockLogDTO> list) {
        list.forEach(stockLogDTO -> {
            if (null == stockLogDTO.getId() || "".equals(stockLogDTO.getId())) {
                stockLogDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.stockLogMapper.saveStockLog(JSON.parseArray(JSON.toJSONString(list), StockLog.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param stockLogDTO 实例对象
     * @return 实例对象
     */
    @Override
    public StockLogDTO updateStockLogByPrimaryKey(StockLogDTO stockLogDTO) {
        StockLog stockLog = new StockLog();
        BeanUtils.copyProperties(stockLogDTO, stockLog);
        this.stockLogMapper.updateStockLogByPrimaryKey(stockLog);
        return this.describeById(stockLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteStockLogByPrimaryKey(String id) {
        return this.stockLogMapper.deleteStockLogByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteStockLogByPrimaryKeys(List<String> list) {
        return this.stockLogMapper.deleteStockLogByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(StockLogDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(StockLog.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getBillNo() && !"".equals(dto.getBillNo())) {
                criteriaName.andLike("billNo", "%" + dto.getBillNo() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
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
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getType() && !"".equals(dto.getType())) {
                criteriaName.andLike("type", "%" + dto.getType() + "%");
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
    private Example initExample(StockLogDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(StockLog.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getBillNo() && !"".equals(dto.getBillNo())) {
                criteriaName.andEqualTo("billNo", dto.getBillNo());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
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
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getType() && !"".equals(dto.getType())) {
                criteriaName.andEqualTo("type", dto.getType());
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