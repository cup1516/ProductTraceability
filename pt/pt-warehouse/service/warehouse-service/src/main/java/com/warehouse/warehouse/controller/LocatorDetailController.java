package com.warehouse.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorDetailDTO;
import com.warehouse.warehouse.service.LocatorDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import java.util.List;

/**
 * (LocatorDetail)表控制层
 *
 * @author makejava
 * @since 2020-06-02 21:20:32
 */
@Api(description = "LocatorDetail 控制层")
@RestController
@RequestMapping(value = "/locatorDetail", produces = "application/json;charset=utf-8")
public class LocatorDetailController {

    private static final Logger log = LogManager.getLogger(LocatorDetailController.class);

    /**
     * 服务对象
     */
    @Resource
    private LocatorDetailService locatorDetailService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String", paramType = "query")})
    @GetMapping("/describe_by_id")
    public ResponseData describeById(@RequestParam(name = "id", required = true) String id) {
        try {
            return ResponseDataUtils.buildSuccess(this.locatorDetailService.describeById(id),"通过主键查询单条数据成功");
        } catch (Exception e) {
            log.error("通过主键查询单条数据异常，异常为：{}", JSON.toJSONString(e));
            return ResponseDataUtils.buildError(e,"查询失败");
        }       
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
        try {
            return ResponseDataUtils.buildSuccess(this.locatorDetailService.describeAllByLimit(offset, limit),"根据下标查询多条数据成功");
        } catch (Exception e) {
            return ResponseDataUtils.buildError(e,"查询失败");
        }
    }
    
    /**
     * 条件分页查询所有数据
     *
     * @param locatorDetailDTO    实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locatorDetailDTO", value = "查询条件", required = false, dataType = "LocatorDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute LocatorDetailDTO locatorDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        try {
            Page<LocatorDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
            List<LocatorDetailDTO> list = this.locatorDetailService.describeAll(locatorDetailDTO);
            PageInfo<LocatorDetailDTO> pageInfo = page.toPageInfo();
            pageInfo.setList(list);
            return ResponseDataUtils.buildSuccess(pageInfo,"条件分页查询所有数据成功");
        } catch (Exception e) {
            return ResponseDataUtils.buildError(e,"查询失败");
        }
    }
    
    /**
     * 条件分页模糊查询所有数据
     *
     * @param locatorDetailDTO    实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locatorDetailDTO", value = "查询条件", required = true, dataType = "LocatorDetailDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute LocatorDetailDTO locatorDetailDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        try {
            Page<LocatorDetailDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
            List<LocatorDetailDTO> list = this.locatorDetailService.describeFuzzy(locatorDetailDTO);
            PageInfo<LocatorDetailDTO> pageInfo = page.toPageInfo();
            pageInfo.setList(list);
            return ResponseDataUtils.buildSuccess(pageInfo,"条件分页模糊查询所有数据成功");
        } catch (Exception e) {
            log.error("条件分页模糊查询所有数据异常，异常为：" + JSON.toJSONString(e));
            return ResponseDataUtils.buildError(e,"查询失败");    
        }
    }
    
    /**
     * 新增数据
     *
     * @param locatorDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "locatorDetailDTO", value = "插入的数据", required = true, dataType = "LocatorDetailDTO", paramType = "body")})
    @PostMapping("/insert_locatorDetail")
    public ResponseData insertLocatorDetail(@RequestBody LocatorDetailDTO locatorDetailDTO) {
        try {
            return ResponseDataUtils.buildSuccess(this.locatorDetailService.insertLocatorDetail(locatorDetailDTO),"新增数据成功");
        } catch (Exception e) {
            log.error("新增数据异常，异常为：" + JSON.toJSONString(e));
            return ResponseDataUtils.buildError(e,"新增失败");
        }
    }
    
    /**
     * 新增多条数据
     *
     * @param list 实例对象集合
     * @return 实例对象集合
     */
    @ApiOperation(value = "新增多条数据", notes = "新增多条数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "LocatorDetailDTO", paramType = "body")})
    @PostMapping("/save_locatorDetail")
    public ResponseData saveLocatorDetail(@RequestBody List<LocatorDetailDTO> list) {
        try {
            return ResponseDataUtils.buildSuccess(this.locatorDetailService.saveLocatorDetail(list), "新增多条数据成功");
        } catch (Exception e) {
            log.error("新增多条数据异常，异常为：" + JSON.toJSONString(e));
            return ResponseDataUtils.buildError(e,"新增失败");
        }
    }
    
    /**
     * 修改数据
     *
     * @param locatorDetailDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "locatorDetailDTO", value = "修改数据对象", required = true, dataType = "LocatorDetailDTO", paramType = "body")})
    @PostMapping("/update_locatorDetail_by_primary_key")
    public ResponseData updateLocatorDetailByPrimaryKey(@RequestBody LocatorDetailDTO locatorDetailDTO) {
        try {
            return ResponseDataUtils.buildSuccess(this.locatorDetailService.updateLocatorDetailByPrimaryKey(locatorDetailDTO), "修改数据成功");
        } catch (Exception e) {
            log.error("修改数据异常，异常为：" + JSON.toJSONString(e));
            return ResponseDataUtils.buildError(e,"修改失败");
        }
    }
    
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键删除数据", notes = "通过主键删除数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "body")})
    @PostMapping("/delete_locatorDetail_by_primary_key")
    public ResponseData deleteLocatorDetailByPrimaryKey(@RequestBody String id) {
        try {
            return ResponseDataUtils.buildSuccess(this.locatorDetailService.deleteLocatorDetailByPrimaryKey(id), "通过主键删除数据成功");
        } catch (Exception e) {
            log.error("通过主键删除数据异常，异常为：" + JSON.toJSONString(e));
            return ResponseDataUtils.buildError(e,"删除失败");
        }
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param list 主键集合
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "通过主键批量删除数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "list", value = "list", required = true, allowMultiple = true, dataType = "String", paramType = "body")})
    @PostMapping("/delete_locatorDetail_by_primary_keys")
    public ResponseData deleteLocatorDetailByPrimaryKeys(@RequestBody List<String> list) {
        try {
            return ResponseDataUtils.buildSuccess(this.locatorDetailService.deleteLocatorDetailByPrimaryKeys(list), "通过主键批量删除数据成功");
        } catch (Exception e) {
            log.error("通过主键批量删除数据异常，异常为：" + JSON.toJSONString(e));
            return ResponseDataUtils.buildError(e,"删除失败");
        }
    }

}