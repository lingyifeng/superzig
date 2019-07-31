package com.android.hz.czc.constant;


import com.android.hz.czc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpSession;
import java.io.File;

@Slf4j
@Component
public class SystemConstant {
    //    public static final String tmp = System.getProperty("java.io.tmpdir")+"tmp.jpg";
//    public static final String tmpPhoto = System.getProperty("java.io.tmpdir")+"tmpPhoto.jpg";
//    public static final String scene = System.getProperty("java.io.tmpdir")+"scene.jpg";
    public static final String tmplocation = System.getProperty("user.dir") + "/data/tmp/";

//    /**
//     * 文件上传临时路径
//     */
//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        String location = System.getProperty("user.dir") + "/data/tmp";
//        File tmpFile = new File(location);
//        if (!tmpFile.exists()) {
//            tmpFile.mkdirs();
//        }
//        factory.setLocation(location);
//        return factory.createMultipartConfig();
//    }


    //删除File
    public static void deleteFile(File f) {
        if (!f.delete()) {
            log.info("请关闭使用该文件的所有进程或者流！！");
        } else {
            log.info("临时文件:" + f.getName() + " 成功被删除！");
        }
    }

}
