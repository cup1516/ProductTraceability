package com.warehouse.warehouse.service.impl.outorder;

import com.alibaba.fastjson.JSON;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.customer.CustomerMapper;
import com.warehouse.warehouse.dao.goods.GoodMapper;
import com.warehouse.warehouse.dao.otherout.OtheroutDetailMapper;
import com.warehouse.warehouse.dao.otherout.OtheroutMapper;
import com.warehouse.warehouse.dao.outorder.OutOrderMapper;
import com.warehouse.warehouse.dao.warehouse.WarehouseMapper;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutDTO;
import com.warehouse.warehouse.pojo.dto.outorder.OutOrderDTO;
import com.warehouse.warehouse.pojo.po.customer.Customer;
import com.warehouse.warehouse.pojo.po.goods.Good;
import com.warehouse.warehouse.pojo.po.otherout.Otherout;
import com.warehouse.warehouse.pojo.po.otherout.OtheroutDetail;
import com.warehouse.warehouse.pojo.po.outorder.OutOrder;
import com.warehouse.warehouse.pojo.po.warehouse.Warehouse;
import com.warehouse.warehouse.service.app.outorder.OutOrderService;
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
 * (OutOrder)表服务实现类
 *
 * @author makejava
 * @since 2020-05-10 19:12:23
 */
@Service("outOrderService")
public class OutOrderServiceImpl implements OutOrderService {

