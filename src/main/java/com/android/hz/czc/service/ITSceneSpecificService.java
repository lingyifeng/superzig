package com.android.hz.czc.service;

import com.android.hz.czc.entity.TScene;
import com.android.hz.czc.entity.TSceneSpecific;
import com.android.hz.czc.resultvue.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.Digits;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
public interface ITSceneSpecificService extends IService<TSceneSpecific> {

    List<TSceneSpecific> IFindSceneSpecificList(Integer sceneId);

    Result IDeleSceneSpecific(Long id);

    Result IUpdateSceneSpecific(TSceneSpecific sceneSpecific);


}
