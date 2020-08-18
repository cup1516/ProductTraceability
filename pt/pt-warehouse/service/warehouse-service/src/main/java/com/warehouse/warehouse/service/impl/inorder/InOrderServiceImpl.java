package com.warehouse.warehouse.service.impl.inorder;

import com.alibaba.fastjson.JSON;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.dao.goods.GoodMapper;
import com.warehouse.warehouse.dao.inorder.InOrderMapper;
import com.warehouse.warehouse.dao.otherin.OtherinDetailMapper;
import com.warehouse.warehouse.dao.otherin.OtherinMapper;
import com.warehouse.warehouse.dao.supplier.SupplierMapper;
import com.warehouse.warehouse.dao.warehouse.WarehouseMapper;
import com.warehouse.warehouse.pojo.dto.inorder.InOrderDTO;
import com.warehouse.warehouse.pojo.dto.otherin.OtherinDTO;
import com.warehouse.warehouse.pojo.po.goods.Good;
import com.warehouse.warehouse.pojo.po.inorder.InOrder;
import com.warehouse.warehouse.pojo.po.otherin.Otherin;
import com.warehouse.warehouse.pojo.po.otherin.OtherinDetail;
import com.warehouse.warehouse.pojo.po.supplier.Supplier;
import com.warehouse.warehouse.pojo.po.warehouse.Warehouse;
import com.warehouse.warehouse.service.app.inorder.InOrderService;
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
 * (InOrder)表服务实现类
 *
 * @author makejava
 * @since 2020-05-10 19:12:20
 */
@Service("inOrderService")
public class InOrderServiceImpl implements InOrderService {

