package com.zhihuishu.flume.domain;

/**
 * @author: lihua
 * @date: 2020/9/8 17:51
 * @Description:
 */
public class NgnixUserLog {
     String timestamp; //时间戳
     int costTimeStamp ; //方法耗时
     String method; //方法
     String params; //方法参数
     String requestId; //请求id
     String requestUrl;// 请求URL
     int userId; //用户Id
     String uuid;

     public NgnixUserLog() {
     }

     public NgnixUserLog(String timestamp, int costTimeStamp, String method, String params, String requestId, String requestUrl, int userId, String uuid) {
          this.timestamp = timestamp;
          this.costTimeStamp = costTimeStamp;
          this.method = method;
          this.params = params;
          this.requestId = requestId;
          this.requestUrl = requestUrl;
          this.userId = userId;
          this.uuid = uuid;
     }

     public String getTimestamp() {
          return timestamp;
     }

     public void setTimestamp(String timestamp) {
          this.timestamp = timestamp;
     }

     public int getCostTimeStamp() {
          return costTimeStamp;
     }

     public void setCostTimeStamp(int costTimeStamp) {
          this.costTimeStamp = costTimeStamp;
     }

     public String getMethod() {
          return method;
     }

     public void setMethod(String method) {
          this.method = method;
     }

     public String getParams() {
          return params;
     }

     public void setParams(String params) {
          this.params = params;
     }

     public String getRequestId() {
          return requestId;
     }

     public void setRequestId(String requestId) {
          this.requestId = requestId;
     }

     public String getRequestUrl() {
          return requestUrl;
     }

     public void setRequestUrl(String requestUrl) {
          this.requestUrl = requestUrl;
     }

     public int getUserId() {
          return userId;
     }

     public void setUserId(int userId) {
          this.userId = userId;
     }

     public String getUuid() {
          return uuid;
     }

     public void setUuid(String uuid) {
          this.uuid = uuid;
     }

     @Override
     public String toString() {
          return timestamp+"\001"+costTimeStamp+"\001"+method+"\001"+params+"\001"+requestId+"\001"+requestUrl+"\001"+userId+"\001"+uuid;
     }


}
