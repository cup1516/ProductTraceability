package com.warehouse.warehouse.dao.otherin;

import com.warehouse.warehouse.pojo.po.otherin.OtherinDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 入库子表(OtherinDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface OtherinDetailMapper extends Mapper<OtherinDetail> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OtherinDetail describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OtherinDetail> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param otherinDetail 实例对象
     * @return 对象列表
     */
    List<OtherinDetail> describeAll(OtherinDetail otherinDetail);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param otherinDetail 实例对象
     * @return 对象列表
     */
    List<OtherinDetail> describeFuzzy(OtherinDetail otherinDetail);

    /**
     * 新增数据
     *
     * @param otherinDetail 实例对象
     * @return 影响行数
     */
    Integer insertOtherinDetail(OtherinDetail otherinDetail);

    /**
     * 新增数据
     *
     * @param List<otherinDetail> 实例对象
     * @return 影响行数
     */
    Integer saveOtherinDetail(@Param("list") List<OtherinDetail> list);

    /**
     * 修改数据
     *
     * @param otherinDetail 实例对象
     * @return 影响行数
     */
    Integer updateOtherinDetailByPrimaryKey(OtherinDetail otherinDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteOtherinDetailByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteOtherinDetailByPrimaryKeys(@Param("list") List<String> list);


    OtherinDetail findInDetailByLocatorId(@Param("locatorId") String locatorId);


    List<OtherinDetail>  findInDetailListByLocatorId(@Param("locatorId") String locatorId);

}