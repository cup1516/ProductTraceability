package com.warehouse.warehouse.service.impl.customer;
import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.customer.CustomerMapper;
import com.warehouse.warehouse.pojo.dto.customer.CustomerDTO;
import com.warehouse.warehouse.pojo.po.customer.Customer;
import com.warehouse.warehouse.service.app.customer.CustomerService;
import com.warehouse.warehouse.utils.InitExampleOrderByUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 供应商表(Customer)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CustomerDTO describeById(String id) {
        CustomerDTO dto = new CustomerDTO();
        Customer customer = this.customerMapper.describeById(id);
        if (null != customer) {
            BeanUtils.copyProperties(customer, dto);
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
    public List<CustomerDTO> describeAllByLimit(long offset, long limit) {
        List<Customer> list = this.customerMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), CustomerDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param customerDTO 对象
     * @return 对象列表
     */
    @Override
    public List<CustomerDTO> describeAll(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        List<Customer> list = this.customerMapper.describeAll(customer);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), CustomerDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param customerDTO 对象
     * @return 对象列表
     */
    @Override
    public List<CustomerDTO> describeFuzzy(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        List<Customer> list = this.customerMapper.describeFuzzy(customer);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), CustomerDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param customerDTO 实例对象
     * @return 实例对象
     */
    @Override
    public CustomerDTO insertCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        if (null == customer.getId() || "".equals(customer.getId())) {
            customer.setId(UUID.randomUUID().toString());
        }
        this.customerMapper.insertCustomer(customer);
        return customerDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<CustomerDTO> saveCustomer(List<CustomerDTO> list) {
        list.forEach(customerDTO -> {
            if (null == customerDTO.getId() || "".equals(customerDTO.getId())) {
                customerDTO.setId(UUID.randomUUID().toString());
            }
        });
        this.customerMapper.saveCustomer(JSON.parseArray(JSON.toJSONString(list), Customer.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param customerDTO 实例对象
     * @return 实例对象
     */
    @Override
    public CustomerDTO updateCustomerByPrimaryKey(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        this.customerMapper.updateCustomerByPrimaryKey(customer);
        return this.describeById(customer.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteCustomerByPrimaryKey(String id) {
        return this.customerMapper.deleteCustomerByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteCustomerByPrimaryKeys(List<String> list) {
        return this.customerMapper.deleteCustomerByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(CustomerDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Customer.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andLike("name", "%" + dto.getName() + "%");
            }
            if (null != dto.getContact() && !"".equals(dto.getContact())) {
                criteriaName.andLike("contact", "%" + dto.getContact() + "%");
            }
            if (null != dto.getPhone() && !"".equals(dto.getPhone())) {
                criteriaName.andLike("phone", "%" + dto.getPhone() + "%");
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
        }
        return example;
    }
    
    /**
     * 初始化 精确查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initExample(CustomerDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Customer.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getName() && !"".equals(dto.getName())) {
                criteriaName.andEqualTo("name", dto.getName());
            }
            if (null != dto.getContact() && !"".equals(dto.getContact())) {
                criteriaName.andEqualTo("contact", dto.getContact());
            }
            if (null != dto.getPhone() && !"".equals(dto.getPhone())) {
                criteriaName.andEqualTo("phone", dto.getPhone());
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
        }
        return example;
    }



    @Override
    public List<CustomerDTO> findAllCustomer() {
        List<Customer> customerList = customerMapper.findAllCustomer();
        if (null != customerList && customerList.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(customerList), CustomerDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
}