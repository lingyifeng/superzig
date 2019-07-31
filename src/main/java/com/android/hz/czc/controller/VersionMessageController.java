package com.android.hz.czc.controller;

import com.android.hz.czc.entity.VersionMessage;
import com.android.hz.czc.service.VersionMessageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/superzig")
public class VersionMessageController {
    @Autowired
    private VersionMessageService versionMessageService;

    @RequestMapping("/queryVersionMessage")
    public VersionMessage queryVersionMessage(String string) {
        VersionMessage versionMessage = versionMessageService.getById(1);
        System.out.println(string);
        if (string.equals(versionMessage.getVersion())) {
            versionMessage.setMessage("new");
        }
        if (!string.equals(versionMessage.getVersion())) {
            versionMessage.setMessage("update");
        }
        return versionMessage;
    }

    @RequestMapping("/downLoadVersionMessage")
    public void downLoadVersionMessage(HttpServletResponse response) throws IOException {
        VersionMessage byId = versionMessageService.getById(1);
        File file = new File(byId.getPathUrl());
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        //指明为下载
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        String filename = "czc.apk";
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        inputStream.close();
    }
}
