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
@ApiModel("查询用户记录")
public class QueryUserResourceRecodeForm implements Serializable {
    private static final long serialVersionUID = 661022217884440509L;
    @ApiModelProperty("资源编号")
    private Long resourceId;
    @ApiModelProperty("用户编号")
    private Long userId;




}
