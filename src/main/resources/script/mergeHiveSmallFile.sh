#!/bin/bash

yesterday=`date -d "-1 day" +%Y%m%d`
yesterday2=`date -d "-1 day" +%Y-%m-%d`
today=`date "+%Y-%m-%d"`

# hive 表的动态分区插入数据，实现小文件合并
/hive/apache-hive-2.3.3-bin/bin/hive -e "
set mapreduce.job.end-notification.url='http://hadoop.zhihuishu.com/hadoop/notice/mr/all?id=111;status=SUCCESSED';
set mapreduce.job.name=ods.cc_studied_lesson_log;
create table if not exists ods.cc_studied_lesson_log(
Id int COMMENT '自增ID',
USER_ID int COMMENT '用户ID',
RECRUIT_ID int,
LESSON_ID int,
LESSON_VIDEO_ID int,
VIDEO_ID int COMMENT '观看视频ID',
PLAY_TIMES int COMMENT '本次播放时长',
STUDY_TOTAL_TIME int COMMENT '提交视频总时长',
LEARN_TIME string COMMENT '当前学习视频播放时刻HH:mm:ss',
SOURSE_TYPE int COMMENT '数据来源：1PC、2IOS、3Android',
CREATE_TIME string COMMENT '创建时间'
) 
PARTITIONED BY (PARTITION_TIME string)
row format delimited fields terminated by ',' lines terminated by '\n';

set hive.jobname.length=100;
set mapreduce.job.name=mergeHiveSmallFile;

set mapreduce.map.memory.mb=3000;  
set mapred.child.java.opts=-Xmx2700m;
set mapreduce.reduce.memory.mb=3000;


set mapred.max.split.size=134217728;
set mapred.min.split.size.per.node=134217728;
set mapred.min.split.size.per.rack=134217728;
set hive.input.format=org.apache.hadoop.hive.ql.io.CombineHiveInputFormat;

set hive.map.aggr=true;
set hive.groupby.skewindata=true;
set hive.optimize.skewjoin=true;

set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;

set hive.exec.parallel=true;
set hive.exec.parallel.thread.number=3;
set hive.auto.convert.join = false;
set mapred.map.tasks=3;
set mapred.reduce.tasks=3;

set hive.exec.compress.intermediate=true;
set hive.intermediate.compression.codec=org.apache.hadoop.io.compress.SnappyCodec;
set hive.intermediate.compression.type=BLOCK;

set hive.exec.compress.output=true;
set mapred.output.compression.codec=org.apache.hadoop.io.compress.LzoCodec;
set mapred.output.compression.type=BLOCK;

set hive.merge.mapfiles=true;
set hive.merge.mapredfiles=true;
set hive.merge.size.per.task=134217728;
set hive.merge.smallfiles.avgsize=134217728;

insert overwrite table ods.cc_studied_lesson_log partition(PARTITION_TIME)
select Id,USER_ID,RECRUIT_ID,LESSON_ID,LESSON_VIDEO_ID,VIDEO_ID,PLAY_TIMES,STUDY_TOTAL_TIME,LEARN_TIME,SOURSE_TYPE,CREATE_TIME,PARTITION_TIME from ods.cc_studied_lesson_log where PARTITION_TIME='$yesterday';


"  >  /flume/flumeFlu/logs/studyLogmergeHiveSmallFile.log   2>&1


if grep -q "FAILED" /flume/flumeFlu/logs/studyLogAddpartition.log 
then 
echo "studyLogMergeHiveSmallFile $yesterday failed !!!" | mail -s 'studyLogMergeHiveSmallFile' lihua@able-elec.com
else
echo "studyLogMergeHiveSmallFile $yesterday success !!!" | mail -s 'studyLogMergeHiveSmallFile' lihua@able-elec.com
fi 


