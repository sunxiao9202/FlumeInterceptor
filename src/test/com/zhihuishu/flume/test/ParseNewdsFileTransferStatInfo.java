package com.zhihuishu.flume.test;/*
package com.zhihuishu.flume.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParseNewdsFileTransferStatInfo implements Interceptor {

    private final Logger log = LoggerFactory.getLogger(ParseNewdsFileTransferStatInfo.class);

    private ParseNewdsFileTransferStatInfo() {
    }

    @Override
    public void initialize() {
        // NO-OP...  
    }

    @Override
    public void close() {
        // NO-OP...  
    }

    @Override
    public Event intercept(Event event) {
        return event;
    }


    public String getStringValue(JSONObject jsonObject, String key) {
        String value = "null";

        value = jsonObject.getString(key);

        if (value == null || "".equals(value.trim()) || "\t".equals(value)) {
            value = "null";
        }

        return value;

    }

    public String getIpString(String ipStr) {
        String ip = "null";
        if (!"null".equals(ipStr)) {
            Pattern pattern = Pattern.compile("(\\d+.\\d+.\\d+.\\d+)");
            Matcher matcher = pattern.matcher(ipStr);
            if (matcher.find()) {
                ip = matcher.group(1);
            } else {
                ip = "null";
            }
        }
        return ip;
    }

    public List<Event> explodeEnvents(List<Event> events) {
        ArrayList<Event> newEvents = new ArrayList<>();

        for (Event event : events) {
            try {
                String bodyOut = new String(event.getBody(), Charsets.UTF_8);
                bodyOut = bodyOut.replace("\\n", "").replace("\\t", "");
                JSONObject jsonObject = JSON.parseObject(bodyOut);
                String from = getStringValue(jsonObject, "from");
                String infos = getStringValue(jsonObject, "infos");
                String machineModel = getStringValue(jsonObject, "machineModel");
                String msn = getStringValue(jsonObject, "msn");
                String originalMachineModel = getStringValue(jsonObject, "originalMachineModel");
                Long sTime = jsonObject.getLong("sTime");
                JSONArray jsonArray = JSONArray.parseArray(infos);
                for (Object e : jsonArray) {
                    try {
                        JSONObject content = JSON.parseObject(e.toString());
                        Long avgSpeed = content.getLong("avgSpeed");
                        String cdnIp = getStringValue(content, "cdnIp");
                        cdnIp = getIpString(cdnIp);
                        String filePath = getStringValue(content, "filePath");
                        Long maxSpeed = content.getLong("maxSpeed");
                        Long minSpeed = content.getLong("minSpeed");
                        String pubIp = getStringValue(content, "pubIp");
                        pubIp = getIpString(pubIp);
                        Long repeatCount = content.getLong("repeatCount");
                        Long result = content.getLong("result");
                        Long time = content.getLong("time");
                        Long type = content.getLong("type");

                        NewdsFileTransferStatInfo newdsFileTransferStatInfo = new NewdsFileTransferStatInfo();
                        newdsFileTransferStatInfo.setFrom(from);
                        newdsFileTransferStatInfo.setAvgSpeed(avgSpeed);
                        newdsFileTransferStatInfo.setCdnIp(cdnIp);
                        newdsFileTransferStatInfo.setFilePath(filePath);
                        newdsFileTransferStatInfo.setMaxSpeed(maxSpeed);
                        newdsFileTransferStatInfo.setMinSpeed(minSpeed);
                        newdsFileTransferStatInfo.setPubIp(pubIp);
                        newdsFileTransferStatInfo.setRepeatCount(repeatCount);
                        newdsFileTransferStatInfo.setResult(result);
                        newdsFileTransferStatInfo.setTime(time);
                        newdsFileTransferStatInfo.setType(type);
                        newdsFileTransferStatInfo.setMachineModel(machineModel);
                        newdsFileTransferStatInfo.setMsn(msn);
                        newdsFileTransferStatInfo.setOriginalMachineModel(originalMachineModel);
                        newdsFileTransferStatInfo.setsTime(sTime);

                        SimpleEvent simpleEvent = new SimpleEvent();
                        simpleEvent.setHeaders(event.getHeaders());
                        simpleEvent.setBody(newdsFileTransferStatInfo.toString().getBytes());
                        newEvents.add(simpleEvent);
                    } catch (Exception e1) {
                        log.error("JsonParse failed : ", e1);
                        continue;
                    }
                }
            } catch (Exception e) {
                log.error("JsonParse failed : ", e);
                continue;
            }
        }

        return newEvents;

    }


    @Override
    public List<Event> intercept(List<Event> events) {
        List<Event> intercepted = new ArrayList<>();
        List<Event> newEvents = explodeEnvents(events);
        for (Event event : newEvents) {
            Event interceptedEvent;
            try {
                interceptedEvent = intercept(event);
            } catch (Exception e) {
                log.error("com.sunmi.flume.ParseNewdsFileTransferStatInfo JsonParse failed : ", e);
                continue;
            }
            if (interceptedEvent != null) {
                intercepted.add(interceptedEvent);
            }
        }
        return intercepted;
    }

    public static class Builder implements Interceptor.Builder {
        //使用Builder初始化Interceptor  
        @Override
        public Interceptor build() {
            return new ParseNewdsFileTransferStatInfo();
        }

        @Override
        public void configure(Context context) {

        }
    }
}  


*/
