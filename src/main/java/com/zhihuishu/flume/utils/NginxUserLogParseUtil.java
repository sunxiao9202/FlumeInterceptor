package com.zhihuishu.flume.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.zhihuishu.flume.domain.NgnixUserLog;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: lihua
 * @date: 2020/9/8 17:50
 * @Description:
 */
public class NginxUserLogParseUtil {

    private static final  Logger logger = LoggerFactory.getLogger(NginxUserLogParseUtil.class);

   public static NgnixUserLog parse(String log){

       NgnixUserLog ngnixUserLog;
       try {
           JSONObject jsonObjectAll = JSON.parseObject(log);
           JSONObject jsonObject = JSON.parseObject(jsonObjectAll.getString("message"));
           String timestamp = jsonObject.getString("timestamp");
           JSONObject messageJO = JSON.parseObject(jsonObject.getString("message"));
           int costTimeStamp = Integer.parseInt(messageJO.getString("costTimeStamp"));
           String method = messageJO.getString("method");
           String params = messageJO.getString("params");
           String requestId = messageJO.getString("requestId");
           String requestUrl = messageJO.getString("requestUrl");
           int userId=0;
           if(messageJO.containsKey("userId")) {
               userId = Integer.parseInt(messageJO.getString("userId"));
           }
           String uuid = messageJO.getString("uuid");
           ngnixUserLog = new NgnixUserLog(timestamp, costTimeStamp, method, params, requestId, requestUrl, userId, uuid);
       }catch (Exception e){
           logger.error("数据解析异常:"+log);
           throw new JsonParseException(e.getMessage());
       }
       return  ngnixUserLog;
   }

    public static void main(String[] args) {
        String str="{\"@timestamp\":\"2020-09-08T11:27:22.681Z\",\"@metadata\":{\"beat\":\"filebeat\",\"type\":\"doc\",\"version\":\"6.5.4\",\"topic\":\"nginx_log\"},\"offset\":247692761,\"message\":\"{\\\"timestamp\\\":\\\"2020-09-08 19:27:21.833\\\",\\\"thread\\\":\\\"http-nio-8090-exec-16\\\",\\\"logger\\\":\\\"com.able.online.web.interceptor.RequestIdInterceptor\\\",\\\"level\\\":\\\"INFO\\\",\\\"message\\\":\\\"{\\\\\\\"costTimeStamp\\\\\\\":116,\\\\\\\"method\\\\\\\":\\\\\\\"com.able.online.student.controller.course.ShareCourseController.queryShareCourseInfo\\\\\\\",\\\\\\\"params\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"requestId\\\\\\\":\\\\\\\"650b55a3efda07c3dc424aea2e6381ce\\\\\\\",\\\\\\\"requestUrl\\\\\\\":\\\\\\\"http://onlineservice.zhihuishu.com/student/course/share/queryShareCourseInfo\\\\\\\",\\\\\\\"uuid\\\\\\\":\\\\\\\"V8DGNv7l\\\\\\\"}\\\"}\",\"prospector\":{\"type\":\"log\"},\"input\":{\"type\":\"log\"},\"host\":{\"name\":\"zhihuishu-online-service-source-104-32\",\"containerized\":true,\"architecture\":\"x86_64\",\"os\":{\"codename\":\"Final\",\"platform\":\"centos\",\"version\":\"6.10 (Final)\",\"family\":\"redhat\"}},\"beat\":{\"name\":\"zhihuishu-online-service-source-104-32\",\"hostname\":\"zhihuishu-online-service-source-104-32\",\"version\":\"6.5.4\"},\"meta\":{\"cloud\":{\"provider\":\"ecs\",\"instance_id\":\"i-bp1bh80q4p152ccykdj6\",\"region\":\"cn-hangzhou\",\"availability_zone\":\"cn-hangzhou-h\"}},\"source\":\"/data/rizhi_log/yewu_online_requestId_service.log\"}\n";
        System.out.println(parse(str));

    }
}
