package com.warehouse.warehouse.dao.customer;
import com.warehouse.warehouse.pojo.po.customer.Customer;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 供应商表(Customer)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */

public interface CustomerMapper extends Mapper<Customer> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Customer describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Customer> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param customer 实例对象
     * @return 对象列表
     */
    List<Customer> describeAll(Customer customer);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param customer 实例对象
     * @return 对象列表
     */
    List<Customer> describeFuzzy(Customer customer);

    /**
     * 新增数据
     *
     * @param customer 实例对象
     * @return 影响行数
     */
    Integer insertCustomer(Customer customer);

    /**
     * 新增数据
     *
     * @param List<customer> 实例对象
     * @return 影响行数
     */
    Integer saveCustomer(@Param("list") List<Customer> list);

    /**
     * 修改数据
     *
     * @param customer 实例对象
     * @return 影响行数
     */
    Integer updateCustomerByPrimaryKey(Customer customer);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteCustomerByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteCustomerByPrimaryKeys(@Param("list") List<String> list);


    List<Customer> findAllCustomer();

}