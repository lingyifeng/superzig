package com.android.hz.czc.controller;


import com.android.hz.czc.entity.User;
import com.android.hz.czc.enums.UserTypeEnum;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.IUserService;
import com.android.hz.czc.utils.HttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hly
 * @since 2019-01-02
 */
//@Api(tags = "user",description = "密码相关")

@Validated
@Slf4j
@Api(value = "用户相关Controller", description = "用户相关", tags = {"user"})
@RestController
@RequestMapping("/czc/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "注册用户", notes = "根据用户名密码注册用户，别名可以指定也可以不填，默认为用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "alias", value = "别名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "角色", dataType = "Integer", paramType = "query",defaultValue = "2")
    })
    @PostMapping(value = "/register")
    public Result register(
            @NotBlank(message = "用户名不能为空") @RequestParam(value = "username") String username,
            @NotBlank(message = "密码不能为空") @RequestParam(value = "password") String password,
            @RequestParam(value = "alias", required = false) String alias,
            @RequestParam(value = "type", defaultValue = "2") Integer type) {
        if (judgeIdentify(getUser())){
            return ResultFactory.buildFailResult("无权限添加用户");
        }
        if (StringUtils.isBlank(alias)) {
            alias = username;
        }
        log.info("user:{}", new User(username, alias, password,type));
        return userService.IRegister(new User(username, alias, password,type));
    }

    @ApiOperation(value = "登陆", notes = "根据用户名密码进行登陆验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public Result login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {
        return userService.ILogin(new User(username, password));
    }

    @ApiOperation(value = "edit",notes = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户名id", required = true,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "alias", value = "别名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "手机号", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "edit",produces = "application/json;charset=UTF-8")
    public Result edit(
            @RequestParam(value = "username",required = false)String username,
            @RequestParam(value = "userid",required = true)Integer userid,
            @RequestParam(value = "password",required = false)String password,
            @RequestParam(value = "alias",required = false)String alias,
            @RequestParam(value = "telephone",required = false)String telephone){

        if (judgeIdentify(getUser())){
            return ResultFactory.buildFailResult("无权限修改用户信息");
        }

        return userService.IEdit(username,password,alias,telephone,userid);
    }

    @ApiOperation(value = "delete",notes = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userid",value = "用户id",required = true,dataType = "Long",paramType = "query")
    })
    @GetMapping(value = "delete" ,produces = "application/json;charset=UTF-8")
    public Result delete(@RequestParam(value = "userid",required = true)Long userid){
        if (judgeIdentify(getUser())){
            return ResultFactory.buildFailResult("无权限删除用户信息");
        }
        return userService.IDelete(userid);
    }

    @ApiOperation(value = "userList",notes = "用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页数", example = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页个数", example = "10", dataType = "Integer", paramType = "query")
    })
    @GetMapping(value = "userList",produces = "application/json;charset=UTF-8")
    public Result userList(
            @RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
            @Range(min = 1, max = 20) @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        return userService.userList(currentPage,size);
    }

}
