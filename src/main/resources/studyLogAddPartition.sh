#!/bin/bash

today=`date +%Y%m%d`
tomorrow=`date -d "+1 day" +%Y%m%d`

# flume搜集kafka数据到hive分区表


ssh hadoop01 hive/apache-hive-2.3.3-bin/bin/hive -e "

alter table ods.cc_studied_lesson_log  if not exists add partition (PARTITION_TIME='$tomorrow');

" > /flume/flumeFlu/logs/studyLogAddpartition.log
