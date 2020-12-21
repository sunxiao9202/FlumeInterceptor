#!/bin/bash

HIVE_HOME=/hive/apache-hive-2.3.3-bin
$HIVE_HOME/bin/hive -e "

CREATE TABLE ods.cc_studied_lesson_log(
id int COMMENT '自增ID',
user_id int COMMENT '用户ID',
recruit_id int,
lesson_id int,
lesson_video_id int,
video_id int COMMENT '观看视频ID',
play_times int COMMENT '本次播放时长',
study_total_time int COMMENT '提交视频总时长',
learn_time string COMMENT '当前学习视频播放时刻HH:mm:ss',
sourse_type int COMMENT '数据来源：1PC、2IOS、3Android',
create_time string COMMENT '创建时间')
PARTITIONED BY ( partition_time string)
row format delimited fields terminated by ',' lines terminated by '\n';

"