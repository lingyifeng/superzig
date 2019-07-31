package com.android.hz.czc.utils;

import com.android.hz.czc.entity.FileProperties;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lingzong on 2019/7/30.
 */
@Slf4j
@Component
public class FileUploadUtil {

    @Autowired
    private FileProperties properties;

    public static FileProperties fileProperties;

    @PostConstruct
    public void init() {
        fileProperties = properties;
    }

    public static HashMap<String, String> fileUpload(String directoryname, MultipartFile file) {
        if (file == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        String path = new File(fileProperties.getFilepath(), directoryname + File.separator + now.get(Calendar.YEAR)
                + File.separator + (now.get(Calendar.MONTH) + 1)).getAbsolutePath();
        String finalurl = null;
        if (!fileProperties.getFileurl().endsWith("/")) {
            finalurl = fileProperties.getFileurl() + "/" + directoryname + "/" + now.get(Calendar.YEAR) +
                    "/" + (now.get(Calendar.MONTH) + 1) + "/";
        } else {
            finalurl = fileProperties.getFileurl() + directoryname + "/" + now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/";
        }

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file_source = null;

//        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1); // 获取文件后缀名
//        String name = file.getOriginalFilename();

        String fileNameSource = file.getName();

        InputStream input = null;        // 图片数据源
        try {
            input = file.getInputStream();
            file_source = new File(dir, fileNameSource);
            finalurl = finalurl + file_source;
            FileOutputStream fos_source = new FileOutputStream(file_source);
            try {
                int LEN = 8196;
                byte[] buf = new byte[LEN];
                while (true) {
                    int len = input.read(buf, 0, LEN);
                    if (len <= 0)
                        break;
                    fos_source.write(buf, 0, len);
                    fos_source.flush();
                }
            } catch (Exception ex) {
                log.error("文件名称 {} 保存失败", file.getOriginalFilename());
                return null;
            } finally {
                fos_source.close();
                input.close();

            }
        } catch (IOException e) {
            log.error("文件名称 {} 保存失败", file.getOriginalFilename());
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("filepath", path);
        hashMap.put("fileUrl", finalurl);
        hashMap.put("filename", fileNameSource);
        return hashMap;
    }


}
