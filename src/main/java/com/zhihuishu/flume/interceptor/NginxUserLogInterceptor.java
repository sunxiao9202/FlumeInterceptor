package com.zhihuishu.flume.interceptor;


import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.zhihuishu.flume.utils.NginxUserLogParseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author: lihua
 * @date: 2020/9/8 18:14
 * @Description:
 */
public class NginxUserLogInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(NginxUserLogInterceptor.class);

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        String nginxUserlogJson = new String(event.getBody(), Charsets.UTF_8);
        String bodyOutput ;
        Map<String, String> headers = event.getHeaders();
        String status = "nginxUserLogParse";
        if (StringUtils.isBlank(nginxUserlogJson)) {
            return null;
        }
        try {
            bodyOutput = NginxUserLogParseUtil.parse(nginxUserlogJson).toString();
            headers.put("nginxUserLog",status);
            event.setHeaders(headers);
            event.setBody(bodyOutput.getBytes());
        }catch(Exception e){
            logger.error("日志json解析异常 "+e.getMessage());
            //将异常日志写入mysql,并监控告警 或  直接监控日志文件,错误信息告警
        }
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {

        List<Event> results = Lists.newArrayListWithCapacity(events.size());
        Event interceptedEvent;
        for (Event event : events) {
            try {
                //调用了intercept(e)的方法
                interceptedEvent = intercept(event);
            } catch (Exception e) {
                logger.error("nginxUserLogInterceptor JsonParse failed "+ e.getMessage());
                continue;
            }
            if (interceptedEvent != null ) {
                results.add(interceptedEvent);
            }
        }
        return results;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new NginxUserLogInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
