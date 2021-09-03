package com.swagger.api.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: UserResourceRecodeForm
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/8/27
 **/
@Data
@ApiModel("记录上传")
public class UpUserResourceRecodeForm implements Serializable {
    private static final long serialVersionUID = 6892011996791226864L;
    @ApiModelProperty("用户编号")
    private Long userId;
    @ApiModelProperty("资源编号")
    private Long resourceId;
    @ApiModelProperty("观看进度")
    private int process;
    @ApiModelProperty("使用时长:秒")
    private int consumptionTime;
    @ApiModelProperty("是否任务")
    private int isTask;
    @ApiModelProperty("总时长,单位:秒")
    private int totalDuration;
}