    @Resource
    private InOrderMapper inOrderMapper;
    @Resource
    private OtherinMapper otherinMapper;
    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private OtherinDetailMapper otherinDetailMapper;
    @Resource
    private GoodMapper goodMapper;
    @Resource
    private SupplierMapper supplierMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InOrderDTO describeById(String id) {
        InOrderDTO dto = new InOrderDTO();
        InOrder inOrder = this.inOrderMapper.describeById(id);
        if (null != inOrder) {
            BeanUtils.copyProperties(inOrder, dto);
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
    public List<InOrderDTO> describeAllByLimit(long offset, long limit) {
        List<InOrder> list = this.inOrderMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), InOrderDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 条件查询多条数据
     *
     * @param inOrderDTO 对象
     * @return 对象列表
     */
    @Override
    public List<InOrderDTO> describeAll(InOrderDTO inOrderDTO) {
        InOrder inOrder = new InOrder();
        BeanUtils.copyProperties(inOrderDTO, inOrder);
        List<InOrder> list = this.inOrderMapper.describeAll(inOrder);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), InOrderDTO.class);
        } else {
            return new ArrayList<>();
        }

    }

    /**
     * 条件模糊查询多条数据
     *
     * @param inOrderDTO 对象
     * @return 对象列表
     */
    @Override
    public List<InOrderDTO> describeFuzzy(InOrderDTO inOrderDTO) {
        InOrder inOrder = new InOrder();
        BeanUtils.copyProperties(inOrderDTO, inOrder);
        List<InOrder> list = this.inOrderMapper.describeFuzzy(inOrder);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), InOrderDTO.class);
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
    public InOrderDTO insertInOrder(OtherinDTO otherinDTO) {
        Otherin otherin = otherinMapper.describeById(otherinDTO.getId());
        Good good = new Good();
        Supplier supplier = new Supplier();
        supplier.setName(otherinDTO.getSupplierName());
        List<Supplier> suppliers = supplierMapper.describeFuzzy(supplier);
        supplier = suppliers.get(0);
        Warehouse warehouse = warehouseMapper.describeById(otherin.getWarehouseId());
        OtherinDetail otherinDetail = new OtherinDetail();
        otherinDetail.setPid(otherin.getOtherinNo());
        List<OtherinDetail> detailList = otherinDetailMapper.describeFuzzy(otherinDetail);
        otherinDetail = detailList.get(0);
        good.setGoodNo(otherinDetail.getCommodityBar());
        List<Good> goodList = goodMapper.describeFuzzy(good);
        good = goodList.get(0);
        InOrder inOrder = new InOrder();
        InOrderDTO inOrderDTO = new InOrderDTO();
        if (null == inOrder.getId() || "".equals(inOrder.getId())) {
            inOrder.setId(UUID.randomUUID().toString());
        }
        if (null == inOrder.getCreateTime() || "".equals(inOrder.getCreateTime())) {
            inOrder.setCreateTime(new Date());
        }
        inOrder.setWarehouseId(otherin.getWarehouseId());
        inOrder.setWarehouseName(warehouse.getName());
        inOrder.setCreateId(otherin.getCreateId());
        inOrder.setCreator(otherin.getCreator());
        inOrder.setSupCompany(otherinDetail.getIncompany());
        System.out.println(otherinDetail.getIncompany()+"iiiuuuuuu");
        inOrder.setCompanyNo(SecurityUtils.getCompanyId());
        inOrder.setCompanyName(SecurityUtils.getCompanyId()+"号仓库");
        inOrder.setOtherinNo(otherin.getOtherinNo());
        inOrder.setSupplierId(otherin.getSupplierId());
        inOrder.setSupplierName(otherin.getSupplierName());
        inOrder.setRemark(otherin.getRemark());
        inOrder.setGoodNo(otherinDetail.getCommodityBar());
        inOrder.setGoodNum(otherinDetail.getQty());
        inOrder.setBatch(otherinDetail.getBatch());
        inOrder.setGoodPrice(good.getPrice());
        inOrder.setCompanyId(SecurityUtils.getCompanyId());
        inOrder.setTotPrice(otherinDetail.getQty() * good.getPrice());
        BeanUtils.copyProperties(inOrder, inOrderDTO);
        this.inOrderMapper.insertInOrder(inOrder);
        return inOrderDTO;
    }

    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<InOrderDTO> saveInOrder(List<InOrderDTO> list) {
        list.forEach(inOrderDTO -> {
            if (null == inOrderDTO.getId() || "".equals(inOrderDTO.getId())) {
                inOrderDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.inOrderMapper.saveInOrder(JSON.parseArray(JSON.toJSONString(list), InOrder.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param inOrderDTO 实例对象
     * @return 实例对象
     */
    @Override
    public InOrderDTO updateInOrderByPrimaryKey(InOrderDTO inOrderDTO) {
        InOrder inOrder = new InOrder();
        BeanUtils.copyProperties(inOrderDTO, inOrder);
        this.inOrderMapper.updateInOrderByPrimaryKey(inOrder);
        return this.describeById(inOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteInOrderByPrimaryKey(String id) {
        return this.inOrderMapper.deleteInOrderByPrimaryKey(id) > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteInOrderByPrimaryKeys(List<String> list) {
        return this.inOrderMapper.deleteInOrderByPrimaryKeys(list) == list.size();
    }

    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(InOrderDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(InOrder.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getOtherinNo() && !"".equals(dto.getOtherinNo())) {
                criteriaName.andLike("otherinNo", "%" + dto.getOtherinNo() + "%");
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
            if (null != dto.getState()) {
                criteriaName.andLike("state", "%" + dto.getState() + "%");
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
            if (null != dto.getSupplierId() && !"".equals(dto.getSupplierId())) {
                criteriaName.andLike("supplierId", "%" + dto.getSupplierId() + "%");
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
    private Example initExample(InOrderDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(InOrder.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getOtherinNo() && !"".equals(dto.getOtherinNo())) {
                criteriaName.andEqualTo("otherinNo", dto.getOtherinNo());
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
            if (null != dto.getState()) {
                criteriaName.andEqualTo("state", dto.getState());
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
            if (null != dto.getSupplierId() && !"".equals(dto.getSupplierId())) {
                criteriaName.andEqualTo("supplierId", dto.getSupplierId());
            }
        }
        return example;
    }




    @Override
    public List<InOrderDTO> describeFuzzyCompany(InOrderDTO inOrderDTO) {
        InOrder inOrder = new InOrder();
        BeanUtils.copyProperties(inOrderDTO, inOrder);
        inOrder.setSupCompany(SecurityUtils.getCompanyId());
        List<InOrder> list = this.inOrderMapper.describeFuzzyCompany(inOrder);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), InOrderDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

}