package com.warehouse.warehouse.service.impl.goods;

import com.alibaba.fastjson.JSON;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.goods.GoodCategoryMapper;
import com.warehouse.warehouse.dao.goods.GoodMapper;
import com.warehouse.warehouse.pojo.dto.goods.GoodCaseCadeDTO;
import com.warehouse.warehouse.pojo.dto.goods.GoodCategoryAndGoodDTO;
import com.warehouse.warehouse.pojo.dto.goods.GoodCategoryDTO;
import com.warehouse.warehouse.pojo.dto.goods.GoodDTO;
import com.warehouse.warehouse.pojo.po.goods.Good;
import com.warehouse.warehouse.pojo.po.goods.GoodCategory;
import com.warehouse.warehouse.service.app.goods.GoodService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * (Good)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 00:05:51
 */
@Service("goodService")
public class GoodServiceImpl implements GoodService {

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private GoodCategoryMapper goodCategoryMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GoodDTO describeById(String id) {
        GoodDTO dto = new GoodDTO();
        Good good = this.goodMapper.describeById(id);
        if (null != good) {
            BeanUtils.copyProperties(good, dto);
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
    public List<GoodDTO> describeAllByLimit(long offset, long limit) {
        List<Good> list = this.goodMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), GoodDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param goodDTO 对象
     * @return 对象列表
     */
    @Override
    public List<GoodDTO> describeAll(GoodDTO goodDTO) {
        Good good = new Good();
        BeanUtils.copyProperties(goodDTO, good);
        List<Good> list = this.goodMapper.describeAll(good);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), GoodDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param goodDTO 对象
     * @return 对象列表
     */
    @Override
    public List<GoodDTO> describeFuzzy(GoodDTO goodDTO) {
        Good good = new Good();
        BeanUtils.copyProperties(goodDTO, good);
        List<Good> list = this.goodMapper.describeFuzzy(good);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), GoodDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param goodDTO 实例对象
     * @return 实例对象
     */

    @Override
    public GoodDTO insertGood(GoodDTO goodDTO) {
        Good good = new Good();
        BeanUtils.copyProperties(goodDTO, good);
        good.setStatus(0);
        if (null == good.getId() || "".equals(good.getId())) {
            good.setId(UUID.randomUUID().toString());
        }
        if (null == goodDTO.getCreateDate()){
            good.setCreateDate(new Date());
        }
        String goodsType = goodDTO.getGoodType();
        GoodCategory goodCategory = new GoodCategory();
        goodCategory.setName(goodsType);
        List<GoodCategory> goodCategories = goodCategoryMapper.describeFuzzy(goodCategory);
        if(goodCategories.size()==0){
           goodCategory.setParentId(1L);
           goodCategory.setCompanyId(SecurityUtils.getCompanyId());
           goodCategoryMapper.insert(goodCategory);
          good.setGoodCategoryId(goodCategoryMapper.describeFuzzy(goodCategory).get(0).getId().toString());
        }

        this.goodMapper.insertGood(good);
        return goodDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<GoodDTO> saveGood(List<GoodDTO> list) {
        list.forEach(goodDTO -> {
            if (null == goodDTO.getId() || "".equals(goodDTO.getId())) {
                goodDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.goodMapper.saveGood(JSON.parseArray(JSON.toJSONString(list), Good.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param goodDTO 实例对象
     * @return 实例对象
     */
    @Override
    public GoodDTO updateGoodByPrimaryKey(GoodDTO goodDTO) {
        Good good = new Good();
        BeanUtils.copyProperties(goodDTO, good);
         good.setModifiedDate(new Date());
        if (null != goodDTO.getCreateUserName()){
            good.setModifiedUserName(goodDTO.getCreateUserName());
            good.setModifiedUserId(goodDTO.getCreateUserId());
        }
        this.goodMapper.updateGoodByPrimaryKey(good);
        return this.describeById(good.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteGoodByPrimaryKey(String id ,String companyId) {
        return this.goodMapper.deleteGoodByPrimaryKey(id,companyId) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteGoodByPrimaryKeys(List<String> list,String companyId) {
        return this.goodMapper.deleteGoodByPrimaryKeys(list,companyId) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(GoodDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Good.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getGoodNo() && !"".equals(dto.getGoodNo())) {
                criteriaName.andLike("goodNo", "%" + dto.getGoodNo() + "%");
            }
            if (null != dto.getGoodName() && !"".equals(dto.getGoodName())) {
                criteriaName.andLike("goodName", "%" + dto.getGoodName() + "%");
            }
            if (null != dto.getGoodType()) {
                criteriaName.andLike("goodType", "%" + dto.getGoodType() + "%");
            }
            if (null != dto.getUnit()) {
                criteriaName.andLike("unit", "%" + dto.getUnit() + "%");
            }
            if (null != dto.getLength()) {
                criteriaName.andLike("length", "%" + dto.getLength() + "%");
            }
            if (null != dto.getWidth()) {
                criteriaName.andLike("width", "%" + dto.getWidth() + "%");
            }
            if (null != dto.getHeigth()) {
                criteriaName.andLike("heigth", "%" + dto.getHeigth() + "%");
            }
            if (null != dto.getWeigth()) {
                criteriaName.andLike("weigth", "%" + dto.getWeigth() + "%");
            }
            if (null != dto.getStatus()) {
                criteriaName.andLike("status", "%" + dto.getStatus() + "%");
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
            }
            if (null != dto.getCreateUserId() && !"".equals(dto.getCreateUserId())) {
                criteriaName.andLike("createUserId", "%" + dto.getCreateUserId() + "%");
            }
            if (null != dto.getCreateUserName() && !"".equals(dto.getCreateUserName())) {
                criteriaName.andLike("createUserName", "%" + dto.getCreateUserName() + "%");
            }
            if (null != dto.getCreateDate()) {
                criteriaName.andLike("createDate", "%" + dto.getCreateDate() + "%");
            }
            if (null != dto.getModifiedUserId() && !"".equals(dto.getModifiedUserId())) {
                criteriaName.andLike("modifiedUserId", "%" + dto.getModifiedUserId() + "%");
            }
            if (null != dto.getModifiedUserName() && !"".equals(dto.getModifiedUserName())) {
                criteriaName.andLike("modifiedUserName", "%" + dto.getModifiedUserName() + "%");
            }
            if (null != dto.getModifiedDate()) {
                criteriaName.andLike("modifiedDate", "%" + dto.getModifiedDate() + "%");
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
    private Example initExample(GoodDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Good.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getGoodNo() && !"".equals(dto.getGoodNo())) {
                criteriaName.andEqualTo("goodNo", dto.getGoodNo());
            }
            if (null != dto.getGoodName() && !"".equals(dto.getGoodName())) {
                criteriaName.andEqualTo("goodName", dto.getGoodName());
            }
            if (null != dto.getGoodType()) {
                criteriaName.andEqualTo("goodType", dto.getGoodType());
            }
            if (null != dto.getUnit()) {
                criteriaName.andEqualTo("unit", dto.getUnit());
            }
            if (null != dto.getLength()) {
                criteriaName.andEqualTo("length", dto.getLength());
            }
            if (null != dto.getWidth()) {
                criteriaName.andEqualTo("width", dto.getWidth());
            }
            if (null != dto.getHeigth()) {
                criteriaName.andEqualTo("heigth", dto.getHeigth());
            }
            if (null != dto.getWeigth()) {
                criteriaName.andEqualTo("weigth", dto.getWeigth());
            }
            if (null != dto.getStatus()) {
                criteriaName.andEqualTo("status", dto.getStatus());
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
            }
            if (null != dto.getCreateUserId() && !"".equals(dto.getCreateUserId())) {
                criteriaName.andEqualTo("createUserId", dto.getCreateUserId());
            }
            if (null != dto.getCreateUserName() && !"".equals(dto.getCreateUserName())) {
                criteriaName.andEqualTo("createUserName", dto.getCreateUserName());
            }
            if (null != dto.getCreateDate()) {
                criteriaName.andEqualTo("createDate", dto.getCreateDate());
            }
            if (null != dto.getModifiedUserId() && !"".equals(dto.getModifiedUserId())) {
                criteriaName.andEqualTo("modifiedUserId", dto.getModifiedUserId());
            }
            if (null != dto.getModifiedUserName() && !"".equals(dto.getModifiedUserName())) {
                criteriaName.andEqualTo("modifiedUserName", dto.getModifiedUserName());
            }
            if (null != dto.getModifiedDate()) {
                criteriaName.andEqualTo("modifiedDate", dto.getModifiedDate());
            }
        }
        return example;
    }

    @Override
    public List<GoodCategoryAndGoodDTO> describeGoodCategoryAndGood() {
        // 1、 获取所有商品分类目录
        List<GoodCategory> list = goodCategoryMapper.describeFuzzy(null);
        // 2、获取所有的商品id
        List<Long> goodCategoryIdList = new ArrayList<>();
        list.forEach(goodCategory->{
            goodCategoryIdList.add(goodCategory.getId());
        });


        List<Good> goods = new ArrayList<>();
        if (null != goodCategoryIdList && goodCategoryIdList.size() > 0){
            goods = goodMapper.describesGoodByGoodCategoryIdList(goodCategoryIdList);

        }
        // 4、封装数据
        List<GoodCategoryAndGoodDTO> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GoodCategory goodCategory = list.get(i);
            GoodCategoryAndGoodDTO goodCategoryAndGoodDTO = new GoodCategoryAndGoodDTO();
            goodCategoryAndGoodDTO.setLabel(goodCategory.getName());
            goodCategoryAndGoodDTO.setValue(goodCategory.getId());
            goodCategoryAndGoodDTO.setCode(goodCategory.getId());
            GoodCategoryDTO goodCategoryDTO = new GoodCategoryDTO();
            BeanUtils.copyProperties(goodCategory,goodCategoryDTO);
            goodCategoryAndGoodDTO.setGoodCategoryDTO(goodCategoryDTO);
            //设置子数据，也就是货位数据
            List<GoodCaseCadeDTO> children = new ArrayList<>();
            goods.forEach(good -> {
                // 如果货位的货架id等于此货架id
                if (good.getGoodCategoryId().equals(goodCategory.getId().toString())){
                    GoodCaseCadeDTO goodCaseCadeDTO = new GoodCaseCadeDTO();
                    goodCaseCadeDTO.setLabel(good.getGoodName());
                    goodCaseCadeDTO.setValue(good.getId());
                    goodCaseCadeDTO.setCode(good.getGoodNo());
                    GoodDTO goodDTO = new GoodDTO();
                    BeanUtils.copyProperties(good,goodDTO);
                    goodCaseCadeDTO.setGoodDTO(goodDTO);
                    children.add(goodCaseCadeDTO);
                }
            });
            goodCategoryAndGoodDTO.setChildren(children);
            returnList.add(goodCategoryAndGoodDTO);
        }
        return returnList;
    }


    @Override
    public Good describeByGoodNo(String goodNo) {
        Good good = new Good();
        good.setGoodNo(goodNo);
        List <Good> goodList =  goodMapper.describeFuzzy(good);
        good = goodList.get(0);
        return good;
    }
}