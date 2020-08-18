package com.warehouse.warehouse.dao.purchase;

import com.warehouse.warehouse.pojo.po.purchase.Purchase;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 采购表(Purchase)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface PurchaseMapper extends Mapper<Purchase> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Purchase describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Purchase> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param purchase 实例对象
     * @return 对象列表
     */
    List<Purchase> describeAll(Purchase purchase);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param purchase 实例对象
     * @return 对象列表
     */
    List<Purchase> describeFuzzy(Purchase purchase);

    /**
     * 新增数据
     *
     * @param purchase 实例对象
     * @return 影响行数
     */
    Integer insertPurchase(Purchase purchase);

    /**
     * 新增数据
     *
     * @param List<purchase> 实例对象
     * @return 影响行数
     */
    Integer savePurchase(@Param("list") List<Purchase> list);

    /**
     * 修改数据
     *
     * @param purchase 实例对象
     * @return 影响行数
     */
    Integer updatePurchaseByPrimaryKey(Purchase purchase);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deletePurchaseByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deletePurchaseByPrimaryKeys(@Param("list") List<String> list);

}