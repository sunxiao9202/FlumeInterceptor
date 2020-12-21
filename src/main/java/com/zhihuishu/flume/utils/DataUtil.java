package com.zhihuishu.flume.utils;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author: lihua
 * @date: 2020/1/10 14:17
 * @Description:
 */
public class DataUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT= new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    private static final ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT= new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    //lang3 需要导入lang3包,如果是lang中的,则没有parse方法
    private static   FastDateFormat SOURCE_TIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss+08:00");
    private static   FastDateFormat GMT_TIME_FORMAT=FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    private static   FastDateFormat UTC_TIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * 将yyyy-MM-dd HH:mm:ss 转化成 yyyyMMdd 格式
     * @param datetime
     * @return
     */
    public static String  date(String datetime){

        String date="";
        try {
            Date parseDate = DATE_TIME_FORMAT.get().parse(datetime);
            date =  DATE_FORMAT.get().format(parseDate);
        } catch (ParseException e) {
           logger.error("将yyyy-MM-dd HH:mm:ss 转化成 yyyyMMdd 格式 解析错误 "+e.getMessage());
        }
        return date;
    }

    /**
     * 将时间(yyyyMMdd格式字符串)转化为时间戳
     * @param date
     * @return
     */
    public static Long getTimeStamp(String date){
        long time=0L;
        try {
            Date parse = DATE_FORMAT.get().parse(date);
             time = parse.getTime();
        } catch (ParseException e) {
            logger.error("将时间转化为时间戳错误"+e.getMessage());
        }

        return  time;
    }


    /**
     * 将时间转化为东八区时间
     * @param date
     * @return
     */
    public static String getGMTTime(String date){
        String GMTTime=null;
        try {
            Long timestamp=UTC_TIME_FORMAT.parse(date).getTime();
            GMTTime  = GMT_TIME_FORMAT.format(new Date(timestamp + 8 * 3600 * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return GMTTime;
    }
}


