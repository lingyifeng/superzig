package com.android.hz.czc.controller;


import com.android.hz.czc.entity.TMeter;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.service.ITMeterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hly
 * @since 2019-01-02
 */
@Api(value = "仪表Controller", description = "仪表模型操作相关", tags = {"meter"})
@RestController
@RequestMapping("/czc/meter")
public class TMeterController {

    @Autowired
    private ITMeterService meterService;

    @ApiOperation(value = "所有仪表模型列表")
    @GetMapping("findAll")
    public Result findAll() {
        return meterService.IFindAllMeter();
    }

    @ApiOperation(value = "修改一个仪表信息", notes = "id不能为空")
    @ApiImplicitParam(name = "meter", value = "一个仪表", required = true, dataType = "TMeter", paramType = "body")
    @PostMapping("updateMeter")
    public Result updateMeter(@RequestBody TMeter meter) {
        return meterService.IUpdateMeter(meter);
    }

    @ApiOperation(value = "根据id删除一个仪表信息", notes = "id不能为空")
    @PostMapping("deleteMeter")
    public Result deleteMeter(Long id) {
        return meterService.IDeleteMeter(id);
    }

    @ApiOperation(value = "添加一个仪表信息")
    @ApiImplicitParam(name = "meter", value = "一个仪表", required = true, dataType = "TMeter", paramType = "body")
    @PostMapping("insertMeter")
    public Result insertMeter(@Valid @RequestBody TMeter meter) {
        return meterService.IInsertMeter(meter);
    }


}
