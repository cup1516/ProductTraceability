package com.warehouse.warehouse.service.impl.allocation;

import com.alibaba.fastjson.JSON;
import com.common.utils.InitOrderNo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.goods.GoodMapper;
import com.warehouse.warehouse.dao.otherin.OtherinDetailMapper;
import com.warehouse.warehouse.dao.otherin.OtherinMapper;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.dao.warehouse.WarehouseMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorMapper;
import com.warehouse.warehouse.pojo.dto.allocation.AllocationDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.pojo.po.allocation.Allocation;
import com.warehouse.warehouse.pojo.po.allocation.AllocationDetail;
import com.warehouse.warehouse.pojo.po.goods.Good;
import com.warehouse.warehouse.pojo.po.otherin.Otherin;
import com.warehouse.warehouse.pojo.po.otherin.OtherinDetail;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.pojo.po.warehouse.Warehouse;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.Shelves;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.Locator;
import com.warehouse.warehouse.service.app.allocation.AllocationService;
import com.warehouse.warehouse.dao.allocation.AllocationDetailMapper;
import com.warehouse.warehouse.dao.allocation.AllocationMapper;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 调拨单(Allocation)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("allocationService")
public class AllocationServiceImpl implements AllocationService {

    @Resource
    private AllocationMapper allocationMapper;

    @Resource
    private AllocationDetailMapper allocationDetailMapper;

    @Resource
    private OtherinMapper otherinMapper;

