package com.pt.ptuser.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pt.ptuser.entity.SysRoleMenu;
import com.pt.ptuser.mapper.SysRoleMenuMapper;
import com.pt.ptuser.service.SysRoleMenuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Override
    public List<SysRoleMenu> listRoleMenu(String roleId, String clientId) {
        return baseMapper.listRoleMenu(roleId,clientId);
    }

    @Override
    public Boolean deleteRoleMenuByRoleId(String roleId) {
        return baseMapper.deleteRoleMenuByRoleId(roleId);
    }

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    @Override
    public Boolean batchRoleMenu(List<SysRoleMenu> roleMenuList){
        return baseMapper.batchRoleMenu(roleMenuList);
    };
}
