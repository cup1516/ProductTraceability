package com.pt.ptuser.mapper;

import com.pt.ptuser.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper  {
    List<SysDept> selectDeptList( @Param("sysDept") SysDept sysDept,@Param("companyId") String companyId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int checkDeptExistUser(@Param("deptId") String deptId,@Param("companyId") String companyId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") String parentId,@Param("companyId") String companyId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    Boolean insertDept(@Param("dept") SysDept dept,@Param("companyId") String companyId);


    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDept selectDeptById(@Param("deptId") String deptId,@Param("companyId") String companyId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    Boolean deleteDeptById(@Param("deptId") String deptId,@Param("companyId") String companyId);
    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int hasChildByDeptId(@Param("deptId") String deptId,@Param("companyId") String companyId);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(@Param("dept") SysDept dept,@Param("companyId") String companyId);

    /**
     * 修改所在部门的父级部门状态
     *
     * @param dept 部门
     */
    Boolean updateDeptStatus(@Param("dept") SysDept dept,@Param("companyId") String companyId);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("depts") List<SysDept> depts,@Param("companyId") String companyId);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<SysDept> selectChildrenDeptById(@Param("deptId") String deptId,@Param("companyId") String companyId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    int selectNormalChildrenDeptById(@Param("deptId") String deptId,@Param("companyId") String companyId);

}