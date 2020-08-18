package com.warehouse.warehouse.controller.otherout;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.pojo.dto.otherout.InsertOtheroutDetailByStockIdDTO;
import com.warehouse.warehouse.pojo.dto.otherout.OtheroutDetailDTO;
import com.warehouse.warehouse.service.app.otherout.OtheroutDetailService;
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
 * 出库子表(OtheroutDetail)表控制层
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Api(description = "OtheroutDetail 控制层")
@RestController
@RequestMapping(value = "/otherout_detail", produces = "application/json;charset=utf-8")
public class OtheroutDetailController {

    private static final Logger logger = LogManager.getLogger(OtheroutDetailController.class);

    /**
     * 服务对象
     */
    @Resource
    private OtheroutDetailService otheroutDetailService;

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
        return ResponseDataUtils.buildSuccess(this.otheroutDetailService.describeById(id), "通过主键查询单条数据成功");
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
        return ResponseDataUtils.buildSuccess(this.otheroutDetailService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param otheroutDetailDTO 实例对象
     * @param pageSize          每页大小
     * @param currentPage       当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDetailDTO", value = "查询条件", required = false, dataType = "OtheroutDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute OtheroutDetailDTO otheroutDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtheroutDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        otheroutDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtheroutDetailDTO> list = this.otheroutDetailService.describeAll(otheroutDetailDTO);
        PageInfo<OtheroutDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param otheroutDetailDTO 实例对象
     * @param pageSize          每页大小
     * @param currentPage       当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDetailDTO", value = "查询条件", required = true, dataType = "OtheroutDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute OtheroutDetailDTO otheroutDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtheroutDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        otheroutDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtheroutDetailDTO> list = this.otheroutDetailService.describeFuzzy(otheroutDetailDTO);
        PageInfo<OtheroutDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param otheroutDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDetailDTO", value = "插入的数据", required = true, dataType = "OtheroutDetailDTO", paramType = "body")})
    @PostMapping("/insert_otheroutDetail")
    public ResponseData insertOtheroutDetail(@RequestBody OtheroutDetailDTO otheroutDetailDTO) {
        otheroutDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.otheroutDetailService.insertOtheroutDetail(otheroutDetailDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "OtheroutDetailDTO", paramType = "body")})
    @PostMapping("/save_otheroutDetail")
    public ResponseData saveOtheroutDetail(@RequestBody List<OtheroutDetailDTO> list) {
        return ResponseDataUtils.buildSuccess(this.otheroutDetailService.saveOtheroutDetail(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param otheroutDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otheroutDetailDTO", value = "修改数据对象", required = true, dataType = "OtheroutDetailDTO", paramType = "body")})
    @PostMapping("/update_otheroutDetail_by_primary_key")
    public ResponseData updateOtheroutDetailByPrimaryKey(@RequestBody OtheroutDetailDTO otheroutDetailDTO) {
        otheroutDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        return ResponseDataUtils.buildSuccess(this.otheroutDetailService.updateOtheroutDetailByPrimaryKey(otheroutDetailDTO), "修改数据成功");
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
    @PostMapping("/delete_otheroutDetail_by_primary_key")
    public ResponseData deleteOtheroutDetailByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.otheroutDetailService.deleteOtheroutDetailByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_otheroutDetail_by_primary_keys")
    public ResponseData deleteOtheroutDetailByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.otheroutDetailService.deleteOtheroutDetailByPrimaryKeys(list), "通过主键批量删除数据成功");
    }

    @ApiOperation(value = "通过货位编号新增数据", notes = "通过货位编号新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dto", value = "货位编号及物品信息", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @PostMapping("/insert_by_stock_id")
    public ResponseData insertByStockId(@RequestBody InsertOtheroutDetailByStockIdDTO dto) {
        Integer integer = this.otheroutDetailService.insertByStockId(dto);
        if (integer == 1) {
            return ResponseDataUtils.buildSuccess(1, "插入成功");
        } else {
            return ResponseDataUtils.buildError(integer, "参数异常");
        }
    }

    @ApiOperation(value = "通过货位编号修改数据", notes = "通过货位编号修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dto", value = "货位编号及物品信息", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @PostMapping("/update_by_stock_id")
    public ResponseData updateByStockId(@RequestBody InsertOtheroutDetailByStockIdDTO dto) {
        Integer integer = this.otheroutDetailService.updateByStockId(dto);
        if (integer == 1) {
            return ResponseDataUtils.buildSuccess(1, "更新成功");
        } else {
            return ResponseDataUtils.buildError(integer, "参数异常");
        }
    }

    @ApiOperation(value = "通过出库编号清空所有出库详情数据", notes = "通过出库编号清空所有出库详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "出库单编号", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @PostMapping("/delete_all_by_pid")
    public ResponseData deleteAllByPid(@RequestBody String orderNo) {
        return ResponseDataUtils.buildSuccess(this.otheroutDetailService.deleteAllByPid(orderNo), "清空成功");
    }
}