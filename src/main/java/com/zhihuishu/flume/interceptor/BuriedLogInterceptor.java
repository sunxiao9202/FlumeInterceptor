package com.zhihuishu.flume.interceptor;

import com.google.common.base.Charsets;
import com.zhihuishu.flume.utils.BuriedLogParseUtil;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lihua
 * @date: 2020/12/16 13:10
 * @Description:
 */
public class BuriedLogInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(BuriedLogInterceptor.class);

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        List<Event> results = new ArrayList<>();
        Event intercept;
        for (Event event : events) {
            try {
                //调用了intercept(e)的方法
                intercept = intercept(event);
                //获取event的body
                String buriedLog = new String(intercept.getBody(), Charsets.UTF_8);
                //解析log转化成SqlList
                List<String> parseList = BuriedLogParseUtil.parse(buriedLog);
                //将每条sql语句从source发送到channel
                for (String sql : parseList) {
                    SimpleEvent simpleEvent = new SimpleEvent();
                    simpleEvent.setBody(sql.getBytes());
                    results.add(simpleEvent);
                }
            } catch (Exception e) {
                logger.error("BuriedLogInterceptor JsonParse failed " + e.getMessage());
            }
        }
        return results;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new BuriedLogInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
