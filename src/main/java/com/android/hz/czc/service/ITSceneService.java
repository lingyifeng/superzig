package com.android.hz.czc.service;

import com.android.hz.czc.entity.Graph;
import com.android.hz.czc.entity.TScene;
import com.android.hz.czc.entity.User;
import com.android.hz.czc.resultvue.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
public interface ITSceneService extends IService<TScene> {
    Result IFindAllScenes(User user);

    Result IFindSceneByPage(Integer currentPage, Integer size);

    Result IUpdateScene(TScene scene);

    Result IDeleteScene(Long id);

    Result IInsertScene(String sceneName, String nodesJson, MultipartFile multipartFile, String specificJson) throws Exception;

    Boolean draw(List<Graph.Node> nodes, File file) throws Exception;
}
