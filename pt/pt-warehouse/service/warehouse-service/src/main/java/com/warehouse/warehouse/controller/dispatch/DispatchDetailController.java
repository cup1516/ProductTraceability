package com.warehouse.warehouse.controller.dispatch;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.pojo.dto.dispatch.DispatchDetailDTO;
import com.warehouse.warehouse.pojo.dto.dispatch.InsertDispatchDetailByStockIdDTO;
import com.warehouse.warehouse.service.app.dispatch.DispatchDetailService;
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
 * 报损子表(DispatchDetail)表控制层
 *
 * @author makejava
 * @since 2020-05-01 19:49:56
 */
@Api(description = "DispatchDetail 控制层")
@RestController
@RequestMapping(value = "/dispatch_detail", produces = "application/json;charset=utf-8")
public class DispatchDetailController {

    private static final Logger logger = LogManager.getLogger(DispatchDetailController.class);

    /**
     * 服务对象
     */
    @Resource
    private DispatchDetailService dispatchDetailService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String", paramType = "query")})
    @GetMapping("/describe_by_id")
    public ResponseData describeById(@RequestParam(name = "id", required = true) String id) {
        return ResponseDataUtils.buildSuccess(this.dispatchDetailService.describeById(id), "通过主键查询单条数据成功");
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
            @ApiImplicitParam(name = "查询条数", value = "limit", required = true, dataType = "long", paramType = "query"),
    })
    @GetMapping("/describe_all_by_limit")
    public ResponseData describeAllByLimit(@RequestParam(name = "offset", required = true) long offset, @RequestParam(name = "limit", required = true) long limit) {
        return ResponseDataUtils.buildSuccess(this.dispatchDetailService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param dispatchDetailDTO 实例对象
     * @param pageSize          每页大小
     * @param currentPage       当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDetailDTO", value = "查询条件", required = false, dataType = "DispatchDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute DispatchDetailDTO dispatchDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<DispatchDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        dispatchDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<DispatchDetailDTO> list = this.dispatchDetailService.describeAll(dispatchDetailDTO);
        PageInfo<DispatchDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param dispatchDetailDTO 实例对象
     * @param pageSize          每页大小
     * @param currentPage       当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDetailDTO", value = "查询条件", required = true, dataType = "DispatchDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute DispatchDetailDTO dispatchDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<DispatchDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        dispatchDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<DispatchDetailDTO> list = this.dispatchDetailService.describeFuzzy(dispatchDetailDTO);
        PageInfo<DispatchDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param dispatchDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDetailDTO", value = "插入的数据", required = true, dataType = "DispatchDetailDTO", paramType = "body")})
    @PostMapping("/insert_dispatch_detail")
    public ResponseData insertDispatchDetail(@RequestBody DispatchDetailDTO dispatchDetailDTO) {
        dispatchDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.dispatchDetailService.insertDispatchDetail(dispatchDetailDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "DispatchDetailDTO", paramType = "body")})
    @PostMapping("/save_dispatch_detail")
    public ResponseData saveDispatchDetail(@RequestBody List<DispatchDetailDTO> list) {

        for(int i =0;i<list.size();i++){
            System.out.println("批量么");
        }
        return ResponseDataUtils.buildSuccess(this.dispatchDetailService.saveDispatchDetail(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param dispatchDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDetailDTO", value = "修改数据对象", required = true, dataType = "DispatchDetailDTO", paramType = "body")})
    @PostMapping("/update_dispatch_detail_by_primary_key")
    public ResponseData updateDispatchDetailByPrimaryKey(@RequestBody DispatchDetailDTO dispatchDetailDTO) {
        dispatchDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.dispatchDetailService.updateDispatchDetailByPrimaryKey(dispatchDetailDTO), "修改数据成功");
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
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "body")})
    @PostMapping("/delete_dispatch_detail_by_primary_key")
    public ResponseData deleteDispatchDetailByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.dispatchDetailService.deleteDispatchDetailByPrimaryKey(id), "通过主键删除数据成功");
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
            @ApiImplicitParam(name = "list", value = "list", required = true, allowMultiple = true, dataType = "String", paramType = "body")})
    @PostMapping("/delete_dispatch_detail_by_primary_keys")
    public ResponseData deleteDispatchDetailByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.dispatchDetailService.deleteDispatchDetailByPrimaryKeys(list), "通过主键批量删除数据成功");
    }

    @ApiOperation(value = "通过货位编号新增数据", notes = "通过货位编号新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dto", value = "货位编号及物品信息", required = true, dataType = "InsertDispatchDetailByStockIdDTO", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @PostMapping("/insert_by_stock_id")
    public ResponseData insertByStockId(@RequestBody InsertDispatchDetailByStockIdDTO dto) {
        dto.setCompanyId(SecurityUtils.getCompanyId());
        Integer integer = this.dispatchDetailService.insertByStockId(dto);
        if (integer == 1) {
            return ResponseDataUtils.buildSuccess(1, "插入成功");
        } else {
            return ResponseDataUtils.buildError(integer, "参数异常");
        }
    }

    @ApiOperation(value = "通过货位编号修改数据", notes = "通过货位编号修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dto", value = "货位编号及物品信息", required = true, dataType = "InsertDispatchDetailByStockIdDTO", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @PostMapping("/update_by_stock_id")
    public ResponseData updateByStockId(@RequestBody InsertDispatchDetailByStockIdDTO dto) {
        Integer integer = this.dispatchDetailService.updateByStockId(dto);
        if (integer == 1) {
            return ResponseDataUtils.buildSuccess(1, "更新成功");
        } else {
            return ResponseDataUtils.buildError(integer, "参数异常");
        }
    }

    @ApiOperation(value = "通过报损编号清空所有报损详情数据", notes = "通过报损编号清空所有报损详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "报损单编号", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @PostMapping("/delete_all_by_pid")
    public ResponseData deleteAllByPid(@RequestBody String orderNo) {
        return ResponseDataUtils.buildSuccess(this.dispatchDetailService.deleteAllByPid(orderNo), "清空成功");
    }

}