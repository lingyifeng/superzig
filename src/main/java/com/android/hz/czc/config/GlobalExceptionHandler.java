package com.android.hz.czc.config;

import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.net.ConnectException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @param request
     * @param methodException
     * @return Result对象
     * @throws Exception 前端传入JSON对象时候出发验证，验证不通过时候捕获MethodArgumentNotValidException异常，进行在次的信息包装返回给前端
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result methodArgumentNotValid(HttpServletRequest request, MethodArgumentNotValidException methodException) throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        BindingResult result = methodException.getBindingResult();
        if (result.hasErrors())
            result.getAllErrors().stream().forEach(x -> errorMessage.append(x.getDefaultMessage() + ","));
        return ResultFactory.buildFailResult(errorMessage.toString());
    }

    /**
     * @param request
     * @param methodException
     * @return Result对象
     * @throws Exception 前端普通方式发送参数时，验证参数的有效性，如验证失败则捕获ConstraintViolationException异常，进行在次的信息包装返回给前端
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Result constrainViolation(HttpServletRequest request, ConstraintViolationException methodException) throws Exception {
        return ResultFactory.buildFailResult(methodException.getMessage());
    }

    /**
     * 前端普通方式发送参数时,格式不正确,JSON转换失败捕获异常
     *
     * @param e
     * @return Result对象
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public Result readableException(HttpMessageNotReadableException e) {
        return ResultFactory.buildFailResult(e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public Result ConnectException(Exception e) {
        return ResultFactory.buildFailResult(e.getMessage());
    }
}
