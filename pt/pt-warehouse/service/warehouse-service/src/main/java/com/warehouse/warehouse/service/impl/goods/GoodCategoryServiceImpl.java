package com.warehouse.warehouse.service.impl.goods;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.goods.GoodCategoryMapper;
import com.warehouse.warehouse.pojo.dto.goods.GoodCategoryDTO;
import com.warehouse.warehouse.pojo.po.goods.GoodCategory;
import com.warehouse.warehouse.service.app.goods.GoodCategoryService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品类目(GoodCategory)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("goodCategoryService")
public class GoodCategoryServiceImpl implements GoodCategoryService {

    @Resource
    private GoodCategoryMapper goodCategoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GoodCategoryDTO describeById(Long id) {
        GoodCategoryDTO dto = new GoodCategoryDTO();
        GoodCategory goodCategory = this.goodCategoryMapper.describeById(id);
        if (null != goodCategory) {
            BeanUtils.copyProperties(goodCategory, dto);
        }
        return dto;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<GoodCategoryDTO> describeAllByLimit(long offset, long limit) {
        List<GoodCategory> list = this.goodCategoryMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), GoodCategoryDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param goodCategoryDTO 对象
     * @return 对象列表
     */
    @Override
    public List<GoodCategoryDTO> describeAll(GoodCategoryDTO goodCategoryDTO) {
        GoodCategory goodCategory = new GoodCategory();
        BeanUtils.copyProperties(goodCategoryDTO, goodCategory);
        List<GoodCategory> list = this.goodCategoryMapper.describeAll(goodCategory);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), GoodCategoryDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param goodCategoryDTO 对象
     * @return 对象列表
     */
    @Override
    public List<GoodCategoryDTO> describeFuzzy(GoodCategoryDTO goodCategoryDTO) {
        GoodCategory goodCategory = new GoodCategory();
        BeanUtils.copyProperties(goodCategoryDTO, goodCategory);
        List<GoodCategory> list = this.goodCategoryMapper.describeFuzzy(goodCategory);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), GoodCategoryDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param goodCategoryDTO 实例对象
     * @return 实例对象
     */
    @Override
    public GoodCategoryDTO insertGoodCategory(GoodCategoryDTO goodCategoryDTO) {
        GoodCategory goodCategory = new GoodCategory();
        BeanUtils.copyProperties(goodCategoryDTO, goodCategory);
        if (null == goodCategory.getId() || "".equals(goodCategory.getId())) {
            //goodCategory.setId(UUID.randomUUID().toString());
        }
        this.goodCategoryMapper.insertGoodCategory(goodCategory);
        return goodCategoryDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<GoodCategoryDTO> saveGoodCategory(List<GoodCategoryDTO> list) {
        list.forEach(goodCategoryDTO -> {
            if (null == goodCategoryDTO.getId() || "".equals(goodCategoryDTO.getId())) {
                //goodCategoryDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.goodCategoryMapper.saveGoodCategory(JSON.parseArray(JSON.toJSONString(list), GoodCategory.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param goodCategoryDTO 实例对象
     * @return 实例对象
     */
    @Override
    public GoodCategoryDTO updateGoodCategoryByPrimaryKey(GoodCategoryDTO goodCategoryDTO) {
        GoodCategory goodCategory = new GoodCategory();
        BeanUtils.copyProperties(goodCategoryDTO, goodCategory);
        this.goodCategoryMapper.updateGoodCategoryByPrimaryKey(goodCategory);
        return this.describeById(goodCategory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteGoodCategoryByPrimaryKey(Long id) {
        return this.goodCategoryMapper.deleteGoodCategoryByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteGoodCategoryByPrimaryKeys(List<Long> list) {
        return this.goodCategoryMapper.deleteGoodCategoryByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(GoodCategoryDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(GoodCategory.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId()) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getParentId()) {
                criteriaName.andLike("parentId", "%" + dto.getParentId() + "%");
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andLike("name", "%" + dto.getName() + "%");
            }
        }
        return example;
    }
    
    /**
     * 初始化 精确查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initExample(GoodCategoryDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(GoodCategory.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId()) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getParentId()) {
                criteriaName.andEqualTo("parentId", dto.getParentId());
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andEqualTo("name", dto.getName());
            }
        }
        return example;
    }
    
}