package com.android.hz.czc.controller;


import com.android.hz.czc.entity.FileProperties;
import com.android.hz.czc.entity.TMeter;
import com.android.hz.czc.entity.TResult;
import com.android.hz.czc.mapper.TResultMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.ITResultService;
import com.android.hz.czc.utils.FileUploadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
@Api(value = "结果Controller", description = "存储结果相关", tags = {"result"})
@RestController
@RequestMapping("/czc/t-result")
public class TResultController extends BaseController {

    @Autowired
    private ITResultService resultService;
    @Autowired
    private TResultMapper resultMapper;

    @ApiOperation(value = "添加结果集合")
    @PostMapping("insertResultList")
    public Result insertResultList(@Valid @ApiParam @RequestBody List<TResult> resultList) {
        resultService.saveBatch(resultList.stream().map(x -> x.setUserId(getUser().getId())).collect(Collectors.toList()));
        return ResultFactory.buildSuccessResult("success");
    }

    @ApiOperation(value = "根据树id查询结果集合")
    @ApiImplicitParam(name = "treeId", value = "树节点", required = true, paramType = "query")
    @GetMapping("findResultList")
    public Result findResultList(@RequestParam("treeId") Long treeId) {
        List<TResult> obj = resultMapper.selectList(new LambdaQueryWrapper<TResult>().eq(TResult::getTreeId, treeId).eq(TResult::getUserId, getUser().getId()));
        return ResultFactory.buildSuccessResult(obj);
    }

    @ApiOperation(value = "查询这个用户的所有")
    @GetMapping("findAllByUser")
    public Result findAllByUser() {
        List<TResult> obj = resultMapper.selectList(new LambdaQueryWrapper<TResult>().eq(TResult::getUserId, getUser().getId()));
        return ResultFactory.buildSuccessResult(obj);
    }

    @ApiOperation(value = "saveResult",notes = "保存结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",dataType = "File",allowMultiple = true,value = "图片文件",required = true,paramType = "query"),
            @ApiImplicitParam(name="result",value = "识别结果",required = true,paramType = "query"),
            @ApiImplicitParam(name="treeId",value = "层级id",required = true,paramType = "query"),
            @ApiImplicitParam(name="resultname",value = "结果名称",required = true,paramType = "query")
    })
    @PostMapping(value = "saveResult")
    public Result saveResult(@RequestParam("file")MultipartFile file,
                             @RequestParam("result")String result,
                             @RequestParam("treeId")Long treeId,
                             @RequestParam("resultname")String resultname){
        return resultService.saveResult(file, result, treeId, resultname, getUser().getId());
    }

    @ApiOperation(value = "getResultList",notes = "查询结果列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",dataType = "Integer",value = "当前页",paramType = "query"),
            @ApiImplicitParam(name="size",dataType = "Integer",value = "每页个数",paramType = "query"),
            @ApiImplicitParam(name="userid",dataType = "Integer",value = "用户id",paramType = "query"),
            @ApiImplicitParam(name="startTime",value = "起始时间",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="endTime",value = "结束时间",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="treeId",value = "节点id",dataType = "Integer",paramType = "query")
    })
    @GetMapping(value = "getResultList",produces = "application/json;charset=UTF-8")
    public Result getResultList(@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,
                                @RequestParam(value = "size",defaultValue = "10")Integer size,
                                @RequestParam(value = "userid",required = false)Integer userid,
                                @RequestParam(value = "startTime",required = false)String startTime,
                                @RequestParam(value = "endTime",required = false)String endTime,
                                @RequestParam(value = "treeId",required = false)Long treeId){
        return resultService.getResultList(currentPage,size,userid,startTime,endTime,treeId);
    }

    @ApiOperation(value = "getResultExcel",notes = "查询结果excel导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",dataType = "Integer",value = "当前页",paramType = "query"),
            @ApiImplicitParam(name="size",dataType = "Integer",value = "每页个数",paramType = "query"),
            @ApiImplicitParam(name="userid",dataType = "Integer",value = "用户id",paramType = "query"),
            @ApiImplicitParam(name="startTime",value = "起始时间",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="endTime",value = "结束时间",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="treeId",value = "节点id",dataType = "Integer",paramType = "query")
    })
    @GetMapping(value = "getResultExcel")
    public Result getResultExcel(@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,
                                @RequestParam(value = "size",defaultValue = "10")Integer size,
                                @RequestParam(value = "userid",required = false)Integer userid,
                                @RequestParam(value = "startTime",required = false)String startTime,
                                @RequestParam(value = "endTime",required = false)String endTime,
                                @RequestParam(value = "treeId",required = false)Long treeId,
                                 HttpServletResponse response){
        return resultService.getResultExcel(response,currentPage,size,userid,startTime,endTime,treeId);
    }

}
