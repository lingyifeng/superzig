package com.android.hz.czc.service.impl;

import com.android.hz.czc.entity.TSceneSpecific;
import com.android.hz.czc.mapper.TSceneSpecificMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.service.ITSceneSpecificService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
@Service
public class TSceneSpecificServiceImpl extends ServiceImpl<TSceneSpecificMapper, TSceneSpecific> implements ITSceneSpecificService {

    @Autowired
    private TSceneSpecificMapper sceneSpecificMapper;


    @Override
    public List<TSceneSpecific> IFindSceneSpecificList(Integer sceneId) {
        return null;
    }

    @Override
    public Result IDeleSceneSpecific(Long id) {
        return null;
    }

    @Override
    public Result IUpdateSceneSpecific(TSceneSpecific sceneSpecific) {
        return null;
    }
}
