package com.pt.ptuser.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.pt.ptcommoncore.constant.*;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptuser.dto.TreeSelect;
import com.pt.ptuser.entity.SysDept;
import com.pt.ptuser.mapper.SysDeptMapper;
import com.pt.ptuser.service.SysDeptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysDeptServiceImpl  implements SysDeptService{

    private final  SysDeptMapper sysDeptMapper;
    @Override
    public List<SysDept> selectDeptList( SysDept sysDept,String companyId) {
        return sysDeptMapper.selectDeptList(sysDept,companyId);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */

    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<String> tempList = new ArrayList<String>();
        for (SysDept dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();)
        {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysDept> it = childList.iterator();
                while (it.hasNext())
                {
                    SysDept n = (SysDept) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StrUtil.isNotEmpty(n.getParentId()) && n.getParentId().equals(t.getDeptId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public Boolean checkDeptExistUser(String deptId,String companyId)
    {
        if(sysDeptMapper.checkDeptExistUser(deptId,companyId) != 0){
            throw new CustomException("部门存在用户,不允许删除");
        }
        return Boolean.TRUE;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public Boolean checkDeptNameUnique(SysDept dept,String companyId)
    {
        if(StrUtil.isEmpty(dept.getDeptId())){

            return Boolean.TRUE;
        }

        SysDept sysDept = sysDeptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId(),companyId);
        if (sysDept != null && !sysDept.getDeptId().equals(dept.getDeptId()))
        {
            throw new CustomException("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        return Boolean.TRUE;
    }
    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public Boolean insertDept(SysDept dept,String companyId)
    {
        SysDept sysDept = sysDeptMapper.selectDeptById(dept.getParentId(),companyId);
        // 如果父节点不为正常状态,则不允许新增子节点
        if (CommonConstants.DEPT_DISABLE.equals(sysDept.getStatus()))
        {
            return Boolean.FALSE;
        }
        dept.setAncestors(sysDept.getAncestors() + "," + dept.getParentId());
        return sysDeptMapper.insertDept(dept,companyId);
    }
    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(String deptId,String companyId)
    {
        return sysDeptMapper.selectDeptById(deptId,companyId);
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public Boolean deleteDeptById(String deptId,String companyId)
    {
        return sysDeptMapper.deleteDeptById(deptId,companyId);
    }
    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public Boolean hasChildByDeptId(String deptId,String companyId)
    {
        int result = sysDeptMapper.hasChildByDeptId(deptId,companyId);
        if (result > 0) {
            throw new CustomException("存在下级部门,不允许删除");
        }
        return  true;
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(String deptId,String companyId)
    {
        return sysDeptMapper.selectNormalChildrenDeptById(deptId,companyId);
    }
    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept,String companyId)
    {
        if (dept.getParentId().equals(dept.getDeptId())) {
            throw new CustomException("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (CommonConstants.DEPT_DISABLE.equals(dept.getStatus())
                && this.selectNormalChildrenDeptById(dept.getDeptId(),companyId) > 0)
        {
            throw new CustomException("该部门包含未停用的子部门！");
        }
        SysDept newParentDept = sysDeptMapper.selectDeptById(dept.getParentId(),companyId);
        SysDept oldDept = sysDeptMapper.selectDeptById(dept.getDeptId(),companyId);
        if (newParentDept != null && oldDept != null)
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), companyId,newAncestors, oldAncestors);
        }
        int result = sysDeptMapper.updateDept(dept,companyId);
        if (CommonConstants.DEPT_NORMAL.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept,companyId);
        }
        return result;
    }
    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept,String companyId)
    {
        String updateBy = dept.getUpdateBy();
        dept = sysDeptMapper.selectDeptById(dept.getDeptId(),companyId);
        dept.setUpdateBy(updateBy);
        sysDeptMapper.updateDeptStatus(dept,companyId);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(String deptId,String companyId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = sysDeptMapper.selectChildrenDeptById(deptId,companyId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            sysDeptMapper.updateDeptChildren(children,companyId);
        }
    }
}
