package com.android.hz.czc.service;

import com.android.hz.czc.entity.User;
import com.android.hz.czc.resultvue.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hly
 * @since 2019-01-02
 */
public interface IUserService extends IService<User> {
    Result IRegister(User user);

    Result ILogin(User user);
    Result IEdit(String username,String password,String alias,String telephone,Integer userid);
    Result IDelete(Long userid);
    Result userList(Integer currentPage,Integer size);
}
