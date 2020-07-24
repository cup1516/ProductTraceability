package com.pt.ptuser.controller;


import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.entity.SysDept;
import com.pt.ptuser.service.SysDeptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/dept")
public class DeptController {

    private SysDeptService sysDeptService;
    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/tree")
    public R treeselect(SysDept sysDept)
    {
        List<SysDept> depts = sysDeptService.selectDeptList(sysDept, SecurityUtils.getCompanyId());
        return R.ok(sysDeptService.buildDeptTreeSelect(depts));
    }
    /**
     * 获取部门列表
     */
    @GetMapping("/list")
    public R list(SysDept dept)
    {
        List<SysDept> depts = sysDeptService.selectDeptList(dept, SecurityUtils.getCompanyId());
        return R.ok(depts);
    }

    /**
     * 新增部门
     */
    @PostMapping
    public R add(@RequestBody SysDept dept)
    {
        sysDeptService.checkDeptNameUnique(dept, SecurityUtils.getCompanyId());
        dept.setDeptId(IdUtils.simpleUUID());
//        dept.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysDeptService.insertDept(dept, SecurityUtils.getCompanyId()));
    }


    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping(value = "/{deptId}")
    public R getInfo(@PathVariable String deptId)
    {
        return R.ok(sysDeptService.selectDeptById(deptId, SecurityUtils.getCompanyId()));
    }
    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public R remove(@PathVariable String deptId)
    {
        sysDeptService.hasChildByDeptId(deptId, SecurityUtils.getCompanyId());
        sysDeptService.checkDeptExistUser(deptId, SecurityUtils.getCompanyId());
        return R.ok(sysDeptService.deleteDeptById(deptId, SecurityUtils.getCompanyId()));
    }

    /**
     * 修改部门
     */
    @PutMapping
    public R edit( @RequestBody SysDept dept)
    {
        sysDeptService.checkDeptNameUnique(dept, SecurityUtils.getCompanyId());

//        dept.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(sysDeptService.updateDept(dept, SecurityUtils.getCompanyId()));
    }
}
