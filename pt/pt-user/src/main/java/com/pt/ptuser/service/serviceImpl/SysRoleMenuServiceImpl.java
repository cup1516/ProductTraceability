package com.pt.ptuser.service.serviceImpl;

import com.pt.ptuser.entity.SysRoleMenu;
import com.pt.ptuser.mapper.SysRoleMenuMapper;
import com.pt.ptuser.service.SysRoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysRoleMenuServiceImpl  implements SysRoleMenuService {
    private final SysRoleMenuMapper sysRoleMenuMapper;
    @Override
    public List<SysRoleMenu> listRoleMenu(String roleId, String clientId) {
        return sysRoleMenuMapper.listRoleMenu(roleId,clientId);
    }

    @Override
    public Boolean deleteRoleMenuByRoleId(String roleId) {
        return sysRoleMenuMapper.deleteRoleMenuByRoleId(roleId);
    }

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    @Override
    public Boolean batchRoleMenu(List<SysRoleMenu> roleMenuList){
        return sysRoleMenuMapper.batchRoleMenu(roleMenuList);
    };
    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int checkMenuExistRole(String menuId){ return sysRoleMenuMapper.checkMenuExistRole(menuId);};

}
