package com.warehouse.warehouse.controller.customer;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warehouse.warehouse.pojo.dto.customer.CustomerDTO;
import com.warehouse.warehouse.service.app.customer.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 客户表(Customer)表控制层
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */
@Api(description = "Customer 控制层")
@RestController
@RequestMapping(value = "/customer", produces = "application/json;charset=utf-8")
public class CustomerController {

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    /**
     * 服务对象
     */
    @Resource
    private CustomerService customerService;

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
        return ResponseDataUtils.buildSuccess(this.customerService.describeById(id), "通过主键查询单条数据成功");
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
        return ResponseDataUtils.buildSuccess(this.customerService.describeAllByLimit(offset, limit), "根据下标查询多条数据成功");
    }

    /**
     * 条件分页查询所有数据
     *
     * @param customerDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页查询所有数据", notes = "条件分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "customerDTO", value = "查询条件", required = false, dataType = "CustomerDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_all")
    public ResponseData describeAll(@ModelAttribute CustomerDTO customerDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<CustomerDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        List<CustomerDTO> list = this.customerService.describeAll(customerDTO);
        PageInfo<CustomerDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页查询所有数据成功");
    }

    /**
     * 条件分页模糊查询所有数据
     *
     * @param customerDTO 实例对象
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 对象列表
     */
    @ApiOperation(value = "条件分页模糊查询所有数据", notes = "条件分页模糊查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "customerDTO", value = "查询条件", required = true, dataType = "CustomerDTO", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, dataType = "Long", paramType = "query")
    })
    @GetMapping("/describe_fuzzy")
    public ResponseData describeFuzzy(@ModelAttribute CustomerDTO customerDTO, @RequestParam(name = "pageSize", required = true) Long pageSize, @RequestParam(name = "currentPage", required = true) Long currentPage) {
        Page<CustomerDTO> page = PageHelper.startPage(currentPage.intValue(), pageSize.intValue());
        List<CustomerDTO> list = this.customerService.describeFuzzy(customerDTO);
        PageInfo<CustomerDTO> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return ResponseDataUtils.buildSuccess(pageInfo, "条件分页模糊查询所有数据成功");
    }

    /**
     * 新增数据
     *
     * @param customerDTO 实例对象
     * @return 实例对象c
     */
    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "customerDTO", value = "插入的数据", required = true, dataType = "CustomerDTO", paramType = "body")})
    @PostMapping("/insert_customer")
    public ResponseData insertCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseDataUtils.buildSuccess(this.customerService.insertCustomer(customerDTO), "新增数据成功");
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
            @ApiImplicitParam(name = "list", value = "插入的数据集合", required = true, allowMultiple = true, dataType = "CustomerDTO", paramType = "body")})
    @PostMapping("/save_customer")
    public ResponseData saveCustomer(@RequestBody List<CustomerDTO> list) {
        return ResponseDataUtils.buildSuccess(this.customerService.saveCustomer(list), "新增多条数据成功");
    }

    /**
     * 修改数据
     *
     * @param customerDTO 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "customerDTO", value = "修改数据对象", required = true, dataType = "CustomerDTO", paramType = "body")})
    @PostMapping("/update_customer_by_primary_key")
    public ResponseData updateCustomerByPrimaryKey(@RequestBody CustomerDTO customerDTO) {
        return ResponseDataUtils.buildSuccess(this.customerService.updateCustomerByPrimaryKey(customerDTO), "修改数据成功");
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
    @PostMapping("/delete_customer_by_primary_key")
    public ResponseData deleteCustomerByPrimaryKey(@RequestBody String id) {
        return ResponseDataUtils.buildSuccess(this.customerService.deleteCustomerByPrimaryKey(id), "通过主键删除数据成功");
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
    @PostMapping("/delete_customer_by_primary_keys")
    public ResponseData deleteCustomerByPrimaryKeys(@RequestBody List<String> list) {
        return ResponseDataUtils.buildSuccess(this.customerService.deleteCustomerByPrimaryKeys(list), "通过主键批量删除数据成功");
    }


    /**
     * @title: findAllUser
     * @description: 查询所有用户
     * @params: []
     * @return: com.common.utils.ResponseData
     * @createTime: 2020/4/5 12:38
     * @version: 1.0
     * @author: wanpp
     */
    @ApiOperation(value = "查询所有供应商", notes = "查询所有供应商信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "认证信息", required = true, dataType = "String", paramType = "header")})
    @GetMapping("/find_all_customer")
    public ResponseData findAllCustomer() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerDTOS = customerService.findAllCustomer();
        return ResponseDataUtils.buildSuccess(customerDTOS, "查询所有用户成功");
    }
}
