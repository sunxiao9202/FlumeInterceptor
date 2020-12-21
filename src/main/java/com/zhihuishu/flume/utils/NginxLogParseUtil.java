package com.zhihuishu.flume.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: lihua
 * @date: 2020/9/4 17:03
 * @Description:
 */

public class NginxLogParseUtil {

    private static final Logger logger = LoggerFactory.getLogger(NginxLogParseUtil.class);
    private static DbConfig config;
    private static DbSearcher searcher;
    private static String JSON_LEFT = "{";
    private static String JSON_RIGHT = "}";
    //本地测试绝对路径
    //private static final String IP2REGION_PATH = "E:\\studyLogFlumeInteceptor\\src\\main\\resources\\ip2region.db";
    //canal01 线上flume路径
    private static final String IP2REGION_PATH = "/flume/flumeFlu/files/ip2region.db";

    static {
        try {
            config = new DbConfig();
            searcher = new DbSearcher(config, IP2REGION_PATH);
        } catch (Exception e) {
            logger.error("ip2region初始化异常" + e.getMessage());
        }
    }

    public static String parse(String log) {
        String str = "";
        try {
            if (isJSONValid(log)) {
                JSONObject jsonObjectAll = JSON.parseObject(log);
                String messageJsonObject = jsonObjectAll.getString("message");
                //处理不符合规范的message的JSON信息
                if (!isJSONValid(messageJsonObject)) {
                    if (!messageJsonObject.startsWith(JSON_LEFT)) {
                        messageJsonObject = JSON_LEFT + messageJsonObject;
                    } else if (!messageJsonObject.endsWith(JSON_RIGHT)) {
                        messageJsonObject = messageJsonObject + JSON_RIGHT;
                    } else if (!messageJsonObject.startsWith(JSON_LEFT) && !messageJsonObject.endsWith(JSON_RIGHT)) {
                        messageJsonObject = JSON_LEFT + messageJsonObject + JSON_RIGHT;
                    }
                }
                JSONObject jsonObject = JSON.parseObject(messageJsonObject);
                String uri = jsonObject.getString("uri");
                String timestamp = "";
                if (jsonObject.containsKey("msec")) {
                    timestamp = jsonObject.getString("msec");
                }
                String requestUri = jsonObject.getString("requestUri");
                double requestTime = Double.parseDouble(jsonObject.getString("request_time")) * 1000;
                String forwardedip = jsonObject.getString("forwardedip");
                int status = Integer.parseInt(jsonObject.getString("status"));
                String hostname = jsonObject.getString("hostname");
                String requestId = jsonObject.getString("request_id");
                String upstreamHttpHost = jsonObject.getString("upstream_http_host");
                String httpUserAgent = jsonObject.getString("http_user_agent");
                String httpReferer = jsonObject.getString("http_referer");
                String ip = getForwardedIp(forwardedip);
                String[] region = getRegion(ip);
                String province = "-";
                String city = "-";
                if (region != null && region.length == 5) {
                    province = region[2];
                    city = region[3];
                    if (null == province || "0".equals(province)) {
                        province = "-";
                    }
                    if (null == city || "0".equals(city)) {
                        city = "-";
                    }
                }
                int bodyBytesSent = Integer.parseInt(jsonObject.getString("body_bytes_sent"));
                String beatHostName = JSON.parseObject(jsonObjectAll.getString("beat")).getString("hostname");
                str = timestamp + "\001" + forwardedip + "\001" + status +
                        "\001" + hostname + "\001" + uri + "\001" + requestId + "\001" +
                        upstreamHttpHost + "\001" + httpUserAgent + "\001" + requestTime + "\001" +
                        requestUri + "\001" + httpReferer + "\001" + ip + "\001" + province + "\001" + city + "\001" + beatHostName + "\001" + bodyBytesSent;
            }
        } catch (Exception e) {
            logger.error("数据解析异常:" + log);
            throw e;
        }
        return str;
    }

    /**
     * 根据IP获得 城市 省份
     *
     * @param ip
     * @return 国家|大区|省份|城市|运营商
     * @throws Exception
     */
    public static String[] getRegion(String ip) {
        //根据ip进行位置信息搜索
        String[] region_split = null;
        try {
            if (Util.isIpAddress(ip)) {
                //采用Btree搜索
                DataBlock block = searcher.btreeSearch(ip);
                //打印位置信息（格式：国家|大区|省份|城市|运营商）
                String region = block.getRegion();
                region_split = region.split("\\|");
            }
        } catch (Exception e) {
            logger.error("ip解析城市异常:{" + ip + "}", e.getMessage());
        }
        return region_split;
    }

    /**
     * ForwardedIp 取倒数第二个
     *
     * @param forwardedIp
     * @return
     */
    public static String getForwardedIp(String forwardedIp) {
        String[] split = forwardedIp.split(",");
        String ip;
        if (split.length >= 2) {
            //注意去掉前后空格
            ip = split[split.length - 2].trim();
        } else {
            ip = split[0].trim();
        }
        return ip;
    }

    /**
     * 判断字符串是否为json字符串
     *
     * @param log
     * @return
     */
    public final static boolean isJSONValid(String log) {
        try {
            JSONObject.parseObject(log);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(log);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String str="";
        System.out.println(parse(str));


        //JSONObject jsonObject = JSON.parseObject(str);
        //String timestamp = jsonObject.getString("timestamp");
        //System.out.println(timestamp);
        //System.out.println(getForwardedIp("223.104.23.179, 114.55.70.20, 47.98.74.70"));
        //System.out.println(getForwardedIp("223.104.23.179, 114.55.70.20, 47.98.74.70"));
        //System.out.println(getForwardedIp("39.144.26.115, 112.124.159.155"));
        //getRegion(getForwardedIp("39.144.26.115, 112.124.159.155"));
        //System.out.println(getRegion("112.124.159.155")[2]);
        // System.out.println(getRegion("112.124.159.155")[3]);

        //byte[] bytes = "".getBytes();
        //System.out.println(Arrays.toString(bytes));

        //SimpleEvent simpleEvent = new SimpleEvent();
        //simpleEvent.setBody("".getBytes());
        //System.out.println(simpleEvent.getBody());

        //if(simpleEvent.getBody().length !=0){
        //    System.out.println(true);
        //}else{
        //    System.out.println(false);
        //}

    }


}
