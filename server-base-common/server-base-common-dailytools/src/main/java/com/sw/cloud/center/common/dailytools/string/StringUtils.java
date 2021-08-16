package com.sw.cloud.center.common.dailytools.string;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sw.cloud.center.common.dailytools.text.StrFormatter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author tom
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String EMPTY_STR = "";
    private static final String NULL_STR = "null";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';
    /**
     * 逗号
     */
    private static final String COMMA = ",";
    private static final String STRING_PATTERN="[1-9]\\d*";
    private static final String NUMBER_PATTERN="^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || EMPTY_STR.equals(str.trim()) || NULL_STR.equalsIgnoreCase(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return EMPTY_STR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY_STR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return EMPTY_STR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY_STR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 截取指定长度的子字符串，长度以字节计算
     * @param source
     * @param byteLen
     * @param charset
     * @return
     */
    public static String subStr(String source, int byteLen, Charset charset) {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        if (byteLen < 0) {
            return source;
        }
        byte[] sourceBytes = source.getBytes();
        int len = Math.min(sourceBytes.length, byteLen);
        byte[] destBytes = new byte[len];
        System.arraycopy(sourceBytes, 0, destBytes, 0, len);
        charset = charset == null ? Charset.forName("UTF-8") : charset;
        return new String(destBytes, charset);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * @param string
     * @return 去掉字符串末尾的逗号
     */
    public static String reMoveLastComma(String string) {
        if (StringUtils.isNotNull(string) && string.endsWith(COMMA)) {
            return string.substring(0, string.length() - 1);
        } else {
            return string;
        }
    }

    /**
     * @param
     * @return 判断集合是否为空集合
     */
    public static boolean listIsNotNull(List list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 字符串转list
     * @param str
     * @param regex
     * @return
     */
    public static List<String> string2List(String str,String regex){
        if (StringUtils.isNotNull(str)){
            String[] split = str.split(regex);
            return new ArrayList<>(Arrays.asList(split));
        }
        return new ArrayList<>(Arrays.asList());
    }

    /**
     * list 转字符串
     * @param strList
     * @return
     */
    public static String list2String(List<String> strList){
        String newStr = strList.stream().collect(Collectors.joining(","));
        return newStr;
    }

    /**
     * 合并两个list集合, 去掉重复数据
     * @param from
     * @param to
     * @return
     */
    public static List<String> unionList(List<String> from, List<String> to){
        to.removeAll(from);
        to.addAll(from);
        return to;
    }



    public static String fenToYuan(Object o) {
        if (o == null){
            return "0.00";
        }
        String s = o.toString();
        int len = -1;
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
            s = removeZero(s);
            if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
                len = s.length();
                int tmp = s.indexOf("-");
                if (tmp >= 0) {
                    if (len == 2) {
                        sb.append("-0.0").append(s.substring(1));
                    } else if (len == 3) {
                        sb.append("-0.").append(s.substring(1));
                    } else {
                        sb.append(s.substring(0, len - 2)).append(".").append(s.substring(len - 2));
                    }
                } else {
                    if (len == 1) {
                        sb.append("0.0").append(s);
                    } else if (len == 2) {
                        sb.append("0.").append(s);
                    } else {
                        sb.append(s.substring(0, len - 2)).append(".").append(s.substring(len - 2));
                    }
                }
            } else {
                sb.append("0.00");
            }
        } else {
            sb.append("0.00");
        }
        return sb.toString();
    }

    /**
     * 功能描述：金额字符串转换：单位元转成单分 时间：2012-02-15
     *
     * @param o 传入需要转换的金额字符串
     * @return 转换后的金额字符串
     */
    public static String yuanToFen(Object o) {
        if (o == null){
            return "0";
        }
        String s = o.toString();
        int posIndex = -1;
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
            posIndex = s.indexOf(".");
            if (posIndex > 0) {
                int len = s.length();
                if (len == posIndex + 1) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append("00");
                } else if (len == posIndex + 2) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 2)).append("0");
                } else if (len == posIndex + 3) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                } else {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                }
            } else {
                sb.append(s).append("00");
            }
        } else {
            sb.append("0");
        }
        str = removeZero(sb.toString());
        if (str != null && str.trim().length() > 0 && !str.trim().equalsIgnoreCase("null")) {
            return str;
        } else {
            return "0";
        }
    }

    /**
     * 功能描述：去除字符串首部为"0"字符 时间：2012-2-15
     *
     * @param str 传入需要转换的字符串
     * @return 转换后的字符串
     * @author ：sunjian
     */
    public static String removeZero(String str) {
        char ch;
        String result = "";
        if (str != null && str.trim().length() > 0 && !str.trim().equalsIgnoreCase("null")) {
            try {
                for (int i = 0; i < str.length(); i++) {
                    ch = str.charAt(i);
                    if (ch != '0') {
                        result = str.substring(i);
                        break;
                    }
                }
            } catch (Exception e) {
                result = "";
            }
        } else {
            result = "";
        }
        return result;

    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 判断是否是json结构
     */
    public static boolean isJson(String value) {
        try {
            com.alibaba.fastjson.JSONObject.parseObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param e
     * @return
     */
    public static String logException(Exception e) {
        // 处理异常的堆栈信息
        String statckTrace = Arrays.toString(e.getStackTrace());// 将堆栈数组转换成字符串
        statckTrace = statckTrace.replace(",", "\r\n"); // 去掉逗号并换行

        // 拼接异常信息和堆栈信息
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------此处发生异常，异常信息为：\r\n").append(e).append("\r\n")
                .append(statckTrace);
        return sb.toString();
    }

    /**
     *
     * 功能描述：判断参数中是否有空值
     * 时间：2015年5月6日
     * author：qiaoxingrui
     * @param args
     * @return
     */
    public static boolean paramIsIntact(Object... args) {
        boolean flag = true;
        for (Object temp : args) {
            if (temp == null) {
                flag = false;
                break;
            }

            if (flag && ("".equals(temp.toString().trim()) || "\"null\"".equals(temp.toString())) || "null".equals(temp.toString())) {
                flag = false;
                break;
            }
        }
        return flag;
    }







    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key) + "";
            //如果值为空则不签名
            if (!StringUtils.paramIsIntact(value)) {
                continue;
            }
            if (prestr.equals("")) {
                prestr = key + "=" + value;
            } else {
                prestr = prestr + "&" + key + "=" + value;
            }
        }
        return prestr;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 注意：该方法不对map进行key值的排序
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringContainEmpty(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            //if(StringUtils.isEmpty(value)){
            //	continue;
            //}
            prestr += "&" + key + "=" + value;
        }
        // 去掉第一个&
        String pre = prestr.substring(1, prestr.length());
        return pre;
    }

    public static TreeMap<String, String> requestParamsToTreeMap(String requestParams) {
        if (StringUtils.isBlank(requestParams)) {
            return new TreeMap();
        }
        TreeMap<String, String> map = new TreeMap<>();
        String[] parts = requestParams.split("&");
        for (String part : parts) {
            if (StringUtils.isBlank(part)) {
                continue;
            }
            int index = part.indexOf("=");
            if (index == -1) {
                continue;
            }
            map.put(part.substring(0, index), part.substring(Math.min(part.length() - 1, index + 1)));
        }
        return map;
    }


    /**
     * 将json中的key 拼接成k1=v1&k2=v2格式
     * @param json
     * @param writeNullValue
     * @return
     * @auth Chentao
     *
     */
    public static String createLinkString(JSONObject json, boolean writeNullValue) {
        List<String> list = new ArrayList<>();
        list.addAll(json.keySet());
        Collections.sort(list);

        StringBuffer sb = new StringBuffer();
        for (String key : list) {
            Object value = json.get(key);
            if (writeNullValue && value instanceof JSONObject) {
                value = com.alibaba.fastjson.JSONObject.toJSONString(value, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue);
            }
            sb.append(key).append("=").append(value).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);   // 去掉多余的“&”
        return sb.toString();
    }


 /*   public static void main(String[] args) {
        Map<String,Object> params=new HashMap<>();
        params.put("a","");
        String str="";
        System.out.println(paramIsIntact(params.get("a")+""));
    }*/

    /**
     * 格式化map为json
     * 将数组类型的value转换成String后放入json
     *
     * @param map
     * @return
     */
    public static JSONObject mapToJson(Map<String, ?> map) {
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            String value;
            if (entry.getValue() == null) {
                value = "";
            } else if (entry.getValue() instanceof String[]) {
                String[] values = ((String[])entry.getValue());
                value = values[0] == null ? "" : values[0];
            } else {
                value = entry.getValue().toString();
            }
            //嵌套json
            if (value.startsWith("{") && value.endsWith("}")) {
                json.put(key, com.alibaba.fastjson.JSONObject.parseObject(value));
                continue;
            }
            json.put(key, value);
        }
        return json;
    }

    private final static String[] hex = {
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", "1E", "1F",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F",
            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D", "5E", "5F",
            "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F",
            "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E", "7F",
            "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B", "8C", "8D", "8E", "8F",
            "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
            "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF",
            "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF",
            "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF",
            "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF",
            "E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF",
            "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF"
    };

    private final static byte[] val = {
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F
    };

    /*
     * @desc JavaScript escape 编码的 Java 实现
     * @param s
     * @return java.lang.String
     **/
    public static String escape(String s) {
        if(s == null) {
            return "";
        }
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if (ch == ' ') {                        // space : map to '+'
                sbuf.append('+');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                    || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*'
                    || ch == '\'' || ch == '('
                    || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch <= 0x007F) {              // other ASCII : map to %XX
                sbuf.append('%');
                sbuf.append(hex[ch]);
            } else {                                // unicode : map to %uXXXX
                sbuf.append('%');
                sbuf.append('u');
                sbuf.append(hex[(ch >>> 8)]);
                sbuf.append(hex[(0x00FF & ch)]);
            }
        }
        return sbuf.toString();
    }

    /*
     * @desc JavaScript unescape 编码的 Java 实现
     * @param s
     * @return java.lang.String
     **/
    public static String unescape(String s) {
        if(s == null) {
            return "";
        }
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if (ch == '+') {                        // + : map to ' '
                sbuf.append(' ');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                    || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*'
                    || ch == '\'' || ch == '('
                    || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i + 1)) {         // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[s.charAt(i + 1)];
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    i += 2;
                } else {                            // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    cint = (cint << 4) | val[s.charAt(i + 3)];
                    cint = (cint << 4) | val[s.charAt(i + 4)];
                    cint = (cint << 4) | val[s.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            }
            i++;
        }
        return sbuf.toString();
    }

    /**
     * 格式化map
     * 将数组类型的value转换成String，主要用于request.getParameterMap之后
     *
     * @param map
     * @return
     */
    public static Map<String, String> formatMap(Map<String, String[]> map) {
        Map<String, String> returnMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            String value = values == null || values.length == 0 ? "" : values[0];
            returnMap.put(key, value);
        }
        return returnMap;
    }

    public static TreeMap<String, Object> jsonToTreeMap(JSONObject json) {
        TreeMap<String, Object> res = new TreeMap<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            res.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return res;
    }

    public static TreeMap<String, String> jsonToTreeMapString(com.alibaba.fastjson.JSONObject json) {
        TreeMap<String, String> res = new TreeMap<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            res.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return res;
    }

    /**
     * 功能描述：规则是:按字母升序序,遇到空值的参数不参加签名。
     * 将jso中的key和value，按照字典序排序后拼接成k1=v1|k2=v2的形式
     *
     * @param json
     * @return
     */
    public static String jsonStringKey(Map json) {
        List<String> list = new ArrayList<>();
        list.addAll(json.keySet());
        Collections.sort(list);

        StringBuffer sb = new StringBuffer();
        for (String key : list) {
            sb.append(key).append("=").append(json.get(key)).append("|");
        }
        sb.deleteCharAt(sb.length() - 1);   // 去掉多余的“|”
        return sb.toString();
    }

    /**
     * 功能描述：将字符串转换为指定长度输出,高位补0
     * @param s      转入的需要转换的的字符串
     * @param length 转换后字符串的长度
     */
    public static String stringToLength(String s, int length) {
        String temp = "";
        if (s == null) {
            for (int i = 0; i < length; i++) {
                temp += 0;
            }
            s = temp;
        } else if (s.length() < length) {

            for (int i = 0; i < length - s.length(); i++) {
                temp += 0;
            }
            s = temp + s;
        }

        return s;
    }

    /**
     * 清除空白字符（TAB，空格，回车，换行）和无法打印的字符
     *
     * @param str
     * @return
     */
    public static String delBlankAndUnprintableChars(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch < 33) {
                continue;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 取出一个指定长度大小的随机Long类型数字
     * @param length 得到数字位数<=18
     * @return long
     **/
    public static long buildRandomLong (int length) {
        double random = Math.random();
        random = random < 0.1 ? random + 0.1 : random;
        return (long) (random * Math.pow(10, length));
    }



    /**
     * 将map转为xml
     * @param map
     * @return
     */
    public static String mapToXml(Map<String, String> map){
        StringBuffer sb = new StringBuffer("<xml>");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("<").append(entry.getKey()).append(">");
            sb.append(entry.getValue());
            sb.append("</").append(entry.getKey()).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }



    /**
     * <p>合并json，coverJson会覆盖掉sourceJson相同key的值</p>
     * <p>FIXME：深层复制需要实现</p>
     *
     * @param sourceJson
     * @param coverJson
     * @return
     */
    public static JSONObject mergeAndCoverJson(JSONObject sourceJson, JSONObject coverJson) {
        if (coverJson == null) {
            return sourceJson == null ? new JSONObject() : sourceJson;
        }
        if (sourceJson == null) {
            return coverJson;
        }
        JSONObject result = (JSONObject) sourceJson.clone();
        for (Map.Entry<String, Object> entry : coverJson.entrySet()) {
            add2Json(result, entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static void add2Json(JSONObject json, String key, Object value) {
        if (json == null) {
            return;
        }
        json.put(key, value);
    }




    /**
     * 如果传入字符串是encode的json字符串,进行decode
     * @param jsonStr
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeJsonIfEncode(String jsonStr, String encode) throws UnsupportedEncodingException {
        return decode(jsonStr, "%7B", encode);
    }

    private static String decode(String str, String head, String encode) throws UnsupportedEncodingException {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return str;
        }
        String trimed = str.trim();
        if (trimed.startsWith(head)) {
            return URLDecoder.decode(trimed, encode);
        }
        return str;
    }

    /**
     * @title	产生一个UUID
     * @desc
     */
    public static String createUUID(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static String createLinkstringWithBrace(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append(createLinkStringContainEmpty(map));
        sb.append("}");
        return sb.toString();
    }

    /**
     * 正整数>0
     * @param value
     * @return
     */
    public static boolean isInteger(String value) {
        Pattern patterns = Pattern.compile(STRING_PATTERN);
        Matcher matcher = patterns.matcher(value);
        return matcher.matches();
    }



    public static <T> T cast(Object obj)
    {
        return (T) obj;
    }

    /**
     * 随机生成字符串
     * @param length 长度
     * @param lower 小写
     * @param upper 大写
     * @param number 数字
     * @param symbol 符号
     * @return
     */
    public static String generate(int length,boolean lower,boolean upper,boolean number,boolean symbol){
        StringBuffer sb = new StringBuffer();
        while(length-sb.length()>0){
            if(symbol){
                sb.append(getChar(3));
            }
            if(number){
                sb.append(getChar(2));
            }
            if(upper){
                sb.append(getChar(1));
            }
            if(lower){
                sb.append(getChar(0));
            }
        }
        String str = sb.toString();
        return str.substring(0,length);
    }

    /**
     * 随机生成字符
     * @param type 字符类型
     * @return
     */
    private static char getChar(int type) {
        Random random =new Random();
        //~!@#$%^&*()_+={}":?><;.,
        String specialChars = "~!@#$%^&*()_+=?";
        char randomChar;
        switch (type) {
            case 0:
                randomChar = (char) ('a' + random.nextInt(26));
                break;
            case 1:
                randomChar = (char) ('A' + random.nextInt(26));
                break;
            case 2:
                randomChar = (char) ('0' + random.nextInt(10));
                break;
            case 3:
                randomChar = specialChars.charAt(random.nextInt(specialChars.length()));
                break;
            default:
                randomChar =' ';
        }
        return randomChar;
    }


    /**
     * 从request对象中提取请求参数
     *
     * @param values   request对象中的map参数
     * @param isGetAll 是否获取所有的值
     * @return 多个值用逗号隔开
     */
    public static String valueOf(String[] values, Boolean isGetAll) {
        String valueStr = "";
        if (isGetAll) {
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            return valueStr;
        } else {
            if (null == values || values.length == 0) {
                return valueStr;
            }
            return values[0];
        }
    }

    /**
     * 数据排序
     * @param ja
     * @param field
     * @param isAsc
     */
    @SuppressWarnings("unchecked")
    public static void jsonSort(JSONArray ja, final String field, boolean isAsc) {
        Collections.sort(ja, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                Object f1 = ((JSONObject)o1).get(field);
                Object f2 = ((JSONObject)o2).get(field);
                if (f1 instanceof Number && f2 instanceof Number) {
                    return ((Number)f1).intValue() - ((Number)f2).intValue();
                } else {
                    return f1.toString().compareTo(f2.toString());
                }
            }
        });
        if (!isAsc) {
            Collections.reverse(ja);
        }
    }




}
