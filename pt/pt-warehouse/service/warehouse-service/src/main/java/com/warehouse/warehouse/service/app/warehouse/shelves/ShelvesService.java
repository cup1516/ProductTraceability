package com.warehouse.warehouse.service.app.warehouse.shelves;

import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesAndLocatorDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.WarehouseAndShelvesDTO;

import java.util.List;

/**
 * 货架表(Shelves)表服务接口
 *
 * @author makejava
 * @since 2020-04-12 20:32:33
 */
public interface ShelvesService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ShelvesDTO describeById(String id);


    ShelvesAndLocatorDTO describeShelfDetail(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ShelvesDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param shelvesDTO 实例对象
     * @return 对象列表
     */
    List<ShelvesDTO> describeAll(ShelvesDTO shelvesDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param shelvesDTO 实例对象
     * @return 对象列表
     */
    List<ShelvesDTO> describeFuzzy(ShelvesDTO shelvesDTO);
    
    
    /**
     * 新增数据
     *
     * @param shelvesDTO 实例对象
     * @return 实例对象
     */
    ShelvesDTO insertShelves(ShelvesDTO shelvesDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<ShelvesDTO> saveShelves(List<ShelvesDTO> list);

    /**
     * 修改数据
     *
     * @param shelvesDTO 实例对象
     * @return 实例对象
     */
    ShelvesDTO updateShelvesByPrimaryKey(ShelvesDTO shelvesDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteShelvesByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteShelvesByPrimaryKeys(List<String> list);


    /**
     * @title: describeShelvesAndLocationByWarehouseId
     * @description: 根据仓库id获取货架货位级联信息
     * @params: [ warehouseId ]
     * @return: java.util.List<com.system.service.dto.ShelvesAndLocatorDTO>
     * @createTime: 2020/4/14 9:07
     * @version: 1.0
     * @author: wanpp
     */
    List<ShelvesAndLocatorDTO> describeShelvesAndLocationByWarehouseId(String warehouseId);


    /**
     * @title: describeUsableShelvesAndLocationByWarehouseId
     * @description: 根据仓库id获取货架货位级联信息
     * @params: [ warehouseId ]
     * @return: java.util.List<com.system.service.dto.ShelvesAndLocatorDTO>
     * @createTime: 2020/4/14 9:07
     * @version: 1.0
     * @author: wanpp
     */
    List<ShelvesAndLocatorDTO> describeUsableShelvesAndLocationByWarehouseId(String warehouseId, String goodsNo,String batch);

    /**
     * @title: describeByLocatorId
     * @description: 根据货位id 查询货架 信息
     * @params: [ locatorId ]
     * @return: com.system.service.dto.ShelvesDTO
     * @createTime: 2020/4/18 14:12
     * @version: 1.0
     * @author: wanpp
     */
    ShelvesDTO describeByLocatorId(String locatorId);

    /**
     * @title: describeAllMineGroupByWarehouse
     * @description: 查询用户所有数据(级联)
     * @params: [ userId ]
     * @return: java.util.List<com.system.service.dto.shelves.WarehouseAndShelvesDTO>
     * @createTime: 2020/5/10 15:42
     * @version: 1.0
     * @author: wanpp
     */
    List<WarehouseAndShelvesDTO> describeAllMineGroupByWarehouse(String userId);


    List<ShelvesDTO> describeAllShellfByWarehouseId(String warehouseId);
    String describeAllShelfNameByWarehouseId(String warehouseId);
}