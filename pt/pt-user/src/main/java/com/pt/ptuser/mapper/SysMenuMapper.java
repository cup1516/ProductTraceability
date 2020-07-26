package com.pt.ptuser.mapper;

import com.pt.ptcommoncore.dto.MenuVO;
import com.pt.ptuser.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper  {
    /**
     * 按角色获取路由
     * @param role
     * @return
     */
    List<SysMenu> listRoutesByRole(@Param("role") String role,@Param("companyId") String companyId);
    /**
     * 按角色获取菜单
     * @param role
     * @return
     */
    List<MenuVO> listMenuByRole(@Param("role") String role,@Param("companyId") String companyId);
    /**
     * 获取所有菜单
     *
     * @return 菜单列表
     */
    List<SysMenu> listAllMenu(@Param("companyId") String companyId);

    SysMenu getMenuByIdAndCompanyId(@Param("menuId") String menuId,@Param("companyId") String companyId);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(@Param("menu") SysMenu menu,@Param("companyId") String companyId);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(@Param("menu") SysMenu menu);

    /**
     * 查询系统菜单列表
     *
     * @return 菜单列表
     */
    List<SysMenu> selectSystemMenuList();
    /**
     * 查询系统菜单列表
     *
     * @return 菜单列表
     */
    List<SysMenu> selectBussinessMenuList(String companyId);
    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenu> selectMenuListByUserId(@Param("menu")SysMenu menu,@Param("companyId") String companyId);

    /**
     * 根据角色获取菜单列表
     * @param roleId
     * @return
     */
    List<String> selectMenuListByRoleId(@Param("roleId") String roleId,@Param("companyId") String companyId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    SysMenu selectMenuById(@Param("menuId")String menuId,@Param("companyId") String companyId);
    /**
     * 新增菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    Boolean insertMenu(@Param("menu")SysMenu menu);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    Boolean updateMenu(@Param("menu")SysMenu menu,@Param("companyId") String companyId);

    /**
     * 校验菜单名称是否唯一
     *
     * @return 结果
     */
    SysMenu checkMenuNameUnique(SysMenu menu);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int hasChildByMenuId(@Param("menuId")String menuId,@Param("companyId") String companyId);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    Boolean deleteMenuById(@Param("menuId")String menuId,@Param("companyId") String companyId);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeAll(@Param("companyId") String companyId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(@Param("userId")String userId,@Param("companyId") String companyId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectCenterMenuTreeByUserId(@Param("userId")String userId);
}