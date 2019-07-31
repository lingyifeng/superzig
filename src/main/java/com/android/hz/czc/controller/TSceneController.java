package com.android.hz.czc.controller;


import com.android.hz.czc.constant.SystemConstant;
import com.android.hz.czc.entity.User;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.service.ITSceneService;
import com.android.hz.czc.service.ITSceneSpecificService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
@Slf4j
@Api(value = "场景Controller", description = "场景模型操作相关", tags = {"scene"})
@RestController
@RequestMapping("/czc/t-scene")
public class TSceneController extends BaseController {

    @Autowired
    private ITSceneService sceneService;

    @ApiOperation(value = "添加场景", notes = "所有的参数必须都有值", response = Result.class)
    @ApiImplicitParam(name = "sceneName", value = "场景名称", example = "A场景", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "insertScene")
    public Result insertScene(
            @NotBlank(message = "场景名不能为空") @RequestParam(value = "sceneName") String sceneName,
            @ApiParam(value = "场景模板图片", required = true) @RequestParam(value = "photo") MultipartFile multipartFile,
            @ApiParam(value = "坐标字符串JSON", required = true) @RequestParam(value = "nodesJson") String nodesJson,
            @ApiParam(value = "详细名称JSON") @RequestParam(value = "specificJson") String specificJson) {
        try {
            return sceneService.IInsertScene(sceneName, nodesJson, multipartFile, specificJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页数", example = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页个数", example = "10", dataType = "Integer", paramType = "query")
    })
    @GetMapping(value = "findScenesByPage",produces = "application/json;charset=UTF-8")
    public Result findScenesByPage(
            @RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
            @Range(min = 1, max = 20) @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        log.info("currentPage:{}", currentPage);
        log.info("size:{}", size);
        return sceneService.IFindSceneByPage(currentPage, size);
    }

    @ApiOperation(value = "全部场景", response = Result.class)
    @GetMapping(value = "findScenesAll")
    public Result findAllScenes() {
        return sceneService.IFindAllScenes(getUser());
    }


}
