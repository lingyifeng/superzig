package com.android.hz.czc.service;

import com.android.hz.czc.entity.TTree;
import com.android.hz.czc.resultvue.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hly
 * @since 2019-01-19
 */
public interface ITTreeService extends IService<TTree> {

    public Result findAll(Long userId);
}
