package com.zhihuishu.flume.test;

public class NewdsFileTransferStatInfo {
    private String from;
    private Long avgSpeed;
    private String cdnIp;
    private String filePath;
    private Long maxSpeed;
    private Long minSpeed;
    private String pubIp;
    private Long repeatCount;
    private Long result;
    private Long time;
    private Long type;
    private String machineModel;
    private String msn;
    private String originalMachineModel;
    private Long sTime;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Long avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public String getCdnIp() {
        return cdnIp;
    }

    public void setCdnIp(String cdnIp) {
        this.cdnIp = cdnIp;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Long maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Long getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(Long minSpeed) {
        this.minSpeed = minSpeed;
    }

    public String getPubIp() {
        return pubIp;
    }

    public void setPubIp(String pubIp) {
        this.pubIp = pubIp;
    }

    public Long getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Long repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(String machineModel) {
        this.machineModel = machineModel;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getOriginalMachineModel() {
        return originalMachineModel;
    }

    public void setOriginalMachineModel(String originalMachineModel) {
        this.originalMachineModel = originalMachineModel;
    }

    public Long getsTime() {
        return sTime;
    }

    public void setsTime(Long sTime) {
        this.sTime = sTime;
    }

    @Override
    public String toString() {
        return from+"\001"+avgSpeed+"\001"+cdnIp+"\001"+filePath+"\001"+maxSpeed+"\001"+minSpeed+"\001"+pubIp+"\001"+repeatCount+"\001"+result+"\001"+time+"\001"+type+"\001"+machineModel+"\001"+msn+"\001"+originalMachineModel+"\001"+sTime;
    }
}
