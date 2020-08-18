package com.warehouse.warehouse.service.impl.stock;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.pojo.dto.stock.StockDTO;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.service.app.stock.StockService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 仓储表(Stock)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("stockService")
public class StockServiceImpl implements StockService {

    @Resource
    private StockMapper stockMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StockDTO describeById(String id) {
        StockDTO dto = new StockDTO();
        Stock stock = this.stockMapper.describeById(id);
        if (null != stock) {
            BeanUtils.copyProperties(stock, dto);
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
    public List<StockDTO> describeAllByLimit(long offset, long limit) {
        List<Stock> list = this.stockMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), StockDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param stockDTO 对象
     * @return 对象列表
     */
    @Override
    public List<StockDTO> describeAll(StockDTO stockDTO) {
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockDTO, stock);
        List<Stock> list = this.stockMapper.describeAll(stock);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), StockDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param stockDTO 对象
     * @return 对象列表
     */
    @Override
    public List<StockDTO> describeFuzzy(StockDTO stockDTO) {
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockDTO, stock);
        List<Stock> list = this.stockMapper.describeFuzzy(stock);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), StockDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param stockDTO 实例对象
     * @return 实例对象
     */
    @Override
    public StockDTO insertStock(StockDTO stockDTO) {
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockDTO, stock);
        if (null == stock.getId() || "".equals(stock.getId())) {
            stock.setId(UUID.randomUUID().toString());
        }
        this.stockMapper.insertStock(stock);
        return stockDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<StockDTO> saveStock(List<StockDTO> list) {
        list.forEach(stockDTO -> {
            if (null == stockDTO.getId() || "".equals(stockDTO.getId())) {
                stockDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.stockMapper.saveStock(JSON.parseArray(JSON.toJSONString(list), Stock.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param stockDTO 实例对象
     * @return 实例对象
     */
    @Override
    public StockDTO updateStockByPrimaryKey(StockDTO stockDTO) {
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockDTO, stock);
        this.stockMapper.updateStockByPrimaryKey(stock);
        return this.describeById(stock.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteStockByPrimaryKey(String id) {
        return this.stockMapper.deleteStockByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteStockByPrimaryKeys(List<String> list) {
        return this.stockMapper.deleteStockByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(StockDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Stock.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getBillNo() && !"".equals(dto.getBillNo())) {
                criteriaName.andLike("billNo", "%" + dto.getBillNo() + "%");
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andLike("locatorId", "%" + dto.getLocatorId() + "%");
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andLike("locatorName", "%" + dto.getLocatorName() + "%");
            }
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andLike("locatorCode", "%" + dto.getLocatorCode() + "%");
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
            if (null != dto.getCqty()) {
                criteriaName.andLike("cqty", "%" + dto.getCqty() + "%");
            }
            if (null != dto.getUqty()) {
                criteriaName.andLike("uqty", "%" + dto.getUqty() + "%");
            }
            if (null != dto.getLqty()) {
                criteriaName.andLike("lqty", "%" + dto.getLqty() + "%");
            }
            if (null != dto.getLocatorState() && !"".equals(dto.getLocatorState())) {
                criteriaName.andLike("locatorState", "%" + dto.getLocatorState() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
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
        }
        return example;
    }
    
    /**
     * 初始化 精确查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initExample(StockDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Stock.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getBillNo() && !"".equals(dto.getBillNo())) {
                criteriaName.andEqualTo("billNo", dto.getBillNo());
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andEqualTo("locatorId", dto.getLocatorId());
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andEqualTo("locatorName", dto.getLocatorName());
            }
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andEqualTo("locatorCode", dto.getLocatorCode());
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
            if (null != dto.getCqty()) {
                criteriaName.andEqualTo("cqty", dto.getCqty());
            }
            if (null != dto.getUqty()) {
                criteriaName.andEqualTo("uqty", dto.getUqty());
            }
            if (null != dto.getLqty()) {
                criteriaName.andEqualTo("lqty", dto.getLqty());
            }
            if (null != dto.getLocatorState() && !"".equals(dto.getLocatorState())) {
                criteriaName.andEqualTo("locatorState", dto.getLocatorState());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
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
        }
        return example;
    }
}