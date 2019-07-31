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
public class TSceneSpecific extends BaseEntity implements Serializable {

    /**
     * 唯一编号
     */
    private String specificCode;

    /**
     * 具体名称或者编号
     */
    private String specificName;

    /**
     * 场景id(外键)
     */
    private Long sceneId;

}
