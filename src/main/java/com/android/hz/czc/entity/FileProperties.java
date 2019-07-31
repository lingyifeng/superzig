package com.android.hz.czc.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by lingzong on 2019/7/30.
 */
@Component
@PropertySource("classpath:file.properties")
public class FileProperties {

    @Value("${file.path}")
    private String filepath;
    @Value("${file.url}")
    private String fileurl;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
