package com.warehouse.warehouse.controller.allocation;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.pojo.dto.allocation.AllocationDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.service.app.allocation.AllocationService;
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
 * 调拨单(Allocation)表控制层
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Api(description = "Allocation 控制层")
@RestController
@RequestMapping(value = "/allocation", produces = "application/json;charset=utf-8")
public class AllocationController {

    private static final Logger logger = LogManager.getLogger(AllocationController.class);

    /**
     * 服务对象
     */
    @Resource
    private AllocationService allocationService;

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
        return ResponseDataUtils.buildSuccess(this.allocationService.describeById(id), "通过主键查询单条数据成功");
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
        return ResponseDataUtils.buildSuccess(this.allocationService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param allocationDTO 实例对象
     * @param pageSize      每页大小
     * @param currentPage   当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDTO", value = "查询条件", required = false, dataType = "AllocationDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute AllocationDTO allocationDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<AllocationDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        allocationDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<AllocationDTO> list = this.allocationService.describeAll(allocationDTO);
        PageInfo<AllocationDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param allocationDTO 实例对象
     * @param pageSize      每页大小
     * @param currentPage   当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDTO", value = "查询条件", required = true, dataType = "AllocationDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute AllocationDTO allocationDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<AllocationDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        allocationDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<AllocationDTO> list = this.allocationService.describeFuzzy(allocationDTO);
        PageInfo<AllocationDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param allocationDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDTO", value = "插入的数据", required = true, dataType = "AllocationDTO", paramType = "body")})
    @PostMapping("/insert_allocation")
    public ResponseData insertAllocation(@RequestBody AllocationDTO allocationDTO) {
        allocationDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.allocationService.insertAllocation(allocationDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "AllocationDTO", paramType = "body")})
    @PostMapping("/save_allocation")
    public ResponseData saveAllocation(@RequestBody List<AllocationDTO> list) {
        return ResponseDataUtils.buildSuccess(this.allocationService.saveAllocation(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param allocationDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDTO", value = "修改数据对象", required = true, dataType = "AllocationDTO", paramType = "body")})
    @PostMapping("/update_allocation_by_primary_key")
    public ResponseData updateAllocationByPrimaryKey(@RequestBody AllocationDTO allocationDTO) {
        allocationDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.allocationService.updateAllocationByPrimaryKey(allocationDTO), "修改数据成功");
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
    @PostMapping("/delete_allocation_by_primary_key")
    public ResponseData deleteAllocationByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.allocationService.deleteAllocationByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_allocation_by_primary_keys")
    public ResponseData deleteAllocationByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.allocationService.deleteAllocationByPrimaryKeys(list), "通过主键批量删除数据成功");
    }

    /**
     * 查询未提交的出库单数据
     *
     * @param allocationDTO 实例对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询未提交的出库单数据", notes = "查询未提交的出库单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDTO", value = "查询条件", required = true, dataType = "OtheroutDTO", paramType = "query")})
    @GetMapping("/describe_not_commit")
    public ResponseData describeNotCommit(@ModelAttribute AllocationDTO allocationDTO) {
        allocationDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<AllocationDTO> list = this.allocationService.describeNotCommit(allocationDTO);
        return ResponseDataUtils.buildSuccess(list, "查询未提交的出库单数据成功");
    }

    /**
     * 生成调拨单号
     *
     * @return 是否成功
     */
    @ApiOperation(value = "初始化工单", notes = "生成调拨单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dto", value = "用户信息", required = true, dataType = "UserDTO", paramType = "query")})
    @PostMapping("/init_order_no")
    public ResponseData initOrderNo(@RequestBody UserDTO dto) {
        return ResponseDataUtils.buildSuccess(this.allocationService.initOrderNo(dto), "调拨单号生成成功");
    }


    /**
     * 修改数据
     *
     * @param allocationDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDTO", value = "修改数据对象", required = true, dataType = "AllocationDTO", paramType = "body")})
    @PostMapping("/update_allocation_by_allocation_no")
    public ResponseData updateAllocationByAllocationNo(@RequestBody AllocationDTO allocationDTO) {
        allocationDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.allocationService.updateAllocationByAllocationNo(allocationDTO), "修改数据成功");
    }

    /**
     * 条件分页模糊查询当前用户待审批出库单数据
     *
     * @param allocationDTO 实例对象
     * @param pageSize      每页大小
     * @param currentPage   当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询当前用户待审批调拨单数据", notes = "条件分页模糊查询当前用户待审批调拨单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDTO", value = "查询条件", required = true, dataType = "AllocationDTO", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当前用户编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_commission_fuzzy")
    public ResponseData describeCommissionFuzzy(@ModelAttribute AllocationDTO allocationDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<AllocationDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        allocationDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<AllocationDTO> list = allocationService.describeCommissionFuzzy(allocationDTO, userId);
        PageInfo<AllocationDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询当前用户待审批调拨单数据成功");
    }

    /**
     * 审批调拨单(通过和打回)
     *
     * @param allocationDTO 实例对象
     * @return 成功或者失败
     */
    @ApiOperation(value = "审批调拨单(通过和打回)", notes = "审批调拨单(通过和打回)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "allocationDTO", value = "审批参数", required = true, dataType = "AllocationDTO", paramType = "query")
    })
    @PostMapping("/approve_allocation")
    public ResponseData approveAllocation(@RequestBody AllocationDTO allocationDTO) {
        allocationDTO.setCompanyId(SecurityUtils.getCompanyId());
        Boolean bool = allocationService.approveAllocation(allocationDTO);
        if (bool) {
            return ResponseDataUtils.buildSuccess(bool, "审批调拨单成功");
        } else {
            return ResponseDataUtils.buildSuccess(bool, "审批调拨单失败");
        }
    }
}