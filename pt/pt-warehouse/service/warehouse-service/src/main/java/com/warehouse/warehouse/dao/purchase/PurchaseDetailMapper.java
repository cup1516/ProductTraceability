package com.warehouse.warehouse.dao.purchase;

import com.warehouse.warehouse.pojo.po.purchase.PurchaseDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 采购单子表(PurchaseDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface PurchaseDetailMapper extends Mapper<PurchaseDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PurchaseDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<PurchaseDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param purchaseDetail 实例对象
     * @return 对象列表
     */
    List<PurchaseDetail> describeAll(PurchaseDetail purchaseDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param purchaseDetail 实例对象
     * @return 对象列表
     */
    List<PurchaseDetail> describeFuzzy(PurchaseDetail purchaseDetail);

    /**
     * 新增数据
     *
     * @param purchaseDetail 实例对象
     * @return 影响行数
     */
    Integer insertPurchaseDetail(PurchaseDetail purchaseDetail);

    /**
     * 新增数据
     *
     * @param List<purchaseDetail> 实例对象
     * @return 影响行数
     */
    Integer savePurchaseDetail(@Param("list") List<PurchaseDetail> list);

    /**
     * 修改数据
     *
     * @param purchaseDetail 实例对象
     * @return 影响行数
     */
    Integer updatePurchaseDetailByPrimaryKey(PurchaseDetail purchaseDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deletePurchaseDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deletePurchaseDetailByPrimaryKeys(@Param("list") List<String> list);

}