package com.warehouse.warehouse.dao.goods;

import com.warehouse.warehouse.pojo.po.goods.Good;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Good)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-11 00:05:51
 */

public interface GoodMapper extends Mapper<Good> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Good describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Good> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param good 实例对象
     * @return 对象列表
     */
    List<Good> describeAll(Good good);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param good 实例对象
     * @return 对象列表
     */
    List<Good> describeFuzzy(Good good);

    /**
     * 新增数据
     *
     * @param good 实例对象
     * @return 影响行数
     */
    Integer insertGood(Good good);

    /**
     * 新增数据
     *
     * @param List<good> 实例对象
     * @return 影响行数
     */
    Integer saveGood(@Param("list") List<Good> list);

    /**
     * 修改数据
     *
     * @param good 实例对象
     * @return 影响行数
     */
    Integer updateGoodByPrimaryKey(Good good);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteGoodByPrimaryKey(@Param("id") String id,@Param("companyId") String companyId);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteGoodByPrimaryKeys(@Param("list") List<String> list,@Param("companyId") String companyId);


    /**
     * 查询所有good
     *
     */
    List<Good> describesAllGood();

    List<Good> describesGoodByGoodCategoryIdList(@Param("goodCategoryIdList") List<Long> goodCategoryIdList);

    /*
    根据商品编码查询商品
     */
    Good describeByGoodCode(@Param("commodityBar") String commodityBar);
}