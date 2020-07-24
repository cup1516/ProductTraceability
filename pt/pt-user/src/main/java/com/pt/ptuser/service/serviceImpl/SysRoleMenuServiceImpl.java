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
    public List<SysRoleMenu> listRoleMenu(String roleId, String companyId) {
        return sysRoleMenuMapper.listRoleMenu(roleId,companyId);
    }

    @Override
    public Boolean deleteRoleMenuByRoleId(String roleId,String companyId) {
        return sysRoleMenuMapper.deleteRoleMenuByRoleId(roleId,companyId);
    }

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    @Override
    public Boolean batchRoleMenu(List<SysRoleMenu> roleMenuList,String companyId){
        return sysRoleMenuMapper.batchRoleMenu(roleMenuList,companyId);
    };
    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int checkMenuExistRole(String menuId,String companyId){ return sysRoleMenuMapper.checkMenuExistRole(menuId,companyId);};

}
