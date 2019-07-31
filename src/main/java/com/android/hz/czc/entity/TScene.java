package com.android.hz.czc.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TScene extends BaseEntity {

    /**
     * 场景名称
     */
    @NotBlank(message = "场景名称不能为空")
    private String sceneName;

    /**
     * 场景图片模板
     */
    private String scenePicture;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 用户id(外键)
     */
    private Long userId;

    /**
     * 详情节点名称对象集合
     */
    @TableField(exist = false)
    private List<TSceneSpecific> sceneSpecifics;

    public TScene() {
    }

    public TScene(@NotBlank(message = "场景名称不能为空") String sceneName, String scenePicture, LocalDateTime createTime) {
        this.sceneName = sceneName;
        this.scenePicture = scenePicture;
        this.createTime = createTime;
    }
}
