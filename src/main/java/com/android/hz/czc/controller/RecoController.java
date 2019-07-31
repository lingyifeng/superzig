package com.android.hz.czc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.hz.czc.constant.SystemConstant;
import com.android.hz.czc.entity.Graph;
import com.android.hz.czc.enums.PhotoEnum;
import com.android.hz.czc.mapper.TMeterMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultCode;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.ITSceneService;
import com.android.hz.czc.utils.Base64Test;
import com.android.hz.czc.utils.HttpRequest;
import com.android.hz.czc.utils.ImageInfoUtil;
import com.android.hz.czc.utils.UrlUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Api(value = "识别服务", description = "识别服务相关", tags = {"reco"})
@RestController
@RequestMapping("/czc/reco")
public class RecoController {

    private static final int pattern = BigDecimal.ROUND_HALF_UP;
    private static final int digit = 8;
    private static final int numNice = 9;
    private static final String digitalUrl = "http://10.0.0.199:6006/";
    private static final String recoMeterUrl = "http://10.0.0.195:5004/";
    private static final String recoPhotoUrl = "http://10.0.0.195:5011/";
    @Autowired
    private TMeterMapper meterMapper;
    @Autowired
    private ITSceneService sceneService;

    /**
     * 识别仪表图片
     */
    @ApiOperation(value = "识别仪表服务", notes = "注意图片的发送和模型的类型")
    @ApiImplicitParam(name = "classId", value = "模型类型", required = false, dataType = "String", paramType = "query")
    @PostMapping(value = "/recoMeter")
    public Result recoMeter(
            @ApiParam(value = "上传的图片", required = true)
            @RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "classId", required = false) String classId) {  //图片  人工智能服务模型id
        //处理逻辑
        File f = new File(tempFileUrl());  //不分系统的临时目录
        try {
//            TMeter m = meterMapper.selectOne(new QueryWrapper<TMeter>().lambda().eq(TMeter::getMeterType,classId));
//            if (!ObjectUtils.allNotNull(m)){
//                return ResultFactory.buildFailResult("没有这个类型的识别模型");
//            }
            //获取文件的编码参数
            String sendParam = getParaByFile(f, file);
            //发送 POST 请求
            String param = "class_id=" + classId + "&photo=" + sendParam + "&total_num=1" + "&camera_id=1" + "&NO=1";
            String result = HttpRequest.sendPost(recoMeterUrl, param);
            return ResultFactory.buildSuccessResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SystemConstant.deleteFile(f);
        }
        return ResultFactory.buildFailResult("识别服务无响应");
    }


    @ApiOperation(value = "识别仪表服务，发一张照片")
    @PostMapping(value = "/recoPhoto")
    public Result recoPhoto(
            @ApiParam(value = "上传的图片", required = true)
            @RequestParam(value = "file", required = true) MultipartFile file) {
        File f = new File(tempFileUrl());  //不分系统的临时目录
        try {
            //获取文件的编码参数
            String sendParam = getParaByFile(f, file);
            //发送 POST 请求
            String param = "photo=" + sendParam;
            String result = HttpRequest.sendPost(recoPhotoUrl, param);
//               System.out.println(result+"空字符串");

            if (StringUtils.isBlank(result)) {
                return ResultFactory.buildFailResult("识别服务无响应");
            }
            JSONObject object = JSON.parseObject(result);
            Base64Test.GenerateImage(object.getString("data"));
            return ResultFactory.buildSuccessResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SystemConstant.deleteFile(f);
        }
        return ResultFactory.buildFailResult("识别服务无响应");
    }

    /**
     * 识别仪表图片
     */
    @ApiOperation(value = "识别数显服务", notes = "注意图片的发送和模型的类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "模型类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "option", value = "客户端类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "meter_id", value = "仪表数组", dataType = "String", paramType = "query"),
    })

    @PostMapping(value = "/recoDigital")
    public Result recoDigital(
            @ApiParam(value = "上传的图片", required = true)
            @RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "classId", required = false) String classId,
            @RequestParam(value = "option", defaultValue = "init") String option,
            @RequestParam(value = "meter_id", required = false) String ids) {
        //处理逻辑
        File f = new File(tempFileUrl());  //不分系统的临时目录
        try {
            //获取文件的编码参数
            String sendParam = getParaByFile(f, file);
            System.out.println("digital");
            //发送 POST 请求
            String param = "class_id=" + classId + "" +
                    "&photo=" + sendParam + "&option=" + option + "&meter_id=" + ids;
            String result = HttpRequest.sendPost(digitalUrl, param);
            JSONObject object = JSON.parseObject(result);
            if (object.getBoolean("success")) {
                //如果success==true成功
                if (option.equals("read")) {
                    //是否是read操作
                    object.put("data", neatenData(object.getString("data")));
                    return ResultFactory.buildSuccessResult(object);
                } else {
                    //是否是定位坐标操作
                    Map<String, Integer> imageMap = ImageInfoUtil.getImageWidthAndHeight(f);
                    //计算图片比例
                    MathContext mc = new MathContext(numNice, RoundingMode.HALF_DOWN);
                    BigDecimal xb = new BigDecimal(imageMap.get(PhotoEnum.HPOTO.getWidth())).divide(new BigDecimal(576), mc);
                    BigDecimal yb = new BigDecimal(imageMap.get(PhotoEnum.HPOTO.getHeight())).divide(new BigDecimal(1024), mc);
                    List<List<List<BigDecimal>>> datas = (List<List<List<BigDecimal>>>) object.get("coordinate");
                    List<Object> digitalDatas = new ArrayList<>();
                    List<Graph> graphs = new ArrayList<>();
                    datas.forEach(data -> {
                        Graph graph = new Graph();
                        List<Graph.Node> nodeList = graph.getNodeList();
                        IntStream.range(0, data.size()).forEach(i -> {
                            Graph.Node node = graph.getNodeInstance();
                            node.setX(scale(data.get(i).get(0)));
                            node.setY(scale(data.get(i).get(1)));
                            nodeList.add(node);
                        });
                        graphs.add(graph);
                    });
                    List<List<Graph.Node>> nodeList = new ArrayList<>();
                    graphs.forEach(g -> {
                        //每一个表
                        List<Graph.Node> nodes = g.getNodeList();
                        //每个表里的4个坐标
                        nodes.forEach(node -> {
                            node.setX(scale(node.getX().multiply(xb)));
                            node.setY(scale(node.getY().multiply(yb)));
                        });
                        nodeList.add(nodes);
                    });
                    List<Graph.Node> oneList = new ArrayList<>();
                    nodeList.forEach(oneList::addAll);
                    //标记图片
//                    sceneService.draw(oneList,f);
//                    byte[] b = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
//                    String base64 = Base64.getEncoder().encodeToString(b);
//                    digitalDatas.add(base64);
                    digitalDatas.add(nodeList);
                    digitalDatas.add(neatenData(object.getString("vals")));
                    return ResultFactory.buidResult(ResultCode.SUCCESS, (String) object.get("msg"), digitalDatas);
                }
            }
            //如果success==false失败
            return ResultFactory.buidResult(ResultCode.FAIL, (String) object.get("msg"), "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SystemConstant.deleteFile(f);
        }
        return ResultFactory.buildFailResult("识别服务无响应");
    }

    //创建临时文件，MultpartFile转File，返回文件的Url编码
    private String getParaByFile(File f, MultipartFile file) throws Exception {
        FileUtils.copyInputStreamToFile(file.getInputStream(), f);
        byte[] b = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
        String base64 = Base64.getEncoder().encodeToString(b);
        return UrlUtil.getURLEncoderString(base64);
    }

    //变为小数点后3位，四舌五入模式
    private BigDecimal scale(BigDecimal bigDecimal) {
        return bigDecimal.setScale(digit, pattern);
    }


    //整理参数
    private List<String> neatenData(String data) {
        return Arrays.asList(data.split("\\|"));
    }

    public static String tempFileUrl() {
        return SystemConstant.tmplocation + System.currentTimeMillis() + "photo.jpg";
    }

}