    @Resource
    private OtherinDetailMapper otherinDetailMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private LocatorMapper locatorMapper;

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private WarehouseMapper warehouseMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AllocationDTO describeById(String id) {
        AllocationDTO dto = new AllocationDTO();
        Allocation allocation = this.allocationMapper.describeById(id);
        if (null != allocation) {
            BeanUtils.copyProperties(allocation, dto);
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
    public List<AllocationDTO> describeAllByLimit(long offset, long limit) {
        List<Allocation> list = this.allocationMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), AllocationDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param allocationDTO 对象
     * @return 对象列表
     */
    @Override
    public List<AllocationDTO> describeAll(AllocationDTO allocationDTO) {
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(allocationDTO, allocation);
        List<Allocation> list = this.allocationMapper.describeAll(allocation);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), AllocationDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param allocationDTO 对象
     * @return 对象列表
     */
    @Override
    public List<AllocationDTO> describeFuzzy(AllocationDTO allocationDTO) {
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(allocationDTO, allocation);
        List<Allocation> list = this.allocationMapper.describeFuzzy(allocation);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), AllocationDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param allocationDTO 实例对象
     * @return 实例对象
     */
    @Override
    public AllocationDTO insertAllocation(AllocationDTO allocationDTO) {
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(allocationDTO, allocation);
        if (null == allocation.getId() || "".equals(allocation.getId())) {
            allocation.setId(UUID.randomUUID().toString());
        }
        this.allocationMapper.insertAllocation(allocation);
        return allocationDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<AllocationDTO> saveAllocation(List<AllocationDTO> list) {
        list.forEach(allocationDTO -> {
            if (null == allocationDTO.getId() || "".equals(allocationDTO.getId())) {
                allocationDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.allocationMapper.saveAllocation(JSON.parseArray(JSON.toJSONString(list), Allocation.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param allocationDTO 实例对象
     * @return 实例对象
     */
    @Override
    public AllocationDTO updateAllocationByPrimaryKey(AllocationDTO allocationDTO) {
        // 对allocationDTO的数据填充
        String bwarehouseId = allocationDTO.getBwarehouseId();
        String awarehouseId = allocationDTO.getAwarehouseId();
        if(StringUtils.isNotEmpty(bwarehouseId)) {
            Warehouse warehouse = warehouseMapper.selectByPrimaryKey(bwarehouseId);
            if(warehouse != null) {
                allocationDTO.setBwarehouseName(warehouse.getName());
            }
        }
        if(StringUtils.isNotEmpty(awarehouseId)) {
            Warehouse warehouse = warehouseMapper.selectByPrimaryKey(awarehouseId);
            if(warehouse != null) {
                allocationDTO.setAwarehouseName(warehouse.getName());
            }
        }
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(allocationDTO, allocation);
        this.allocationMapper.updateAllocationByPrimaryKey(allocation);
        return this.describeById(allocation.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteAllocationByPrimaryKey(String id) {
        return this.allocationMapper.deleteAllocationByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteAllocationByPrimaryKeys(List<String> list) {
        return this.allocationMapper.deleteAllocationByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(AllocationDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Allocation.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getAllocationNo() && !"".equals(dto.getAllocationNo())) {
                criteriaName.andLike("allocationNo", "%" + dto.getAllocationNo() + "%");
            }
            if (null != dto.getBwarehouseId() && !"".equals(dto.getBwarehouseId())) {
                criteriaName.andLike("bwarehouseId", "%" + dto.getBwarehouseId() + "%");
            }
            if (null != dto.getBwarehouseName() && !"".equals(dto.getBwarehouseName())) {
                criteriaName.andLike("bwarehouseName", "%" + dto.getBwarehouseName() + "%");
            }
            if (null != dto.getAwarehouseId() && !"".equals(dto.getAwarehouseId())) {
                criteriaName.andLike("awarehouseId", "%" + dto.getAwarehouseId() + "%");
            }
            if (null != dto.getAwarehouseName() && !"".equals(dto.getAwarehouseName())) {
                criteriaName.andLike("awarehouseName", "%" + dto.getAwarehouseName() + "%");
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
    private Example initExample(AllocationDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Allocation.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getAllocationNo() && !"".equals(dto.getAllocationNo())) {
                criteriaName.andEqualTo("allocationNo", dto.getAllocationNo());
            }
            if (null != dto.getBwarehouseId() && !"".equals(dto.getBwarehouseId())) {
                criteriaName.andEqualTo("bwarehouseId", dto.getBwarehouseId());
            }
            if (null != dto.getBwarehouseName() && !"".equals(dto.getBwarehouseName())) {
                criteriaName.andEqualTo("bwarehouseName", dto.getBwarehouseName());
            }
            if (null != dto.getAwarehouseId() && !"".equals(dto.getAwarehouseId())) {
                criteriaName.andEqualTo("awarehouseId", dto.getAwarehouseId());
            }
            if (null != dto.getAwarehouseName() && !"".equals(dto.getAwarehouseName())) {
                criteriaName.andEqualTo("awarehouseName", dto.getAwarehouseName());
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
     * 查询未提交的工单
     * @param allocationDTO
     * @return
     */
    @Override
    public List<AllocationDTO> describeNotCommit(AllocationDTO allocationDTO) {
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(allocationDTO,allocation);
        List<Allocation> list = allocationMapper.describeNotCommit(allocation);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), AllocationDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public AllocationDTO initOrderNo(UserDTO dto) {
        Allocation allocation = new Allocation();
        if(StringUtils.isNotEmpty(dto.getUserId())){
            allocation.setCreateId(dto.getUserId());
        }else{
            throw new RuntimeException("用户不能为空");
        }
        if(StringUtils.isNotEmpty(dto.getUsername())){
            allocation.setCreator(dto.getUsername());
        }
        String orderNo = InitOrderNo.getNowFormatDate();
        allocation.setId(UUID.randomUUID().toString());
        allocation.setAllocationNo(orderNo);
        // TODO 这里特别注意下
        allocation.setState("0");
        allocation.setCompanyId(SecurityUtils.getCompanyId());
        allocation.setCreateTime(new Date());
        allocationMapper.insertAllocation(allocation);
        AllocationDTO allocationDTO = new AllocationDTO();
        BeanUtils.copyProperties(allocation, allocationDTO);
        return allocationDTO;
    }

    @Override
    public Boolean updateAllocationByAllocationNo(AllocationDTO allocationDTO) {
        Allocation queryParam = new Allocation();
        queryParam.setAllocationNo(allocationDTO.getAllocationNo());
        Allocation result = allocationMapper.selectOne(queryParam);
        if(result != null) {
            result.setBwarehouseId(allocationDTO.getBwarehouseId());
            result.setAwarehouseId(allocationDTO.getAwarehouseId());
            result.setCompanyId(allocationDTO.getCompanyId());
            System.out.println(result+"iiiiiiiiiiiiiiiiiiiiii");
            Integer rowCount = allocationMapper.updateByPrimaryKey(result);
            if(rowCount > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<AllocationDTO> describeCommissionFuzzy(AllocationDTO allocationDTO, String userId) {
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(allocationDTO, allocation);
        List<Map<String, Object>> list = this.allocationMapper.describeCommissionFuzzy(allocation,userId);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), AllocationDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 审批调拨单
     */
    @Transactional(propagation=Propagation.SUPPORTS)
    @Override
    public Boolean approveAllocation(AllocationDTO allocationDTO) {
        Allocation allocation = allocationMapper.selectByPrimaryKey(allocationDTO.getId());
        if(allocation == null) { return false; }
        if ("4".equals(allocationDTO.getState())) {
            //审批通过，需要处理仓库、货架、货位、仓储等数据信息
            // 开始增减库存
            String allocationNo = allocation.getAllocationNo();
            String fromWarehouseId = allocation.getBwarehouseId();
            String toWarehouseId = allocation.getAwarehouseId();
            // 1、获取调拨单下的所有调拨单详情
            List<AllocationDetail> allocationDetails = allocationDetailMapper.findAllocationDetailListByPid(allocationNo);
            // 2、 根据调拨项列表执行减库存操作
            Boolean flag = this.reduceStock(allocationDetails,fromWarehouseId);
            if(flag) {
                // 调拨项的减库存成功，再考虑调拨项的增库存
                Boolean flag2 = this.increaseStock(allocationDetails,toWarehouseId);
                if(!flag2) {
                    // 增库存失败，减库存成功，需要回滚增库存操作
                    throw new RuntimeException("库存调拨失败，请核实");
                }else {
                    // 增库存也成功
                    allocation.setState("4");
                    allocation.setAuditor("");
                    allocation.setAuditTime(new Date());
                    if(allocationMapper.updateByPrimaryKey(allocation) > 0) {
                        return true;
                    }else{
                        throw new RuntimeException("库存调拨失败，请核实");
                    }
                }
            }
        }
        if("5".equals(allocationDTO.getState())) {
            allocation.setState("4");
            allocation.setAuditor("");
            allocation.setAuditTime(new Date());
            if(allocationMapper.updateByPrimaryKey(allocation) > 0) {
                return false;
            }else {
                throw new RuntimeException("审批调拨单失败，请核实");
            }
        }
        return false;
    }

    /**
     * 修改货位记录状态
     * @param warehouseId
     * @param locatorId
     * @param commodityId
     * @param state
     * @return
     */
    private Boolean updateLocatorState(String warehouseId, String locatorId, String commodityId, String state){
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


    private Stock findStockByWarehouseIdAndLocatorIdAndCommodityId(String fromWarehouseId, String locatorId, String commodityId) {
        Example example = new Example(Stock.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("warehouseId", fromWarehouseId);
        criteria.andEqualTo("locatorId", locatorId);
        criteria.andEqualTo("commodityId", commodityId);
        List<Stock> stocks = stockMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(stocks)) {
            return null;
        }
        return stocks.get(0);
    }


    private Stock insertIntoStockFromLocatorAndGood(Shelves shelves, Locator locator, Good good, Integer cqty, String warehouseId) {
        // 可以将当前调拨项入库
        Stock temp = new Stock();
        temp.setId(UUID.randomUUID().toString());
        temp.setLocatorId(locator.getId());
        temp.setLocatorName(locator.getName());
        temp.setLocatorCode(locator.getCode());
        temp.setLocatorState(locator.getState());
        temp.setCommodityId(good.getId());
        temp.setCommodityBar(good.getGoodNo());
        temp.setCommodityName(good.getGoodName());
        temp.setCqty(cqty);
        temp.setWarehouseId(warehouseId);
        if(shelves != null){
            temp.setShelvesId(shelves.getId());
            temp.setShelvesName(shelves.getShelvesName());
        }
        // temp.setCreateId();
        temp.setCreateTime(new Date());
        temp.setCreator("");
        return temp;
    }

    //批量更新仓储记录并删除仓储记录
    private Boolean updateBatchStockListInCqtyOrDeleteStock(List<Stock> stockList) {
        List<String> delStockIdList = new ArrayList<>();
        List<Stock> updateStockList = new ArrayList<>();
        for (Stock stock : stockList) {
            if(stock.getCqty() == 0) {
                // 库存为0，删除记录
                delStockIdList.add(stock.getId());
            }else {
                updateStockList.add(stock);
            }
        }
        if(CollectionUtils.isEmpty(updateStockList)) {
            // 如果待修改的记录为空
            if(CollectionUtils.isNotEmpty(delStockIdList)) {
                if(stockMapper.deleteStockByPrimaryKeys(delStockIdList) <= 0){
                    return false;
                }
            }
            return true;
        }else{
            //  如果待修改的记录不为空
            if(CollectionUtils.isNotEmpty(delStockIdList)) {
                if(stockMapper.deleteStockByPrimaryKeys(delStockIdList) <= 0) {
                    throw new RuntimeException("数据异常");
                }
            }
            return stockMapper.updateBatchStockListInCqty(updateStockList) > 0;
        }
    }

    /**
     * 调拨单步骤之一：
     * 减库存
     * @param allocationDetails 调拨项列表
     * @param fromWarehouseId 出库的仓库ID
     * @return
     */
    private Boolean reduceStock(List<AllocationDetail> allocationDetails, String fromWarehouseId) {
        // 存储仓储表修改库存后的记录列表
        List<Stock> stocks = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(allocationDetails)) {
            for (AllocationDetail allocationDetail : allocationDetails) {
                // 2.1 从每个调拨项中取出原货位ID、商品ID、调拨数量
                String locatorId = allocationDetail.getLocatorId();
                String commodityId = allocationDetail.getCommodityId();
                int allotNum = allocationDetail.getQty();
                // 3. 出库相关操作
                // 3.1 从Stock表中取出仓储记录，留待出库使用
                Stock stock = this.findStockByWarehouseIdAndLocatorIdAndCommodityId(fromWarehouseId,locatorId,commodityId);
                if(stock == null) {
                    throw new RuntimeException("数据发生异常，请联系管理员");
                }
                // 3.2 减库存操作 因为审核成功了，出库了，就没考虑减库存到负数的可能性
                // 库存数量
                int stockNum = stock.getCqty();
                stock.setCqty(stockNum - allotNum);
                stocks.add(stock);
                // 只要是减了库存，自然或为就是可用状态 TODO 可以考虑优化，第一次减库存修改状态值为0,以后都不用再修改
                if(! updateLocatorState(fromWarehouseId,locatorId,commodityId,"0")) {
                    return false;
                }
            }
            // 3.3 批量更新且如果仓储量为0，则删除记录
            return this.updateBatchStockListInCqtyOrDeleteStock(stocks);
        }
        return false;
    }


    /**
     * 调拨单步骤二：
     * 加库存
     * @param allocationDetails
     * @param toWarehouseId
     * @return
     */
    private Boolean increaseStock(List<AllocationDetail> allocationDetails, String toWarehouseId) {
        if(CollectionUtils.isNotEmpty(allocationDetails)) {
            for (AllocationDetail allocationDetail : allocationDetails) {
                // 3.1 从每个调拨项中取出目标货位ID、商品ID、调拨数量
                String toLocatorId = allocationDetail.getToLocatorId();
                String commodityId = allocationDetail.getCommodityId();
                int allotNum = allocationDetail.getQty();
                // 3.2 入库相关操作
                // 3.2.1 入库的时候，货位状态分为多种，可用，已满和预占用
                // 3.2.1.1 判断目标货位使用情况和存放哪种商品
                Locator locator = locatorMapper.selectByPrimaryKey(toLocatorId);
                if(locator == null || !"[2,7]".equals(locator.getType())) {
                    throw new RuntimeException("数据异常，没有该货位或货位类型错误，请联系管理员");
                }
                String locatorState = locator.getState();
                double maxWeight = locator.getWeight();
                Example example = new Example(Good.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("id", commodityId);
                Good good = goodMapper.selectOneByExample(example);
                if(good != null) {
                    if ("0".equals(locatorState)) {
                        // 可用状态 又分成空 或者 部分使用
                        Stock stock1 = new Stock();
                        stock1.setLocatorId(toLocatorId);
                        List<Stock> stocks = stockMapper.select(stock1);
                        if (CollectionUtils.isEmpty(stocks)) {
                            // 为空 直接入库
                            // 入库前判断待入库商品是否小于maxWeight
                            // 容量判断逻辑
                            double toDoPhysicsWeight = good.getWeigth() * allotNum;
                            if (toDoPhysicsWeight < maxWeight) {
                                // 可以将当前调拨项入库
                                Stock temp = this.insertIntoStockFromLocatorAndGood(null, locator, good, allotNum, toWarehouseId);
                                int rowCount = stockMapper.insertStock(temp);
                                if (rowCount > 0) {
                                    return true;
                                }
                            }
                        } else {
                            // 已用
                            if (commodityId.equals(stocks.get(0).getCommodityId())) {
                                // 入库
                                int totalQty = 0;
                                for (Stock stock : stocks) {
                                    totalQty += stock.getCqty();
                                }
                                // 计算总的物理占用重量消耗
                                double physicsUseWeight = (totalQty + allotNum) * good.getWeigth();
                                if (physicsUseWeight < maxWeight) {
                                    // 可以将当前调拨项入库
                                    Stock temp = this.insertIntoStockFromLocatorAndGood(null, locator, good, allotNum, toWarehouseId);
                                    int rowCount = stockMapper.insertStock(temp);
                                    if (rowCount > 0) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }

                    if ("3".equals(locatorState)) {
                        // locator的状态为预备状态，即之前有入库单数据还未审核，逻辑上占用空间
                        // 首先获取已提交但未审核的入库单子表数据 一个locator可以对应多个入库单子表数据，
                        List<OtherinDetail> otherinDetailList = otherinDetailMapper.findInDetailListByLocatorId(toLocatorId);
                        if (CollectionUtils.isNotEmpty(otherinDetailList)) {
                            // 存在记录
                            int totalQty = 0;
                            for (OtherinDetail otherinDetail : otherinDetailList) {
                                // 统计所有符合条件的入库单数目
                                Otherin otherin = otherinMapper.selectByPrimaryKey(otherinDetail.getPid());
                                if (commodityId.equals(otherinDetail.getCommodityId()) && ("0".equals(otherin.getState()) || "4".equals(otherin.getState()))) {
                                    // 对于符合条件的记录汇总判断满不满，
                                    int singleQty = otherinDetail.getQty();
                                    totalQty += singleQty;
                                }
                            }
                            double logicalAndPhysicsUseWeight = good.getWeigth() * totalQty;
                            if (logicalAndPhysicsUseWeight < maxWeight) {
                                // 证明该货位可以继续添加商品
                                // 可以将当前调拨项入库
                                Stock temp = this.insertIntoStockFromLocatorAndGood(null, locator, good, allotNum, toWarehouseId);
                                int rowCount = stockMapper.insertStock(temp);
                                if (rowCount > 0) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


}