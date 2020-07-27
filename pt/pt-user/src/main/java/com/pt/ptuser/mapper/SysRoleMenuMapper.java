package com.pt.ptuser.mapper;

import com.pt.ptuser.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper  {

    List<SysRoleMenu> listRoleMenu(@Param("roleId") String roleId,@Param("companyId") String companyId);

    Boolean deleteRoleMenuByRoleId(@Param("roleId") String roleId,@Param("companyId") String companyId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    Boolean batchRoleMenu(@Param("roleMenuList") List<SysRoleMenu> roleMenuList,@Param("companyId") String companyId);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int checkMenuExistRole(@Param("menuId")String menuId);
}