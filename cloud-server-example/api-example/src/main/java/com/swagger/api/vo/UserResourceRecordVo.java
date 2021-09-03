package com.swagger.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: UserResourceRecordVo
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/8/27
 **/
@Data
@ApiModel("用户资源记录情况")
public class UserResourceRecordVo implements Serializable {
    private static final long serialVersionUID = -72016682441779517L;
    @ApiModelProperty("用户编号")
    private Long userId;
    @ApiModelProperty("资源编号")
    private Long resourceId;
    @ApiModelProperty("使用时长")
    private int consumptionTime;
    @ApiModelProperty("观看进度")
    private int process;
    @ApiModelProperty("完结状态 1：已完结，0：未完结")
    private int status;
}
