package com.warehouse.warehouse.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.pojo.dto.warehouse.WarehouseDTO;
import com.warehouse.warehouse.service.app.warehouse.WarehouseService;
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
 * 仓库表(Warehouse)表控制层
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Api(description = "Warehouse 控制层")
@RestController
@RequestMapping(value = "/warehouse", produces = "application/json;charset=utf-8")
public class WarehouseController {

    private static final Logger logger = LogManager.getLogger(WarehouseController.class);

    /**
     * 服务对象
     */
    @Resource
    private WarehouseService warehouseService;

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
        return ResponseDataUtils.buildSuccess(this.warehouseService.describeById(id), "通过主键查询单条数据成功");
    }

    /**
     * @title: 根据负责人id查询仓库
     * @description: 根据负责人id查询仓库
     * @params:
     * @return:
     * @createTime: 2020/4/14 23:06
     * @version: 1.0
     * @author: wanpp
     */
    @ApiOperation(value = "根据负责人id查询仓库", notes = "根据负责人id查询仓库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "请求Authorization参数", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "principalId", value = "负责人Id", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/get_all_store_by_principal_id")
    public ResponseData getAllStoreByPrincipalId(@RequestParam(value = "principalId", required = true) String principalId,
                                                 @RequestParam(value = "companyId", required = true) String companyId ){
        return ResponseDataUtils.buildSuccess(this.warehouseService.getAllStoreByPrincipalId(principalId,SecurityUtils.getCompanyId()), "根据负责人id查询仓库成功");
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
            @ApiImplicitParam(name = "查询起始位置", value = "offset", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "查询条数", value = "limit", required = true, dataType = "long", paramType = "query"),
    })
    @GetMapping("/describe_all_by_limit")
    public ResponseData describeAllByLimit(@RequestParam(name = "offset", required = true) long offset, @RequestParam(name = "limit", required = true) long limit) {
        return ResponseDataUtils.buildSuccess(this.warehouseService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param warehouseDTO 实例对象
     * @param pageSize     每页大小
     * @param currentPage  当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "warehouseDTO", value = "查询条件", required = false, dataType = "WarehouseDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute WarehouseDTO warehouseDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<WarehouseDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        String companyId = SecurityUtils.getCompanyId();
        warehouseDTO.setCompanyId(companyId);
        List<WarehouseDTO> list = this.warehouseService.describeAll(warehouseDTO);
        PageInfo<WarehouseDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param warehouseDTO 实例对象
     * @param pageSize     每页大小
     * @param currentPage  当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "warehouseDTO", value = "查询条件", required = true, dataType = "WarehouseDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute WarehouseDTO warehouseDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<WarehouseDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        String companyId = SecurityUtils.getCompanyId();
        warehouseDTO.setCompanyId(companyId);
        List<WarehouseDTO> list = this.warehouseService.describeFuzzy(warehouseDTO);
        PageInfo<WarehouseDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param warehouseDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "warehouseDTO", value = "插入的数据", required = true, dataType = "WarehouseDTO", paramType = "body")
    })
    @PostMapping("/insert_warehouse")
    public ResponseData insertWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        warehouseDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.warehouseService.insertWarehouse(warehouseDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "WarehouseDTO", paramType = "body")
    })
    @PostMapping("/save_warehouse")
    public ResponseData saveWarehouse(@RequestBody List<WarehouseDTO> list) {
        return ResponseDataUtils.buildSuccess(this.warehouseService.saveWarehouse(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param warehouseDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "warehouseDTO", value = "修改数据对象", required = true, dataType = "WarehouseDTO", paramType = "body")
    })
    @PostMapping("/update_warehouse_by_primary_key")
    public ResponseData updateWarehouseByPrimaryKey(@RequestBody WarehouseDTO warehouseDTO) {
        warehouseDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.warehouseService.updateWarehouseByPrimaryKey(warehouseDTO), "修改数据成功");
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
    @PostMapping("/delete_warehouse_by_primary_key")
    public ResponseData deleteWarehouseByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.warehouseService.deleteWarehouseByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_warehouse_by_primary_keys")
    public ResponseData deleteWarehouseByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.warehouseService.deleteWarehouseByPrimaryKeys(list), "通过主键批量删除数据成功");
    }


    @GetMapping("/get_storage_by_warehouse_id")
    public ResponseData getStorageByWarehouseId(@RequestParam(name = "id", required = true) String id) {
        return ResponseDataUtils.buildSuccess(this.warehouseService.getStorageByWarehouseId(id), "通过主键批量删除数据成功");
    }




}