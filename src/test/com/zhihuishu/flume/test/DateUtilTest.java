package com.zhihuishu.flume.test;

import com.zhihuishu.flume.utils.DataUtil;

import java.util.HashMap;

/**
 * @author: lihua
 * @date: 2020/8/10 17:25
 * @Description:
 */
public class DateUtilTest {

    public static void main(String[] args) {


        System.out.println(DataUtil.getTimeStamp("20200810"));
        HashMap<String, String> map = new HashMap<>();
        map.put("createTime","2020-12-20T01:34:56.526Z");
        //System.out.println(getGMTTime("2020-12-20T01:34:56.526Z"));
        String utcTime=String.valueOf(map.get("createTime"));
        if(utcTime.contains("T")){
            utcTime=DataUtil.getGMTTime(utcTime);
        }
        System.out.println(utcTime);

    }
}
