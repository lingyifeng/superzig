package com.android.hz.czc.service.impl;

import com.android.hz.czc.entity.User;
import com.android.hz.czc.enums.UserTypeEnum;
import com.android.hz.czc.mapper.UserMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hly
 * @since 2019-01-02
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result IRegister(User user) {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return ResultFactory.buildFailResult("用户名或者密码不能为空");
        }
        userMapper.insert(user);
        return ResultFactory.buildSuccessResult(userMapper.selectById(user.getId()));
    }

    @Override
    public Result ILogin(User user) {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return ResultFactory.buildFailResult("用户名或者密码不能为空");
        }
        User userDB = userMapper.selectOne(new QueryWrapper<User>()
                .lambda().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if (ObjectUtils.allNotNull(userDB)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userDB);
            log.info("欢迎“{}”成功登陆后台系统", userDB.getUsername());
            //查询到用户
            return ResultFactory.buildSuccessResult(userDB);
        }
        return ResultFactory.buildFailResult("登陆失败,没有该用户");
    }

    @Override
    public Result IEdit(String username,String password,String alias,String telephone,Integer userid) {
        User edituser = userMapper.selectById(userid);
        if(edituser.getType()==UserTypeEnum.ADMIN.getType()){
            return ResultFactory.buildFailResult("该用户为管理员,不可修改");
        }
//        User user = new User();
//        user.setId(Long.valueOf(userid));
//        if (!StringUtils.isBlank(username)) {
//            user.setUsername(username);
//        }
//        if (!StringUtils.isBlank(password)) {
//            user.setPassword(password);
//        }
//        if (!StringUtils.isBlank(alias)) {
//            user.setAlias(alias);
//        }
//        if (!StringUtils.isBlank(telephone)) {
//            user.setTelephone(telephone);
//        }
//        userMapper.updateById(user);
        userMapper.update(edituser,new UpdateWrapper<User>().lambda()
                .set(!StringUtils.isBlank(username),User::getUsername,username)
                .set(!StringUtils.isBlank(password),User::getPassword,password)
                .set(!StringUtils.isBlank(alias),User::getAlias,alias)
                .set(!StringUtils.isBlank(telephone),User::getTelephone,telephone)
                .eq(User::getId,userid));
        return ResultFactory.buildSuccessResult(userMapper.selectById(userid));
    }

    @Override
    public Result IDelete(Long userid) {
        User deleteuser = userMapper.selectById(userid);
        if(deleteuser.getType()==UserTypeEnum.ADMIN.getType()){
            return ResultFactory.buildFailResult("该用户为管理员,不可删除");
        }
        int i = userMapper.deleteById(userid);
        if(i==1){
            return ResultFactory.buildSuccessResult(null);
        }else {
            return ResultFactory.buildFailResult("用户id不存在");
        }
    }

    @Override
    public Result userList(Integer currentPage, Integer size) {
        Page<User> page = new Page<>(currentPage, size);
        IPage<User> list = userMapper.selectPage(page, null);
        return ResultFactory.buildSuccessResult(list);
    }


}
