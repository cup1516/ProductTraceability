package com.warehouse.warehouse.service.app.employee;

import com.warehouse.warehouse.pojo.dto.employee.EmployeeDTO;

import java.util.List;

/**
 * (Employee)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
public interface EmployeeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EmployeeDTO describeById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<EmployeeDTO> describeAllByLimit(long offset, long limit);

    /**
     * 条件查询所有数据
     *
     * @param employeeDTO 实例对象
     * @return 对象列表
     */
    List<EmployeeDTO> describeAll(EmployeeDTO employeeDTO);

    /**
     * 条件模糊查询所有数据
     *
     * @param employeeDTO 实例对象
     * @return 对象列表
     */
    List<EmployeeDTO> describeFuzzy(EmployeeDTO employeeDTO);


    /**
     * 新增数据
     *
     * @param employeeDTO 实例对象
     * @return 实例对象
     */
    EmployeeDTO insertEmployee(EmployeeDTO employeeDTO);

    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    List<EmployeeDTO> saveEmployee(List<EmployeeDTO> list);

    /**
     * 修改数据
     *
     * @param employeeDTO 实例对象
     * @return 实例对象
     */
    EmployeeDTO updateEmployeeByPrimaryKey(EmployeeDTO employeeDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteEmployeeByPrimaryKey(String id);

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    boolean deleteEmployeeByPrimaryKeys(List<String> list);

}