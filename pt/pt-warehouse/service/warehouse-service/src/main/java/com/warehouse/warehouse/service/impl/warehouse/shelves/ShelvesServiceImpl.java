package com.warehouse.warehouse.service.impl.warehouse.shelves;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.warehouse.warehouse.dao.goods.GoodMapper;
import com.warehouse.warehouse.dao.otherin.OtherinDetailMapper;
import com.warehouse.warehouse.dao.otherin.OtherinMapper;
import com.warehouse.warehouse.dao.stock.StockMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.ShelvesMapper;
import com.warehouse.warehouse.dao.warehouse.shelves.locator.LocatorMapper;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesAndLocatorDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.WarehouseAndShelvesDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.WarehouseChidrenDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorCaseCadeDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDTO;
import com.warehouse.warehouse.pojo.po.goods.Good;
import com.warehouse.warehouse.pojo.po.otherin.Otherin;
import com.warehouse.warehouse.pojo.po.otherin.OtherinDetail;
import com.warehouse.warehouse.pojo.po.stock.Stock;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.Shelves;
import com.warehouse.warehouse.pojo.po.warehouse.shelves.locator.Locator;
import com.warehouse.warehouse.service.app.warehouse.shelves.ShelvesService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 货架表(Shelves)表服务实现类
 *
 * @author makejava
 * @since 2020-04-12 20:32:33
 */
@Service("shelvesService")
public class ShelvesServiceImpl implements ShelvesService {

    private static final Logger logger = LogManager.getLogger(ShelvesServiceImpl.class);

    @Resource
    private ShelvesMapper shelvesMapper;

    @Resource
    private LocatorMapper locatorMapper;

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private OtherinDetailMapper otherinDetailMapper;

    @Resource
    private OtherinMapper otherinMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ShelvesDTO describeById(String id) {
        ShelvesDTO dto = new ShelvesDTO();
        Shelves shelves = this.shelvesMapper.describeById(id);
        if (null != shelves) {
            BeanUtils.copyProperties(shelves, dto);
        }
        return dto;
    }

    @Override
    public ShelvesAndLocatorDTO describeShelfDetail(String id) {
        // 判断map 集合的key有没有当前货物名称 如果有flag变为1 数量相加 ，否则 增加新的key（商品名）和数量
        int flag = 0;
        //定义map进行 货物名称和数量  统计
        Map<String,Integer> GoodsNameAndNum = new HashMap();
        ShelvesDTO dto = new ShelvesDTO();
        //根据货架id查找到货价
        Shelves shelves = this.shelvesMapper.describeById(id);
        if (null != shelves) {
            BeanUtils.copyProperties(shelves, dto);
        }
        ShelvesAndLocatorDTO shelvesAndLocatorDTO  =new ShelvesAndLocatorDTO();
        shelvesAndLocatorDTO.setShelvesDTO(dto);
        List shelvesIdList = new ArrayList();
        shelvesIdList.add(shelves.getId());
        List<Locator> locators = new ArrayList<>();
        List<LocatorCaseCadeDTO> children = new ArrayList<>();
        //通过货架id查询到所有货位
        if (CollectionUtils.isNotEmpty(shelvesIdList)) {
            locators = locatorMapper.desrcibesLocatorByShelveseIdList(shelvesIdList);
        }
         List locatorNameList = new ArrayList();
        //对每一个货位遍历得到得到对应stock
        for(int i = 0;i<locators.size() ;i++){
            locatorNameList.add(locators.get(i).getName());
            LocatorCaseCadeDTO locatorCaseCadeDTO = new LocatorCaseCadeDTO();
            flag = 0;
            LocatorDTO locatorDTO=new LocatorDTO();
            BeanUtils.copyProperties(locators.get(i), locatorDTO);
            locatorCaseCadeDTO.setLocatorDTO(locatorDTO);

            children.add(locatorCaseCadeDTO);

            String  locatorId = locators.get(i).getId();
            Stock stock = new Stock();
            stock.setLocatorId(locatorId);
            List<Stock> stocks =stockMapper.select(stock);
            if (!CollectionUtils.isEmpty(stocks)){
                stock = stocks.get(0);


                // 如果map为空直接 添加第一条
                if(GoodsNameAndNum.size() == 0){
                    GoodsNameAndNum.put(stock.getCommodityName(),stock.getCqty());
                }
                //否则 遍历map的key与当前的商品名称是否相同 是 则数量相加，且flag=1，跳出循环
                else {
                    Iterator in = GoodsNameAndNum.entrySet().iterator();
                    while (in.hasNext()) {
                        Map.Entry entry = (Map.Entry)in.next();
                        String key =entry.getKey().toString();
                        if (key.equals(stock.getCommodityName())) {
                            GoodsNameAndNum.put(stock.getCommodityName(), GoodsNameAndNum.get(key)+stock.getCqty());
                            flag =1;
                            break;
                        }
                    }
                    //如果flag =0 则没有对应key 直接添加商品名称和数量，
                    if (flag == 0){
                        GoodsNameAndNum.put(stock.getCommodityName(),stock.getCqty()) ;
                    }

                }
            }
        }
        String GoodsNameAndNum1 = JSONObject.toJSONString(GoodsNameAndNum);
        String locatorNameList1 = JSONObject.toJSONString(locatorNameList);
        shelvesAndLocatorDTO.setShelvesDTO(dto);
        shelvesAndLocatorDTO.setMap1(GoodsNameAndNum1);
        shelvesAndLocatorDTO.setMap2(locatorNameList1);
        shelvesAndLocatorDTO.setChildren(children);
        return shelvesAndLocatorDTO;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ShelvesDTO> describeAllByLimit(long offset, long limit) {
        List<Shelves> list = this.shelvesMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ShelvesDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 条件查询多条数据
     *
     * @param shelvesDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ShelvesDTO> describeAll(ShelvesDTO shelvesDTO) {
        Shelves shelves = new Shelves();
        BeanUtils.copyProperties(shelvesDTO, shelves);
        List<Shelves> list = this.shelvesMapper.describeAll(shelves);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ShelvesDTO.class);
        } else {
            return new ArrayList<>();
        }

    }

