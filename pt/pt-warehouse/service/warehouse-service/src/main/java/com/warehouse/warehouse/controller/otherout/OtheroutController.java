package com.warehouse.warehouse.controller.otherout;

import com.alibaba.fastjson.JSON;
import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutDTO;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutWithWarehouseNameDTO;
import com.warehouse.warehouse.pojo.dto.user.UserDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesAndStockDTO;
import com.warehouse.warehouse.service.app.otherout.OtheroutService;
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
 * 出库表(Otherout)表控制层
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Api(description = "Otherout 控制层")
@RestController
@RequestMapping(value = "/otherout", produces = "application/json;charset=utf-8")
public class OtheroutController {

    private static final Logger logger = LogManager.getLogger(OtheroutController.class);

    /**
     * 服务对象
     */
    @Resource
    private OtheroutService otheroutService;

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
        return ResponseDataUtils.buildSuccess(this.otheroutService.describeById(id), "通过主键查询单条数据成功");
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
        return ResponseDataUtils.buildSuccess(this.otheroutService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param otheroutDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "查询条件", required = false, dataType = "OtheroutDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute OtheroutDTO otheroutDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtheroutDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtheroutDTO> list = this.otheroutService.describeAll(otheroutDTO);
        PageInfo<OtheroutDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param otheroutDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "查询条件", required = true, dataType = "OtheroutDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute OtheroutDTO otheroutDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtheroutDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtheroutDTO> list = this.otheroutService.describeFuzzy(otheroutDTO);
        PageInfo<OtheroutDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 查询未提交的出库单数据
     *
     * @param otheroutDTO 实例对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询未提交的出库单数据", notes = "查询未提交的出库单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "查询条件", required = true, dataType = "OtheroutDTO", paramType = "query")})
    @GetMapping("/describe_not_commit")
    public ResponseData describeNotCommit(@ModelAttribute OtheroutDTO otheroutDTO) {
       otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtheroutDTO> list = this.otheroutService.describeNotCommit(otheroutDTO);
        return ResponseDataUtils.buildSuccess(list, "查询未提交的出库单数据成功");
    }

    /**
     * 新增数据
     *
     * @param otheroutDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "插入的数据", required = true, dataType = "OtheroutDTO", paramType = "body")})
    @PostMapping("/insert_otherout")
    public ResponseData insertOtherout(@RequestBody OtheroutDTO otheroutDTO) {
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.otheroutService.insertOtherout(otheroutDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "OtheroutDTO", paramType = "body")})
    @PostMapping("/save_otherout")
    public ResponseData saveOtherout(@RequestBody List<OtheroutDTO> list) {
        return ResponseDataUtils.buildSuccess(this.otheroutService.saveOtherout(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param otheroutDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "修改数据对象", required = true, dataType = "OtheroutDTO", paramType = "body")})
    @PostMapping("/update_otherout_by_primary_key")
    public ResponseData updateOtheroutByPrimaryKey(@RequestBody OtheroutDTO otheroutDTO) {
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.otheroutService.updateOtheroutByPrimaryKey(otheroutDTO), "修改数据成功");
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
    @PostMapping("/delete_otherout_by_primary_key")
    public ResponseData deleteOtheroutByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.otheroutService.deleteOtheroutByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_otherout_by_primary_keys")
    public ResponseData deleteOtheroutByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.otheroutService.deleteOtheroutByPrimaryKeys(list), "通过主键批量删除数据成功");
    }

    /**
     * 生成出库单号
     *
     * @return 是否成功
     */
    @ApiOperation(value = "初始化工单", notes = "生成出库单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "dto", value = "用户信息", required = true, allowMultiple = true, dataType = "UserDTO", paramType = "body")})
    @PostMapping("/init_order_no")
    public ResponseData initOrderNo(@RequestBody UserDTO dto) {
        return ResponseDataUtils.buildSuccess(this.otheroutService.initOrderNo(dto), "入库单号生成成功");
    }

    @ApiOperation(value = "通过仓库编号查询所有货架货物", notes = "通过仓库编号查询所有货架货物")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "仓库编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @GetMapping("/describe_shelves_and_stock_by_warehouse_id")
    public ResponseData describeShelvesAndStockByWarehouseId(@RequestParam(name = "warehouseId", required = true) String warehouseId) {
        List<ShelvesAndStockDTO> shelvesAndStockDTOList = this.otheroutService.describeShelvesAndStockByWarehouseId(warehouseId);
        System.out.println(shelvesAndStockDTOList+"OOOOOOOOOOOOOOOOOOOddddddddddddd");
        return ResponseDataUtils.buildSuccess(shelvesAndStockDTOList, "查询成功");
    }

    /**
     * 根据OtheroutNo修改数据的仓库信息
     *
     * @param otheroutDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "根据OtheroutNo修改数据的仓库信息", notes = "根据OtheroutNo修改数据的仓库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "修改数据对象", required = true, dataType = "OtheroutDTO", paramType = "body")})
    @PostMapping("/update_warehouse_id_by_otherout_no")
    public ResponseData updateWarehouseIdByOtheroutNo(@RequestBody OtheroutDTO otheroutDTO) {
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        Integer integer = this.otheroutService.updateWarehouseIdByOtheroutNo(otheroutDTO);
        if (integer == 0) {
            return ResponseDataUtils.buildError(0, "参数错误");
        } else {
            return ResponseDataUtils.buildSuccess(integer, "修改数据成功");
        }
    }

    /**
     * 条件分页模糊查询当前用户待审批出库单数据
     *
     * @param otheroutDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询当前用户待审批出库单数据", notes = "条件分页模糊查询当前用户待审批出库单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "查询条件", required = true, dataType = "OtheroutDTO", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当前用户编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_commission_fuzzy")
    public ResponseData describeCommissionFuzzy(@ModelAttribute OtheroutDTO otheroutDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtheroutWithWarehouseNameDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtheroutWithWarehouseNameDTO> list = this.otheroutService.describeCommissionFuzzy(otheroutDTO, userId);
        PageInfo<OtheroutWithWarehouseNameDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询当前用户待审批出库单数据成功");
    }

    /**
     * 审批出库单(通过和打回)
     *
     * @param otheroutDTO 实例对象
     * @return 成功或者失败
     */
    @ApiOperation(value = "审批出库单(通过和打回)", notes = "审批出库单(通过和打回)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "审批参数", required = true, dataType = "OtheroutDTO", paramType = "query")
    })
    @PostMapping("/approve_otherout")
    public ResponseData approveOtherout(@RequestBody OtheroutDTO otheroutDTO) {
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        Boolean bool = otheroutService.approveOtherout(otheroutDTO);
        if (bool) {
            return ResponseDataUtils.buildSuccess(bool, "审批出库单成功");
        } else {
            return ResponseDataUtils.buildSuccess(bool, "审批出库单失败");
        }
    }

    /**
     * 条件分页模糊查询当前用户提交出库单数据
     *
     * @param otheroutDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询当前用户提交出库单数据", notes = "条件分页模糊查询当前用户提交出库单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "查询条件", required = true, dataType = "OtheroutDTO", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当前用户编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_mine_fuzzy")
    public ResponseData describeMineFuzzy(@ModelAttribute OtheroutDTO otheroutDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtheroutWithWarehouseNameDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtheroutWithWarehouseNameDTO> list = this.otheroutService.describeMineFuzzy(otheroutDTO, userId);
        PageInfo<OtheroutWithWarehouseNameDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询当前用户提交出库单数据成功");
    }

    /**
     * 条件分页模糊查询当前用户已经审批的出库单数据
     *
     * @param otheroutDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询当前用户已经审批的出库单数据", notes = "条件分页模糊查询当前用户已经审批的出库单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDTO", value = "查询条件", required = true, dataType = "OtheroutDTO", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当前用户编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_already_done_fuzzy")
    public ResponseData describeAlreadDoneFuzzy(@ModelAttribute OtheroutDTO otheroutDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtheroutWithWarehouseNameDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        otheroutDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtheroutWithWarehouseNameDTO> list = this.otheroutService.describeAlreadDoneFuzzy(otheroutDTO, userId);
        PageInfo<OtheroutWithWarehouseNameDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询当前用户已经审批的出库单数据成功");
    }

}