package com.warehouse.warehouse.service.impl.otherout;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.otherout.OtheroutDetailMapper;
import com.warehouse.warehouse.dao.otherout.OtheroutMapper;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.pojo.dto.otherout.InsertOtheroutDetailByStockIdDTO;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutDetailDTO;
import com.warehouse.warehouse.pojo.po.otherout.Otherout;
import com.warehouse.warehouse.pojo.po.otherout.OtheroutDetail;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.service.app.otherout.OtheroutDetailService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 出库子表(OtheroutDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("otheroutDetailService")
public class OtheroutDetailServiceImpl implements OtheroutDetailService {

    private static final Logger log = LogManager.getLogger(OtheroutDetailServiceImpl.class);


    @Resource
    private OtheroutDetailMapper otheroutDetailMapper;

    @Resource
    private OtheroutMapper otheroutMapper;

    @Resource
    private StockMapper stockMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OtheroutDetailDTO describeById(String id) {
        OtheroutDetailDTO dto = new OtheroutDetailDTO();
        OtheroutDetail otheroutDetail = this.otheroutDetailMapper.describeById(id);
        if (null != otheroutDetail) {
            BeanUtils.copyProperties(otheroutDetail, dto);
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
    public List<OtheroutDetailDTO> describeAllByLimit(long offset, long limit) {
        List<OtheroutDetail> list = this.otheroutDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param otheroutDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtheroutDetailDTO> describeAll(OtheroutDetailDTO otheroutDetailDTO) {
        OtheroutDetail otheroutDetail = new OtheroutDetail();
        BeanUtils.copyProperties(otheroutDetailDTO, otheroutDetail);
        List<OtheroutDetail> list = this.otheroutDetailMapper.describeAll(otheroutDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param otheroutDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtheroutDetailDTO> describeFuzzy(OtheroutDetailDTO otheroutDetailDTO) {
        OtheroutDetail otheroutDetail = new OtheroutDetail();
        BeanUtils.copyProperties(otheroutDetailDTO, otheroutDetail);
        List<OtheroutDetail> list = this.otheroutDetailMapper.describeFuzzy(otheroutDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param otheroutDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OtheroutDetailDTO insertOtheroutDetail(OtheroutDetailDTO otheroutDetailDTO) {
        OtheroutDetail otheroutDetail = new OtheroutDetail();
        BeanUtils.copyProperties(otheroutDetailDTO, otheroutDetail);
        if (null == otheroutDetail.getId() || "".equals(otheroutDetail.getId())) {
            otheroutDetail.setId(UUID.randomUUID().toString());
        }
        if (null == otheroutDetail.getCreateTime()) {
            otheroutDetail.setCreateTime(new Date());
        }
        if (null == otheroutDetail.getUpdateTime()) {
            otheroutDetail.setUpdateTime(new Date());
        }
        this.otheroutDetailMapper.insertOtheroutDetail(otheroutDetail);
        return otheroutDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<OtheroutDetailDTO> saveOtheroutDetail(List<OtheroutDetailDTO> list) {
        list.forEach(otheroutDetailDTO -> {
            if (null == otheroutDetailDTO.getId() || "".equals(otheroutDetailDTO.getId())) {
                otheroutDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.otheroutDetailMapper.saveOtheroutDetail(JSON.parseArray(JSON.toJSONString(list), OtheroutDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param otheroutDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OtheroutDetailDTO updateOtheroutDetailByPrimaryKey(OtheroutDetailDTO otheroutDetailDTO) {
        OtheroutDetail otheroutDetail = new OtheroutDetail();
        BeanUtils.copyProperties(otheroutDetailDTO, otheroutDetail);
        this.otheroutDetailMapper.updateOtheroutDetailByPrimaryKey(otheroutDetail);
        return this.describeById(otheroutDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteOtheroutDetailByPrimaryKey(String id) {
        return this.otheroutDetailMapper.deleteOtheroutDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteOtheroutDetailByPrimaryKeys(List<String> list) {
        return this.otheroutDetailMapper.deleteOtheroutDetailByPrimaryKeys(list) == list.size();
    }

    /**
     * @title: insertByStockId
     * @description: 通过仓储表编号新增数据
     * @params: [ dto ]
     * @return: java.lang.Integer
     * @createTime: 2020/4/18 15:03
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Integer insertByStockId(InsertOtheroutDetailByStockIdDTO dto) {
        Integer returnData = 0;
        try{
            Stock stock = stockMapper.selectByPrimaryKey(dto.getStockId());
            OtheroutDetail detail = new OtheroutDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setPid(dto.getPid());
            detail.setLocatorId(stock.getLocatorId());
            detail.setLocatorCode(stock.getLocatorCode());
            detail.setLocatorName(stock.getLocatorName());
            detail.setCommodityId(stock.getCommodityId());
            detail.setCommodityBar(stock.getCommodityBar());
            detail.setCommodityName(stock.getCommodityName());
            detail.setQty(dto.getNum());
            detail.setCreateId(dto.getCreateId());
            detail.setCreator(dto.getCreator());
            detail.setCreateTime(new Date());
            detail.setUpdateId(dto.getUpdateId());
            detail.setUpdator(dto.getUpdator());
            detail.setUpdateTime(new Date());
            detail.setStockId(dto.getStockId());
            detail.setBatch(stock.getBatch());
            detail.setOutcompany(dto.getOutcompany());
            detail.setCompanyId(dto.getCompanyId());

            returnData = otheroutDetailMapper.insertOtheroutDetail(detail);
        }catch (Exception e){
            log.error("通过仓储表编号新增数据异常，异常为：{}",JSON.toJSONString(e));
        }
        return returnData;
    }

    /**
     * @title: updateByStockId
     * @description: 通过仓储表编号更新数据
     * @params: [ dto ]
     * @return: java.lang.Integer
     * @createTime: 2020/4/18 16:43
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Integer updateByStockId(InsertOtheroutDetailByStockIdDTO dto) {
        Integer returnData = 0;
        try{
            Stock stock = stockMapper.selectByPrimaryKey(dto.getStockId());
            OtheroutDetail detail = new OtheroutDetail();
            detail.setPid(dto.getPid());
            detail.setLocatorId(stock.getLocatorId());
            detail.setLocatorCode(stock.getLocatorCode());
            detail.setLocatorName(stock.getLocatorName());
            detail.setCommodityId(stock.getCommodityId());
            detail.setCommodityBar(stock.getCommodityBar());
            detail.setCommodityName(stock.getCommodityName());
            detail.setQty(dto.getNum());
            detail.setCreateId(dto.getCreateId());
            detail.setCreator(dto.getCreator());
            detail.setCreateTime(new Date());
            detail.setUpdateId(dto.getUpdateId());
            detail.setUpdator(dto.getUpdator());
            detail.setUpdateTime(new Date());
            detail.setStockId(dto.getStockId());
            detail.setId(dto.getDetailId());
            returnData = otheroutDetailMapper.updateOtheroutDetailByPrimaryKey(detail);
        }catch (Exception e){
            log.error("通过仓储表编号更新数据异常，异常为：{}",JSON.toJSONString(e));
        }
        return returnData;
    }

    /**
     * @title: deleteAllByPid
     * @description: 通过出库编号清空所有出库详情数据
     * @params: [ orderNo ]
     * @return: java.lang.Integer
     * @createTime: 2020/4/18 17:00
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Integer deleteAllByPid(String orderNo) {
        // 数据情况前，需要处理入库单仓库信息
        Otherout otherout = new Otherout();
        otherout.setOtheroutNo(orderNo);
        Otherout otheroutResult = otheroutMapper.selectOne(otherout);
        otheroutResult.setWarehouseId("");
        otheroutMapper.updateOtheroutByPrimaryKey(otheroutResult);
        OtheroutDetail otheroutDetail = new OtheroutDetail();
        otheroutDetail.setPid(orderNo);
        int delete = otheroutDetailMapper.delete(otheroutDetail);
        return delete;
    }

    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(OtheroutDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OtheroutDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andLike("pid", "%" + dto.getPid() + "%");
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andLike("locatorId", "%" + dto.getLocatorId() + "%");
            }
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andLike("locatorCode", "%" + dto.getLocatorCode() + "%");
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andLike("locatorName", "%" + dto.getLocatorName() + "%");
            }
            if (null != dto.getCommodityId() && !"".equals(dto.getCommodityId())) {
                criteriaName.andLike("commodityId", "%" + dto.getCommodityId() + "%");
            }
            if (null != dto.getCommodityBar() && !"".equals(dto.getCommodityBar())) {
                criteriaName.andLike("commodityBar", "%" + dto.getCommodityBar() + "%");
            }
            if (null != dto.getCommodityName() && !"".equals(dto.getCommodityName())) {
                criteriaName.andLike("commodityName", "%" + dto.getCommodityName() + "%");
            }
            if (null != dto.getQty()) {
                criteriaName.andLike("qty", "%" + dto.getQty() + "%");
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
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
    private Example initExample(OtheroutDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OtheroutDetail.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andEqualTo("pid", dto.getPid());
            }
            if (null != dto.getLocatorId() && !"".equals(dto.getLocatorId())) {
                criteriaName.andEqualTo("locatorId", dto.getLocatorId());
            }
            if (null != dto.getLocatorCode() && !"".equals(dto.getLocatorCode())) {
                criteriaName.andEqualTo("locatorCode", dto.getLocatorCode());
            }
            if (null != dto.getLocatorName() && !"".equals(dto.getLocatorName())) {
                criteriaName.andEqualTo("locatorName", dto.getLocatorName());
            }
            if (null != dto.getCommodityId() && !"".equals(dto.getCommodityId())) {
                criteriaName.andEqualTo("commodityId", dto.getCommodityId());
            }
            if (null != dto.getCommodityBar() && !"".equals(dto.getCommodityBar())) {
                criteriaName.andEqualTo("commodityBar", dto.getCommodityBar());
            }
            if (null != dto.getCommodityName() && !"".equals(dto.getCommodityName())) {
                criteriaName.andEqualTo("commodityName", dto.getCommodityName());
            }
            if (null != dto.getQty()) {
                criteriaName.andEqualTo("qty", dto.getQty());
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
            }
        }
        return example;
    }
    
}