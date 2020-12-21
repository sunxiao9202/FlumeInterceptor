package com.zhihuishu.flume.domain;

/**
 * @author: lihua
 * @date: 2020/1/10 10:15
 * @Description:
 */
public class CcStudyLessonLog {
     private int id;
     private int useId;
     private int recruitId;
     private int lessonId;
     private int lessonVideoId;
     private int videoId;
     private int playTimes;
     private int sutdyTotalTime;
     private String learnTime;
     private int sourceType;
     private String createTime;
     private String partitionTime;

    public CcStudyLessonLog() {
    }

    public CcStudyLessonLog(int id, int useId, int recruitId, int lessonId, int lessonVideoId, int videoId, int playTimes, int sutdyTotalTime, String learnTime, int sourceType, String createTime, String partitionTime) {
        this.id = id;
        this.useId = useId;
        this.recruitId = recruitId;
        this.lessonId = lessonId;
        this.lessonVideoId = lessonVideoId;
        this.videoId = videoId;
        this.playTimes = playTimes;
        this.sutdyTotalTime = sutdyTotalTime;
        this.learnTime = learnTime;
        this.sourceType = sourceType;
        this.createTime = createTime;
        this.partitionTime = partitionTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public int getRecruitId() {
        return recruitId;
    }

    public void setRecruitId(int recruitId) {
        this.recruitId = recruitId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getLessonVideoId() {
        return lessonVideoId;
    }

    public void setLessonVideoId(int lessonVideoId) {
        this.lessonVideoId = lessonVideoId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public int getSutdyTotalTime() {
        return sutdyTotalTime;
    }

    public void setSutdyTotalTime(int sutdyTotalTime) {
        this.sutdyTotalTime = sutdyTotalTime;
    }

    public String getLearnTime() {
        return learnTime;
    }

    public void setLearnTime(String learnTime) {
        this.learnTime = learnTime;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPartitionTime() {
        return partitionTime;
    }

    public void setPartitionTime(String partitionTime) {
        this.partitionTime = partitionTime;
    }

    @Override
    public String toString() {
       return  id+","+useId+","+recruitId+","+lessonId+","+lessonVideoId+","+videoId+","+playTimes+","+sutdyTotalTime+","+learnTime+","+sourceType+","+createTime+","+partitionTime;
    }
}



