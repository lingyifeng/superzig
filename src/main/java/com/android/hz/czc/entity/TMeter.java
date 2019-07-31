package com.android.hz.czc.entity;

import com.android.hz.czc.enums.SizeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "仪表模型")
public class TMeter extends BaseEntity {
    /**
     * 仪表类型
     */
    @NotBlank(message = "仪表类型不能为空")
    @ApiModelProperty(value = "仪表类型", required = true, example = "0291")
    private String meterType;
    /**
     * 仪表名称
     */
    @NotBlank(message = "仪表名称不能为空")
    @ApiModelProperty(value = "仪表名称", required = true, example = "xx仪表")
    private String meterName;
    /**
     * 仪表量程
     */
    @NotBlank(message = "仪表量程不能为空")
    @ApiModelProperty(value = "仪表量程", required = true, example = "1-100ma")
    private String meterRange;
    /**
     * 仪表大小
     */
//    @NotBlank(message = "仪表大小不能为空")
    @ApiModelProperty(value = "仪表大小", required = true, example = "BIG")
    private SizeEnum meterSize;
    /**
     * 默认的使用次数
     */
    @Range(min = 1, max = 50, message = "数字必须介于1到50之间")
    @ApiModelProperty(value = "默认的使用次数", example = "20", required = true)
    private Integer defNum;

}


