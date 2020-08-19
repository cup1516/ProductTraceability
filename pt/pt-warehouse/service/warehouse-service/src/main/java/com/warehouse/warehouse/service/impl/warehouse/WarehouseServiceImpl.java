package com.warehouse.warehouse.service.impl.warehouse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.dao.warehouse.WarehouseMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.ShelvesMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorMapper;
import com.warehouse.warehouse.pojo.dto.warehouse.WarehouseDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDTO;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.pojo.po.warehouse.Warehouse;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.Shelves;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.Locator;
import com.warehouse.warehouse.service.app.warehouse.WarehouseService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 仓库表(Warehouse)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private ShelvesMapper shelvesMapper;
    @Resource
    private StockMapper stockMapper;
    @Resource
    private LocatorMapper locatorMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WarehouseDTO describeById(String id) {
        WarehouseDTO dto = new WarehouseDTO();
        Warehouse warehouse = this.warehouseMapper.describeById(id);
        if (null != warehouse) {
            BeanUtils.copyProperties(warehouse, dto);
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
    public List<WarehouseDTO> describeAllByLimit(long offset, long limit) {
        List<Warehouse> list = this.warehouseMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), WarehouseDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param warehouseDTO 对象
     * @return 对象列表
     */
    @Override
    public List<WarehouseDTO> describeAll(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDTO, warehouse);
        List<Warehouse> list = this.warehouseMapper.describeAll(warehouse);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), WarehouseDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param warehouseDTO 对象
     * @return 对象列表
     */
    @Override
    public List<WarehouseDTO> describeFuzzy(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDTO, warehouse);
        List<Warehouse> list = this.warehouseMapper.describeFuzzy(warehouse);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), WarehouseDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param warehouseDTO 实例对象
     * @return 实例对象
     */
    @Override
    public WarehouseDTO insertWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDTO, warehouse);
        if (null == warehouse.getId() || "".equals(warehouse.getId())) {
            warehouse.setId(UUID.randomUUID().toString());
        }
        Date date = new Date();
        if (null == warehouse.getCreateTime()){
            warehouse.setCreateTime(date);
        }
        if (null == warehouse.getUpdateTime()){
            warehouse.setUpdateTime(date);
        }
        this.warehouseMapper.insertWarehouse(warehouse);
        return warehouseDTO;
    }
	
	
	 /**
     * 无条件查询所有仓库
     *
     * @return 实例对象集合
     */
	
    @Override
    public List<WarehouseDTO> getAllStoreByPrincipalId(String principalId,String companyId) {
       List<Warehouse> list = this.warehouseMapper.getAllStoreByPrincipalId(principalId,companyId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), WarehouseDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<WarehouseDTO> saveWarehouse(List<WarehouseDTO> list) {
        list.forEach(warehouseDTO -> {
            if (null == warehouseDTO.getId() || "".equals(warehouseDTO.getId())) {
                warehouseDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.warehouseMapper.saveWarehouse(JSON.parseArray(JSON.toJSONString(list), Warehouse.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param warehouseDTO 实例对象
     * @return 实例对象
     */
    @Override
    public WarehouseDTO updateWarehouseByPrimaryKey(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDTO, warehouse);
		warehouse.setUpdateTime(new Date());
        if (null != warehouse.getCreator()){
            warehouse.setUpdator(warehouseDTO.getCreator());
            warehouse.setUpdateId(warehouseDTO.getCreateId());
        }
        if (null == warehouse.getUpdateTime()){
            warehouse.setUpdateTime(new Date());
        }
        this.warehouseMapper.updateWarehouseByPrimaryKey(warehouse);
        return this.describeById(warehouse.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteWarehouseByPrimaryKey(String id) {
        return this.warehouseMapper.deleteWarehouseByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteWarehouseByPrimaryKeys(List<String> list) {
        return this.warehouseMapper.deleteWarehouseByPrimaryKeys(list) == list.size();
    }

    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(WarehouseDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Warehouse.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andLike("name", "%" + dto.getName() + "%");
            }
            if (null != dto.getAlias() && !"".equals(dto.getAlias())) {
                criteriaName.andLike("alias", "%" + dto.getAlias() + "%");
            }
            if (null != dto.getAddress() && !"".equals(dto.getAddress())) {
                criteriaName.andLike("address", "%" + dto.getAddress() + "%");
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
    private Example initExample(WarehouseDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Warehouse.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andEqualTo("name", dto.getName());
            }
            if (null != dto.getAlias() && !"".equals(dto.getAlias())) {
                criteriaName.andEqualTo("alias", dto.getAlias());
            }
            if (null != dto.getAddress() && !"".equals(dto.getAddress())) {
                criteriaName.andEqualTo("address", dto.getAddress());
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


    @Override
    public String getStorageByWarehouseId(String id) {
        int flag = 0;
        Map<String,Integer> GoodsNameAndNum = new HashMap();
        List<Shelves> shelves = shelvesMapper.describesAllShelvesByWarehouseId(id);
        List<String>shelvesIdlist = new ArrayList<>() ;
        for(int i = 0 ;i<shelves.size();i++){
            shelvesIdlist.add(shelves.get(i).getId());
        }
        List <Locator> locators = locatorMapper.desrcibesLocatorByShelveseIdList(shelvesIdlist);
        for(int i = 0;i<locators.size() ;i++){
            flag = 0;
            LocatorDTO locatorDTO=new LocatorDTO();
            BeanUtils.copyProperties(locators.get(i), locatorDTO);
            String  locatorId = locators.get(i).getId();
            Stock stock = new Stock();
            stock.setLocatorId(locatorId);
            List<Stock> stocks =stockMapper.select(stock);
            if (!CollectionUtils.isEmpty(stocks)){
                stock = stocks.get(0);


                // 如果map为空直接 添加第一条
                if(GoodsNameAndNum.size() == 0){
                    GoodsNameAndNum.put(stock.getCommodityName(),stock.getCqty());
                }
                //否则 遍历map的key与当前的商品名称是否相同 是 则数量相加，且flag=1，跳出循环
                else {
                    Iterator in = GoodsNameAndNum.entrySet().iterator();
                    while (in.hasNext()) {
                        Map.Entry entry = (java.util.Map.Entry)in.next();
                        String key =entry.getKey().toString();
                        if (key.equals(stock.getCommodityName())) {
                            GoodsNameAndNum.put(stock.getCommodityName(), GoodsNameAndNum.get(key)+stock.getCqty());
                            flag =1;
                            break;
                        }
                    }
                    //如果flag =0 则没有对应key 直接添加商品名称和数量，
                    if (flag == 0){
                        GoodsNameAndNum.put(stock.getCommodityName(),stock.getCqty()) ;
                    }

                }
            }
        }
        String GoodsNameAndNum1 = JSONObject.toJSONString(GoodsNameAndNum);
        return GoodsNameAndNum1;
    }
    
}