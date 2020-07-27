package com.pt.ptuser.controller;


import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.entity.SysMenu;
import com.pt.ptuser.service.SysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(SecurityUtils.getId(),SecurityUtils.getCompanyId());
        return R.ok(sysMenuService.buildMenus(menus));
    }
    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    public R list(SysMenu menu)
    {
        List<SysMenu> menus = sysMenuService.selectMenuList(menu);
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
//        List<SysMenu> menus = sysMenuService.selectMenuList(sysMenu,SecurityUtils.getId(),SecurityUtils.getCompanyId());
//        return R.ok(sysMenuService.buildMenuTreeSelect(menus));
        List<SysMenu> sysMenus = sysMenuService.selectSystemMenuList();
        List<SysMenu> bussinessMenus = sysMenuService.selectBussinessMenuList(SecurityUtils.getCompanyId());
        List<SysMenu> menus = new ArrayList<>();
        menus.addAll(sysMenus);
        menus.addAll(bussinessMenus);
        return R.ok(sysMenuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public R roleMenuTreeselect(@PathVariable("roleId") String roleId)
    {
        List<SysMenu> sysMenus = sysMenuService.selectSystemMenuList();
        List<SysMenu> bussinessMenus = sysMenuService.selectBussinessMenuList(SecurityUtils.getCompanyId());
        List<SysMenu> menus = new ArrayList<>();
        menus.addAll(sysMenus);
        menus.addAll(bussinessMenus);
        Map result = new HashMap<String, List<String>>();
        //个人菜单
        result.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId,SecurityUtils.getCompanyId()));
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
        sysMenuService.checkMenuNameUnique(menu);
        menu.setCreateBy(SecurityUtils.getNickName());
        return R.ok(sysMenuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public R edit(@Validated @RequestBody SysMenu menu)
    {
        sysMenuService.checkMenuNameUnique(menu);
        menu.setUpdateBy(SecurityUtils.getNickName());
        return R.ok(sysMenuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @DeleteMapping()
    public R remove(@RequestBody SysMenu menu)
    {
        sysMenuService.hasChild(menu);
        sysMenuService.checkMenuExistRole(menu);
        return R.ok(sysMenuService.deleteMenu(menu));
    }
}
