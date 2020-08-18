package com.warehouse.warehouse.dao.employee;

import com.warehouse.warehouse.pojo.po.employee.Employee;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Employee)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */

public interface EmployeeMapper extends Mapper<Employee> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Employee describeById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Employee> describeAllByLimit(@Param("offset") long offset, @Param("limit") long limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param employee 实例对象
     * @return 对象列表
     */
    List<Employee> describeAll(Employee employee);

    /**
     * 通过实体作为筛选条件模糊查询
     *
     * @param employee 实例对象
     * @return 对象列表
     */
    List<Employee> describeFuzzy(Employee employee);

    /**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 影响行数
     */
    Integer insertEmployee(Employee employee);

    /**
     * 新增数据
     *
     * @param List<employee> 实例对象
     * @return 影响行数
     */
    Integer saveEmployee(@Param("list") List<Employee> list);

    /**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 影响行数
     */
    Integer updateEmployeeByPrimaryKey(Employee employee);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    Integer deleteEmployeeByPrimaryKey(@Param("id") String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 影响行数
     */
    Integer deleteEmployeeByPrimaryKeys(@Param("list") List<String> list);

}