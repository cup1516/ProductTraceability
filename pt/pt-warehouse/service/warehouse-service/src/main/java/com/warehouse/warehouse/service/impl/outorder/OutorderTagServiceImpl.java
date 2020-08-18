package com.warehouse.warehouse.service.impl.outorder;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.outorder.OutorderTagMapper;
import com.warehouse.warehouse.pojo.dto.outorder.OutorderTagDTO;
import com.warehouse.warehouse.pojo.po.outorder.OutorderTag;
import com.warehouse.warehouse.service.app.outorder.OutorderTagService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * (OutorderTag)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("outorderTagService")
public class OutorderTagServiceImpl implements OutorderTagService {

    @Resource
    private OutorderTagMapper outorderTagMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OutorderTagDTO describeById(String id) {
        OutorderTagDTO dto = new OutorderTagDTO();
        OutorderTag outorderTag = this.outorderTagMapper.describeById(id);
        if (null != outorderTag) {
            BeanUtils.copyProperties(outorderTag, dto);
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
    public List<OutorderTagDTO> describeAllByLimit(long offset, long limit) {
        List<OutorderTag> list = this.outorderTagMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutorderTagDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param outorderTagDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OutorderTagDTO> describeAll(OutorderTagDTO outorderTagDTO) {
        OutorderTag outorderTag = new OutorderTag();
        BeanUtils.copyProperties(outorderTagDTO, outorderTag);
        List<OutorderTag> list = this.outorderTagMapper.describeAll(outorderTag);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutorderTagDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param outorderTagDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OutorderTagDTO> describeFuzzy(OutorderTagDTO outorderTagDTO) {
        OutorderTag outorderTag = new OutorderTag();
        BeanUtils.copyProperties(outorderTagDTO, outorderTag);
        List<OutorderTag> list = this.outorderTagMapper.describeFuzzy(outorderTag);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutorderTagDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param outorderTagDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OutorderTagDTO insertOutorderTag(OutorderTagDTO outorderTagDTO) {
        OutorderTag outorderTag = new OutorderTag();
        BeanUtils.copyProperties(outorderTagDTO, outorderTag);
        if (null == outorderTag.getId() || "".equals(outorderTag.getId())) {
            outorderTag.setId(UUID.randomUUID().toString());
        }
        this.outorderTagMapper.insertOutorderTag(outorderTag);
        return outorderTagDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<OutorderTagDTO> saveOutorderTag(List<OutorderTagDTO> list) {
        list.forEach(outorderTagDTO -> {
            if (null == outorderTagDTO.getId() || "".equals(outorderTagDTO.getId())) {
                outorderTagDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.outorderTagMapper.saveOutorderTag(JSON.parseArray(JSON.toJSONString(list), OutorderTag.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param outorderTagDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OutorderTagDTO updateOutorderTagByPrimaryKey(OutorderTagDTO outorderTagDTO) {
        OutorderTag outorderTag = new OutorderTag();
        BeanUtils.copyProperties(outorderTagDTO, outorderTag);
        this.outorderTagMapper.updateOutorderTagByPrimaryKey(outorderTag);
        return this.describeById(outorderTag.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteOutorderTagByPrimaryKey(String id) {
        return this.outorderTagMapper.deleteOutorderTagByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteOutorderTagByPrimaryKeys(List<String> list) {
        return this.outorderTagMapper.deleteOutorderTagByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(OutorderTagDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OutorderTag.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andLike("pid", "%" + dto.getPid() + "%");
            }
            if (null != dto.getOutorderNo() && !"".equals(dto.getOutorderNo())) {
                criteriaName.andLike("outorderNo", "%" + dto.getOutorderNo() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
            }
            if (null != dto.getProvince() && !"".equals(dto.getProvince())) {
                criteriaName.andLike("province", "%" + dto.getProvince() + "%");
            }
            if (null != dto.getCity() && !"".equals(dto.getCity())) {
                criteriaName.andLike("city", "%" + dto.getCity() + "%");
            }
            if (null != dto.getCounty() && !"".equals(dto.getCounty())) {
                criteriaName.andLike("county", "%" + dto.getCounty() + "%");
            }
            if (null != dto.getType() && !"".equals(dto.getType())) {
                criteriaName.andLike("type", "%" + dto.getType() + "%");
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
    private Example initExample(OutorderTagDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OutorderTag.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andEqualTo("pid", dto.getPid());
            }
            if (null != dto.getOutorderNo() && !"".equals(dto.getOutorderNo())) {
                criteriaName.andEqualTo("outorderNo", dto.getOutorderNo());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
            }
            if (null != dto.getProvince() && !"".equals(dto.getProvince())) {
                criteriaName.andEqualTo("province", dto.getProvince());
            }
            if (null != dto.getCity() && !"".equals(dto.getCity())) {
                criteriaName.andEqualTo("city", dto.getCity());
            }
            if (null != dto.getCounty() && !"".equals(dto.getCounty())) {
                criteriaName.andEqualTo("county", dto.getCounty());
            }
            if (null != dto.getType() && !"".equals(dto.getType())) {
                criteriaName.andEqualTo("type", dto.getType());
            }
        }
        return example;
    }
    
}