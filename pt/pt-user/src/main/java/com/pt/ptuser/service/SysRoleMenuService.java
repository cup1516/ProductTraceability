package com.pt.ptuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pt.ptuser.entity.SysRoleMenu;

import java.util.List;

/**
 * @author wl
 * @date 2020/5/25
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    List<SysRoleMenu> listRoleMenu(String roleId,String clientId);

    Boolean deleteRoleMenuByRoleId(String roleId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    Boolean batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
