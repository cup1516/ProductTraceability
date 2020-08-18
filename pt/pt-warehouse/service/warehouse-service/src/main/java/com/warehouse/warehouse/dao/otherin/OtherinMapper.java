package com.warehouse.warehouse.dao.otherin;

import com.warehouse.warehouse.pojo.po.otherin.Otherin;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 入库表(Otherin)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface OtherinMapper extends Mapper<Otherin> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Otherin describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Otherin> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param otherin 实例对象
     * @return 对象列表
     */
    List<Otherin> describeAll(Otherin otherin);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param otherin 实例对象
     * @return 对象列表
     */
    List<Otherin> describeFuzzy(Otherin otherin);

    /**
     * 查询未提交的入库单数据
     *
     * @param otherin 实例对象
     * @return 对象列表
     */
    List<Otherin> describeNotCommit(Otherin otherin);

    /**
     * 新增数据
     *
     * @param otherin 实例对象
     * @return 影响行数
     */
    Integer insertOtherin(Otherin otherin);

    /**
     * 新增数据
     *
     * @param List<otherin> 实例对象
     * @return 影响行数
     */
    Integer saveOtherin(@Param("list") List<Otherin> list);

    /**
     * 修改数据
     *
     * @param otherin 实例对象
     * @return 影响行数
     */
    Integer updateOtherinByPrimaryKey(Otherin otherin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteOtherinByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteOtherinByPrimaryKeys(@Param("list") List<String> list);

    List<Map<String, Object>> describeMineFuzzy(Otherin otherin, String userId);


    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批ru库单数据
     * @params: [ otherin,userId ]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @createTime: 2020/4/19 12:33
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String,Object>> describeCommissionFuzzy(@Param("otherin") Otherin otherin, @Param("userId") String userId);


    /**
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的出库单数据
     * @params: [otherout, userId]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @createTime: 2020/4/20 20:31
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String, Object>> describeAlreadDoneFuzzy(Otherin otherin, String userId);
}