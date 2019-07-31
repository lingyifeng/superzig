package com.android.hz.czc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.utils.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;

/**
 * @program: springboot-dev-template    IdentifyController
 * @description:
 * @author: Mr.L
 * @create: 2019-06-17 09:38
 **/
@Controller
@RequestMapping("/superzig")
public class IdentifyController {
    @PostMapping("/reader")
    public Result reader(String photo, String config) throws Exception {
        String sendPost = HttpRequest.sendPost("http://10.0.0.194:5011/reader/", "photo=" + URLEncoder.encode(photo, "UTF-8") + "&config=" + config);
        JSONObject object = JSON.parseObject(sendPost);

        return ResultFactory.buildSuccessResult(object.get("model_list").toString());
    }

    @ResponseBody
    @RequestMapping(value = "/backModel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result backModel(String config) throws Exception {
        String sendPost = HttpRequest.sendPost("http://10.0.0.194:5011/backmodel/", "config=" + config);
        /*if(sendPost==null){
            return "识别出错";
        }*/
        JSONObject object = JSON.parseObject(sendPost);
        return ResultFactory.buildSuccessResult(object.get("model_list").toString());

    }

    @ResponseBody
    @RequestMapping(value = "/config", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject config(String photo, String config) throws Exception {
        String sendPost = HttpRequest.sendPost("http://10.0.0.194:5011/config/", "photo=" + URLEncoder.encode(photo, "UTF-8") + "&config=" + config);
        /*f(sendPost==null){
            return "识别出错";
        }*/
        JSONObject object = JSON.parseObject(sendPost);
        return object;
       // return ResultFactory.buildSuccessResult(object.get("model_list").toString());

    }

    @ResponseBody
    @RequestMapping(value = "/scenario", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result scenario(String username) throws Exception {
        String sendPost = HttpRequest.sendPost("http://10.0.0.150:10000", "username=" + username);
        /*if(sendPost==null){
            return "识别出错";
        }*/
        JSONObject object = JSON.parseObject(sendPost);
        return ResultFactory.buildSuccessResult(object.get("scenenames").toString());

    }

    @ResponseBody
    @RequestMapping(value = "/detect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result detect(String username, String photo) throws Exception {
        String sendPost = HttpRequest.sendPost("http://10.0.0.150:10000/detect", "photo=" + URLEncoder.encode(photo, "UTF-8") + "&username=" + username);
       /* if(sendPost==null){
            return "识别出错";
        }*/
        JSONObject object = JSON.parseObject(sendPost);
        return ResultFactory.buildSuccessResult(object.get("boxes").toString());

    }

    @ResponseBody
    @RequestMapping(value = "/registerScene", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result registerScene(String photo, String username, String scenename, String selected_boxes) throws Exception {
        String sendPost = HttpRequest.sendPost("http://10.0.0.150:10000/register_scene", "photo=" + URLEncoder.encode(photo, "UTF-8") + "&username=" + username + "&scenename=" + scenename + "&selected_boxes=" + selected_boxes);
      /*  if(sendPost==null){
            return "识别出错";
        }*/
        JSONObject object = JSON.parseObject(sendPost);
        return ResultFactory.buildSuccessResult(object.get("status").toString());

    }

    @ResponseBody
    @RequestMapping(value = "/recognize", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result recognize(String username, String photo, String scenename) throws Exception {
        String sendPost = HttpRequest.sendPost("http://10.0.0.150:10000/recognize", "photo=" + URLEncoder.encode(photo, "UTF-8") + "&username=" + username + "&scenename=" + scenename);
        JSONObject object = JSON.parseObject(sendPost);
        Result result = new Result();
        result.setCode(200);
        result.setMessage(object.get("boxes").toString());
        result.setData(object.get("result").toString());
        return result;
    }
}

