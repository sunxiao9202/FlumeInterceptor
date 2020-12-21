package com.zhihuishu.flume.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lihua
 * @date: 2020/12/16 11:10
 * @Description:
 */
public class JSONUtil {
    /**
     * JSON字符串转为Map
     * @param json
     * @return
     */
    public static Map<String, Object> ParseJSONToMap(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.putAll(jsonObject);
        return valueMap;
    }
}
