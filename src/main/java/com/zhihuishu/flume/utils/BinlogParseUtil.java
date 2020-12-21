package com.zhihuishu.flume.utils;

import com.alibaba.otter.canal.client.adapter.support.Dml;
import com.alibaba.otter.canal.client.adapter.support.MessageUtil;
import com.alibaba.otter.canal.kafka.client.MessageDeserializer;
import com.alibaba.otter.canal.protocol.Message;
import com.zhihuishu.flume.domain.CcStudyLessonLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lihua
 * @date: 2020/3/2 16:12
 * @Description:
 */
public class BinlogParseUtil {

    private static final Logger logger = LoggerFactory.getLogger(BinlogParseUtil.class);

    public static List<CcStudyLessonLog> binlogParse(byte[] body) {
        MessageDeserializer messageDeserializer = new MessageDeserializer();
        Message message = messageDeserializer.deserialize("", body);

        List<CcStudyLessonLog> ccStudyLessonLogList = new ArrayList<>();

        List<Dml> dmls = MessageUtil.parse4Dml("", "", message);
        for (Dml dml : dmls) {
            List<Map<String, Object>> data = dml.getData();
            for (Map<String, Object> map : data) {
                if(!map.isEmpty()) {
                    CcStudyLessonLog ccStudyLessonLog= new CcStudyLessonLog();
                    int id = Integer.valueOf(map.get("Id").toString());
                    int userId = Integer.valueOf(map.get("USER_ID").toString());
                    int recruitId = Integer.valueOf(map.get("RECRUIT_ID").toString());
                    int lessonId = Integer.valueOf(map.get("LESSON_ID").toString());
                    int lessonVideoId = Integer.valueOf(map.get("LESSON_VIDEO_ID").toString());
                    int videoId = Integer.valueOf(map.get("VIDEO_ID").toString());
                    int playTimes = Integer.valueOf(map.get("PLAY_TIMES").toString());
                    int studyTotalTime = Integer.valueOf(map.get("STUDY_TOTAL_TIME").toString());
                    String learnTime = map.get("LEARN_TIME").toString();
                    int sourseType = Integer.valueOf(map.get("SOURSE_TYPE").toString());
                    String createTime = map.get("CREATE_TIME").toString();
                    String partitionTime = DataUtil.date(createTime);
                    ccStudyLessonLog.setId(id);
                    ccStudyLessonLog.setUseId(userId);
                    ccStudyLessonLog.setRecruitId(recruitId);
                    ccStudyLessonLog.setLessonId(lessonId);
                    ccStudyLessonLog.setLessonVideoId(lessonVideoId);
                    ccStudyLessonLog.setVideoId(videoId);
                    ccStudyLessonLog.setPlayTimes(playTimes);
                    ccStudyLessonLog.setSutdyTotalTime(studyTotalTime);
                    ccStudyLessonLog.setLearnTime(learnTime);
                    ccStudyLessonLog.setSourceType(sourseType);
                    ccStudyLessonLog.setCreateTime(createTime);
                    ccStudyLessonLog.setPartitionTime(partitionTime);

                    ccStudyLessonLogList.add(ccStudyLessonLog);
                }
            }
        }

        return ccStudyLessonLogList;
    }


}
