package com.android.hz.czc.entity;

import java.io.Serializable;

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
public class TPicture extends BaseEntity implements Serializable {

    /**
     * 图片base64编码
     */
    private String pictureBase64;

    /**
     * 名字
     */
    private String name;
    /**
     * 图片访问url
     */
    private String pictureUrl;

    /**
     * 图片路径
     */
    private String picturePath;


}
