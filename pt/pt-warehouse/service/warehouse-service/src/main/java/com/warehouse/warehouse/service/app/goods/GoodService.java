package com.warehouse.warehouse.service.app.goods;

import com.warehouse.warehouse.pojo.dto.goods.GoodCategoryAndGoodDTO;
import com.warehouse.warehouse.pojo.dto.goods.GoodDTO;
import com.warehouse.warehouse.pojo.po.goods.Good;

import java.util.List;

/**
 * (Good)表服务接口
 *
 * @author makejava
 * @since 2020-04-11 00:05:51
 */
public interface GoodService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<GoodDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param goodDTO 实例对象
     * @return 对象列表
     */
    List<GoodDTO> describeAll(GoodDTO goodDTO);

    /**
     * 条件模糊查询所有数据
     *
     * @param goodDTO 实例对象
     * @return 对象列表
     */
    List<GoodDTO> describeFuzzy(GoodDTO goodDTO);


    /**
     * 新增数据
     *
     * @param goodDTO 实例对象
     * @return 实例对象
     */
    GoodDTO insertGood(GoodDTO goodDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<GoodDTO> saveGood(List<GoodDTO> list);

    /**
     * 修改数据
     *
     * @param goodDTO 实例对象
     * @return 实例对象
     */
    GoodDTO updateGoodByPrimaryKey(GoodDTO goodDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteGoodByPrimaryKey(String id, String companyId);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteGoodByPrimaryKeys(List<String> list, String companyId);

    public List<GoodCategoryAndGoodDTO> describeGoodCategoryAndGood();


    /**
     * 通过goodNo查询单条数据
     *
     * @param goodNo no
     * @return 实例对象
     */
    Good describeByGoodNo(String goodNo);

}
