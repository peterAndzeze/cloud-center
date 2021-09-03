package com.swagger.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @CompanyName www.mxbc.com
 * @Description: 返回结果
 * @Author fanls
 * @Date 2020/3/10
 * @Version V1.0
 **/
@ApiModel(value = "返回结果")
public class Result<T> {
    @ApiModelProperty(value = "状态码")
    private int code = 0;
    @ApiModelProperty(value = "返回结果说明")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static <T> Result ok(String msg, T data) {
        Result r = new Result();
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> Result ok(T data) {
        return ok("", data);
    }

    public static Result ok() {
        return ok("", null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
