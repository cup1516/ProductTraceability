package com.warehouse.warehouse.controller.dispatch;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.pojo.dto.dispatch.DispatchDTO;
import com.warehouse.warehouse.pojo.dto.dispatch.DispatchWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.service.app.dispatch.DispatchService;
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
 * 报损表(Dispatch)表控制层
 *
 * @author makejava
 * @since 2020-05-01 19:49:56
 */
@Api(description = "Dispatch 控制层")
@RestController
@RequestMapping(value = "/dispatch", produces = "application/json;charset=utf-8")
public class DispatchController {

    private static final Logger logger = LogManager.getLogger(DispatchController.class);

    /**
     * 服务对象
     */
    @Resource
    private DispatchService dispatchService;

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
        return ResponseDataUtils.buildSuccess(this.dispatchService.describeById(id), "通过主键查询单条数据成功");
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
        return ResponseDataUtils.buildSuccess(this.dispatchService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param dispatchDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "查询条件", required = false, dataType = "DispatchDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute DispatchDTO dispatchDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<DispatchDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<DispatchDTO> list = this.dispatchService.describeAll(dispatchDTO);
        PageInfo<DispatchDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param dispatchDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "查询条件", required = true, dataType = "DispatchDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute DispatchDTO dispatchDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<DispatchDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<DispatchDTO> list = this.dispatchService.describeFuzzy(dispatchDTO);
        PageInfo<DispatchDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param dispatchDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "插入的数据", required = true, dataType = "DispatchDTO", paramType = "body")})
    @PostMapping("/insert_dispatch")
    public ResponseData insertDispatch(@RequestBody DispatchDTO dispatchDTO) {
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.dispatchService.insertDispatch(dispatchDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "DispatchDTO", paramType = "body")})
    @PostMapping("/save_dispatch")
    public ResponseData saveDispatch(@RequestBody List<DispatchDTO> list) {
        return ResponseDataUtils.buildSuccess(this.dispatchService.saveDispatch(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param dispatchDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "修改数据对象", required = true, dataType = "DispatchDTO", paramType = "body")})
    @PostMapping("/update_dispatch_by_primary_key")
    public ResponseData updateDispatchByPrimaryKey(@RequestBody DispatchDTO dispatchDTO) {
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.dispatchService.updateDispatchByPrimaryKey(dispatchDTO), "修改数据成功");
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
    @PostMapping("/delete_dispatch_by_primary_key")
    public ResponseData deleteDispatchByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.dispatchService.deleteDispatchByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_dispatch_by_primary_keys")
    public ResponseData deleteDispatchByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.dispatchService.deleteDispatchByPrimaryKeys(list), "通过主键批量删除数据成功");
    }


    /**
     * 生成报损单号
     *
     * @return 是否成功
     */
    @ApiOperation(value = "初始化工单", notes = "生成报损单号")
    @PostMapping("/init_order_no")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dto", value = "用户信息", required = true, allowMultiple = true, dataType = "UserDTO", paramType = "body")})
    public ResponseData initOrderNo(@RequestBody UserDTO dto) {
        return ResponseDataUtils.buildSuccess(this.dispatchService.initOrderNo(dto), "报损单号生成成功");
    }

    /**
     * 查询未提交的报损单数据
     *
     * @param dispatchDTO 实例对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询未提交的报损单数据", notes = "查询未提交的报损单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "查询条件", required = true, dataType = "DispatchDTO", paramType = "query")})
    @GetMapping("/describe_not_commit")
    public ResponseData describeNotCommit(@ModelAttribute DispatchDTO dispatchDTO) {
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<DispatchDTO> list = this.dispatchService.describeNotCommit(dispatchDTO);
        return ResponseDataUtils.buildSuccess(list, "查询未提交的报损单数据成功");
    }

    /**
     * 根据DispatchNo修改数据的仓库信息
     *
     * @param dispatchDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "根据DispatchNo修改数据的仓库信息", notes = "根据DispatchNo修改数据的仓库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "修改数据对象", required = true, dataType = "DispatchDTO", paramType = "body")})
    @PostMapping("/update_warehouse_id_by_dispatch_no")
    public ResponseData updateWarehouseIdByDispatchNo(@RequestBody DispatchDTO dispatchDTO) {
        Integer integer = this.dispatchService.updateWarehouseIdByDispatchNo(dispatchDTO);
        if (integer == 0) {
            return ResponseDataUtils.buildError(0, "参数错误");
        } else {
            return ResponseDataUtils.buildSuccess(integer, "修改数据成功");
        }
    }

    /**
     * 条件分页模糊查询当前用户提交报损单数据
     *
     * @param dispatchDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询当前用户提交报损单数据", notes = "条件分页模糊查询当前用户提交报损单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "查询条件", required = true, dataType = "DispatchDTO", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当前用户编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_mine_fuzzy")
    public ResponseData describeMineFuzzy(@ModelAttribute DispatchDTO dispatchDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<DispatchWithWarehouseNameDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<DispatchWithWarehouseNameDTO> list = this.dispatchService.describeMineFuzzy(dispatchDTO, userId);
        PageInfo<DispatchWithWarehouseNameDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询当前用户提交报损单数据成功");
    }

    /**
     * 条件分页模糊查询当前用户待审批报损单数据
     *
     * @param otheroutDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询当前用户待审批报损单数据", notes = "条件分页模糊查询当前用户待审批报损单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "查询条件", required = true, dataType = "DispatchDTO", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当前用户编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_commission_fuzzy")
    public ResponseData describeCommissionFuzzy(@ModelAttribute DispatchDTO dispatchDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<DispatchWithWarehouseNameDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
         List<DispatchWithWarehouseNameDTO> list = this.dispatchService.describeCommissionFuzzy(dispatchDTO, userId);
        PageInfo<DispatchWithWarehouseNameDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询当前用户待审批报损单数据成功");
    }

    /**
     * 审批报损单(通过和打回)
     *
     * @param dispatchDTO 实例对象
     * @return 成功或者失败
     */
    @ApiOperation(value = "审批报损单(通过和打回)", notes = "审批报损单(通过和打回)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "审批参数", required = true, dataType = "DispatchDTO", paramType = "query")
    })
    @PostMapping("/approve_dispatch")
    public ResponseData approveDispatch(@RequestBody DispatchDTO dispatchDTO) {
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
        Boolean bool = dispatchService.approveDispatch(dispatchDTO);
        if (bool) {
            return ResponseDataUtils.buildSuccess(bool, "审批报损单成功");
        } else {
            return ResponseDataUtils.buildSuccess(bool, "审批报损单失败");
        }
    }

    /**
     * 条件分页模糊查询当前用户已经审批的报损单数据
     *
     * @param dispatchDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询当前用户已经审批的报损单数据", notes = "条件分页模糊查询当前用户已经审批的报损单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dispatchDTO", value = "查询条件", required = true, dataType = "DispatchDTO", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当前用户编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_already_done_fuzzy")
    public ResponseData describeAlreadDoneFuzzy(@ModelAttribute DispatchDTO dispatchDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<DispatchWithWarehouseNameDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        dispatchDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<DispatchWithWarehouseNameDTO> list = this.dispatchService.describeAlreadDoneFuzzy(dispatchDTO, userId);
        PageInfo<DispatchWithWarehouseNameDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询当前用户已经审批的报损单数据成功");
    }

}