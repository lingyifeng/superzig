package com.android.hz.czc.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import lombok.experimental.Accessors;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TResult extends BaseEntity implements Serializable {

    /**
     * 结构树id(外键)
     */
    private Long treeId;

    /**
     * 识别结果
     */
    private String result;

    /**
     * 图片的base64编码
     */
    private String pictureBase64;

    /**
     * 名称
     */
    private String name;

    /**
     * 用户id（外键）
     */
    private Long userId;


    private String currentDate;
    /**
     * 图片id （外键）
     */
    private Long pictureId;
}
