package com.zhihuishu.flume.test;//package com.zhihuishu.flume.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhihuishu.flume.domain.CcStudyLessonLog;
import com.zhihuishu.flume.utils.DataUtil;

/**
 * @author: lihua
 * @date: 2020/1/10 10:19
 * @Description:
 */
public class JsonParse {

    public static void main(String[] args) {

        String log = "   {\n" +
                "        \"data\": [{\n" +
                "                \"Id\": \"6132797\",\n" +
                "                \"USER_ID\": \"209329507\",\n" +
                "                \"RECRUIT_ID\": \"22473\",\n" +
                "                \"LESSON_ID\": \"1112347\",\n" +
                "                \"LESSON_VIDEO_ID\": \"0\",\n" +
                "                \"VIDEO_ID\": \"562983\",\n" +
                "                \"PLAY_TIMES\": \"124\",\n" +
                "                \"STUDY_TOTAL_TIME\": \"629\",\n" +
                "                \"LEARN_TIME\": \"00:10:28\",\n" +
                "                \"SOURSE_TYPE\": \"3\",\n" +
                "                \"CREATE_TIME\": \"2020-01-10 09:53:19\"\n" +
                "    }],\n" +
                "        \"database\": \"BUSINES_LOG\",\n" +
                "            \"es\": 1578621199000,\n" +
                "            \"id\": 303087,\n" +
                "            \"isDdl\": false,\n" +
                "            \"mysqlType\": {\n" +
                "        \"Id\": \"int(11)\",\n" +
                "                \"USER_ID\": \"int(11)\",\n" +
                "                \"RECRUIT_ID\": \"int(11)\",\n" +
                "                \"LESSON_ID\": \"int(11)\",\n" +
                "                \"LESSON_VIDEO_ID\": \"int(11)\",\n" +
                "                \"VIDEO_ID\": \"int(11)\",\n" +
                "                \"PLAY_TIMES\": \"int(11)\",\n" +
                "                \"STUDY_TOTAL_TIME\": \"int(11)\",\n" +
                "                \"LEARN_TIME\": \"varchar(255)\",\n" +
                "                \"SOURSE_TYPE\": \"int(11)\",\n" +
                "                \"CREATE_TIME\": \"datetime\"\n" +
                "    },\n" +
                "        \"old\": null,\n" +
                "            \"pkNames\": [\"Id\"],\n" +
                "        \"sql\": \"\",\n" +
                "            \"sqlType\": {\n" +
                "        \"Id\": 4,\n" +
                "                \"USER_ID\": 4,\n" +
                "                \"RECRUIT_ID\": 4,\n" +
                "                \"LESSON_ID\": 4,\n" +
                "                \"LESSON_VIDEO_ID\": 4,\n" +
                "                \"VIDEO_ID\": 4,\n" +
                "                \"PLAY_TIMES\": 4,\n" +
                "                \"STUDY_TOTAL_TIME\": 4,\n" +
                "                \"LEARN_TIME\": 12,\n" +
                "                \"SOURSE_TYPE\": 4,\n" +
                "                \"CREATE_TIME\": 93\n" +
                "    },\n" +
                "        \"table\": \"CC_STUDIED_LESSON_LOG\",\n" +
                "            \"ts\": 1578621199519,\n" +
                "            \"type\": \"INSERT\"\n" +
                "    }";

        JSONObject jsonObject = JSON.parseObject(log);
        JSONArray data = (JSONArray) jsonObject.get("data");
        JSONObject json = (JSONObject) data.get(0);

        int id = Integer.valueOf(json.get("Id").toString());
        int userId = Integer.valueOf(json.get("USER_ID").toString());
        int recruitId = Integer.valueOf(json.get("RECRUIT_ID").toString());
        int lessonId = Integer.valueOf(json.get("LESSON_ID").toString());
        int lessonVideoId = Integer.valueOf(json.get("LESSON_VIDEO_ID").toString());
        int videoId = Integer.valueOf(json.get("VIDEO_ID").toString());
        int playTimes = Integer.valueOf(json.get("PLAY_TIMES").toString());
        int studyTotalTime = Integer.valueOf(json.get("STUDY_TOTAL_TIME").toString());
        String learnTime = json.get("LEARN_TIME").toString();
        int sourseType = Integer.valueOf(json.get("SOURSE_TYPE").toString());
        String createTime = json.get("CREATE_TIME").toString();
        String partitionTime = DataUtil.date(createTime);
        CcStudyLessonLog ccStudyLessonLog = new CcStudyLessonLog(id, userId, recruitId, lessonId, lessonVideoId, videoId, playTimes, studyTotalTime, learnTime, sourseType, createTime, partitionTime);
        System.out.println(ccStudyLessonLog);
    }
}
