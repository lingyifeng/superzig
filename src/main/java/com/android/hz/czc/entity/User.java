package com.android.hz.czc.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author hly
 * @since 2019-01-02
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "t_user")
@JSONType(ignores = {"password"})
@ApiModel(description = "用户")
public class User extends BaseEntity {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true, example = "czc")
    private String username;
    /**
     * 别名
     */
    @ApiModelProperty(value = "别名")
    private String alias;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true, example = "您的密码")
    private String password;
    /**
     * 电话
     */

    @JSONField(serialize = false)
    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "角色")
    private Integer type;

    public User() {
        super();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String alias, String password,Integer type) {
        this.username = username;
        this.alias = alias;
        this.password = password;
        this.type=type;
    }

}
