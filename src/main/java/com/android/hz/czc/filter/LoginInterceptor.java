package com.android.hz.czc.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.hz.czc.entity.User;
import com.android.hz.czc.resultvue.ResultFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    private static final String NO_LOGIN = "No Login";


    //不需要登录就可以访问的路径(比如:注册登录等)
//    String[] includeUrls = new String[]{"/czc/user/login","/czc/user/register"};
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/superzig1.0_android/czc/user/login")));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
//        System.out.println("拦截器preHandle方法执行");
        Writer writer = response.getWriter();
//
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession(false);
        log.info("当前请求的路径是:" + request.getRequestURI());
//        boolean needFilter = isNeedFilter(request.getRequestURI());

        if (ALLOWED_PATHS.contains(request.getRequestURI())) { //不需要过滤直接传给下一个过滤器
            return true;
        } else {
            //需要过滤
            // session中包含user对象,则是登录状态
            if (session == null) {
                writer.write(JSON.toJSONString(ResultFactory.buildFailResult("请登陆之后在执行操作")));
                writer.flush();
                return false;
            }
            if (session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");
                log.info("用户“{}”已经登陆了", user.getUsername());
                return true;
            } else {
                //判断是否是ajax请求
                String requestType = request.getHeader("X-Requested-With");
                if (requestType != null && "XMLHttpRequest".equals(requestType)) {
                    log.info("这个请求类型是AJAX请求");
                    writer.write(JSON.toJSONString(ResultFactory.buildFailResult(this.NO_LOGIN)));
                    writer.flush();
                    return false;
                }
                writer.write(JSON.toJSONString(ResultFactory.buildFailResult(this.NO_LOGIN)));
                writer.flush();
                System.out.println("NO LOGIN.....");
                return false;
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3)
            throws Exception {
    }

//    private boolean isNeedFilter(String uri) {
//        for (String includeUrl : includeUrls) {
//            if(includeUrl.equals(uri)) {
//                return false;
//            }
//        }
//        return true;
//    }
}
