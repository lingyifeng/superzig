package com.android.hz.czc.service;

import com.android.hz.czc.entity.TMeter;
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
public interface ITMeterService extends IService<TMeter> {
    Result IFindAllMeter();

    Result IUpdateMeter(TMeter meter);

    Result IDeleteMeter(Long id);

    Result IInsertMeter(TMeter meter);
}
