package com.warehouse.warehouse.dao.logging;

import com.warehouse.warehouse.pojo.po.logging.Logging;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Logging)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-27 14:53:46
 */
public interface LoggingMapper extends Mapper<Logging> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Logging describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Logging> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param logging 实例对象
     * @return 对象列表
     */
    List<Logging> describeAll(Logging logging);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param logging 实例对象
     * @return 对象列表
     */
    List<Logging> describeFuzzy(Logging logging);

    /**
     * 新增数据
     *
     * @param logging 实例对象
     * @return 影响行数
     */
    Integer insertLogging(Logging logging);

    /**
     * 新增数据
     *
     * @param List<logging> 实例对象
     * @return 影响行数
     */
    Integer saveLogging(@Param("list") List<Logging> list);

    /**
     * 修改数据
     *
     * @param logging 实例对象
     * @return 影响行数
     */
    Integer updateLoggingByPrimaryKey(Logging logging);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteLoggingByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteLoggingByPrimaryKeys(@Param("list") List<String> list);

}