    /**
     * 条件模糊查询多条数据
     *
     * @param shelvesDTO 对象
     * @return 对象列表
     */
    @Override
    public List<ShelvesDTO> describeFuzzy(ShelvesDTO shelvesDTO) {
        Shelves shelves = new Shelves();
        BeanUtils.copyProperties(shelvesDTO, shelves);
        List<Shelves> list = this.shelvesMapper.describeFuzzy(shelves);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), ShelvesDTO.class);
        } else {

            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param shelvesDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ShelvesDTO insertShelves(ShelvesDTO shelvesDTO) {
        Shelves shelves = new Shelves();
        BeanUtils.copyProperties(shelvesDTO, shelves);
        if (null == shelves.getId() || "".equals(shelves.getId())) {
            shelves.setId(UUID.randomUUID().toString());
        }
        Date date = new Date();
        if (null == shelves.getCreateTime()){
            shelves.setCreateTime(date);
        }
        if (null == shelves.getUpdateTime()){
            shelves.setUpdateTime(date);
        }
        this.shelvesMapper.insertShelves(shelves);
        return shelvesDTO;
    }

    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<ShelvesDTO> saveShelves(List<ShelvesDTO> list) {
        list.forEach(shelvesDTO -> {
            if (null == shelvesDTO.getId() || "".equals(shelvesDTO.getId())) {
                shelvesDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.shelvesMapper.saveShelves(JSON.parseArray(JSON.toJSONString(list), Shelves.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param shelvesDTO 实例对象
     * @return 实例对象
     */
    @Override
    public ShelvesDTO updateShelvesByPrimaryKey(ShelvesDTO shelvesDTO) {
        Shelves shelves = new Shelves();
        BeanUtils.copyProperties(shelvesDTO, shelves);
        this.shelvesMapper.updateShelvesByPrimaryKey(shelves);
        return this.describeById(shelves.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteShelvesByPrimaryKey(String id) {
        return this.shelvesMapper.deleteShelvesByPrimaryKey(id) > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteShelvesByPrimaryKeys(List<String> list) {
        return this.shelvesMapper.deleteShelvesByPrimaryKeys(list) == list.size();
    }

    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(ShelvesDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Shelves.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getShelvesName() && !"".equals(dto.getShelvesName())) {
                criteriaName.andLike("shelvesName", "%" + dto.getShelvesName() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
            }
            if (null != dto.getWarehouseName() && !"".equals(dto.getWarehouseName())) {
                criteriaName.andLike("warehouseName", "%" + dto.getWarehouseName() + "%");
            }
            if (null != dto.getCreateUserId() && !"".equals(dto.getCreateUserId())) {
                criteriaName.andLike("createUserId", "%" + dto.getCreateUserId() + "%");
            }
            if (null != dto.getCreateUserName() && !"".equals(dto.getCreateUserName())) {
                criteriaName.andLike("createUserName", "%" + dto.getCreateUserName() + "%");
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andLike("createTime", "%" + dto.getCreateTime() + "%");
            }
            if (null != dto.getUpdateUserId() && !"".equals(dto.getUpdateUserId())) {
                criteriaName.andLike("updateUserId", "%" + dto.getUpdateUserId() + "%");
            }
            if (null != dto.getUpdateUserName() && !"".equals(dto.getUpdateUserName())) {
                criteriaName.andLike("updateUserName", "%" + dto.getUpdateUserName() + "%");
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andLike("updateTime", "%" + dto.getUpdateTime() + "%");
            }
            if (null != dto.getStatus()) {
                criteriaName.andLike("status", "%" + dto.getStatus() + "%");
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
    private Example initExample(ShelvesDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Shelves.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getShelvesName() && !"".equals(dto.getShelvesName())) {
                criteriaName.andEqualTo("shelvesName", dto.getShelvesName());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
            }
            if (null != dto.getWarehouseName() && !"".equals(dto.getWarehouseName())) {
                criteriaName.andEqualTo("warehouseName", dto.getWarehouseName());
            }
            if (null != dto.getCreateUserId() && !"".equals(dto.getCreateUserId())) {
                criteriaName.andEqualTo("createUserId", dto.getCreateUserId());
            }
            if (null != dto.getCreateUserName() && !"".equals(dto.getCreateUserName())) {
                criteriaName.andEqualTo("createUserName", dto.getCreateUserName());
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andEqualTo("createTime", dto.getCreateTime());
            }
            if (null != dto.getUpdateUserId() && !"".equals(dto.getUpdateUserId())) {
                criteriaName.andEqualTo("updateUserId", dto.getUpdateUserId());
            }
            if (null != dto.getUpdateUserName() && !"".equals(dto.getUpdateUserName())) {
                criteriaName.andEqualTo("updateUserName", dto.getUpdateUserName());
            }
            if (null != dto.getUpdateTime()) {
                criteriaName.andEqualTo("updateTime", dto.getUpdateTime());
            }
            if (null != dto.getStatus()) {
                criteriaName.andEqualTo("status", dto.getStatus());
            }
        }
        return example;
    }

    @Override
    public List<ShelvesAndLocatorDTO> describeShelvesAndLocationByWarehouseId(String warehouseId) {
        // 1、 获取仓库下的所有货架
        List<Shelves> list = shelvesMapper.describesAllShelvesByWarehouseId(warehouseId);
        List<ShelvesAndLocatorDTO> returnList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> shelvesIdList = new ArrayList<>();
            list.forEach(shelves -> {
                // 2 获取所有的货架ID
                String shelvesId = shelves.getId();
                if (shelvesId != null) {
                    shelvesIdList.add(shelvesId);
                }
            });
            // 3、根据货架id集合查询出所有的货位信息
            List<Locator> locators = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(shelvesIdList)) {
                locators = locatorMapper.desrcibesLocatorByShelveseIdList(shelvesIdList);
            }
            // 4、 封装数据
            for (Shelves shelves : list) {
                ShelvesAndLocatorDTO shelvesAndLocatorDTO = new ShelvesAndLocatorDTO();
                shelvesAndLocatorDTO.setLabel(shelves.getShelvesName());
                shelvesAndLocatorDTO.setValue(shelves.getId());
                ShelvesDTO shelvesDTO = new ShelvesDTO();
                BeanUtils.copyProperties(shelves, shelvesDTO);
                shelvesAndLocatorDTO.setShelvesDTO(shelvesDTO);
                // 设置子数据，也就是货位数据
                List<LocatorCaseCadeDTO> children = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(locators)) {
                    locators.forEach(locator -> {
                        // 如果货架id等于此货位的引用货架id
                        if (shelves.getId().equals(locator.getShelvesId())) {
                            LocatorCaseCadeDTO locatorCaseCadeDTO = new LocatorCaseCadeDTO();
                            locatorCaseCadeDTO.setValue(locator.getId());
                            locatorCaseCadeDTO.setLabel(locator.getName());
                            LocatorDTO locatorDTO = new LocatorDTO();
                            BeanUtils.copyProperties(locator, locatorDTO);
                            locatorCaseCadeDTO.setLocatorDTO(locatorDTO);
                            children.add(locatorCaseCadeDTO);
                        }
                    });
                    shelvesAndLocatorDTO.setChildren(children);
                }
                returnList.add(shelvesAndLocatorDTO);
            }
        }
        return returnList;
    }

    @Override
    public List<ShelvesAndLocatorDTO> describeUsableShelvesAndLocationByWarehouseId(String warehouseId, String goodsNo,String batch ) {
        // 1、 获取仓库下的所有可用货架
        List<Shelves> list = shelvesMapper.describesAllUsableShelvesByWarehouseId(warehouseId);
        logger.info("查询出来的货架信息为：{}",JSON.toJSONString(list));
        List<ShelvesAndLocatorDTO> returnList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> shelvesIdList = new ArrayList<>();
            list.forEach(shelves -> {
                // 2 获取所有的货架ID
                String shelvesId = shelves.getId();
                if (shelvesId != null) {
                    shelvesIdList.add(shelvesId);
                }
            });
            // 3、根据货架id集合查询出所有的有可能可用于入库的货位信息
            List<Locator> locators = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(shelvesIdList)) {
                // 3 4 无数据
                locators = locatorMapper.desrcibesUsableLocatorByShelveseIdList(shelvesIdList);
            }
            // 对locators筛选符合条件的locator
            List<Locator> suitlocators = new ArrayList<>();
            for (int i = 0; i < locators.size(); i++) {
                // 获取每个记录的locatorId、状态、locator实体
                String locatorId = locators.get(i).getId();
                String locatorState = locators.get(i).getState();
                Locator locator = locators.get(i);
                // locator的状态为可用
                if ("0".equals(locatorState)) {
                    // 可用分为两种已用或者空
                    Stock stock1 = new Stock();
                    stock1.setLocatorId(locatorId);
                    List<Stock> stocks = stockMapper.select(stock1);
                    if (CollectionUtils.isEmpty(stocks)) {
                        // 为空
                        suitlocators.add(locator);
                    } else {
                        // 已用 返回已经使用的货位
                        if (goodsNo.equals(stocks.get(0).getCommodityBar())&batch.equals(stocks.get(0).getBatch())) {
                            suitlocators.add(locator);
                        }
                    }
                }
                // locator的状态为预备状态，即之前有入库单数据还未审核，逻辑上占用空间
                if ("3".equals(locatorState)) {
                    // 首先获取已提交但未审核的入库单子表数据 一个locator可以对应多个入库单子表数据，
                    List<OtherinDetail> otherinDetailList = otherinDetailMapper.findInDetailListByLocatorId(locatorId);
                    if (CollectionUtils.isNotEmpty(otherinDetailList)) {
                        // 存在记录
                        int totalQty = 0;
                        List<Otherin> otherins = new ArrayList();
                        for (OtherinDetail otherinDetail : otherinDetailList) {
                            // 统计所有符合条件的入库单数目
                            Otherin otherin  = new Otherin();
                            otherin.setOtherinNo(otherinDetail.getPid());
                            otherins = otherinMapper.describeFuzzy(otherin);
                            otherin = otherins.get(0);
                            System.out.println();
                            if (goodsNo.equals(otherinDetail.getCommodityBar()) && batch.equals(otherinDetail.getBatch()) && ("0".equals(otherin.getState()) || "4".equals(otherin.getState()))) {
                                // 对于符合条件的记录汇总判断满不满，符合条件就把当前locator添加到 suitlocators
                                int singleQty = otherinDetail.getQty();
                                totalQty += singleQty;
                            }}
                                // 容量判断逻辑
                                Example example = new Example(Good.class);
                                Example.Criteria criteria = example.createCriteria();
                                criteria.andEqualTo("goodNo", goodsNo);
                                Good good = goodMapper.selectOneByExample(example);
                                // 判断满不满的逻辑
                                if (good != null) {
                                    double maxLoadWeight = locator.getWeight();
                                    double logicalAndPhysicsUseWeight = good.getWeigth() * totalQty;
                                    if (logicalAndPhysicsUseWeight < maxLoadWeight) {
                                        // 证明该货位可以继续添加商品
                                        suitlocators.add(locator);
                                    }
                                }

                    }
                }
            }
            for (Shelves shelves : list) {
                ShelvesAndLocatorDTO shelvesAndLocatorDTO = new ShelvesAndLocatorDTO();
                shelvesAndLocatorDTO.setLabel(shelves.getShelvesName());
                shelvesAndLocatorDTO.setValue(shelves.getId());
                ShelvesDTO shelvesDTO = new ShelvesDTO();
                BeanUtils.copyProperties(shelves, shelvesDTO);
                shelvesAndLocatorDTO.setShelvesDTO(shelvesDTO);
                // 设置子数据，也就是货位数据
                List<LocatorCaseCadeDTO> children = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(suitlocators)) {
                    suitlocators.forEach(locator -> {
                        // 如果货架id等于此货位的引用货架id
                        if (shelves.getId().equals(locator.getShelvesId())) {
                            LocatorCaseCadeDTO locatorCaseCadeDTO = new LocatorCaseCadeDTO();
                            locatorCaseCadeDTO.setValue(locator.getId());
                            locatorCaseCadeDTO.setLabel(locator.getName());
                            LocatorDTO locatorDTO = new LocatorDTO();
                            BeanUtils.copyProperties(locator, locatorDTO);
                            locatorCaseCadeDTO.setLocatorDTO(locatorDTO);
                            children.add(locatorCaseCadeDTO);
                        }
                    });
                    shelvesAndLocatorDTO.setChildren(children);
                }
                if (CollectionUtils.isNotEmpty(children)) {
                    returnList.add(shelvesAndLocatorDTO);
                }
            }
        }
        return returnList;
    }





    /**
     * @title: describeByLocatorId
     * @description: 根据货位id 查询货架 信息
     * @params: [ locatorId ]
     * @return: com.system.service.dto.ShelvesDTO
     * @createTime: 2020/4/18 14:13
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public ShelvesDTO describeByLocatorId(String locatorId) {
        Shelves shelves = shelvesMapper.describeByLocatorId(locatorId);
        ShelvesDTO dto = new ShelvesDTO();
        BeanUtils.copyProperties(shelves, dto);
        return dto;
    }

    /**
     * @title: describeAllMineGroupByWarehouse
     * @description: 查询用户所有数据(级联)
     * @params: [ userId ]
     * @return: java.util.List<com.system.service.dto.shelves.WarehouseAndShelvesDTO>
     * @createTime: 2020/5/10 15:42
     * @version: 1.0
     * @author: wanpp
     */
    @Override
    public List<WarehouseAndShelvesDTO> describeAllMineGroupByWarehouse(String userId) {
        List<Shelves> shelvesList = shelvesMapper.describeAllMineGroupByWarehouse(userId);
        List<WarehouseAndShelvesDTO> returnList = new ArrayList<>();
        // 获取所有的仓库信息
        List<String> warehouseIdList = new ArrayList<>();
        shelvesList.forEach(shelves->{
            if (!warehouseIdList.contains(shelves.getWarehouseId())){
                warehouseIdList.add(shelves.getWarehouseId());
                WarehouseAndShelvesDTO warehouseAndShelvesDTO = new WarehouseAndShelvesDTO();
                warehouseAndShelvesDTO.setValue(shelves.getWarehouseId());
                warehouseAndShelvesDTO.setLabel(shelves.getWarehouseName());
                List<WarehouseChidrenDTO> list = new ArrayList<>();
                warehouseAndShelvesDTO.setChildren(list);
                returnList.add(warehouseAndShelvesDTO);
            }
        });

        //组装数据
        returnList.forEach(warehouseAndShelvesDTO->{
            shelvesList.forEach(shelves->{
                if (shelves.getWarehouseId().equals(warehouseAndShelvesDTO.getValue())){
                    WarehouseChidrenDTO warehouseChidrenDTO  =  new WarehouseChidrenDTO();
                    warehouseChidrenDTO.setLabel(shelves.getShelvesName());
                    warehouseChidrenDTO.setValue(shelves.getId());
                    ShelvesDTO shelvesDTO = new ShelvesDTO();
                    BeanUtils.copyProperties(shelves,shelvesDTO);
                    warehouseChidrenDTO.setShelvesDTO(shelvesDTO);
                    List<WarehouseChidrenDTO> list = warehouseAndShelvesDTO.getChildren();
                    list.add(warehouseChidrenDTO);
                    warehouseAndShelvesDTO.setChildren(list);
                }
            });
        });

        return returnList;
    }

    @Override
    public List<ShelvesDTO> describeAllShellfByWarehouseId(String warehouseId) {
        List<Shelves> shelvesList = shelvesMapper.describesAllShelvesByWarehouseId(warehouseId);
        List<ShelvesDTO> shelvesDTOList = new ArrayList<>();
        for(int i =0;i<shelvesList.size();i++){
            ShelvesDTO shelvesDTO = new ShelvesDTO();
            BeanUtils.copyProperties(shelvesList.get(i),shelvesDTO);
            shelvesDTOList.add(shelvesDTO);
        }
        return  shelvesDTOList;

    }


    @Override
    public String describeAllShelfNameByWarehouseId(String warehouseId) {
        List<Shelves> shelvesList = shelvesMapper.describesAllShelvesByWarehouseId(warehouseId);
        List<String> shelvesNameList = new ArrayList<>();
        for(int i =0;i<shelvesList.size();i++){
            shelvesNameList.add(shelvesList.get(i).getShelvesName());

        }
        String shelvesNameList1 = JSONObject.toJSONString(shelvesNameList);
        return  shelvesNameList1;
    }
}