    @Resource
    private OutOrderMapper outOrderMapper;
    @Resource
    private OtheroutMapper otheroutMapper;
    @Resource
    private GoodMapper goodMapper;
    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private OtheroutDetailMapper otheroutDetailMapper;
    @Resource
    private CustomerMapper customerMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OutOrderDTO describeById(String id) {
        OutOrderDTO dto = new OutOrderDTO();
        OutOrder outOrder = this.outOrderMapper.describeById(id);
        if (null != outOrder) {
            BeanUtils.copyProperties(outOrder, dto);
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
    public List<OutOrderDTO> describeAllByLimit(long offset, long limit) {
        List<OutOrder> list = this.outOrderMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutOrderDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param outOrderDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OutOrderDTO> describeAll(OutOrderDTO outOrderDTO) {
        OutOrder outOrder = new OutOrder();
        BeanUtils.copyProperties(outOrderDTO, outOrder);
        List<OutOrder> list = this.outOrderMapper.describeAll(outOrder);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutOrderDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param outOrderDTO 对象
     * @return 对象列表
     */
    @Override
    public List<OutOrderDTO> describeFuzzy(OutOrderDTO outOrderDTO) {
        OutOrder outOrder = new OutOrder();
        BeanUtils.copyProperties(outOrderDTO, outOrder);
        List<OutOrder> list = this.outOrderMapper.describeFuzzy(outOrder);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutOrderDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param
     * @return 实例对象
     */
    @Override
    public OutOrderDTO insertOutOrder(OtheroutDTO otheroutDTO) {

        Otherout otherout = otheroutMapper.describeById(otheroutDTO.getId());
        System.out.println(otherout+"666ssss");
        Good good = new Good();
        Customer customer = new Customer();
        customer.setName(otherout.getCustomerName());
        List<Customer> customers = customerMapper.describeFuzzy(customer);
        customer = customers.get(0);
        Warehouse warehouse = warehouseMapper.describeById(otherout.getWarehouseId());
        OtheroutDetail otheroutDetail = new OtheroutDetail();
        otheroutDetail.setPid(otherout.getOtheroutNo());
        List<OtheroutDetail> detailList = otheroutDetailMapper.describeFuzzy(otheroutDetail);
        otheroutDetail = detailList.get(0);
        good.setGoodNo(otheroutDetail.getCommodityBar());
        List<Good> goodList = goodMapper.describeFuzzy(good);
        good = goodList.get(0);
        OutOrder outOrder = new OutOrder();
        OutOrderDTO outOrderDTO = new OutOrderDTO();
        if (null == outOrder.getId() || "".equals(outOrder.getId())) {
            outOrder.setId(UUID.randomUUID().toString());
        }
        if (null == outOrder.getCreateTime() || "".equals(outOrder.getCreateTime())) {
            outOrder.setCreateTime(new Date());
        }
        outOrder.setWarehouseId(otherout.getWarehouseId());
        outOrder.setWarehouseName(warehouse.getName());
        outOrder.setCreateId(otherout.getCreateId());
        outOrder.setCreator(otherout.getCreator());
        outOrder.setCompanyNo("1200");
        outOrder.setCompanyName("仓库管理公司");
        outOrder.setCompanyId(SecurityUtils.getCompanyId());
        outOrder.setOtheroutNo(otherout.getOtheroutNo());
        outOrder.setCustomerId(otherout.getCustomerId());
        outOrder.setCustomerName(otherout.getCustomerName());
        outOrder.setCusCompany(otheroutDetail.getOutcompany());
        outOrder.setRemark(otherout.getRemark());
        outOrder.setGoodNo(otheroutDetail.getCommodityBar());
        outOrder.setGoodNum(otheroutDetail.getQty());
        outOrder.setBatch(otheroutDetail.getBatch());
        outOrder.setGoodPrice(good.getPrice());
        outOrder.setTotPrice(otheroutDetail.getQty()* good.getPrice());
        BeanUtils.copyProperties(outOrder, outOrderDTO);
        this.outOrderMapper.insertOutOrder(outOrder);
        return outOrderDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<OutOrderDTO> saveOutOrder(List<OutOrderDTO> list) {
        list.forEach(outOrderDTO -> {
            if (null == outOrderDTO.getId() || "".equals(outOrderDTO.getId())) {
                outOrderDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.outOrderMapper.saveOutOrder(JSON.parseArray(JSON.toJSONString(list), OutOrder.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param outOrderDTO 实例对象
     * @return 实例对象
     */
    @Override
    public OutOrderDTO updateOutOrderByPrimaryKey(OutOrderDTO outOrderDTO) {
        OutOrder outOrder = new OutOrder();
        BeanUtils.copyProperties(outOrderDTO, outOrder);
        this.outOrderMapper.updateOutOrderByPrimaryKey(outOrder);
        return this.describeById(outOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteOutOrderByPrimaryKey(String id) {
        return this.outOrderMapper.deleteOutOrderByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteOutOrderByPrimaryKeys(List<String> list) {
        return this.outOrderMapper.deleteOutOrderByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(OutOrderDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OutOrder.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getOtheroutNo() && !"".equals(dto.getOtheroutNo())) {
                criteriaName.andLike("otheroutNo", "%" + dto.getOtheroutNo() + "%");
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andLike("pid", "%" + dto.getPid() + "%");
            }
            if (null != dto.getCompanyNo() && !"".equals(dto.getCompanyNo())) {
                criteriaName.andLike("companyNo", "%" + dto.getCompanyNo() + "%");
            }
            if (null != dto.getCompanyName() && !"".equals(dto.getCompanyName())) {
                criteriaName.andLike("companyName", "%" + dto.getCompanyName() + "%");
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andLike("remark", "%" + dto.getRemark() + "%");
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andLike("warehouseId", "%" + dto.getWarehouseId() + "%");
            }
            if (null != dto.getWarehouseName() && !"".equals(dto.getWarehouseName())) {
                criteriaName.andLike("warehouseName", "%" + dto.getWarehouseName() + "%");
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andLike("createTime", "%" + dto.getCreateTime() + "%");
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andLike("createId", "%" + dto.getCreateId() + "%");
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andLike("creator", "%" + dto.getCreator() + "%");
            }
            if (null != dto.getCustomerId() && !"".equals(dto.getCustomerId())) {
                criteriaName.andLike("customerId", "%" + dto.getCustomerId() + "%");
            }
            if (null != dto.getState()) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
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
    private Example initExample(OutOrderDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(OutOrder.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getOtheroutNo() && !"".equals(dto.getOtheroutNo())) {
                criteriaName.andEqualTo("otheroutNo", dto.getOtheroutNo());
            }
            if (null != dto.getPid() && !"".equals(dto.getPid())) {
                criteriaName.andEqualTo("pid", dto.getPid());
            }
            if (null != dto.getCompanyNo() && !"".equals(dto.getCompanyNo())) {
                criteriaName.andEqualTo("companyNo", dto.getCompanyNo());
            }
            if (null != dto.getCompanyName() && !"".equals(dto.getCompanyName())) {
                criteriaName.andEqualTo("companyName", dto.getCompanyName());
            }
            if (null != dto.getRemark() && !"".equals(dto.getRemark())) {
                criteriaName.andEqualTo("remark", dto.getRemark());
            }
            if (null != dto.getWarehouseId() && !"".equals(dto.getWarehouseId())) {
                criteriaName.andEqualTo("warehouseId", dto.getWarehouseId());
            }
            if (null != dto.getWarehouseName() && !"".equals(dto.getWarehouseName())) {
                criteriaName.andEqualTo("warehouseName", dto.getWarehouseName());
            }
            if (null != dto.getCreateTime()) {
                criteriaName.andEqualTo("createTime", dto.getCreateTime());
            }
            if (null != dto.getCreateId() && !"".equals(dto.getCreateId())) {
                criteriaName.andEqualTo("createId", dto.getCreateId());
            }
            if (null != dto.getCreator() && !"".equals(dto.getCreator())) {
                criteriaName.andEqualTo("creator", dto.getCreator());
            }
            if (null != dto.getCustomerId() && !"".equals(dto.getCustomerId())) {
                criteriaName.andEqualTo("customerId", dto.getCustomerId());
            }
            if (null != dto.getState()) {
                criteriaName.andEqualTo("state", dto.getState());
            }
        }
        return example;
    }

    @Override
    public List<OutOrderDTO> describeFuzzyCompany(OutOrderDTO outOrderDTO) {
        OutOrder outOrder = new OutOrder();
        BeanUtils.copyProperties(outOrderDTO, outOrder);
        outOrder.setCusCompany(SecurityUtils.getCompanyId());
        List<OutOrder> list = this.outOrderMapper.describeFuzzyCompany(outOrder);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), OutOrderDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
}