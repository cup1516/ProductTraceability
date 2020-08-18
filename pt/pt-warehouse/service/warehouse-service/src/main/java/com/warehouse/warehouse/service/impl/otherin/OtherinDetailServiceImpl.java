package com.warehouse.warehouse.service.impl.otherin;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.goods.GoodMapper;
import com.warehouse.warehouse.dao.otherin.OtherinDetailMapper;
import com.warehouse.warehouse.dao.otherin.OtherinMapper;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.ShelvesMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorMapper;
import com.warehouse.warehouse.pojo.dto.otherin.OtherinDetailDTO;
import com.warehouse.warehouse.pojo.po.goods.Good;
import com.warehouse.warehouse.pojo.po.otherin.OtherinDetail;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.Locator;
import com.warehouse.warehouse.service.app.otherin.OtherinDetailService ;
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
 * 入库子表(OtherinDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("otherinDetailService")
public class OtherinDetailServiceImpl implements OtherinDetailService {

    @Resource
    private OtherinDetailMapper otherinDetailMapper;
    @Resource
    private StockMapper stockMapper;
   @Resource
    private LocatorMapper locatorMapper;
    @Resource
    private GoodMapper goodMapper;
    @Resource
    private ShelvesMapper shelvesMapper;
    @Resource
    private OtherinMapper otherinMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OtherinDetailDTO describeById(String id) {
        OtherinDetailDTO dto = new OtherinDetailDTO();
        OtherinDetail otherinDetail = this.otherinDetailMapper.describeById(id);
        if (null != otherinDetail) {
            BeanUtils.copyProperties(otherinDetail, dto);
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
    public List<OtherinDetailDTO> describeAllByLimit(long offset, long limit) {
        List<OtherinDetail> list = this.otherinDetailMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param otherinDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtherinDetailDTO> describeAll(OtherinDetailDTO otherinDetailDTO) {
        OtherinDetail otherinDetail = new OtherinDetail();
        BeanUtils.copyProperties(otherinDetailDTO, otherinDetail);
        List<OtherinDetail> list = this.otherinDetailMapper.describeAll(otherinDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param otherinDetailDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtherinDetailDTO> describeFuzzy(OtherinDetailDTO otherinDetailDTO) {
        OtherinDetail otherinDetail = new OtherinDetail();
        otherinDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        BeanUtils.copyProperties(otherinDetailDTO, otherinDetail);
        List<OtherinDetail> list = this.otherinDetailMapper.describeFuzzy(otherinDetail);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinDetailDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param otherinDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OtherinDetailDTO insertOtherinDetail(OtherinDetailDTO otherinDetailDTO) {
        OtherinDetail otherinDetail = new OtherinDetail();

        if (null == otherinDetailDTO.getId() || "".equals(otherinDetailDTO.getId())) {
            otherinDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
            otherinDetailDTO.setId(UUID.randomUUID().toString());
            otherinDetail.setIncompany(otherinDetailDTO.getIncompany());
            BeanUtils.copyProperties(otherinDetailDTO, otherinDetail);
        }
        this.otherinDetailMapper.insertOtherinDetail(otherinDetail);
        return otherinDetailDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<OtherinDetailDTO> saveOtherinDetail(List<OtherinDetailDTO> list) {
        list.forEach(otherinDetailDTO -> {
            if (null == otherinDetailDTO.getId() || "".equals(otherinDetailDTO.getId())) {
                otherinDetailDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.otherinDetailMapper.saveOtherinDetail(JSON.parseArray(JSON.toJSONString(list), OtherinDetail.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param otherinDetailDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OtherinDetailDTO updateOtherinDetailByPrimaryKey(OtherinDetailDTO otherinDetailDTO) {
        OtherinDetail otherinDetail = new OtherinDetail();
        otherinDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        BeanUtils.copyProperties(otherinDetailDTO, otherinDetail);
        this.otherinDetailMapper.updateOtherinDetailByPrimaryKey(otherinDetail);
        return this.describeById(otherinDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteOtherinDetailByPrimaryKey(String id) {
        return this.otherinDetailMapper.deleteOtherinDetailByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteOtherinDetailByPrimaryKeys(List<String> list) {
        for(int i =0;i<list.size();i++){
            OtherinDetail otherinDetail = otherinDetailMapper.describeById(list.get(i));
            String  locatorId = otherinDetail.getLocatorId();
            Locator locator = locatorMapper.describeById(locatorId);
            locator.setState("0");
            locatorMapper.updateLocatorByPrimaryKey(locator);
        }
        return this.otherinDetailMapper.deleteOtherinDetailByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(OtherinDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OtherinDetail.class, dto.getOrderBy());
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
    private Example initExample(OtherinDetailDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OtherinDetail.class, dto.getOrderBy());
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

    /**
     * 根据otherin插入子表
     *
     * @param dto 数据对象
     * @return Integer
     */
    @Override
    public Integer insertOtherinDetailbyLocatorId(OtherinDetailDTO dto) {

        Integer returnData = 0;
        Integer total = 0;
        Double weight=0.0;
        Double allWeight=0.0;
        System.out.println(dto.getIncompany()+"ssssssssssssdddd");

            //根据dto的locatorId获取stockList集合
            List<Stock> stockList = stockMapper.findByLocatorId(dto.getLocatorId());
            Stock stock = new Stock();
            //如果不为空，得到stock
            if(stockList.size()>0){
                stock = stockList.get(0);
            }else{
                stock = null;
            }
            //根据dto的locatorId获取locator从而得到locator状态以及载重
            Locator locator = locatorMapper.describeById(dto.getLocatorId());
            List<String> shelvesIdList = new ArrayList<>();
            shelvesIdList.add(locator.getShelvesId());
            Good good = goodMapper.describeByGoodCode(dto.getCommodityBar());
            String state =locator.getState();
            int qty = dto.getQty();
            double goodsWeight = good.getWeigth();
            //判断是否超重，非预占用状态
            if(state.equals("0")) {
                if (stock == null) {
                    allWeight = qty * goodsWeight;
                } else {
                    allWeight = stock.getCqty() * goodsWeight + qty * goodsWeight;
                }
                //没有超重，生成detail
                if (allWeight < locator.getWeight()) {
                    OtherinDetail detail = new OtherinDetail();
                    detail.setId(UUID.randomUUID().toString());
                    detail.setPid(dto.getPid());
                    detail.setLocatorId(locator.getId());
                    detail.setLocatorCode(locator.getCode());
                    detail.setLocatorName(locator.getName());
                    detail.setCommodityId(good.getId());
                    detail.setCommodityBar(good.getGoodNo());
                    detail.setCommodityName(good.getGoodName());
                    detail.setQty(dto.getQty());
                    detail.setCreateId(dto.getCreateId());
                    detail.setCreator(dto.getCreator());
                    detail.setCreateTime(new Date());

                    detail.setIncompany(dto.getIncompany());
                    System.out.println(dto.getIncompany()+"inooooo");
                    detail.setUpdateId(dto.getUpdateId());
                    detail.setUpdator(dto.getUpdator());
                    detail.setCompanyId(SecurityUtils.getCompanyId());
                    detail.setUpdateTime(new Date());
                    detail.setBatch(dto.getBatch());
                    locator.setState("3");   //更新为预占用
                    locatorMapper.updateLocatorByPrimaryKey(locator);
                    returnData = otherinDetailMapper.insertOtherinDetail(detail);
                } else {
                    returnData = 999;   //代表超载
                }
            }
            //判断是否超重，预占用状态
            if (state.equals("3")) {
                OtherinDetail otherinDetail = new OtherinDetail();
                otherinDetail.setPid(dto.getPid());
                List<OtherinDetail> detailList = otherinDetailMapper.describeFuzzy(otherinDetail);
                for (int j = 0; j < detailList.size(); j++) {
                    if (detailList.get(j).getLocatorId().equals(dto.getLocatorId())) {
                        weight += detailList.get(j).getQty() * goodsWeight;
                    }        //预备入库状态该货位的所有重量
                }
                if (stock == null){
                    allWeight = weight;
                }else
                {
                    allWeight = weight + stock.getCqty() * goodsWeight;
                }

                if (allWeight < locator.getWeight()) {
                    OtherinDetail detail = new OtherinDetail();
                    detail.setId(UUID.randomUUID().toString());
                    detail.setPid(dto.getPid());
                    detail.setLocatorId(locator.getId());
                    detail.setLocatorCode(locator.getCode());
                    detail.setLocatorName(locator.getName());
                    detail.setIncompany(dto.getIncompany());
                    System.out.println(dto.getIncompany()+"inooooo");
                    detail.setCommodityId(good.getId());
                    detail.setCommodityBar(good.getGoodNo());
                    detail.setCommodityName(good.getGoodName());
                    detail.setQty(dto.getQty());
                    detail.setCreateId(dto.getCreateId());
                    detail.setCreator(dto.getCreator());
                    detail.setCreateTime(new Date());
                    detail.setUpdateId(dto.getUpdateId());
                    detail.setUpdator(dto.getUpdator());
                    detail.setBatch(dto.getBatch());
                    detail.setUpdateTime(new Date());
                    //detail.setStockId(dto.getStockId());
                    locator.setState("3");   //预占用
                    locatorMapper.updateLocatorByPrimaryKey(locator);
                    returnData = otherinDetailMapper.insertOtherinDetail(detail);

                } else {
                    returnData = 999;
                }

            }



        return returnData;
    }
}