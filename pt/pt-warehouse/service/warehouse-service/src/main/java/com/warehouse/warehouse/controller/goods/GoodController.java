package com.warehouse.warehouse.controller.goods;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.service.app.goods.GoodService;
import com.warehouse.warehouse.pojo.dto.goods.GoodCategoryAndGoodDTO;
import com.warehouse.warehouse.pojo.dto.goods.GoodDTO;
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
 * (Good)表控制层
 *
 * @author makejava
 * @since 2020-04-11 00:05:51
 */
@Api(description = "Good 控制层")
@RestController
@RequestMapping(value = "/good", produces = "application/json;charset=utf-8")
public class GoodController {

    private static final Logger logger = LogManager.getLogger(GoodController.class);

    /**
     * 服务对象
     */
    @Resource
    private GoodService goodService;

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
        return ResponseDataUtils.buildSuccess(this.goodService.describeById(id), "通过主键查询单条数据成功");
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
        return ResponseDataUtils.buildSuccess(this.goodService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param goodDTO     实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "goodDTO", value = "查询条件", required = false, dataType = "GoodDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute GoodDTO goodDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<GoodDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        List<GoodDTO> list = this.goodService.describeAll(goodDTO);
        PageInfo<GoodDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param goodDTO     实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "goodDTO", value = "查询条件", required = true, dataType = "GoodDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute GoodDTO goodDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<GoodDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        String companyId = SecurityUtils.getCompanyId();
        goodDTO.setCompanyId(companyId);
        List<GoodDTO> list = this.goodService.describeFuzzy(goodDTO);
        PageInfo<GoodDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param goodDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "goodDTO", value = "插入的数据", required = true, dataType = "GoodDTO", paramType = "body")})
    @PostMapping("/insert_good")
    public ResponseData insertGood(@RequestBody GoodDTO goodDTO) {
        String companyId = SecurityUtils.getCompanyId();
        goodDTO.setCompanyId(companyId);
        return ResponseDataUtils.buildSuccess(this.goodService.insertGood(goodDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "GoodDTO", paramType = "body")})
    @PostMapping("/save_good")
    public ResponseData saveGood(@RequestBody List<GoodDTO> list) {
        return ResponseDataUtils.buildSuccess(this.goodService.saveGood(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param goodDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "goodDTO", value = "修改数据对象", required = true, dataType = "GoodDTO", paramType = "body")})
    @PostMapping("/update_good_by_primary_key")
    public ResponseData updateGoodByPrimaryKey(@RequestBody GoodDTO goodDTO) {
        String companyId = SecurityUtils.getCompanyId();
        goodDTO.setCompanyId(companyId);
        return ResponseDataUtils.buildSuccess(this.goodService.updateGoodByPrimaryKey(goodDTO), "修改数据成功");
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
    @PostMapping("/delete_good_by_primary_key")
    public ResponseData deleteGoodByPrimaryKey(@RequestBody String id) {

        return ResponseDataUtils.buildSuccess(this.goodService.deleteGoodByPrimaryKey(id,SecurityUtils.getCompanyId()), "通过主键删除数据成功");
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
    @PostMapping("/delete_good_by_primary_keys")
    public ResponseData deleteGoodByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.goodService.deleteGoodByPrimaryKeys(list,SecurityUtils.getCompanyId()), "通过主键批量删除数据成功");
    }

    @ApiOperation(value = "通过商品类型查询所有商品单", notes = "通过商品类型查询所有商品单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "warehouseId", value = "商品类型", required = true, dataType = "String", paramType = "query")})
    @GetMapping("/describe_good_and_goodcategory")
    public ResponseData describeShelvesAndLocationByWarehouseId() {
        List<GoodCategoryAndGoodDTO> goodCategoryAndGoodList = this.goodService.describeGoodCategoryAndGood();
        return ResponseDataUtils.buildSuccess(goodCategoryAndGoodList, "查询成功");
    }

    /**
     * 通过主键查询单条数据
     *
     * @param goodNo
     * @return 单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "goodNo", value = "编码", required = true, dataType = "String", paramType = "query")})
    @GetMapping("/describe_by_good_no")
    public ResponseData describeByGoodNo(@RequestParam(name = "goodNo", required = true) String goodNo) {
        return ResponseDataUtils.buildSuccess(this.goodService.describeByGoodNo(goodNo), "通过主键查询单条数据成功");
    }

}