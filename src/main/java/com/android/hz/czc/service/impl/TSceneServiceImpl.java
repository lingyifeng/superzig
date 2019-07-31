package com.android.hz.czc.service.impl;

import com.alibaba.fastjson.JSON;
import com.android.hz.czc.constant.SystemConstant;
import com.android.hz.czc.controller.RecoController;
import com.android.hz.czc.entity.Graph;
import com.android.hz.czc.entity.TScene;
import com.android.hz.czc.entity.TSceneSpecific;
import com.android.hz.czc.entity.User;
import com.android.hz.czc.mapper.TSceneMapper;
import com.android.hz.czc.mapper.TSceneSpecificMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.ITSceneService;
import com.android.hz.czc.service.ITSceneSpecificService;
import com.android.hz.czc.utils.MyBase64Utils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
@Slf4j
@Service
public class TSceneServiceImpl extends ServiceImpl<TSceneMapper, TScene> implements ITSceneService {

    @Autowired
    private TSceneMapper sceneMapper;
    @Autowired
    private ITSceneSpecificService sceneSpecificService;
    @Autowired
    private TSceneSpecificMapper sceneSpecificMapper;

    @Override
    public Result IFindAllScenes(User user) {
        List<TScene> scenes = sceneMapper
                .selectList(
                        new LambdaQueryWrapper<TScene>().eq(TScene::getUserId, user.getId()));
        scenes.forEach(s ->
                s.setSceneSpecifics(sceneSpecificMapper
                        .selectList(new LambdaQueryWrapper<TSceneSpecific>().eq(TSceneSpecific::getSceneId, s.getId()))));
        return ResultFactory.buildSuccessResult(scenes);
    }

    @Override
    public Result IFindSceneByPage(Integer currentPage, Integer size) {
        Page<TScene> iPage = new Page<>(currentPage, size);
        IPage<TScene> tSceneIPage = sceneMapper.selectPage(iPage, null);
        return ResultFactory.buildSuccessResult(tSceneIPage);
    }

    @Override
    public Result IUpdateScene(TScene meter) {
        return null;
    }

    @Override
    public Result IDeleteScene(Long id) {
        return null;
    }

    @Override
    @Transactional
    public Result IInsertScene(String sceneName, String nodesJson, MultipartFile multipartFile, String specificJson) throws Exception {
        List<Graph.Node> nodes = JSON.parseArray(nodesJson, Graph.Node.class);
        File file = new File(RecoController.tempFileUrl());
        MyBase64Utils.getBase64ByMultipartFile(file, multipartFile);
        log.info("创建文件成功");
        if (draw(nodes, file)) {
            byte[] b = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            String base64 = Base64.getEncoder().encodeToString(b);
            TScene scene = new TScene(sceneName, base64, LocalDateTime.now());
            sceneMapper.insert(scene);
            List<TSceneSpecific> sceneSpecifics = new ArrayList<>();
            List<TSceneSpecific> sceneSpecificList = JSON.parseArray(specificJson, TSceneSpecific.class);
            for (TSceneSpecific sceneSpecific : sceneSpecificList) {
                sceneSpecifics.add(
                        new TSceneSpecific()
                                .setSpecificCode(sceneSpecific.getSpecificCode())
                                .setSpecificName(sceneSpecific.getSpecificName())
                                .setSceneId(scene.getId()));
            }
            if (sceneSpecificService.saveBatch(sceneSpecifics)) {
                scene.setSceneSpecifics(sceneSpecifics);
            }
            SystemConstant.deleteFile(file);
            return ResultFactory.buildSuccessResult(scene);
        }
        SystemConstant.deleteFile(file);
        return ResultFactory.buildFailResult("error");
    }

    //List<Graph> graphs
    public Boolean draw(List<Graph.Node> nodes, File file) throws Exception {
        //读取图片文件，得到BufferedImage对象
        FileInputStream f = new FileInputStream(file);
        BufferedImage bimg = ImageIO.read(f);
        //得到Graphics2D 对象//获取图形上下文,graphics想象成一个画笔
        Graphics2D g2d = (Graphics2D) bimg.getGraphics();
        //设置颜色和画笔粗细
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(6));
        //消除线条锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //绘制图案或文字
        List<Integer> allXY = new ArrayList<>();
        nodes.forEach(node -> {
            allXY.add(node.getX().intValue());
            allXY.add(node.getY().intValue());
        });
        for (int i = 0; i < allXY.size(); i = i + 8) {
            allXY.get(i);
            allXY.get(i + 1);
            allXY.get(i + 2);
            allXY.get(i + 3);
            allXY.get(i + 4);
            allXY.get(i + 5);
            allXY.get(i + 6);
            allXY.get(i + 7);
            g2d.drawLine(allXY.get(i), allXY.get(i + 1), allXY.get(i + 2), allXY.get(i + 3));
            g2d.drawLine(allXY.get(i + 4), allXY.get(i + 5), allXY.get(i + 6), allXY.get(i + 7));
            g2d.drawLine(allXY.get(i), allXY.get(i + 1), allXY.get(i + 6), allXY.get(i + 7));
            g2d.drawLine(allXY.get(i + 2), allXY.get(i + 3), allXY.get(i + 4), allXY.get(i + 5));
        }
        FileOutputStream fileOutputStream = new FileOutputStream(SystemConstant.tmplocation);
        //保存新图片
        Boolean flag = ImageIO.write(bimg, "JPG", fileOutputStream);
        fileOutputStream.close();
        f.close();
        return flag;
    }


}
