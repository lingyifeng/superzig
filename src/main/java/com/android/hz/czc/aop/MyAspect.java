package com.android.hz.czc.aop;

import com.android.hz.czc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class MyAspect {

    ////表示匹配com.android.hz.czc.controller包及其子包下的所有方法
//    @Pointcut("execution(* com.android.hz.czc.controller..*.*(..)) ")
//    public void controllerPointcut() {}
//    @Pointcut("execution(* com.android.hz.czc.service..*.*(..)) ")
//    public void servicePointcut() {}
//
//
//    @Before("controllerPointcut()")
//    public void before() {
//
//    }

}
