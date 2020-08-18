package com.warehouse.warehouse.service.app.goods;

import com.warehouse.warehouse.pojo.dto.goods.GoodCategoryDTO;

import java.util.List;

/**
 * 商品类目(GoodCategory)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface GoodCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodCategoryDTO describeById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<GoodCategoryDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param goodCategoryDTO 实例对象
     * @return 对象列表
     */
    List<GoodCategoryDTO> describeAll(GoodCategoryDTO goodCategoryDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param goodCategoryDTO 实例对象
     * @return 对象列表
     */
    List<GoodCategoryDTO> describeFuzzy(GoodCategoryDTO goodCategoryDTO);
    
    
    /**
     * 新增数据
     *
     * @param goodCategoryDTO 实例对象
     * @return 实例对象
     */
    GoodCategoryDTO insertGoodCategory(GoodCategoryDTO goodCategoryDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<GoodCategoryDTO> saveGoodCategory(List<GoodCategoryDTO> list);

    /**
     * 修改数据
     *
     * @param goodCategoryDTO 实例对象
     * @return 实例对象
     */
    GoodCategoryDTO updateGoodCategoryByPrimaryKey(GoodCategoryDTO goodCategoryDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteGoodCategoryByPrimaryKey(Long id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteGoodCategoryByPrimaryKeys(List<Long> list);

}