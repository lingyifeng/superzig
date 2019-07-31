package com.android.hz.czc.controller;

import com.android.hz.czc.entity.User;
import com.android.hz.czc.enums.UserTypeEnum;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;


public class BaseController {

    private static ThreadLocal<User> authContext = new ThreadLocal<>();

    //  此方法会在每次请求前调用（这个类的处理方法）
    @ModelAttribute
    public void initUser(HttpSession session) {
        Object obj = session.getAttribute("user");
        if (obj instanceof User) {
            User user = (User) obj;
            authContext.set(user);
        }
    }

    public User getUser() {
        return authContext.get();
    }

    public boolean judgeIdentify(User user) {
        return user != null && user.getType() != UserTypeEnum.ADMIN.getType();
    }
}
