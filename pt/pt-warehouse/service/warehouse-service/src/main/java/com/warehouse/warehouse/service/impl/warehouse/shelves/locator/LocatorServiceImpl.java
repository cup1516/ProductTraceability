package com.warehouse.warehouse.service.impl.warehouse.shelves.locator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.ShelvesMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorDetailMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorMapper;
import com.warehouse.warehouse.pojo.dto.stock.StockDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorAndGoodsDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDetailDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.Shelves;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.Locator;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.LocatorDetail;
import com.warehouse.warehouse.service.app.warehouse.shelves.locator.LocatorService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 货位表(Locator)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("locatorService")
public class LocatorServiceImpl implements LocatorService {

    @Resource
    private LocatorMapper locatorMapper;

    @Resource
    private ShelvesMapper shelvesMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private LocatorDetailMapper locatorDetailMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public LocatorDTO describeById(String id) {
        LocatorDTO dto = new LocatorDTO();
        Locator locator = this.locatorMapper.describeById(id);
        if (null != locator) {
            BeanUtils.copyProperties(locator, dto);
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
    public List<LocatorDTO> describeAllByLimit(long offset, long limit) {
        List<Locator> list = this.locatorMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), LocatorDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param locatorDTO 对象
     * @return 对象列表
     */
    @Override
    public List<LocatorDTO> describeAll(LocatorDTO locatorDTO) {
        Locator locator = new Locator();
        BeanUtils.copyProperties(locatorDTO, locator);
        List<Locator> list = this.locatorMapper.describeAll(locator);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), LocatorDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param locatorDTO 对象
     * @return 对象列表
     */
    @Override
    public List<LocatorDTO> describeFuzzy(LocatorDTO locatorDTO) {
        Locator locator = new Locator();
        BeanUtils.copyProperties(locatorDTO, locator);
        List<Locator> list = this.locatorMapper.describeFuzzy(locator);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), LocatorDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param locatorDTO 实例对象
     * @return 实例对象
     */
    @Override
    public LocatorDTO insertLocator(LocatorDTO locatorDTO) {
        Locator locator = new Locator();
        BeanUtils.copyProperties(locatorDTO, locator);
        if (null == locator.getId() || "".equals(locator.getId())) {
            locator.setId(UUID.randomUUID().toString());
        }
        if (null == locator.getShelvesName() || "".equals(locator.getShelvesName())){
            Shelves shelves = shelvesMapper.selectByPrimaryKey(locator.getShelvesId());
            locator.setShelvesName(shelves.getShelvesName());
        }
        Date date = new Date();
        if (null == locator.getCreateTime()){
            locator.setCreateTime(date);
        }
        if (null == locator.getUpdateTime()){
            locator.setUpdateTime(date);
        }
        this.locatorMapper.insertLocator(locator);
        return locatorDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<LocatorDTO> saveLocator(List<LocatorDTO> list) {
        list.forEach(locatorDTO -> {
            if (null == locatorDTO.getId() || "".equals(locatorDTO.getId())) {
                locatorDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.locatorMapper.saveLocator(JSON.parseArray(JSON.toJSONString(list), Locator.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param locatorDTO 实例对象
     * @return 实例对象
     */
    @Override
    public LocatorDTO updateLocatorByPrimaryKey(LocatorDTO locatorDTO) {
        Locator locator = new Locator();
        BeanUtils.copyProperties(locatorDTO, locator);

        if (null == locator.getShelvesName() || "".equals(locator.getShelvesName())){
            Shelves shelves = shelvesMapper.selectByPrimaryKey(locator.getShelvesId());
            locator.setShelvesName(shelves.getShelvesName());
        }
        Date date = new Date();
        if (null == locator.getUpdateTime()){
            locator.setUpdateTime(date);
        }
        this.locatorMapper.updateLocatorByPrimaryKey(locator);
        return this.describeById(locator.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteLocatorByPrimaryKey(String id) {
        return this.locatorMapper.deleteLocatorByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteLocatorByPrimaryKeys(List<String> list) {
        return this.locatorMapper.deleteLocatorByPrimaryKeys(list) == list.size();
    }

    /**
     * @title: describeFuzzyWithWarehouseName
     * @description: 条件分页模糊查询所有数据(携带仓库名称)
     * @params: [locatorDTO]
     * @return: java.util.List<com.system.service.dto.locator.LocatorWithWarehouseNameDTO>
     * @createTime: 2020/5/10 14:47
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<LocatorWithWarehouseNameDTO> describeFuzzyWithWarehouseName(LocatorDTO locatorDTO) {
        Locator locator = new Locator();
        BeanUtils.copyProperties(locatorDTO, locator);
        List<Map<String,Object>> list = this.locatorMapper.describeFuzzyWithWarehouseName(locator);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), LocatorWithWarehouseNameDTO.class);
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
    private Example initFuzzyExample(LocatorDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Locator.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andLike("name", "%" + dto.getName() + "%");
            }
            if (null != dto.getCode() && !"".equals(dto.getCode())) {
                criteriaName.andLike("code", "%" + dto.getCode() + "%");
            }
            if (null != dto.getLength()) {
                criteriaName.andLike("length", "%" + dto.getLength() + "%");
            }
            if (null != dto.getWidth()) {
                criteriaName.andLike("width", "%" + dto.getWidth() + "%");
            }
            if (null != dto.getHeight()) {
                criteriaName.andLike("height", "%" + dto.getHeight() + "%");
            }
            if (null != dto.getWeight()) {
                criteriaName.andLike("weight", "%" + dto.getWeight() + "%");
            }
            if (null != dto.getType() && !"".equals(dto.getType())) {
                criteriaName.andLike("type", "%" + dto.getType() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
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
        }
        return example;
    }
    
    /**
     * 初始化 精确查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initExample(LocatorDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Locator.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andEqualTo("name", dto.getName());
            }
            if (null != dto.getCode() && !"".equals(dto.getCode())) {
                criteriaName.andEqualTo("code", dto.getCode());
            }
            if (null != dto.getLength()) {
                criteriaName.andEqualTo("length", dto.getLength());
            }
            if (null != dto.getWidth()) {
                criteriaName.andEqualTo("width", dto.getWidth());
            }
            if (null != dto.getHeight()) {
                criteriaName.andEqualTo("height", dto.getHeight());
            }
            if (null != dto.getWeight()) {
                criteriaName.andEqualTo("weight", dto.getWeight());
            }
            if (null != dto.getType() && !"".equals(dto.getType())) {
                criteriaName.andEqualTo("type", dto.getType());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
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
        }
        return example;
    }

    /**
     * 通过编码查询单条数据
     *
     * @param locatorCode 货位编码
     * @return 单条数据
     */
    @Override
    public LocatorDTO describeByLocatorCode(String locatorCode) {
        LocatorDTO dto = new LocatorDTO();
        Locator locator = new Locator();
        locator.setCode(locatorCode);
        List<Locator> list = this.locatorMapper.describeFuzzy(locator);
        locator = list.get(0);
        if (null != locator) {
            BeanUtils.copyProperties(locator, dto);
        }
        return dto;
    }


    @Override
    public LocatorAndGoodsDTO getLocatorDetail(String locatorId ,String companyId) {
        LocatorAndGoodsDTO locatorAndGoodsDTO = new LocatorAndGoodsDTO();
        LocatorDTO locatorDTO = new LocatorDTO();
        StockDTO stockDTO = new StockDTO();
        Locator locator = locatorMapper.describeById(locatorId);
        BeanUtils.copyProperties(locator, locatorDTO);
        List<Stock> stockList = stockMapper.findByLocatorId(locatorId);
        if(stockList.size()!=0){
            Stock stock = stockList.get(0);
            BeanUtils.copyProperties(stock, stockDTO);
        }
        locatorAndGoodsDTO.setLocatorDTO(locatorDTO);
        locatorAndGoodsDTO.setStockDTO(stockDTO);

        return locatorAndGoodsDTO;
    }
    @Override
    public String describeAllLocatorsByWarehouseId(String warehouseId) {
        List<Locator> locators = locatorMapper.describesAllLocatorsByWarehouseId(warehouseId);
        List<String> allLocatorName = new ArrayList<>();
        for(int i=0;i<locators.size();i++){
            allLocatorName.add(locators.get(i).getName());
        }
        String allLocatorName1 = JSONObject.toJSONString(allLocatorName);
        return allLocatorName1;
    }

    @Override
    public String describeFullLocatorsByWarehouseId(String warehouseId) {
        List<Locator> locators = locatorMapper.describeFullLocatorsByWarehouseId(warehouseId);
        List<String> allLocatorName = new ArrayList<>();
        for(int i=0;i<locators.size();i++){
            allLocatorName.add(locators.get(i).getName());
        }
        String allLocatorName1 = JSONObject.toJSONString(allLocatorName);
        return allLocatorName1;
    }

    @Override
    public String describeUseLocatorsByWarehouseId(String warehouseId) {
        List<Locator> locators = locatorMapper.describeUseLocatorsByWarehouseId(warehouseId);
        List<String> allLocatorName = new ArrayList<>();
        for(int i=0;i<locators.size();i++){
            allLocatorName.add(locators.get(i).getName());
        }
        String allLocatorName1 = JSONObject.toJSONString(allLocatorName);
        return allLocatorName1;

    }
}