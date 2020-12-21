package com.zhihuishu.flume.test;

import com.zhihuishu.flume.utils.JSONUtil;

import java.util.Map;

/**
 * @author: lihua
 * @date: 2020/12/16 13:04
 * @Description:
 */
public class JSONUtilsTest {

    public static void main(String[] args) {

            String str="{\"name\":\"li\",\"age\":12,\"gender\":\"male\"}";
            Map<String, Object> map = JSONUtil.ParseJSONToMap(str);
            System.out.println(map.get("name"));
            System.out.println(map.get("age"));
            System.out.println(map.getOrDefault("male",0));
    }
}
