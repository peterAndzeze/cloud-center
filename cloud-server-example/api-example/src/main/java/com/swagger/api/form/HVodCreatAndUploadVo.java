package com.swagger.api.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: HVodCreatAndUploadVo
 * @description: 创建返回
 * @author: sw
 * @date: 2021/8/28
 **/
@Data
public class HVodCreatAndUploadVo  implements Serializable {
    private static final long serialVersionUID = 7833115392112629299L;
    //云上资源id
    private String cloudResourceId;
    /**
     * 云上资源地址
     */
    private String cloudUrl;
    /**
     * 资源分类
     */
    private int categoryId;
    /**
     * 资源类型
     */
    private int type;


}
