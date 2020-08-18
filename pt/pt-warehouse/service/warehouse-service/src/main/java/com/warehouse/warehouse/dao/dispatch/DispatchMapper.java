package com.warehouse.warehouse.dao.dispatch;

import com.warehouse.warehouse.pojo.po.dispatch.Dispatch;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 报损表(Dispatch)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-01 19:48:49
 */

public interface DispatchMapper extends Mapper<Dispatch> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dispatch describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Dispatch> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dispatch 实例对象
     * @return 对象列表
     */
    List<Dispatch> describeAll(Dispatch dispatch);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param dispatch 实例对象
     * @return 对象列表
     */
    List<Dispatch> describeFuzzy(Dispatch dispatch);

    /**
     * 新增数据
     *
     * @param dispatch 实例对象
     * @return 影响行数
     */
    Integer insertDispatch(Dispatch dispatch);

    /**
     * 新增数据
     *
     * @param List<dispatch> 实例对象
     * @return 影响行数
     */
    Integer saveDispatch(@Param("list") List<Dispatch> list);

    /**
     * 修改数据
     *
     * @param dispatch 实例对象
     * @return 影响行数
     */
    Integer updateDispatchByPrimaryKey(Dispatch dispatch);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteDispatchByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteDispatchByPrimaryKeys(@Param("list") List<String> list);

    /**
     * @title: describeNotCommit
     * @description: 查询未提交的报损单数据
     * @params: [dispatch]
     * @return: java.util.List<com.dao.entity.Otherout>
     * @createTime: 2020/5/1 20:56
     * @version: 1.0
     * @author: wanpp
     */
    List<Dispatch> describeNotCommit(Dispatch dispatch);

    /**
     * @title: describeMineFuzzy
     * @description: 条件模糊查询当前用户提交报损单数据
     * @params: [dispatch, userId]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @createTime: 2020/5/2 9:08
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String, Object>> describeMineFuzzy(Dispatch dispatch, String userId);

    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批报损单数据
     * @params: [ dispatch, userId ]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @createTime: 2020/5/2 11:41
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String, Object>> describeCommissionFuzzy(Dispatch dispatch, String userId);

    /**
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的报损单数据
     * @params: [ dispatch, userId ]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @createTime: 2020/5/2 12:56
     * @version: 1.0
     * @author: wanpp
     */
    List<Map<String, Object>> describeAlreadDoneFuzzy(Dispatch dispatch, String userId);
}