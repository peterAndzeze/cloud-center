package com.sw.cloud.center.common.dailytools.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @className: FastJsonTool
 * @description: json 处理
 * @author: sw
 * @date: 2021/7/3
 **/
public class FastJsonTool {
    /**
     * 功能描述：把JSON数据转换成指定的java对象
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     * @return 指定的java对象
     */
    public static <T> T deserialize(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把java对象转换成JSON数据
     *
     * @param object java对象
     * @return JSON数据
     */
    public static String serialize(Object object) {
        return JSON.toJSONString(object);
    }
    /**
     * 功能描述：把java对象转换成JSON数据(含空属性）
     *
     * @param object java对象
     * @return JSON数据
     */
    public static String serializeWithNullValue(Object object) {
        return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     * @return List<T>
     */
    public static <T> List<T> jsonStringToList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
     *
     * @param jsonData JSON数据
     * @return List<Map < String, Object>>
     */
    public static List<Map<String, Object>> jsonStringToListMap(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {});
    }

    /**
     * 将map转化为string
     *
     * @param m
     * @return
     */
    public static String mapToJsonString(Map m) {
        String s = JSONObject.toJSONString(m);
        return s;
    }

    /**
     * json字符串转化为map
     *
     * @param s
     * @return
     */
    public static Map jsonStringToMap(String s) {
        Map m = JSONObject.parseObject(s);
        return m;

    }


    /**
     * 暴力解析:Alibaba fastjson
     * @param test
     * @return
     */
    public final static boolean isJSONValid(String test) {
        try {
            JSONObject.parseObject(test);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 暴力解析:Alibaba fastjson
     * @param test
     * @return
     */
    public final static boolean isJSONValid(String test, Class clazz) {
        try {
            JSONObject.parseObject(test, clazz);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(test, clazz);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param data
     */
    public static String dataToJsonArray(Object data){
        if(validate(data)){
            return JSONArray.toJSONString(data);
        }
        return null;
    }

    /**
     * 本地数据验证
     * @param data
     * @return
     */
    private static boolean validate(Object data){
        if(null !=data && !"".equals(data)){
            return true;
        }
        return false;
    }
}
