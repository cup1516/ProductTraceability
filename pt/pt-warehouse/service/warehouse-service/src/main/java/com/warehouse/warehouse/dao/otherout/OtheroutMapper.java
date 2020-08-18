package com.warehouse.warehouse.dao.otherout;

import com.warehouse.warehouse.pojo.po.otherout.Otherout;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 出库表(Otherout)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface OtheroutMapper extends Mapper<Otherout> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Otherout describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Otherout> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param otherout 实例对象
     * @return 对象列表
     */
    List<Otherout> describeAll(Otherout otherout);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param otherout 实例对象
     * @return 对象列表
     */
    List<Otherout> describeFuzzy(Otherout otherout);

    /**
     * 查询未提交的出库单数据
     *
     * @param otherout 实例对象
     * @return 对象列表
     */
    List<Otherout> describeNotCommit(Otherout otherout);

    /**
     * 新增数据
     *
     * @param otherout 实例对象
     * @return 影响行数
     */
    Integer insertOtherout(Otherout otherout);

    /**
     * 新增数据
     *
     * @param List<otherout> 实例对象
     * @return 影响行数
     */
    Integer saveOtherout(@Param("list") List<Otherout> list);

    /**
     * 修改数据
     *
     * @param otherout 实例对象
     * @return 影响行数
     */
    Integer updateOtheroutByPrimaryKey(Otherout otherout);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteOtheroutByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteOtheroutByPrimaryKeys(@Param("list") List<String> list);

    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批出库单数据
     * @params: [ otherout,userId ]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @createTime: 2020/4/19 12:33
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String,Object>> describeCommissionFuzzy(@Param("otherout") Otherout otherout, @Param("userId") String userId);

    /**
     * @title: describeMineFuzzy
     * @description: 条件分页模糊查询当前用户提交出库单数据
     * @params: [otherout, userId]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @createTime: 2020/4/19 17:43
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String, Object>> describeMineFuzzy(Otherout otherout, String userId);

    /**
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的出库单数据
     * @params: [otherout, userId]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @createTime: 2020/4/20 20:31
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String, Object>> describeAlreadDoneFuzzy(Otherout otherout, String userId);
}