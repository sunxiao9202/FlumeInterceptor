package com.zhihuishu.flume.interceptor;


import com.zhihuishu.flume.domain.CcStudyLessonLog;
import com.zhihuishu.flume.utils.BinlogParseUtil;
import com.zhihuishu.flume.utils.DataUtil;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lihua
 * @date: 2019/7/26 10:15
 * @Description:
 */
public class StudytLogInterceptor implements Interceptor {
    //打印日志，便于测试方法的执行顺序
    private static final Logger logger = LoggerFactory.getLogger(CcStudyLessonLog.class);
    //自定义拦截器参数，用来接收自定义拦截器flume配置参数
    private static String param = "";

    /**
     * 执行顺序  3
     * 拦截器构造方法，在自定义拦截器静态内部类的build方法中调用，用来创建自定义拦截器对象。
     */
    public StudytLogInterceptor() {
        //logger.info("----------3 自定义拦截器构造方法执行");

    }


    /**
     * 执行顺序  4
     * 该方法用来初始化拦截器，在拦截器的构造方法执行之后执行，也就是创建完拦截器对象之后执行
     */
    @Override
    public void initialize() {
        //logger.info("---------- 4 自定义拦截器的initialize方法执行");
    }


    /**
     * 执行顺序  6
     * 用来处理每一个event对象，该方法不会被系统自动调用，一般在 List<Event> intercept(List<Event> events) 方法内部调用。
     *
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {
        //logger.info("----------6 intercept(Event event)方法执行，处理单个event");
        //logger.info("----------6 接收到的自定义拦截器参数值param值为：" + param);
        return event;
    }

//        String lessonJson = new String(event.getBody(), Charsets.UTF_8);
//        if (StringUtils.isBlank(lessonJson)) {
//            return null;
//        }
//        Map<String, String> headers = event.getHeaders();
//        String bodyOutput = lessonJson;
//        try {
//            JSONObject jsonObject = JSON.parseObject(lessonJson);
//            //解析json
//            bodyOutput = LogParseUtil.logParse(jsonObject).toString();
//            //logger.info("解析后的数据\t"+bodyOutput);
//        }catch(Exception e){
//            logger.error("日志json解析异常,此数据为: "+ lessonJson,e);
//        }finally {
//            headers.put("studyLog","successStudyLog");
//            event.setHeaders(headers);
//            event.setBody(bodyOutput.getBytes());
//        }

//        return event;
//    }


    /**
     * 执行顺序  5
     * 用来处理一批event对象集合，集合大小与flume启动配置有关，和transactionCapacity大小保持一致。一般直接调用 Event intercept(Event event) 处理每一个event数据。
     *
     * @param events
     * @return
     */

    @Override
    public List<Event> intercept(List<Event> events) {
        //logger.info("----------5 intercept(List<Event> events)方法执行");
        /*
        这里编写对于event对象集合的处理代码，一般都是遍历event的对象集合，对于每一个event对象，调用 Event intercept(Event event) 方法，然后根据返回值是否为null，
        来将其添加到新的集合中。
         */
        List<Event> results = new ArrayList<>();

        for (Event event : events) {
            Event interceptedEvent;
            List<CcStudyLessonLog> ccStudyLessonLogList = new ArrayList<>();
            try {
                //调用了intercept(e)的方法
                interceptedEvent = intercept(event);
                byte[] body = interceptedEvent.getBody();
                if (body.length > 0) {
                //解析二进制数据
                ccStudyLessonLogList = BinlogParseUtil.binlogParse(body);
                }
            } catch (Exception e) {
                logger.error("binlog exception", e);
                continue;
            }
            if (interceptedEvent != null) {
                for (CcStudyLessonLog ccStudyLessonLog : ccStudyLessonLogList) {
                    Map<String, String> headers = interceptedEvent.getHeaders();
                    headers.put("studyLog", "successStudyLog");

                    // 设置timestamp 头信息，落到HDFS的分区是由Event的header中的timestamp(时间戳)决定的
                    long timestamp;
                    String partitionTime = ccStudyLessonLog.getPartitionTime();
                    if(partitionTime==null || partitionTime.length()==0 ){
                        timestamp= System.currentTimeMillis();
                    }else{
                        timestamp= DataUtil.getTimeStamp(partitionTime);
                    }

                    headers.put("timestamp",Long.toString(timestamp));

                    SimpleEvent simpleEvent = new SimpleEvent();
                    simpleEvent.setHeaders(headers);
                    simpleEvent.setBody(ccStudyLessonLog.toString().getBytes());
                    //logger.info("解析后的数据: "+ccStudyLessonLog.toString());
                    results.add(simpleEvent);
                }
            }
        }
        return results;


    }

    /**
     * 执行顺序  7
     * 该方法主要用来销毁拦截器对象值执行，一般是一些释放资源的处理
     */
    @Override
    public void close() {
        //logger.info("----------7 自定义拦截器close方法执行");

    }

    /**
     * 通过该静态内部类来创建自定义对象供flume使用，实现Interceptor.Builder接口，并实现其抽象方法
     */
    public static class Builder implements Interceptor.Builder {


        /**
         * 执行顺序  2
         * 该方法主要用来返回创建的自定义类拦截器对象
         *
         * @return
         */
        @Override
        public Interceptor build() {
            //logger.info("----------2 初始化拦截器");
            return new StudytLogInterceptor();
        }

        /**
         * 执行顺序  1
         * 用来接收flume配置自定义拦截器参数
         *
         * @param context 通过该对象可以获取flume配置自定义拦截器的参数
         */
        @Override
        public void configure(Context context) {
            //logger.info("----------1 configure方法执行");
            /*
            通过调用context对象的getString方法来获取flume配置自定义拦截器的参数，方法参数要和自定义拦截器配置中的参数保持一致+
             */
            //param = context.getString("param");
        }
    }
}
