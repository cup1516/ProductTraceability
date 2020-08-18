package com.warehouse.warehouse.controller.warehouse.shelves;

import com.alibaba.fastjson.JSON;
import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesAndLocatorDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.ShelvesDTO;
import com.warehouse.warehouse.pojo.dto.warehouse.shelves.WarehouseAndShelvesDTO;
import com.warehouse.warehouse.service.app.warehouse.shelves.ShelvesService;
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
 * 货架表(Shelves)表控制层
 *
 * @author makejava
 * @since 2020-04-12 20:32:33
 */
@Api(description = "Shelves 控制层")
@RestController
@RequestMapping(value = "/shelves", produces = "application/json;charset=utf-8")
public class ShelvesController {

    private static final Logger logger = LogManager.getLogger(ShelvesController.class);

    /**
     * 服务对象
     */
    @Resource
    private ShelvesService shelvesService;

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
            return ResponseDataUtils.buildSuccess(this.shelvesService.describeById(id), "通过主键查询单条数据成功");
    }

    /**
     * 查询货架详情
     * @param id 货架id
     * @return
     */

    @GetMapping("/describe_shelf_detail")
    public ResponseData describeShelfDetail(@RequestParam(name = "id", required = true) String id) {
        return ResponseDataUtils.buildSuccess(this.shelvesService.describeShelfDetail(id), "通过主键查询单条数据成功");
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
            @ApiImplicitParam(name = "查询条数", value = "limit", required = true, dataType = "long", paramType = "query")
    })
    @GetMapping("/describe_all_by_limit")
    public ResponseData describeAllByLimit(@RequestParam(name = "offset", required = true) long offset, @RequestParam(name = "limit", required = true) long limit) {
            return ResponseDataUtils.buildSuccess(this.shelvesService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param shelvesDTO  实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shelvesDTO", value = "查询条件", required = false, dataType = "ShelvesDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute ShelvesDTO shelvesDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
            Page<ShelvesDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
            List<ShelvesDTO> list = this.shelvesService.describeAll(shelvesDTO);
            PageInfo<ShelvesDTO> pageInfo = page.toPageInfo();
            pageInfo.setList(list);
            return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param shelvesDTO  实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shelvesDTO", value = "查询条件", required = true, dataType = "ShelvesDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute ShelvesDTO shelvesDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<ShelvesDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        String companyId = SecurityUtils.getCompanyId();
        shelvesDTO.setCompanyId(companyId);
            List<ShelvesDTO> list = this.shelvesService.describeFuzzy(shelvesDTO);
            PageInfo<ShelvesDTO> pageInfo = page.toPageInfo();
            pageInfo.setList(list);
        System.out.println(list+"qqqqqqqqqqqqqqqqqqqqqq");
            return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 查询用户所有数据(级联)
     *
     * @param userId  用户id
     * @return 对象列表
     */
    @ApiOperation(value = "查询用户所有数据(级联)", notes = "查询用户所有数据(级联)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/describe_all_mine_group_by_warehouse")
    public ResponseData describeAllMineGroupByWarehouse(@RequestParam(name = "userId", required = true) String userId) {
            List<WarehouseAndShelvesDTO> list = this.shelvesService.describeAllMineGroupByWarehouse(userId);
            return ResponseDataUtils.buildSuccess(list, "查询用户所有数据(级联)成功");
    }

    /**
     * 新增数据
     *
     * @param shelvesDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shelvesDTO", value = "插入的数据", required = true, dataType = "ShelvesDTO", paramType = "body")
    })
    @PostMapping("/insert_shelves")
    public ResponseData insertShelves(@RequestBody ShelvesDTO shelvesDTO) {
        String companyId = SecurityUtils.getCompanyId();
        shelvesDTO.setCompanyId(companyId);
            return ResponseDataUtils.buildSuccess(this.shelvesService.insertShelves(shelvesDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "ShelvesDTO", paramType = "body")
    })
    @PostMapping("/save_shelves")
    public ResponseData saveShelves(@RequestBody List<ShelvesDTO> list) {

            return ResponseDataUtils.buildSuccess(this.shelvesService.saveShelves(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param shelvesDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shelvesDTO", value = "修改数据对象", required = true, dataType = "ShelvesDTO", paramType = "body")
    })
    @PostMapping("/update_shelves_by_primary_key")
    public ResponseData updateShelvesByPrimaryKey(@RequestBody ShelvesDTO shelvesDTO) {
        shelvesDTO.setCompanyId(SecurityUtils.getCompanyId());
            return ResponseDataUtils.buildSuccess(this.shelvesService.updateShelvesByPrimaryKey(shelvesDTO), "修改数据成功");
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
    @PostMapping("/delete_shelves_by_primary_key")
    public ResponseData deleteShelvesByPrimaryKey(@RequestBody String id) {
            return ResponseDataUtils.buildSuccess(this.shelvesService.deleteShelvesByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_shelves_by_primary_keys")
    public ResponseData deleteShelvesByPrimaryKeys(@RequestBody List<String> list) {
            return ResponseDataUtils.buildSuccess(this.shelvesService.deleteShelvesByPrimaryKeys(list), "通过主键批量删除数据成功");
    }

    @ApiOperation(value = "通过仓库编号查询所有货架", notes = "通过仓库编号查询所有货架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "仓库编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @GetMapping("/describe_shelves_and_location_by_warehouse_id")
    public ResponseData describeShelvesAndLocationByWarehouseId(@RequestParam(name = "warehouseId", required = true) String warehouseId) {
            List<ShelvesAndLocatorDTO> shelvesAndLocationDTOList = this.shelvesService.describeShelvesAndLocationByWarehouseId(warehouseId);
            return ResponseDataUtils.buildSuccess(shelvesAndLocationDTOList, "查询成功");
    }

    @ApiOperation(value = "通过仓库编号查询所有可用货架", notes = "通过仓库编号查询所有可用货架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "仓库编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/describe_usable_shelves_and_location_by_warehouse_id")
    public ResponseData describeUsableShelvesAndLocationByWarehouseId(@RequestParam(name = "warehouseId", required = true) String warehouseId , @RequestParam(name = "goodsNo", required = true) String goodsNo,@RequestParam(name = "batch", required = true) String batch) {
            List<ShelvesAndLocatorDTO> shelvesAndLocationDTOList = this.shelvesService.describeUsableShelvesAndLocationByWarehouseId(warehouseId,goodsNo,batch);
            return ResponseDataUtils.buildSuccess(shelvesAndLocationDTOList, "查询成功");
    }

    /**
     * 通过货位id查询单条数据
     *
     * @param locatorId
     * @return 单条数据
     */
    @ApiOperation(value = "通过货位id查询单条数据", notes = "通过货位id查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locatorId", value = "货位id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/describe_by_locator_id")
    public ResponseData describeByLocatorId(@RequestParam(name = "locatorId", required = true) String locatorId) {
            return ResponseDataUtils.buildSuccess(this.shelvesService.describeByLocatorId(locatorId), "通过货位id查询单条数据成功");
    }

    @GetMapping("/describe_all_shelves_by_warehouseId")
    public ResponseData describeAllShelvesByWarehouseId(@RequestParam(name = "warehouseId", required = true) String warehouseId) {
        return ResponseDataUtils.buildSuccess(this.shelvesService.describeAllShellfByWarehouseId(warehouseId), "通过仓库id查询所有货架数据成功");
    }

    @GetMapping("/describe_all_shelvesName_by_warehouseId")
    public ResponseData describeAllShelvesNameByWarehouseId(@RequestParam(name = "warehouseId", required = true) String warehouseId) {
        return ResponseDataUtils.buildSuccess(this.shelvesService.describeAllShelfNameByWarehouseId(warehouseId), "通过仓库id查询所有货架数据成功");
    }

}