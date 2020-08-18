package com.warehouse.warehouse.service.impl.otherout;

import com.alibaba.fastjson.JSON;
import com.common.utils.InitOrderNo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.otherout.OtheroutDetailMapper;
import com.warehouse.warehouse.dao.otherout.OtheroutMapper;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.ShelvesMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorMapper;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutDTO;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.dto.stock.StockCaseCadeDTO;
import com.warehouse.warehouse.pojo.dto.stock.StockDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesAndStockDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesDTO;
import com.warehouse.warehouse.pojo.po.otherout.Otherout;
import com.warehouse.warehouse.pojo.po.otherout.OtheroutDetail;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.Shelves;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.Locator;
import com.warehouse.warehouse.service.app.otherout.OtheroutService;
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
 * 出库表(Otherout)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("otheroutService")
public class OtheroutServiceImpl implements OtheroutService {

    @Resource
    private ShelvesMapper shelvesMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private OtheroutMapper otheroutMapper;

    @Resource
    private OtheroutDetailMapper otheroutDetailMapper;

    @Resource
    private LocatorMapper locatorMapper;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OtheroutDTO describeById(String id) {
        OtheroutDTO dto = new OtheroutDTO();
        Otherout otherout = this.otheroutMapper.describeById(id);
        if (null != otherout) {
            BeanUtils.copyProperties(otherout, dto);
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
    public List<OtheroutDTO> describeAllByLimit(long offset, long limit) {
        List<Otherout> list = this.otheroutMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 条件查询多条数据
     *
     * @param otheroutDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtheroutDTO> describeAll(OtheroutDTO otheroutDTO) {
        Otherout otherout = new Otherout();
        BeanUtils.copyProperties(otheroutDTO, otherout);
        List<Otherout> list = this.otheroutMapper.describeAll(otherout);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutDTO.class);
        } else {
            return new ArrayList<>();
        }

    }

    /**
     * 条件模糊查询多条数据
     *
     * @param otheroutDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtheroutDTO> describeFuzzy(OtheroutDTO otheroutDTO) {
        Otherout otherout = new Otherout();
        BeanUtils.copyProperties(otheroutDTO, otherout);
        List<Otherout> list = this.otheroutMapper.describeFuzzy(otherout);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 查询未提交的出库单数据
     *
     * @param otheroutDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OtheroutDTO> describeNotCommit(OtheroutDTO otheroutDTO) {
        Otherout otherout = new Otherout();
        BeanUtils.copyProperties(otheroutDTO, otherout);
        List<Otherout> list = this.otheroutMapper.describeNotCommit(otherout);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param otheroutDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OtheroutDTO insertOtherout(OtheroutDTO otheroutDTO) {
        Otherout otherout = new Otherout();
        BeanUtils.copyProperties(otheroutDTO, otherout);
        if (null == otherout.getId() || "".equals(otherout.getId())) {
            otherout.setId(UUID.randomUUID().toString());
        }
        this.otheroutMapper.insertOtherout(otherout);
        return otheroutDTO;
    }

    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<OtheroutDTO> saveOtherout(List<OtheroutDTO> list) {
        list.forEach(otheroutDTO -> {
            if (null == otheroutDTO.getId() || "".equals(otheroutDTO.getId())) {
                otheroutDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.otheroutMapper.saveOtherout(JSON.parseArray(JSON.toJSONString(list), Otherout.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param otheroutDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OtheroutDTO updateOtheroutByPrimaryKey(OtheroutDTO otheroutDTO) {
        Otherout otherout = new Otherout();
        BeanUtils.copyProperties(otheroutDTO, otherout);
        otherout.setUpdateTime(new Date());
        this.otheroutMapper.updateOtheroutByPrimaryKey(otherout);
        return this.describeById(otherout.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteOtheroutByPrimaryKey(String id) {
        return this.otheroutMapper.deleteOtheroutByPrimaryKey(id) > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteOtheroutByPrimaryKeys(List<String> list) {
        return this.otheroutMapper.deleteOtheroutByPrimaryKeys(list) == list.size();
    }

    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(OtheroutDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Otherout.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getOtheroutNo() && !"".equals(dto.getOtheroutNo())) {
                criteriaName.andLike("otheroutNo", "%" + dto.getOtheroutNo() + "%");
            }
            if (null != dto.getCustomerId() && !"".equals(dto.getCustomerId())) {
                criteriaName.andLike("customerId", "%" + dto.getCustomerId() + "%");
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
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
    private Example initExample(OtheroutDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Otherout.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getOtheroutNo() && !"".equals(dto.getOtheroutNo())) {
                criteriaName.andEqualTo("otheroutNo", dto.getOtheroutNo());
            }
            if (null != dto.getCustomerId() && !"".equals(dto.getCustomerId())) {
                criteriaName.andEqualTo("customerId", dto.getCustomerId());
            }
            if (null != dto.getState() && !"".equals(dto.getState())) {
                criteriaName.andEqualTo("state", dto.getState());
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
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
     * 生成出库单号
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    @Override
    public OtheroutDTO initOrderNo(UserDTO dto) {
        Otherout otherout = new Otherout();
        if (null != dto.getUserId() && !"".equals(dto.getUserId())) {
            otherout.setCreateId(dto.getUserId());
        } else {
            throw new RuntimeException("用户不能为空");
        }
        if (null != dto.getUsername() && !"".equals(dto.getUsername())) {
            otherout.setCreator(dto.getUsername());
        }
        String orderNo = InitOrderNo.getNowFormatDate();
        otherout.setId(UUID.randomUUID().toString());
        otherout.setOtheroutNo(orderNo);
        otherout.setCompanyId(SecurityUtils.getCompanyId());
        otherout.setState("3");
        otherout.setCreateTime(new Date());
        this.otheroutMapper.insertOtherout(otherout);
        OtheroutDTO returnDTO = new OtheroutDTO();
        BeanUtils.copyProperties(otherout, returnDTO);
        return returnDTO;
    }

    @Override
    public List<ShelvesAndStockDTO> describeShelvesAndStockByWarehouseId(String warehouseId) {
        // 1、 获取仓库下的所有货架
        List<Shelves> list = shelvesMapper.describesAllShelvesByWarehouseId(warehouseId);
        // 2、获取所有的货架id
        List<String> shelvesIdList = new ArrayList<>();
        list.forEach(shelves -> {
            shelvesIdList.add(shelves.getId());
        });

        // 3、根据货架id集合查询出所有的仓储信息
        List<Stock> stocks = new ArrayList<>();
        if (null != shelvesIdList && shelvesIdList.size() > 0) {
            stocks = stockMapper.describesStockByShelveseIdList(shelvesIdList);
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
            stocks.forEach(stock -> {
                // 如果货位的货架id等于此货架id
                if (stock.getShelvesId().equals(shelves.getId())) {
                    StockCaseCadeDTO stockCaseCadeDTO = new StockCaseCadeDTO();
                    stockCaseCadeDTO.setLabel(stock.getCommodityName());
                    stockCaseCadeDTO.setValue(stock.getCommodityId());
                    StockDTO stockDTO = new StockDTO();
                    BeanUtils.copyProperties(stock, stockDTO);
                    stockCaseCadeDTO.setValue(stockDTO.getId());
                    stockCaseCadeDTO.setStockDTO(stockDTO);
                    children.add(stockCaseCadeDTO);
                }
            });
            shelvesAndStockDTO.setChildren(children);
            // 返回的级联数据都是一级数据下一定存在二级数据
            if(CollectionUtils.isNotEmpty(children)) {
                returnList.add(shelvesAndStockDTO);
            }
        }
        return returnList;
    }

    /**
     * @title: updateWarehouseIdByOtheroutNo
     * @description: 根据OtheroutNo修改数据的仓库信息
     * @params: [ otheroutDTO ]
     * @return: java.lang.Integer
     * @createTime: 2020/4/18 18:57
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public Integer updateWarehouseIdByOtheroutNo(OtheroutDTO otheroutDTO) {
        try {
            Otherout queryOtheroutParam = new Otherout();
            queryOtheroutParam.setOtheroutNo(otheroutDTO.getOtheroutNo());
            Otherout queryOtheroutResult = otheroutMapper.selectOne(queryOtheroutParam);
            queryOtheroutResult.setWarehouseId(otheroutDTO.getWarehouseId());
            queryOtheroutResult.setCustomerId(otheroutDTO.getCustomerId());
            queryOtheroutResult.setCustomerName(otheroutDTO.getCustomerName());
            queryOtheroutResult.setCustomerId(otheroutDTO.getCompanyId());

            Integer integer = otheroutMapper.updateOtheroutByPrimaryKey(queryOtheroutResult);
            return integer;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @title: describeCommissionFuzzy
     * @description: 条件分页模糊查询当前用户待审批出库单数据
     * @params: [ otheroutDTO, userId ]
     * @return: java.util.List<com.system.service.dto.OtheroutWithWarehouseNameDTO>
     * @createTime: 2020/4/19 12:32
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<OtheroutWithWarehouseNameDTO> describeCommissionFuzzy(OtheroutDTO otheroutDTO, String userId) {
        Otherout otherout = new Otherout();
        BeanUtils.copyProperties(otheroutDTO, otherout);
        List<Map<String, Object>> list = this.otheroutMapper.describeCommissionFuzzy(otherout, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @title: approveOtherout
     * @description: 审批出库单(通过和打回)
     * @params: [ otheroutDTO ]
     * @return: java.lang.Boolean
     * @createTime: 2020/4/19 13:37
     * @version: 1.0
     * @author: wanpp
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Boolean approveOtherout(OtheroutDTO otheroutDTO) {
        Boolean result = false;
        Otherout otherout = otheroutMapper.selectByPrimaryKey(otheroutDTO.getId());
        if(otherout == null) {
            return false;
        }
        if ("4".equals(otheroutDTO.getState())) {

            //审批通过，需要处理仓库、货架、货位、仓储等数据信息
            // 1、获取出库单下的所有出库单详情表
            String otheroutNo = otherout.getOtheroutNo();
            String warehouseId = otherout.getWarehouseId();
            Example example = new Example(OtheroutDetail.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("pid", otheroutNo);
            List<OtheroutDetail> otheroutDetails = otheroutDetailMapper.selectByExample(example);
            // 2、遍历每个入库项
            List<Stock> stocks = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(otheroutDetails)) {
                for (OtheroutDetail otheroutDetail : otheroutDetails) {
                    // 2.1、从每个入库项中取出货位ID、商品ID、仓储ID和出库数量
                    String locatorId = otheroutDetail.getLocatorId();
                    String stockId = otheroutDetail.getStockId();
                    Integer outNum = otheroutDetail.getQty();
                    // 2、2 找到对应的仓储表记录 --这块可以抽成一个函数
                    Stock stock = stockMapper.selectByPrimaryKey(stockId);
                    if (stock != null) {
                        // 3、减库存 因为审核成功了，出库了，就没考虑减库存到负数的可能性
                        Integer currentNum = stock.getCqty();
                        stock.setCqty(currentNum - outNum);
                        stocks.add(stock);
                        // 货位上的货物清空了了
                        if(currentNum - outNum == 0){
                            // 根据仓库ID、商品ID、货位ID、定位货位表
                           if(! updateLocatorState(warehouseId,locatorId,"0")){
                               return false;
                           }
                        }
                        //  4、保存到数据库
                        stockMapper.updateStockByPrimaryKey(stock);
                    }
                }


            }
            // 5、将出库主表的信息存库
            otherout.setState(otheroutDTO.getState());
            otherout.setAuditTime(new Date());
            if(otheroutMapper.updateOtheroutByPrimaryKey(otherout) > 0){
                result = true;
            }
        }
        if ("5".equals(otheroutDTO.getState())) {
            otherout.setState("5");
            otheroutMapper.updateByPrimaryKey(otherout);
        }
        return result;
    }


    /**
     * 修改货位记录状态
     * @param warehouseId
     * @param locatorId
     * @param state
     * @return
     */
    private Boolean updateLocatorState(String warehouseId, String locatorId, String state){
        Example example1 = new Example(Locator.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("warehouseId",warehouseId);
        criteria1.andEqualTo("id",locatorId);
        List<Locator> locators =  locatorMapper.selectByExample(example1);
        if(CollectionUtils.isNotEmpty(locators)){
            Locator locator = locators.get(0);
            // 0代表货位已清空
            locator.setState(state);
            if(locatorMapper.updateLocatorByPrimaryKey(locator) >0){
                return true;
            }
        }
        return false;
    }

    /**
     * @title: describeMineFuzzy
     * @description: 条件分页模糊查询当前用户提交出库单数据
     * @params: [otheroutDTO, userId]
     * @return: java.util.List<com.system.service.dto.otherout.OtheroutWithWarehouseNameDTO>
     * @createTime: 2020/4/19 17:43
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<OtheroutWithWarehouseNameDTO> describeMineFuzzy(OtheroutDTO otheroutDTO, String userId) {
        Otherout otherout = new Otherout();
        BeanUtils.copyProperties(otheroutDTO, otherout);
        List<Map<String, Object>> list = this.otheroutMapper.describeMineFuzzy(otherout, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @title: describeAlreadDoneFuzzy
     * @description: 条件分页模糊查询当前用户已经审批的出库单数据
     * @params: [otheroutDTO, userId]
     * @return: java.util.List<com.system.service.dto.otherout.OtheroutWithWarehouseNameDTO>
     * @createTime: 2020/4/20 20:30
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<OtheroutWithWarehouseNameDTO> describeAlreadDoneFuzzy(OtheroutDTO otheroutDTO, String userId) {
        Otherout otherout = new Otherout();
        BeanUtils.copyProperties(otheroutDTO, otherout);
        List<Map<String, Object>> list = this.otheroutMapper.describeAlreadDoneFuzzy(otherout, userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OtheroutWithWarehouseNameDTO.class);
        } else {
            return new ArrayList<>();
        }
    }


}