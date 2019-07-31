package com.android.hz.czc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author hly
 * @since 2019-01-19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TTree extends BaseEntity implements Serializable {

    /**
     * 名称
     */
    @NotBlank(message = "名字不能为空")
    private String name;

    /**
     * 等级
     */
    @Digits(integer = 2, fraction = 0)
    private Integer level;

    /**
     * 用户id
     */
    @JSONField(serialize = false)
    private Long userId;

    /**
     * 上级id
     */
    private Long pid;

    @TableField(exist = false)
    private List<TTree> treeList = new ArrayList<>();

    private String structcode;
    private String allName;
}
