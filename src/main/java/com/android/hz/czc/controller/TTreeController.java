package com.android.hz.czc.controller;


import com.android.hz.czc.entity.TTree;
import com.android.hz.czc.entity.User;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.ITTreeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hly
 * @since 2019-01-19
 */
@Api(value = "定位相关Controller", description = "定位相关", tags = {"tree"})
@RestController
@RequestMapping("/czc/t-tree")
public class TTreeController extends BaseController {

    @Autowired
    private ITTreeService treeService;

    @ApiOperation(value = "添加节点")
    @ApiImplicitParam(name = "tree", value = "一个节点", required = true, dataType = "TTree", paramType = "body")
    @PostMapping(value = "insertTree")
    public Result insertTree(@RequestBody TTree tree) {
        try {
            User user = getUser();
            tree.setUserId(user.getId());
//            TTree tree1 = treeService.getOne(new LambdaQueryWrapper<TTree>().eq(TTree::getPid,0L));
//            if (tree1 != null){
//                if (tree.getPid() == 0L){
//                    return ResultFactory.buildFailResult("存储节点出错，因为和源根节点重复，请在现有的节点上增加");
//                }
//            }
            treeService.save(tree);
            return ResultFactory.buildSuccessResult(tree);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultFactory.buildFailResult("error");
    }

    @ApiOperation(value = "查询")
    @GetMapping(value = "findAll")
    public Result findAll() {
        Long userId = getUser().getId();
        return treeService.findAll(userId);
    }


}
