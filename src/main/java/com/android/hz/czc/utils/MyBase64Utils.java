package com.android.hz.czc.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


public class MyBase64Utils {

    public static String getBase64ByMultipartFile(File file, MultipartFile multipartFile) throws Exception {
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        byte[] b = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return Base64.getEncoder().encodeToString(b);
    }


}
