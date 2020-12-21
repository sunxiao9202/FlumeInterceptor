package com.zhihuishu.flume.test;

import com.zhihuishu.flume.utils.BuriedLogParseUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: lihua
 * @date: 2020/12/16 13:27
 * @Description:
 *
 * baiduApplets  weChatApplets
 * 9zERKdkR
 */
public class BuriedLogParseUtilTest {
    public static void main(String[] args) {

        String log="\n" +
                "{\n" +
                "    \"appType\":\"baiduApplets\",    \n" +
                "    \"appPlatform\":\"ANDROID\",\n" +
                "    \"appVersion\":\"\",\n" +
                "    \"data\":[\n" +
                "        {\n" +
                "            \"module\":\"applets\", \n" +
                "            \"uuid\":\"\", \n" +
                "            \"type\":\"choice\",  \n" +
                "            \"subtype\":\"renqihaokeclick\", \n" +
                "            \"createTime\":\"2020-12-20T01:34:56.526Z\",\n" +
                "            \"courseId\":1151515151,\n" +
                "            \"courseName\":\"课程1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"module\":\"applets\",\n" +
                "            \"uuid\":\"\",   \n" +
                "            \"type\":\"choice\",\n" +
                "            \"subtype\":\"renqihaokeclick\",\n" +
                "            \"createTime\":\"2020-12-20T01:34:56.526Z\",\n" +
                "            \"courseId\":1151515151,\n" +
                "            \"courseName\":\"课程2\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

         print(log);

    }

    public static void print(String log){
        List<String> parse = BuriedLogParseUtil.parse(log);
        for (String s : parse) {
            System.out.println(s);
        }

//        AtomicInteger atomicInteger = new AtomicInteger(ThreadLocalRandom.current().nextInt());
//        System.out.println(atomicInteger);
    }
}
