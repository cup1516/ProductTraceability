package com.warehouse.warehouse.controller.warehouse.shelves.locator;


import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import com.pt.ptcommonsecurity.util.*;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorWithWarehouseNameDTO;
import com.warehouse.warehouse.service.app.warehouse.shelves.locator.LocatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 货位表(Locator)表控制层
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Api(description = "Locator 控制层")
@RestController
@RequestMapping(value = "/locator", produces = "application/json;charset=utf-8")
public class LocatorController {

    private static final Logger logger = LogManager.getLogger(LocatorController.class);

    /**
     * 服务对象
     */
    @Resource
    private LocatorService locatorService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/describe_by_id")
    public ResponseData describeById(@RequestParam(name = "id", required = true) String id) {
        return ResponseDataUtils.buildSuccess(this.locatorService.describeById(id), "通过主键查询单条数据成功");
    }

    /**
     * 根据下标查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @ApiOperation(value = "根据下标查询多条数据", notes = "根据下标查询多条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "查询起始位置", value = "offset", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "查询条数", value = "limit", required = true, dataType = "long", paramType = "query")
    })
    @GetMapping("/describe_all_by_limit")
    public ResponseData describeAllByLimit(@RequestParam(name = "offset", required = true) long offset, @RequestParam(name = "limit", required = true) long limit) {
        return ResponseDataUtils.buildSuccess(this.locatorService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param locatorDTO  实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "locatorDTO", value = "查询条件", required = false, dataType = "LocatorDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute LocatorDTO locatorDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<LocatorDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        locatorDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<LocatorDTO> list = this.locatorService.describeAll(locatorDTO);
        PageInfo<LocatorDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param locatorDTO  实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "locatorDTO", value = "查询条件", required = true, dataType = "LocatorDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute LocatorDTO locatorDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<LocatorDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        locatorDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<LocatorDTO> list = this.locatorService.describeFuzzy(locatorDTO);
        PageInfo<LocatorDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据(携带仓库名称)
     *
     * @param locatorDTO  实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据(携带仓库名称)", notes = "条件分页模糊查询所有数据(携带仓库名称)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "locatorDTO", value = "查询条件", required = true, dataType = "LocatorDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy_with_warehouse_name")
    public ResponseData describeFuzzyWithWarehouseName(@ModelAttribute LocatorDTO locatorDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<LocatorWithWarehouseNameDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
         locatorDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<LocatorWithWarehouseNameDTO> list = this.locatorService.describeFuzzyWithWarehouseName(locatorDTO);
        PageInfo<LocatorWithWarehouseNameDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param locatorDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "locatorDTO", value = "插入的数据", required = true, dataType = "LocatorDTO", paramType = "body")
    })
    @PostMapping("/insert_locator")
    public ResponseData insertLocator(@RequestBody LocatorDTO locatorDTO) {
        locatorDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.locatorService.insertLocator(locatorDTO), "新增数据成功");
    }


    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @ApiOperation(value = "新增多条数据", notes = "新增多条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "LocatorDTO", paramType = "body")
    })
    @PostMapping("/save_locator")
    public ResponseData saveLocator(@RequestBody List<LocatorDTO> list) {
        return ResponseDataUtils.buildSuccess(this.locatorService.saveLocator(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param locatorDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "locatorDTO", value = "修改数据对象", required = true, dataType = "LocatorDTO", paramType = "body")
    })
    @PostMapping("/update_locator_by_primary_key")
    public ResponseData updateLocatorByPrimaryKey(@RequestBody LocatorDTO locatorDTO) {
        locatorDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.locatorService.updateLocatorByPrimaryKey(locatorDTO), "修改数据成功");
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键删除数据", notes = "通过主键删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/delete_locator_by_primary_key")
    public ResponseData deleteLocatorByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.locatorService.deleteLocatorByPrimaryKey(id), "通过主键删除数据成功");
    }

    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "通过主键批量删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "list", value = "list", required = true, allowMultiple = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/delete_locator_by_primary_keys")
    public ResponseData deleteLocatorByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.locatorService.deleteLocatorByPrimaryKeys(list), "通过主键批量删除数据成功");
    }


    /**
     * 通过编码查询单条数据
     *
     * @param locatorCode 货位编码
     * @return 单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "locatorCode", value = "货位编码", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/describe_by_locator_code")
    public ResponseData describeByLocatorCode(@RequestParam(name = "locatorCode", required = true) String locatorCode) {
        return ResponseDataUtils.buildSuccess(this.locatorService.describeByLocatorCode(locatorCode), "通过主键查询单条数据成功");
    }


    /**
     * 查看货位详情
     * @param locatorId
     * @return
     */

    @GetMapping("/getLocatorDetail")
    public ResponseData getLocatorDetail(@RequestParam(name = "locatorId", required = true) String locatorId) {
//
        return ResponseDataUtils.buildSuccess(this.locatorService.getLocatorDetail(locatorId, SecurityUtils.getCompanyId()), "通过主键查询单条数据成功");
    }
    @ApiOperation(value = "通过仓库编号查询所有货位", notes = "通过仓库编号查询所有货位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "仓库编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @GetMapping("/describe_all_locators_by_warehouse_id")
    public ResponseData describeAllLocatorsByWarehouseId(@RequestParam(name = "warehouseId", required = true) String warehouseId) {
        String locatorsName = this.locatorService.describeAllLocatorsByWarehouseId(warehouseId);
        return ResponseDataUtils.buildSuccess(locatorsName, "查询成功");
    }

    /*
     查询空货位
     */
    @GetMapping("/describe_full_locators_by_warehouse_id")
    public ResponseData describeEmptyLocatorsByWarehouseId(@RequestParam(name = "warehouseId", required = true) String warehouseId) {
        String locatorsName  = this.locatorService.describeFullLocatorsByWarehouseId(warehouseId);
        return ResponseDataUtils.buildSuccess(locatorsName, "查询成功");
    }

    @GetMapping("/describe_use_locators_by_warehouse_id")
    public ResponseData describeUseLocatorsByWarehouseId(@RequestParam(name = "warehouseId", required = true) String warehouseId) {
        String locatorsName   = this.locatorService.describeUseLocatorsByWarehouseId(warehouseId);
        return ResponseDataUtils.buildSuccess(locatorsName, "查询成功");
    }
}