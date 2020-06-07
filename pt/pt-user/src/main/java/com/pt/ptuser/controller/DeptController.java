package com.pt.ptuser.controller;

import com.pt.ptcommon.constant.CommonConstants;
import com.pt.ptcommon.util.IdUtils;
import com.pt.ptcommon.util.R;
import com.pt.ptcommon.util.SecurityUtils;
import com.pt.ptuser.entity.SysDept;
import com.pt.ptuser.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private SysDeptService sysDeptService;
    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/tree")
    public R treeselect(SysDept sysDept)
    {
        List<SysDept> depts = sysDeptService.selectDeptList(sysDept);
        return R.ok(sysDeptService.buildDeptTreeSelect(depts));
    }
    /**
     * 获取部门列表
     */
    @GetMapping("/list")
    public R list(SysDept dept)
    {
        List<SysDept> depts = sysDeptService.selectDeptList(dept);
        return R.ok(depts);
    }

    /**
     * 新增部门
     */
    @PostMapping
    public R add(@RequestBody SysDept dept)
    {
        if (!sysDeptService.checkDeptNameUnique(dept))
        {
            return R.failed("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setDeptId(IdUtils.simpleUUID());
//        dept.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysDeptService.insertDept(dept));
    }


    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping(value = "/{deptId}")
    public R getInfo(@PathVariable String deptId)
    {
        return R.ok(sysDeptService.selectDeptById(deptId));
    }
    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public R remove(@PathVariable String deptId)
    {
        if (sysDeptService.hasChildByDeptId(deptId))
        {
            return R.failed("存在下级部门,不允许删除");
        }
        if (sysDeptService.checkDeptExistUser(deptId))
        {
            return R.failed("部门存在用户,不允许删除");
        }
        return R.ok(sysDeptService.deleteDeptById(deptId));
    }

    /**
     * 修改部门
     */
    @PutMapping
    public R edit( @RequestBody SysDept dept)
    {
        if (!sysDeptService.checkDeptNameUnique(dept))
        {
            return R.failed("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getParentId().equals(dept.getDeptId()))
        {
            return R.failed("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (CommonConstants.DEPT_DISABLE.equals(dept.getStatus())
                && sysDeptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0)
        {
            return R.failed("该部门包含未停用的子部门！");
        }
//        dept.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(sysDeptService.updateDept(dept));
    }
}
