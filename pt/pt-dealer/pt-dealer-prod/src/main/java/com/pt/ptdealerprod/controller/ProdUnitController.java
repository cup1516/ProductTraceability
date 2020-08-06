package com.pt.ptdealerprod.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerprod.entity.ProdUnit;
import com.pt.ptdealerprod.service.ProdUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 单位表
 *
 * @author pig code generator
 * @date 2020-03-31 20:06:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/unit" )
@Api(value = "unit", tags = "单位表管理")
public class ProdUnitController {

    private ProdUnitService prodUnitService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param prodUnit 加工单位表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getProdUnitPage(Page page, ProdUnit prodUnit) {
        return R.ok(prodUnitService.getProdUnitPage(page, prodUnit,SecurityUtils.getCompanyId()));
    }

    /**
     * 获取单位列表
     * @return
     */
    @GetMapping("/list" )
    public R getProdUnitList() {
        return R.ok(prodUnitService.getProdUnitList(SecurityUtils.getCompanyId()));
    }

    /**
     * 根据单位编号获取详细信息
     */
    @GetMapping(value = "/{unitId}")
    public R getInfo(@PathVariable String unitId)
    {
        return R.ok(prodUnitService.selectUnitById(unitId,SecurityUtils.getCompanyId()));
    }

    /**
     * 新增单位
     */

    @PostMapping
    public R add( @RequestBody ProdUnit unit)
    {
        prodUnitService.checkUnitNameUnique(unit,SecurityUtils.getCompanyId());
        prodUnitService.checkUnitCodeUnique(unit,SecurityUtils.getCompanyId());
        unit.setCreateBy(SecurityUtils.getUserName());
        return R.ok(prodUnitService.insertUnit(unit,SecurityUtils.getCompanyId()));
    }

    /**
     * 修改单位
     */

    @PutMapping
    public R edit(@Validated @RequestBody ProdUnit unit)
    {
        prodUnitService.checkUnitNameUnique(unit,SecurityUtils.getCompanyId());
        prodUnitService.checkUnitCodeUnique(unit,SecurityUtils.getCompanyId());
        return R.ok(prodUnitService.updateUnit(unit,SecurityUtils.getCompanyId()));
    }

    /**
     * 删除单位
     */

    @DeleteMapping("/{unitIds}")
    public R remove(@PathVariable String[] unitIds)
    {
        return R.ok(prodUnitService.deleteUnitByIds(unitIds,SecurityUtils.getCompanyId()));
    }

    /**
     * 获取单位选择框列表
     */
    @GetMapping("/optionselect")
    public R optionselect()
    {
        List<ProdUnit> units = prodUnitService.selectUnitAll(SecurityUtils.getCompanyId());
        return R.ok(units);
    }
}
