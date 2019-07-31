package com.android.hz.czc.service.impl;

import com.android.hz.czc.entity.TMeter;
import com.android.hz.czc.mapper.TMeterMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.ITMeterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hly
 * @since 2019-01-02
 */
@Service
public class TMeterServiceImpl extends ServiceImpl<TMeterMapper, TMeter> implements ITMeterService {

    @Autowired
    private TMeterMapper meterMapper;

    @Override
    public Result IFindAllMeter() {
        return ResultFactory.buildSuccessResult(meterMapper.selectList(null));
    }

    @Override
    public Result IUpdateMeter(TMeter meter) {
        if (StringUtils.isBlank(meter.getId().toString())) {
            return ResultFactory.buildFailResult("修改的id不能为空");
        }
        meterMapper.updateById(meter);
        return ResultFactory.buildSuccessResult(meterMapper.selectById(meter.getId()));
    }

    @Override
    public Result IDeleteMeter(Long id) {
        if (id == null) {
            return ResultFactory.buildFailResult("删除的id不能为空");
        }
        if (ObjectUtils.allNotNull(meterMapper.selectById(id))) {
            meterMapper.deleteById(id);
            return ResultFactory.buildSuccessResult("删除完成");
        }
        return ResultFactory.buildFailResult("删除失败，没有找到该记录");
    }

    @Override
    public Result IInsertMeter(TMeter meter) {
        meterMapper.insert(meter);
        return ResultFactory.buildSuccessResult(meterMapper.selectById(meter.getId()));
    }

}
