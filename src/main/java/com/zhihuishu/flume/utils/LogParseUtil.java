package com.zhihuishu.flume.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhihuishu.flume.domain.CcStudyLessonLog;

/**
 * @author: lihua
 * @date: 2020/1/10 10:33
 * @Description:
 */
public class LogParseUtil {

    /**
     * 解析kafka中的json,封装到对象CcStudyLessonLog中
     * @param jsonObject
     * @return
     */
    public static CcStudyLessonLog  logParse(JSONObject jsonObject){

        JSONArray data = (JSONArray) jsonObject.get("data");
        JSONObject json = (JSONObject) data.get(0);
        int id = Integer.valueOf(json.get("Id").toString());
        int userId =Integer.valueOf(json.get("USER_ID").toString());
        int recruitId =Integer.valueOf(json.get("RECRUIT_ID").toString());
        int lessonId = Integer.valueOf(json.get("LESSON_ID").toString());
        int lessonVideoId = Integer.valueOf(json.get("LESSON_VIDEO_ID").toString());
        int videoId = Integer.valueOf(json.get("VIDEO_ID").toString());
        int playTimes = Integer.valueOf(json.get("PLAY_TIMES").toString());
        int studyTotalTime = Integer.valueOf(json.get("STUDY_TOTAL_TIME").toString());
        String learnTime = json.get("LEARN_TIME").toString();
        int sourseType = Integer.valueOf(json.get("SOURSE_TYPE").toString());
        String createTime = json.get("CREATE_TIME").toString();
        String partitionTime=DataUtil.date(createTime);
        CcStudyLessonLog ccStudyLessonLog =new CcStudyLessonLog(id,userId,recruitId,lessonId,lessonVideoId,videoId,playTimes,studyTotalTime,learnTime,sourseType,createTime,partitionTime);
        return ccStudyLessonLog;

    }

}
