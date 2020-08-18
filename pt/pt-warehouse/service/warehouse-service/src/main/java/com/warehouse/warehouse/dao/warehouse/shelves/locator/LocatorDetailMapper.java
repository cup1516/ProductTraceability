package com.warehouse.warehouse.dao.warehouse.shelves.locator;

import tk.mybatis.mapper.common.Mapper;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.LocatorDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (LocatorDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-02 21:20:32
 */

public interface LocatorDetailMapper extends Mapper<LocatorDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LocatorDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<LocatorDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param locatorDetail 实例对象
     * @return 对象列表
     */
    List<LocatorDetail> describeAll(LocatorDetail locatorDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param locatorDetail 实例对象
     * @return 对象列表
     */
    List<LocatorDetail> describeFuzzy(LocatorDetail locatorDetail);

    /**
     * 新增数据
     *
     * @param locatorDetail 实例对象
     * @return 影响行数
     */
    Integer insertLocatorDetail(LocatorDetail locatorDetail);

    /**
     * 新增数据
     *
     * @param List<locatorDetail> 实例对象
     * @return 影响行数
     */
    Integer saveLocatorDetail(@Param("list") List<LocatorDetail> list);

    /**
     * 修改数据
     *
     * @param locatorDetail 实例对象
     * @return 影响行数
     */
    Integer updateLocatorDetailByPrimaryKey(LocatorDetail locatorDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteLocatorDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteLocatorDetailByPrimaryKeys(@Param("list") List<String> list);

    /**
     * 通过locatirId查找locatorDetail
     * @param locatorId
     * @return
     */
    LocatorDetail getLocatorDetail(@Param("locatorId") String locatorId, @Param("companyId") String companyId);

}