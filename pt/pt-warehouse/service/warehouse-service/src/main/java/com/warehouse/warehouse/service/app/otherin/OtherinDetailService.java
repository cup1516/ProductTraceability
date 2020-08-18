package com.warehouse.warehouse.service.app.otherin;

import com.warehouse.warehouse.pojo.dto.otherin.OtherinDetailDTO;

import java.util.List;

/**
 * 入库子表(OtherinDetail)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface OtherinDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OtherinDetailDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OtherinDetailDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param otherinDetailDTO 实例对象
     * @return 对象列表
     */
    List<OtherinDetailDTO> describeAll(OtherinDetailDTO otherinDetailDTO);
    
    /**
     * 条件模糊查询所有数据
     *
     * @param otherinDetailDTO 实例对象
     * @return 对象列表
     */
    List<OtherinDetailDTO> describeFuzzy(OtherinDetailDTO otherinDetailDTO);
    
    
    /**
     * 新增数据
     *
     * @param otherinDetailDTO 实例对象
     * @return 实例对象
     */
    OtherinDetailDTO insertOtherinDetail(OtherinDetailDTO otherinDetailDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<OtherinDetailDTO> saveOtherinDetail(List<OtherinDetailDTO> list);

    /**
     * 修改数据
     *
     * @param otherinDetailDTO 实例对象
     * @return 实例对象
     */
    OtherinDetailDTO updateOtherinDetailByPrimaryKey(OtherinDetailDTO otherinDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteOtherinDetailByPrimaryKey(String id);
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteOtherinDetailByPrimaryKeys(List<String> list);

    Integer insertOtherinDetailbyLocatorId(OtherinDetailDTO dto);

}