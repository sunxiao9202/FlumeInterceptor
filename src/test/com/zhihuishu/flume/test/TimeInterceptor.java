package com.zhihuishu.flume.test;

/**
 * @author: lihua
 * @date: 2020/8/10 19:05
 * @Description:
 */
import com.alibaba.fastjson.JSON;
import org.apache.commons.compress.utils.Charsets;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TimeInterceptor implements Interceptor {


    private static final Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);
    private final boolean preserveExisting;
    private final String header;
    private long timeStamp=0l;


    private TimeInterceptor(boolean preserveExisting, String header) {
        this.preserveExisting = preserveExisting;
        this.header = header;
    }

    public void initialize() { }

    public Event intercept(Event event) {

        try{
            Map<String, String> headers = event.getHeaders();

            //获取event中的server_time(2020-03-20T04:46:42.926+0800)字段对应的时间戳作为timestamp
            String line = new String(event.getBody(), Charsets.UTF_8);
            String server_time = JSON.parseObject(line).getString("server_time");
//            logger.info(server_time);
            if(server_time == null || server_time.length() <= 0){
                timeStamp = System.currentTimeMillis();
//                logger.info("---server_time is null or the length is zero---");
            }else {
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(server_time);
                timeStamp = date.getTime();
//                logger.info("----The event server_time is----"+ timeStamp);
            }
            headers.put("timestamp",Long.toString(timeStamp));
        }catch (Exception e){
            logger.info(e.toString());
        }
        return event;
    }

    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    public void close() { }


    public static class Builder implements Interceptor.Builder {
        //这两个配置是默认的，不写也可以
        private boolean preserveExisting = false;
        private String header = "timestamp";

        @Override
        public Interceptor build() {
            return new TimeInterceptor(preserveExisting, header);
        }

        @Override
        public void configure(Context context) {
            preserveExisting = context.getBoolean("preserveExisting", false);
            header = context.getString("headerName", "timestamp");
        }
    }
}
