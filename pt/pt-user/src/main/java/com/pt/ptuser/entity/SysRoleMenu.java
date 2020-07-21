package com.pt.ptuser.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wl
 */
@Data
public class SysRoleMenu {

    /**
     * 角色id
     */
    @ApiModelProperty(value="角色id")
    private String roleId;
    /**
    * 菜单id
    */
    @ApiModelProperty(value="菜单id")
    private String menuId;
    private String companyId;


}