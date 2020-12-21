package com.zhihuishu.flume.interceptor;

import com.google.common.base.Charsets;
import com.zhihuishu.flume.utils.NginxLogParseUtil;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lihua
 * @date: 2020/9/4 16:45
 * @Description:
 */
public class NginxLogInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(NginxLogInterceptor.class);

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        event.setBody(NginxLogParseUtil.parse
                ( new String(event.getBody(), Charsets.UTF_8)).getBytes());
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {

        List<Event> results = new ArrayList<>();
        Event intercept;
        for (Event event : events) {
            try {
                //调用了intercept(e)的方法
                intercept= intercept(event);
                if(intercept.getBody() != null && intercept.getBody().length != 0) {
                    results.add(intercept);
                }
            } catch (Exception e) {
                logger.error("nginxLogInterceptor JsonParse failed "+ e.getMessage());
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
            return new NginxLogInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
