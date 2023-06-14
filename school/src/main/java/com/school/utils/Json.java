package com.school.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class Json {
    /**
     * @param jsonStr json格式的字符串
     * @param key     要获取值的键
     * @return Object
     * @Title getJsonValueByKey
     * @Description 获取Json格式字符串中key对应的值
     * @version V1.0
     */
    public static Object getJsonValueByKey(String jsonStr, String key) {
        // 此处引入的是 com.alibaba.fastjson.JSONObject; 对象
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        // 获取到 key 对应的值
        return jsonObject.get(key);
    }
    //修改Key值对应的value值

    /**
     * @param json     要修改的json
     * @param key      目标key
     * @param newValue 目标value
     * @return
     */
    public static String updateByKey(String json, String key, String newValue) {
        Map<String, String> oldValue = JSON.parseObject(json, Map.class);
        oldValue.put(key, newValue);//改变值
        return JSONObject.toJSONString(oldValue);
    }

    public static void main(String[] args) {
        String json = "{'status':0,'data':['数码设备','证件','日用品','服饰','其他']}";
        System.err.println(getJsonValueByKey(json, "data"));
    }


}
