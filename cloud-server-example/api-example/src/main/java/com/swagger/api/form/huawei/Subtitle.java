package com.swagger.api.form.huawei;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: Subtitle
 * @description: 字幕信息
 * @author: sw
 * @date: 2021/8/28
 **/
@ApiModel("字幕信息")
@Data
public class Subtitle implements Serializable {
    private static final long serialVersionUID = -2291528139942148744L;
    @ApiModelProperty(required = true,value = "字幕id")
    private int id;
    @ApiModelProperty(required = true,value = "字幕类型，目前只支持：SRT")
    private String type;
    @ApiModelProperty(required = true,value = "字幕语音类型。取值如下：CN：表示中文字幕。EN：表示英文字幕。")
    private String language;
    @ApiModelProperty(required = false,value = "字幕文件的MD5值")
    private String md5;
    @ApiModelProperty(required = false,value = "字幕描述")
    private String description;

}
