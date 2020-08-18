package com.warehouse.warehouse.service.impl.otherin;

import com.alibaba.fastjson.JSON;
import com.common.utils.InitOrderNo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.goods.GoodMapper;
import com.warehouse.warehouse.dao.otherin.OtherinDetailMapper;
import com.warehouse.warehouse.dao.otherin.OtherinMapper;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.ShelvesMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorMapper;
import com.warehouse.warehouse.pojo.dto.otherin.OtherinDTO;
import com.warehouse.warehouse.pojo.dto.otherin.OtherinWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.dto.stock.StockCaseCadeDTO;
import com.warehouse.warehouse.pojo.dto.stock.StockDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesAndStockDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesDTO;
import com.warehouse.warehouse.pojo.po.goods.Good;
import com.warehouse.warehouse.pojo.po.otherin.Otherin;
import com.warehouse.warehouse.pojo.po.otherin.OtherinDetail;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.Shelves;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.Locator;
import com.warehouse.warehouse.service.app.otherin.OtherinService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 入库表(Otherin)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("otherinService")
public class OtherinServiceImpl implements OtherinService {

    @Resource
    private OtherinMapper otherinMapper;
    @Resource
    private OtherinDetailMapper otherinDetailMapper;
    @Resource
    private StockMapper stockMapper;
    @Resource
    private ShelvesMapper shelvesMapper;
    @Resource
    private LocatorMapper locatorMapper;
    @Resource
    private GoodMapper goodMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OtherinDTO describeById(String id) {
        OtherinDTO dto = new OtherinDTO();
        Otherin otherin = this.otherinMapper.describeById(id);
        if (null != otherin) {
            BeanUtils.copyProperties(otherin, dto);
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
    public List<OtherinDTO> describeAllByLimit(long offset, long limit) {
        List<Otherin> list = this.otherinMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 条件查询多条数据
     *
     * @param otherinDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtherinDTO> describeAll(OtherinDTO otherinDTO) {
        Otherin otherin = new Otherin();
        BeanUtils.copyProperties(otherinDTO, otherin);
        List<Otherin> list = this.otherinMapper.describeAll(otherin);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinDTO.class);
        } else {
            return new ArrayList<>();
        }

    }

    /**
     * 条件模糊查询多条数据
     *
     * @param otherinDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtherinDTO> describeFuzzy(OtherinDTO otherinDTO) {
        Otherin otherin = new Otherin();
        BeanUtils.copyProperties(otherinDTO, otherin);
        List<Otherin> list = this.otherinMapper.describeFuzzy(otherin);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 查询未提交的入库单数据
     *
     * @param otherinDTO 实例对象
     * @return 对象列表
     */
    @Override
    public List<OtherinDTO> describeNotCommit(OtherinDTO otherinDTO) {
        Otherin otherin = new Otherin();
        BeanUtils.copyProperties(otherinDTO, otherin);
        List<Otherin> list = this.otherinMapper.describeNotCommit(otherin);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param otherinDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OtherinDTO insertOtherin(OtherinDTO otherinDTO) {
        Otherin otherin = new Otherin();
        BeanUtils.copyProperties(otherinDTO, otherin);
        if (null == otherin.getId() || "".equals(otherin.getId())) {
            otherin.setId(UUID.randomUUID().toString());
        }
        this.otherinMapper.insertOtherin(otherin);
        return otherinDTO;
    }

    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<OtherinDTO> saveOtherin(List<OtherinDTO> list) {
        list.forEach(otherinDTO -> {
            if (null == otherinDTO.getId() || "".equals(otherinDTO.getId())) {
                otherinDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.otherinMapper.saveOtherin(JSON.parseArray(JSON.toJSONString(list), Otherin.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param otherinDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OtherinDTO updateOtherinByPrimaryKey(OtherinDTO otherinDTO) {
        Otherin otherin = new Otherin();
        BeanUtils.copyProperties(otherinDTO, otherin);
        otherin.setUpdateTime(new Date());
        System.out.println(otherin+"88888888888898989");
        this.otherinMapper.updateOtherinByPrimaryKey(otherin);
        return this.describeById(otherin.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteOtherinByPrimaryKey(String id) {
        return this.otherinMapper.deleteOtherinByPrimaryKey(id) > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteOtherinByPrimaryKeys(List<String> list) {
        return this.otherinMapper.deleteOtherinByPrimaryKeys(list) == list.size();
    }

    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(OtherinDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Otherin.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getOtherinNo() && !"".equals(dto.getOtherinNo())) {
                criteriaName.andLike("otherinNo", "%" + dto.getOtherinNo() + "%");
            }
            if (null != dto.getSupplierId() && !"".equals(dto.getSupplierId())) {
                criteriaName.andLike("supplierId", "%" + dto.getSupplierId() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andLike("createId", "%" + dto.getCreateId() + "%");
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andLike("creator", "%" + dto.getCreator() + "%");
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andLike("createTime", "%" + dto.getCreateTime() + "%");
            }
            if (null != dto.getUpdateId() && !"".equals(dto.getUpdateId())) {
                criteriaName.andLike("updateId", "%" + dto.getUpdateId() + "%");
            }
            if (null != dto.getUpdator() && !"".equals(dto.getUpdator())) {
                criteriaName.andLike("updator", "%" + dto.getUpdator() + "%");
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andLike("updateTime", "%" + dto.getUpdateTime() + "%");
            }
            if (null != dto.getAuditId() && !"".equals(dto.getAuditId())) {
                criteriaName.andLike("auditId", "%" + dto.getAuditId() + "%");
            }
            if (null != dto.getAuditor() && !"".equals(dto.getAuditor())) {
                criteriaName.andLike("auditor", "%" + dto.getAuditor() + "%");
            }
            if (null != dto.getAuditTime()) {
                criteriaName.andLike("auditTime", "%" + dto.getAuditTime() + "%");
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
    private Example initExample(OtherinDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Otherin.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getOtherinNo() && !"".equals(dto.getOtherinNo())) {
                criteriaName.andEqualTo("otherinNo", dto.getOtherinNo());
            }
            if (null != dto.getSupplierId() && !"".equals(dto.getSupplierId())) {
                criteriaName.andEqualTo("supplierId", dto.getSupplierId());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andEqualTo("createId", dto.getCreateId());
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andEqualTo("creator", dto.getCreator());
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andEqualTo("createTime", dto.getCreateTime());
            }
            if (null != dto.getUpdateId() && !"".equals(dto.getUpdateId())) {
                criteriaName.andEqualTo("updateId", dto.getUpdateId());
            }
            if (null != dto.getUpdator() && !"".equals(dto.getUpdator())) {
                criteriaName.andEqualTo("updator", dto.getUpdator());
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andEqualTo("updateTime", dto.getUpdateTime());
            }
            if (null != dto.getAuditId() && !"".equals(dto.getAuditId())) {
                criteriaName.andEqualTo("auditId", dto.getAuditId());
            }
            if (null != dto.getAuditor() && !"".equals(dto.getAuditor())) {
                criteriaName.andEqualTo("auditor", dto.getAuditor());
            }
            if (null != dto.getAuditTime()) {
                criteriaName.andEqualTo("auditTime", dto.getAuditTime());
            }
        }
        return example;
    }

    /**
     * 生成入库单号
     *
     * @return 实例对象
     * @params otherinDTO 实例对象
     */
    @Override
    public OtherinDTO initOrderNo(UserDTO dto) {
        Otherin otherin = new Otherin();
        if (null != dto.getUserId() && !"".equals(dto.getUserId())) {
            otherin.setCreateId(dto.getUserId());
        } else {
            throw new RuntimeException("用户不能为空");
        }
        if (null != dto.getUsername() && !"".equals(dto.getUsername())) {
            otherin.setCreator(dto.getUsername());
        }
        String orderNo = InitOrderNo.getNowFormatDate();
        otherin.setId(UUID.randomUUID().toString());
        otherin.setOtherinNo(orderNo);
        otherin.setCompanyId(SecurityUtils.getCompanyId());
        otherin.setState("3");
        otherin.setCreateTime(new Date());
        this.otherinMapper.insertOtherin(otherin);
        OtherinDTO returnDTO = new OtherinDTO();
        BeanUtils.copyProperties(otherin, returnDTO);
        return returnDTO;
    }

    @Override
    public List<OtherinWithWarehouseNameDTO> describeMineFuzzy(OtherinDTO otherinDTO, String userId) {
        Otherin otherin = new Otherin();
        BeanUtils.copyProperties(otherinDTO, otherin);
        List<Map<String, Object>> list = this.otherinMapper.describeMineFuzzy(otherin, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer updateWarehouseIdByOtherinNo(OtherinDTO otherinDTO) {
        try {

            Otherin queryOtherinParam = new Otherin();
            queryOtherinParam.setOtherinNo(otherinDTO.getOtherinNo());
            Otherin queryOtherinResult = otherinMapper.selectOne(queryOtherinParam);
            queryOtherinResult.setWarehouseId(otherinDTO.getWarehouseId());
            queryOtherinResult.setAuditId(otherinDTO.getAuditId());
            queryOtherinResult.setAuditor(otherinDTO.getAuditor());
            queryOtherinResult.setSupplierId(otherinDTO.getSupplierId());
            queryOtherinResult.setSupplierName(otherinDTO.getSupplierName());
            queryOtherinResult.setCompanyId(otherinDTO.getCompanyId());
            Integer integer = otherinMapper.updateOtherinByPrimaryKey(queryOtherinResult);
            return integer;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批入库单数据
     * @params: [ otherinDTO, userId ]
     * @return: java.util.List<com.system.service.dto.OtherinWithWarehouseNameDTO>
     * @createTime: 2020/4/19 12:32
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<OtherinWithWarehouseNameDTO> describeCommissionFuzzy(OtherinDTO otherinDTO, String userId) {
        Otherin otherin = new Otherin();
        BeanUtils.copyProperties(otherinDTO, otherin);
        List<Map<String, Object>> list = this.otherinMapper.describeCommissionFuzzy(otherin, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @title: approveOtherin
     * @description: 审批出库单(通过和打回)
     * @params: [ otherinDTO ]
     * @return: java.lang.Boolean
     * @createTime: 2020/4/19 13:37
     * @version: 1.0
     * @author: wanpp
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Boolean approveOtherin(OtherinDTO otherinDTO) {
        Boolean result = false;
        Otherin otherin = otherinMapper.selectByPrimaryKey(otherinDTO.getId());
        if (otherin == null) {
            return false;
        }
        if ("4".equals(otherinDTO.getState())) {
            //审批通过，需要处理仓库、货架、货位、仓储等数据信息
            // 1、获取出库单下的所有出库单详情表
            String otherinNo = otherin.getOtherinNo();
            Example example = new Example(OtherinDetail.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("pid", otherinNo);
            List<OtherinDetail> otherinDetails = otherinDetailMapper.selectByExample(example);
            // 2、遍历每个入库项
            List<Stock> stocks = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(otherinDetails)) {
                for (OtherinDetail otherinDetail : otherinDetails) {
                    // 2.1、从每个入库项中取出货位ID、商品ID、仓储ID和出库数量

                    String locatorId = otherinDetail.getLocatorId();
                    Locator locator = locatorMapper.describeById(locatorId);
                    String commodityBar = otherinDetail.getCommodityBar();
                    Good good = goodMapper.describeByGoodCode(commodityBar);
                    List<Stock> stockList = stockMapper.findByLocatorId(locatorId);
                    if (stockList.size() == 0) {
                        Stock newstock = new Stock();
                        newstock.setId(UUID.randomUUID().toString());
                        newstock.setLocatorId(locator.getId());
                        newstock.setLocatorName(locator.getName());
                        newstock.setShelvesId(locator.getShelvesId());
                        newstock.setWarehouseId(locator.getWarehouseId());
                        newstock.setCommodityId(good.getId());
                        newstock.setCommodityBar(good.getGoodNo());
                        newstock.setCommodityName(good.getGoodName());
                        newstock.setLqty(otherinDetail.getQty());
                        newstock.setCqty(otherinDetail.getQty());
                        newstock.setUqty(otherinDetail.getQty());
                        newstock.setLocatorCode(locator.getCode());
                        newstock.setCreateId(otherinDetail.getCreateId());
                        newstock.setCreator(otherinDetail.getCreator());
                        newstock.setShelvesId(locator.getShelvesId());
                        newstock.setShelvesName(locator.getShelvesName());
                        newstock.setCreateTime(new Date());
                        newstock.setLocatorState("2");
                        newstock.setBatch(otherinDetail.getBatch());
                        System.out.println(otherinDetail.getBatch()+"888888888888888888899");

                        stocks.add(newstock);
                        stockMapper.insertStock(newstock);
                        if (locator.getWeight() - newstock.getCqty() * good.getWeigth() < good.getWeigth()) {
                            locator.setState("2");
                        } else {
                            locator.setState("0");
                        }
                        locatorMapper.updateLocatorByPrimaryKey(locator);

                    } else {
                        Stock stock = stockList.get(0);
                        stock.setCqty(stock.getCqty() + otherinDetail.getQty());
                        stock.setBatch(otherinDetail.getBatch());
                        stockMapper.updateStockByPrimaryKey(stock);
                        System.out.println(locator.getWeight() - stock.getCqty() * good.getWeigth() + "666" + good.getWeigth());
                        if (locator.getWeight() - stock.getCqty() * good.getWeigth() < good.getWeigth()) {
                            locator.setState("2");
                        } else {
                            locator.setState("0");
                        }
                        locatorMapper.updateLocatorByPrimaryKey(locator);
                    }

                }

            }
            // 5、将出库主表的信息存库
            otherin.setState(otherinDTO.getState());
            otherin.setAuditTime(new Date());
            if (otherinMapper.updateOtherinByPrimaryKey(otherin) > 0) {
                result = true;
            }
        }
        if ("5".equals(otherinDTO.getState())) {
            otherin.setState("5");
            otherinMapper.updateByPrimaryKey(otherin);
        }
        return result;
    }

    @Override
    public List<ShelvesAndStockDTO> describeShelvesAndStockByWarehouseId(String warehouseId, String goodsNo) {
        // 1、 获取仓库下的所有货架
        List<Shelves> list = shelvesMapper.describesAllShelvesByWarehouseId(warehouseId);
        // 2、获取所有的货架id
        List<String> shelvesIdList = new ArrayList<>();
        list.forEach(shelves -> {
            shelvesIdList.add(shelves.getId());
        });

        // 3、根据货架id集合查询出所有的仓储信息
        List<Stock> stocks = new ArrayList<>();
        List<Stock> suitstck = new ArrayList<>();
        if (null != shelvesIdList && shelvesIdList.size() > 0) {
            stocks = stockMapper.describesStockByShelveseIdList(shelvesIdList);
            for (int i = 0; i < stocks.size(); i++) {
                if (!stocks.get(i).getLocatorState().equals("2") && stocks.get(i).getCommodityBar().equals(goodsNo)) {
                    suitstck.add(stocks.get(i));
                }
            }
        }
        // 4、封装数据
        List<ShelvesAndStockDTO> returnList = new ArrayList<>();
        // 4.1 遍历查询出来的货架数据
        for (int i = 0; i < list.size(); i++) {
            Shelves shelves = list.get(i);
            ShelvesAndStockDTO shelvesAndStockDTO = new ShelvesAndStockDTO();
            shelvesAndStockDTO.setLabel(shelves.getShelvesName());
            shelvesAndStockDTO.setValue(shelves.getId());
            ShelvesDTO shelvesDTO = new ShelvesDTO();
            BeanUtils.copyProperties(shelves, shelvesDTO);
            shelvesAndStockDTO.setValue(shelvesDTO.getId());
            shelvesAndStockDTO.setShelvesDTO(shelvesDTO);
            //设置子数据，也就是货位数据
            List<StockCaseCadeDTO> children = new ArrayList<>();
            suitstck.forEach(stock -> {
                // 如果货位的货架id等于此货架id
                if (stock.getShelvesId().equals(shelves.getId())) {
                    StockCaseCadeDTO stockCaseCadeDTO = new StockCaseCadeDTO();
                    stockCaseCadeDTO.setLabel(stock.getLocatorName());
                    stockCaseCadeDTO.setValue(stock.getLocatorId());
                    StockDTO stockDTO = new StockDTO();
                    BeanUtils.copyProperties(stock, stockDTO);
                    stockCaseCadeDTO.setValue(stockDTO.getId());
                    stockCaseCadeDTO.setStockDTO(stockDTO);
                    children.add(stockCaseCadeDTO);
                }
            });
            shelvesAndStockDTO.setChildren(children);
            returnList.add(shelvesAndStockDTO);
        }
        return returnList;
    }


    /**
     * in
     *
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的出库单数据
     * @params: [otherinDTO, userId]
     * @return: java.util.List<com.system.service.dto.otherin.OtherinWithWarehouseNameDTO>
     * @createTime: 2020/4/20 20:30
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<OtherinWithWarehouseNameDTO> describeAlreadDoneFuzzy(OtherinDTO otherinDTO, String userId) {
        Otherin otherin = new Otherin();
        BeanUtils.copyProperties(otherinDTO, otherin);
        List<Map<String, Object>> list = this.otherinMapper.describeAlreadDoneFuzzy(otherin, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtherinWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public OtherinDTO describeByOtherinNo(String otherinNo) {
        OtherinDTO dto = new OtherinDTO();
        Otherin otherin = new Otherin();
        otherin.setOtherinNo(otherinNo);
        List<Otherin> list = this.otherinMapper.describeFuzzy(otherin);
        otherin = list.get(0);
        if (null != otherin) {
            BeanUtils.copyProperties(otherin, dto);
        }
        return dto;
    }

}