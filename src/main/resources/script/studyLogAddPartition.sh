#!/bin/bash

today=`date +%Y%m%d`
tomorrow=`date -d "+1 day" +%Y%m%d`

# flume搜集kafka数据到hive分区表


/hive/apache-hive-2.3.3-bin/bin/hive -e "

alter table ods.cc_studied_lesson_log  add if not exists partition (PARTITION_TIME='$tomorrow');

" > /flume/flumeFlu/logs/studyLogAddpartition.log 2>&1 

if grep -q "FAILED" /flume/flumeFlu/logs/studyLogAddpartition.log 
then 
echo "studyLogAddPartition $tomorrow failed !!!" | mail -s 'Flume studyLogAddPartition' lihua@able-elec.com
else
echo "studyLogAddPartition $tomorrow success !!!" | mail -s 'Flume studyLogAddPartition' lihua@able-elec.com
fi 

# 删除分区
# alter table ods.cc_studied_lesson_log drop if exists partition(PARTITION_TIME='');

