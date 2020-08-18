package com.warehouse.warehouse.service.impl.employee;

import com.alibaba.fastjson.JSON;
import com.warehouse.warehouse.dao.employee.EmployeeMapper;
import com.warehouse.warehouse.pojo.dto.employee.EmployeeDTO;
import com.warehouse.warehouse.pojo.po.employee.Employee;
import com.warehouse.warehouse.service.app.employee.EmployeeService;
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
 * (Employee)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EmployeeDTO describeById(String id) {
        EmployeeDTO dto = new EmployeeDTO();
        Employee employee = this.employeeMapper.describeById(id);
        if (null != employee) {
            BeanUtils.copyProperties(employee, dto);
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
    public List<EmployeeDTO> describeAllByLimit(long offset, long limit) {
        List<Employee> list = this.employeeMapper.describeAllByLimit(offset, limit);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), EmployeeDTO.class);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * 条件查询多条数据
     *
     * @param employeeDTO 对象
     * @return 对象列表
     */
    @Override
    public List<EmployeeDTO> describeAll(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        List<Employee> list = this.employeeMapper.describeAll(employee);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), EmployeeDTO.class);
        } else {
            return new ArrayList<>();
        }
        
    }
    
    /**
     * 条件模糊查询多条数据
     *
     * @param employeeDTO 对象
     * @return 对象列表
     */
    @Override
    public List<EmployeeDTO> describeFuzzy(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        List<Employee> list = this.employeeMapper.describeFuzzy(employee);
        if (null != list && list.size() > 0) {
            return JSON.parseArray(JSON.toJSONString(list), EmployeeDTO.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 新增数据
     *
     * @param employeeDTO 实例对象
     * @return 实例对象
     */
    @Override
    public EmployeeDTO insertEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        if (null == employee.getId() || "".equals(employee.getId())) {
            employee.setId(UUID.randomUUID().toString());
        }
        if (null == employee.getInTime()){
            employee.setInTime(new Date());
        }
        if (null == employee.getPassword() || "".equals(employee.getPassword())){
            employee.setPassword("warehouse");
        }
        this.employeeMapper.insertEmployee(employee);
        return employeeDTO;
    }
    
    /**
     * 新增集合数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @Override
    public List<EmployeeDTO> saveEmployee(List<EmployeeDTO> list) {
        list.forEach(employeeDTO -> {
            if (null == employeeDTO.getId() || "".equals(employeeDTO.getId())) {
                employeeDTO.setId(UUID.randomUUID().toString());
            }
            if (null == employeeDTO.getInTime()){
                employeeDTO.setInTime(new Date());
            }
            if (null == employeeDTO.getPassword() || "".equals(employeeDTO.getPassword())){
                employeeDTO.setPassword("warehouse");
            }
        });
        this.employeeMapper.saveEmployee(JSON.parseArray(JSON.toJSONString(list), Employee.class));
        return list;
    }

    /**
     * 修改数据
     *
     * @param employeeDTO 实例对象
     * @return 实例对象
     */
    @Override
    public EmployeeDTO updateEmployeeByPrimaryKey(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        this.employeeMapper.updateEmployeeByPrimaryKey(employee);
        return this.describeById(employee.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteEmployeeByPrimaryKey(String id) {
        return this.employeeMapper.deleteEmployeeByPrimaryKey(id) > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @Override
    public boolean deleteEmployeeByPrimaryKeys(List<String> list) {
        return this.employeeMapper.deleteEmployeeByPrimaryKeys(list) == list.size();
    }
    
    /**
     * 初始化 模糊查询 example
     *
     * @param dto 数据对象
     * @return Example
     */
    private Example initFuzzyExample(EmployeeDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Employee.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andLike("id", "%" + dto.getId() + "%");
            }
            if (null != dto.getEmployeeName() && !"".equals(dto.getEmployeeName())) {
                criteriaName.andLike("employeeName", "%" + dto.getEmployeeName() + "%");
            }
            if (null != dto.getPassword() && !"".equals(dto.getPassword())) {
                criteriaName.andLike("password", "%" + dto.getPassword() + "%");
            }
            if (null != dto.getTelephone() && !"".equals(dto.getTelephone())) {
                criteriaName.andLike("telephone", "%" + dto.getTelephone() + "%");
            }
            if (null != dto.getAddr() && !"".equals(dto.getAddr())) {
                criteriaName.andLike("addr", "%" + dto.getAddr() + "%");
            }
            if (null != dto.getStatus()) {
                criteriaName.andLike("status", "%" + dto.getStatus() + "%");
            }
            if (null != dto.getSex()) {
                criteriaName.andLike("sex", "%" + dto.getSex() + "%");
            }
            if (null != dto.getBirthday()) {
                criteriaName.andLike("birthday", "%" + dto.getBirthday() + "%");
            }
            if (null != dto.getIsMerried()) {
                criteriaName.andLike("isMerried", "%" + dto.getIsMerried() + "%");
            }
            if (null != dto.getInTime()) {
                criteriaName.andLike("inTime", "%" + dto.getInTime() + "%");
            }
            if (null != dto.getDepartment() && !"".equals(dto.getDepartment())) {
                criteriaName.andLike("department", "%" + dto.getDepartment() + "%");
            }
            if (null != dto.getLeaderId() && !"".equals(dto.getLeaderId())) {
                criteriaName.andLike("leaderId", "%" + dto.getLeaderId() + "%");
            }
            if (null != dto.getNation() && !"".equals(dto.getNation())) {
                criteriaName.andLike("nation", "%" + dto.getNation() + "%");
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
    private Example initExample(EmployeeDTO dto) {
        Example example = InitExampleOrderByUtils.initExampleOrderBy(Employee.class, dto.getOrderBy());
        Example.Criteria criteriaName = example.createCriteria();
        if (null != dto) {
            if (null != dto.getId() && !"".equals(dto.getId())) {
                criteriaName.andEqualTo("id", dto.getId());
            }
            if (null != dto.getEmployeeName() && !"".equals(dto.getEmployeeName())) {
                criteriaName.andEqualTo("employeeName", dto.getEmployeeName());
            }
            if (null != dto.getPassword() && !"".equals(dto.getPassword())) {
                criteriaName.andEqualTo("password", dto.getPassword());
            }
            if (null != dto.getTelephone() && !"".equals(dto.getTelephone())) {
                criteriaName.andEqualTo("telephone", dto.getTelephone());
            }
            if (null != dto.getAddr() && !"".equals(dto.getAddr())) {
                criteriaName.andEqualTo("addr", dto.getAddr());
            }
            if (null != dto.getStatus()) {
                criteriaName.andEqualTo("status", dto.getStatus());
            }
            if (null != dto.getSex()) {
                criteriaName.andEqualTo("sex", dto.getSex());
            }
            if (null != dto.getBirthday()) {
                criteriaName.andEqualTo("birthday", dto.getBirthday());
            }
            if (null != dto.getIsMerried()) {
                criteriaName.andEqualTo("isMerried", dto.getIsMerried());
            }
            if (null != dto.getInTime()) {
                criteriaName.andEqualTo("inTime", dto.getInTime());
            }
            if (null != dto.getDepartment() && !"".equals(dto.getDepartment())) {
                criteriaName.andEqualTo("department", dto.getDepartment());
            }
            if (null != dto.getLeaderId() && !"".equals(dto.getLeaderId())) {
                criteriaName.andEqualTo("leaderId", dto.getLeaderId());
            }
            if (null != dto.getNation() && !"".equals(dto.getNation())) {
                criteriaName.andEqualTo("nation", dto.getNation());
            }
        }
        return example;
    }
    
}