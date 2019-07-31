package com.android.hz.czc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("version_message")
public class VersionMessage implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String pathUrl;
    private String version;
    private String message;
    private String updateDes;

    @Override
    public String toString() {
        return "VersionMessage{" +
                "id=" + id +
                ", pathUrl='" + pathUrl + '\'' +
                ", version='" + version + '\'' +
                ", message='" + message + '\'' +
                ", updateDes='" + updateDes + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdateDes() {
        return updateDes;
    }

    public void setUpdateDes(String updateDes) {
        this.updateDes = updateDes;
    }

    public VersionMessage() {
    }

    public VersionMessage(Integer id, String pathUrl, String version, String message, String updateDes) {
        this.id = id;
        this.pathUrl = pathUrl;
        this.version = version;
        this.message = message;
        this.updateDes = updateDes;
    }
}
