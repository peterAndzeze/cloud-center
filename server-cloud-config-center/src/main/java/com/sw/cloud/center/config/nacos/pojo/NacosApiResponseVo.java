package com.sw.cloud.center.config.nacos.pojo;

import java.util.List;

/**
 * @className: ApiResponseVo
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/7/6
 **/
public class NacosApiResponseVo {
    /**
     * 状态码 200 成功
     */
    private int code;
    private String message;
    /**
     * 数据集合
     */
    private List data;



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
