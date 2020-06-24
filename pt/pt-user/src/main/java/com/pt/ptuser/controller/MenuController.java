package com.pt.ptuser.controller;


import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.entity.SysMenu;
import com.pt.ptuser.service.SysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wl
 * @date 2020/5/27
 */
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuController {
    private SysMenuService sysMenuService;

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/routers")
    public R getRouters()
    {
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(SecurityUtils.getId());
        return R.ok(sysMenuService.buildMenus(menus));
    }
    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    public R list(SysMenu menu)
    {
        List<SysMenu> menus = sysMenuService.selectMenuList(menu, SecurityUtils.getId());
        return R.ok(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    public R getInfo(@PathVariable String menuId)
    {
        return R.ok(sysMenuService.selectMenuById(menuId));
    }
    /**
     * 返回当前用户的树形菜单集合
     *
     * @param sysMenu 父节点ID
     * @return 当前用户的树形菜单
     */
    @GetMapping("/tree")
    public R getTree(SysMenu sysMenu) {

        // 获取符合条件的菜单
        List<SysMenu> menus = sysMenuService.selectMenuList(sysMenu, SecurityUtils.getId());
        return R.ok(sysMenuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public R roleMenuTreeselect(@PathVariable("roleId") String roleId)
    {
        List<SysMenu> menus = sysMenuService.selectMenuList(SecurityUtils.getId());
        Map result = new HashMap<String, List<String>>();
        //个人菜单
        result.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId));
        //全部菜单
        result.put("menus", sysMenuService.buildMenuTreeSelect(menus));
        return R.ok(result);
    }
    /**
     * 新增菜单
     */
    @PostMapping
    public R add(@Validated @RequestBody SysMenu menu)
    {
        if (!sysMenuService.checkMenuNameUnique(menu))
        {
            return R.ok("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setCreateBy(SecurityUtils.getNickName());
        return R.ok(sysMenuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public R edit(@Validated @RequestBody SysMenu menu)
    {
        if (!sysMenuService.checkMenuNameUnique(menu))
        {
            return R.failed("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setUpdateBy(SecurityUtils.getNickName());
        return R.ok(sysMenuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public R remove(@PathVariable("menuId") String menuId)
    {
        if (sysMenuService.hasChildByMenuId(menuId))
        {
            return R.failed("存在子菜单,不允许删除");
        }
        if (sysMenuService.checkMenuExistRole(menuId))
        {
            return R.failed("菜单已分配,不允许删除");
        }
        return R.ok(sysMenuService.deleteMenuById(menuId));
    }
}
