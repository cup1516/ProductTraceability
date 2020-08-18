package com.warehouse.warehouse.service.app.customer;

import com.warehouse.warehouse.pojo.dto.customer.CustomerDTO;

import java.util.List;

public interface CustomerService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CustomerDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CustomerDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param customerDTO 实例对象
     * @return 对象列表
     */
    List<CustomerDTO> describeAll(CustomerDTO customerDTO);

    /**
     * 条件模糊查询所有数据
     *
     * @param customerDTO 实例对象
     * @return 对象列表
     */
    List<CustomerDTO> describeFuzzy(CustomerDTO customerDTO);


    /**
     * 新增数据
     *
     * @param customerDTO 实例对象
     * @return 实例对象
     */
    CustomerDTO insertCustomer(CustomerDTO customerDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<CustomerDTO> saveCustomer(List<CustomerDTO> list);

    /**
     * 修改数据
     *
     * @param customerDTO 实例对象
     * @return 实例对象
     */
    CustomerDTO updateCustomerByPrimaryKey(CustomerDTO customerDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteCustomerByPrimaryKey(String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteCustomerByPrimaryKeys(List<String> list);



    public List<CustomerDTO> findAllCustomer();
}
