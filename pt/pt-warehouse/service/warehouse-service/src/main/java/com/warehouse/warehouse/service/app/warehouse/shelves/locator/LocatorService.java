package com.warehouse.warehouse.service.app.warehouse.shelves.locator;

import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorAndGoodsDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDetailDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorWithWarehouseNameDTO;

import java.util.List;

/**
 * 货位表(Locator)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface LocatorService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LocatorDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<LocatorDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param locatorDTO 实例对象
     * @return 对象列表
     */
    List<LocatorDTO> describeAll(LocatorDTO locatorDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param locatorDTO 实例对象
     * @return 对象列表
     */
    List<LocatorDTO> describeFuzzy(LocatorDTO locatorDTO);
    
    
    /**
     * 新增数据
     *
     * @param locatorDTO 实例对象
     * @return 实例对象
     */
    LocatorDTO insertLocator(LocatorDTO locatorDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<LocatorDTO> saveLocator(List<LocatorDTO> list);

    /**
     * 修改数据
     *
     * @param locatorDTO 实例对象
     * @return 实例对象
     */
    LocatorDTO updateLocatorByPrimaryKey(LocatorDTO locatorDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteLocatorByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteLocatorByPrimaryKeys(List<String> list);

    /**
     * @title: describeFuzzyWithWarehouseName
     * @description: 条件分页模糊查询所有数据(携带仓库名称)
     * @params: [locatorDTO]
     * @return: java.util.List<com.system.service.dto.locator.LocatorWithWarehouseNameDTO>
     * @createTime: 2020/5/10 14:46
     * @version: 1.0
     * @author: wanpp
     */
    List<LocatorWithWarehouseNameDTO> describeFuzzyWithWarehouseName(LocatorDTO locatorDTO);

    /**
     * 通过编码查询单条数据
     *
     * @param locatorCode 货位编码
     * @return 单条数据
     */
    LocatorDTO describeByLocatorCode(String locatorCode);

    LocatorAndGoodsDTO getLocatorDetail(String locatorId, String companyId);

    String  describeAllLocatorsByWarehouseId (String warehouseId);
    String describeFullLocatorsByWarehouseId (String warehouseId);
    String describeUseLocatorsByWarehouseId (String warehouseId);
}