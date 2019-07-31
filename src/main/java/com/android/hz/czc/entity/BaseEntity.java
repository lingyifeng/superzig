package com.android.hz.czc.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 所有entity的基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BaseEntity {

    protected Long id;
    @JSONField(serialize = false)
    @TableLogic
    @ApiModelProperty(hidden = true)
    private Integer isDelete;

//    @TableField(exist = false)
//    private List<T> list;

}
