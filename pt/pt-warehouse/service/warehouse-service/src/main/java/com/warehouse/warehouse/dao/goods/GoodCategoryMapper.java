package com.warehouse.warehouse.dao.goods;

import com.warehouse.warehouse.pojo.po.goods.GoodCategory;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 商品类目(GoodCategory)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface GoodCategoryMapper extends Mapper<GoodCategory> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodCategory describeById(@Param("id") Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<GoodCategory> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param goodCategory 实例对象
     * @return 对象列表
     */
    List<GoodCategory> describeAll(GoodCategory goodCategory);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param goodCategory 实例对象
     * @return 对象列表
     */
    List<GoodCategory> describeFuzzy(GoodCategory goodCategory);

    /**
     * 新增数据
     *
     * @param goodCategory 实例对象
     * @return 影响行数
     */
    Integer insertGoodCategory(GoodCategory goodCategory);

    /**
     * 新增数据
     *
     * @param List<goodCategory> 实例对象
     * @return 影响行数
     */
    Integer saveGoodCategory(@Param("list") List<GoodCategory> list);

    /**
     * 修改数据
     *
     * @param goodCategory 实例对象
     * @return 影响行数
     */
    Integer updateGoodCategoryByPrimaryKey(GoodCategory goodCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteGoodCategoryByPrimaryKey(@Param("id") Long id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteGoodCategoryByPrimaryKeys(@Param("list") List<Long> list);

    /**
     * 根基商品类型查询所有商品

     */
    List<GoodCategory>  describesGoodCategoryByGoodIdList(@Param("goodIdList") List<Long> goodIdList);
}