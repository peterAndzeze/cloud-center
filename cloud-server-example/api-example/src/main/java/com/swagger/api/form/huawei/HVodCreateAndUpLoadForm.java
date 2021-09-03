package com.swagger.api.form.huawei;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: HCreateAndUpLoadForm
 * @description: 华为vod api 输入
 * @author: sw
 * @date: 2021/8/28
 **/
@Data
@ApiModel("华为vod资源上传")
public class HVodCreateAndUpLoadForm implements Serializable {
    private static final long serialVersionUID = 6232092833276407906L;
    @ApiModelProperty(required = true,value = "媒资标题，长度不超过128个字节，UTF-8编码")
    private String title;
    @ApiModelProperty(required = false,value = "视频描述，长度不超过1024个字节")
    private String description;
    @ApiModelProperty(required = true,value = "音视频文件名，长度不超过128个字节。文件名后缀可选")
    private String videoName;
    @ApiModelProperty(value = "本地地址",hidden = true)
    private String videoFileUrl;
    @ApiModelProperty(required = true,value = "上传音视频文件的格式。取值如下：视频文件：MP4、TS、MOV、MXF、MPG、FLV、" +
            "WMV、AVI、M4V、F4V、MPEG、3GP、ASF、MKV、HLS\n" +
            "音频文件：MP3、OGG、WAV、WMA、APE、FLAC、AAC、AC3、MMF、AMR、M4A、M4R、WV、MP2\n" +
            "若上传格式为音频文件，则不支持转码、添加水印和字幕。")
    private String videoType;
    @ApiModelProperty(required = true,value = "媒资分类ID")
    private int categoryId=-1;
    @ApiModelProperty(required = false,value = "视频文件MD5值")
    private String videoMd5;
    @ApiModelProperty(required = false,value = "封面图片文件类型。取值如下：JPG,PNG")
    private String coverType;
    @ApiModelProperty(required = false,value = "封面文件MD5值")
    private String coverMd5;
    @ApiModelProperty(required = false,value = "字幕信息")
    private String subtitles;
    @ApiModelProperty(required = false,value = "视频标签。单个标签不超过16个字节，最多不超过16个标签。多个用逗号分隔，UTF8编码")
    private String tags;
    @ApiModelProperty(required = false,value = "是否自动发布,取值如下：0：表示不自动发布。1：表示自动发布。默认值：0")
    private int autoPublish=0;
    @ApiModelProperty(required = false,value = "是否自动审核，1：是，0：否")
    private  int autoCheck=0;
    @ApiModelProperty(required = false,value = "转码模板组名称。\n" +
            "若不为空，则使用指定的转码模板对上传的音视频进行转码，您可以在视频点播控制台配置转码模板若同时设置了" +
            "“template_group_name”和“workflow_name”字段，则" +
            "template_group_name字段生效")
    private String templateGroupName;
    @ApiModelProperty(required = false,value = "是否自动加密。\n"+
            "0：表示不加密。1：表示需要加密。默认值：0。\n" +
            "加密与转码必须要一起进行，当需要加密时，转码参数不能为空，且转码输出格式必须要为HLS。\n")
    private int autoEncrypt=0;
    @ApiModelProperty(required = false,value = "是否自动预热到CDN。取值如下：0：表示不自动预热。1：表示自动预热。默认值：0。")
    private int autoPreheat=0;
    @ApiModelProperty(value = "文件大小",hidden = true)
    private int videoSize;

    @ApiModelProperty(required = false,value = "工作流名称,若不为空，则使用指定的工作流对上传的音视频进行处理，您可以在视频点播控制台配置工作流")
    private String workflowName;

}
