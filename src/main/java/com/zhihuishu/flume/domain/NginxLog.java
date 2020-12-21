package com.zhihuishu.flume.domain;

/**
 * @author: lihua
 * @date: 2020/9/4 16:47
 * @Description:
 */
public class NginxLog {
    private String timestamp;  //请求时间戳
    private String forwardedip;  //请求前端来源IP 多个逗号隔开
    private int status; //请求状态
    private String hostname; //请求域名
    private String uri; //请求URL路径
    private String requestId;  //请求ID
    private String upstreamHttpHost; //请求到达服务器IP
    private String httpUserAgent;  //请求设备
    private double requestTime;  //请求响应时长 以秒为单位 保留3位小数 比如 0.034=34毫秒
    private String requestUri;  //请求URL路径带参数
    private String httpReferer; //请求上一个来源页面地址
    private String ip; //通过forwardedip解析的倒数第二个ip地址
    private String province ;  //前端来源IP所在省份（多个IP取倒数第2个IP）
    private String city;  //前端来源IP所在城市（多个IP取倒数第2个IP）
    private String beatHostName; //机器hostname
    private int bodyBytesSent;

    public NginxLog() {
    }

    public NginxLog(String timestamp, String forwardedip, int status, String hostname, String uri, String requestId, String upstreamHttpHost, String httpUserAgent, double requestTime, String requestUri, String httpReferer, String ip, String province, String city, String beatHostName, int bodyBytesSent) {
        this.timestamp = timestamp;
        this.forwardedip = forwardedip;
        this.status = status;
        this.hostname = hostname;
        this.uri = uri;
        this.requestId = requestId;
        this.upstreamHttpHost = upstreamHttpHost;
        this.httpUserAgent = httpUserAgent;
        this.requestTime = requestTime;
        this.requestUri = requestUri;
        this.httpReferer = httpReferer;
        this.ip = ip;
        this.province = province;
        this.city = city;
        this.beatHostName = beatHostName;
        this.bodyBytesSent = bodyBytesSent;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getForwardedip() {
        return forwardedip;
    }

    public void setForwardedip(String forwardedip) {
        this.forwardedip = forwardedip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUpstreamHttpHost() {
        return upstreamHttpHost;
    }

    public void setUpstreamHttpHost(String upstreamHttpHost) {
        this.upstreamHttpHost = upstreamHttpHost;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }

    public double getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(double requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBeatHostName() {
        return beatHostName;
    }

    public void setBeatHostName(String beatHostName) {
        this.beatHostName = beatHostName;
    }

    public int getBodyBytesSent() {
        return bodyBytesSent;
    }

    public void setBodyBytesSent(int bodyBytesSent) {
        this.bodyBytesSent = bodyBytesSent;
    }

    @Override
    public String toString() {
        return timestamp+"\001"+forwardedip+"\001"+status+
                "\001"+hostname+"\001"+uri+"\001"+requestId+"\001"+
                upstreamHttpHost+"\001"+httpUserAgent+"\001" +requestTime+"\001"+
                requestUri+"\001"+httpReferer+"\001"+ip+"\001"+province+"\001"+city+"\001"+beatHostName+"\001"+bodyBytesSent;
    }

}
