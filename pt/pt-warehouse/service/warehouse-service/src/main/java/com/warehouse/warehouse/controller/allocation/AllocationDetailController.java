package com.warehouse.warehouse.controller.allocation;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warehouse.warehouse.pojo.dto.allocation.AllocationDetailDTO;
import com.warehouse.warehouse.service.app.allocation.AllocationDetailService;
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
 * 调拨单子表(AllocationDetail)表控制层
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Api(description = "AllocationDetail 控制层")
@RestController
@RequestMapping(value = "/allocationDetail", produces = "application/json;charset=utf-8")
public class AllocationDetailController {

    private static final Logger logger = LogManager.getLogger(AllocationDetailController.class);

    /**
     * 服务对象
     */
    @Resource
    private AllocationDetailService allocationDetailService;

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
        return ResponseDataUtils.buildSuccess(this.allocationDetailService.describeById(id), "通过主键查询单条数据成功");
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
        return ResponseDataUtils.buildSuccess(this.allocationDetailService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param allocationDetailDTO 实例对象
     * @param pageSize            每页大小
     * @param currentPage         当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDetailDTO", value = "查询条件", required = false, dataType = "AllocationDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute AllocationDetailDTO allocationDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<AllocationDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        List<AllocationDetailDTO> list = this.allocationDetailService.describeAll(allocationDetailDTO);
        PageInfo<AllocationDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    @ApiOperation(value = "根据pid分页查询数据", notes = "根据pid分页查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationId", value = "查询条件", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all_by_pid_on_page")
    public ResponseData describeAllByPidOnPage(@RequestParam("allocationId") String allocationId,
                                               @RequestParam(name = "pageSize", required = true) Long pageSize,
                                               @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<AllocationDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        List<AllocationDetailDTO> list = this.allocationDetailService.findByAllocationId(allocationId);
        PageInfo<AllocationDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param allocationDetailDTO 实例对象
     * @param pageSize            每页大小
     * @param currentPage         当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDetailDTO", value = "查询条件", required = true, dataType = "AllocationDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute AllocationDetailDTO allocationDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<AllocationDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        List<AllocationDetailDTO> list = this.allocationDetailService.describeFuzzy(allocationDetailDTO);
        PageInfo<AllocationDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param allocationDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDetailDTO", value = "插入的数据", required = true, dataType = "AllocationDetailDTO", paramType = "body")})
    @PostMapping("/insert_allocationDetail")
    public ResponseData insertAllocationDetail(@RequestBody AllocationDetailDTO allocationDetailDTO) {
        return ResponseDataUtils.buildSuccess(this.allocationDetailService.insertAllocationDetail(allocationDetailDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "AllocationDetailDTO", paramType = "body")})
    @PostMapping("/save_allocationDetail")
    public ResponseData saveAllocationDetail(@RequestBody List<AllocationDetailDTO> list) {
        return ResponseDataUtils.buildSuccess(this.allocationDetailService.saveAllocationDetail(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param allocationDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDetailDTO", value = "修改数据对象", required = true, dataType = "AllocationDetailDTO", paramType = "body")})
    @PostMapping("/update_allocationDetail_by_primary_key")
    public ResponseData updateAllocationDetailByPrimaryKey(@RequestBody AllocationDetailDTO allocationDetailDTO) {
        return ResponseDataUtils.buildSuccess(this.allocationDetailService.updateAllocationDetailByPrimaryKey(allocationDetailDTO), "修改数据成功");
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
    @PostMapping("/delete_allocationDetail_by_primary_key")
    public ResponseData deleteAllocationDetailByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.allocationDetailService.deleteAllocationDetailByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_allocationDetail_by_primary_keys")
    public ResponseData deleteAllocationDetailByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.allocationDetailService.deleteAllocationDetailByPrimaryKeys(list), "通过主键批量删除数据成功");
    }

    @ApiOperation(value = "通过调拨编号清空所有调拨详情数据", notes = "通过调拨编号清空所有调拨详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "orderNo", value = "调拨单编号", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @PostMapping("/delete_all_by_pid")
    public ResponseData deleteAllByPid(@RequestBody String orderNo) {
        return ResponseDataUtils.buildSuccess(this.allocationDetailService.deleteAllByPid(orderNo), "清空成功");
    }

}