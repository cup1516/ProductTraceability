package com.warehouse.warehouse.controller.otherin;

import com.alibaba.fastjson.JSON;
import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.pojo.dto.otherin.OtherinDetailDTO;
import com.warehouse.warehouse.service.app.otherin.OtherinDetailService;
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
 * 入库子表(OtherinDetail)表控制层
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Api(description = "OtherinDetail 控制层")
@RestController
@RequestMapping(value = "/otherin_detail", produces = "application/json;charset=utf-8")
public class OtherinDetailController {

    private static final Logger logger = LogManager.getLogger(OtherinDetailController.class);

    /**
     * 服务对象
     */
    @Resource
    private OtherinDetailService otherinDetailService;

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
        return ResponseDataUtils.buildSuccess(this.otherinDetailService.describeById(id), "通过主键查询单条数据成功");
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
        return ResponseDataUtils.buildSuccess(this.otherinDetailService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param otherinDetailDTO 实例对象
     * @param pageSize         每页大小
     * @param currentPage      当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otherinDetailDTO", value = "查询条件", required = false, dataType = "OtherinDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute OtherinDetailDTO otherinDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtherinDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
         otherinDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtherinDetailDTO> list = this.otherinDetailService.describeAll(otherinDetailDTO);
        PageInfo<OtherinDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param otherinDetailDTO 实例对象
     * @param pageSize         每页大小
     * @param currentPage      当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otherinDetailDTO", value = "查询条件", required = true, dataType = "OtherinDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute OtherinDetailDTO otherinDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<OtherinDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        otherinDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        List<OtherinDetailDTO> list = this.otherinDetailService.describeFuzzy(otherinDetailDTO);
        PageInfo<OtherinDetailDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param otherinDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otherinDetailDTO", value = "插入的数据", required = true, dataType = "OtherinDetailDTO", paramType = "body")})
    @PostMapping("/insert_otherinDetail")
    public ResponseData insertOtherinDetail(@RequestBody OtherinDetailDTO otherinDetailDTO) {
        otherinDetailDTO.setCompanyId(SecurityUtils.getCompanyId());
        System.out.println(otherinDetailDTO+"oooooooin");
        int i = otherinDetailService.insertOtherinDetailbyLocatorId(otherinDetailDTO);
        if (i == 1) {
            return ResponseDataUtils.buildSuccess(i, "新增数据成功");
        } else {
            return ResponseDataUtils.buildError(i, "此货位容量不足，请分批次插入");
        }

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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "OtherinDetailDTO", paramType = "body")})
    @PostMapping("/save_otherinDetail")
    public ResponseData saveOtherinDetail(@RequestBody List<OtherinDetailDTO> list) {
        return ResponseDataUtils.buildSuccess(this.otherinDetailService.saveOtherinDetail(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param otherinDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "otherinDetailDTO", value = "修改数据对象", required = true, dataType = "OtherinDetailDTO", paramType = "body")})
    @PostMapping("/update_otherinDetail_by_primary_key")
    public ResponseData updateOtherinDetailByPrimaryKey(@RequestBody OtherinDetailDTO otherinDetailDTO) {
        return ResponseDataUtils.buildSuccess(this.otherinDetailService.updateOtherinDetailByPrimaryKey(otherinDetailDTO), "修改数据成功");
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
    @PostMapping("/delete_otherinDetail_by_primary_key")
    public ResponseData deleteOtherinDetailByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.otherinDetailService.deleteOtherinDetailByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_otherinDetail_by_primary_keys")
    public ResponseData deleteOtherinDetailByPrimaryKeys(@RequestBody List<String> list) {
        if (list.size() == 0) {
            return ResponseDataUtils.buildError(null, "没有可以清空的数据");
        }
        return ResponseDataUtils.buildSuccess(this.otherinDetailService.deleteOtherinDetailByPrimaryKeys(list), "通过主键批量删除数据成功");
    }

}