package com.android.hz.czc.controller;


import com.android.hz.czc.mapper.TPictureMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
@Api(value = "图片Controller", description = "图片", tags = {"picture"})
@RestController
@RequestMapping("/czc/t-picture")
public class TPictureController {

    @Autowired
    private TPictureMapper pictureMapper;

    @ApiOperation(value = "获取照片")
    @GetMapping(value = "get")
    public Result get() {
        return ResultFactory.buildSuccessResult(pictureMapper.selectList(null));
    }


}
