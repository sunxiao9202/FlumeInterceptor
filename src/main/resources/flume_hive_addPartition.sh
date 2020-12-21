#!/bin/bash 


timeone=`date -d "+1 day" +%Y%m%d`
timetwo=`date -d "+2 day" +%Y%m%d`

function add_partition(){

#提前创建2天ods.nginx_log与ods.nginx_user_log分区
for i in {0..47}
do
days=`date -d "$timeone $i hour" +%Y%m%d`
hours=`date -d "$timeone $i hour" +%H`

echo "alter table ods.nginx_log add if not exists partition (day='$days',hour='$hours');" >> /opt/add_partition.sql
echo "alter table ods.nginx_user_log add if not exists partition (day='$days',hour='$hours');" >> /opt/add_partition.sql

done 

#提前创建2天ods.cc_studied_lesson_log分区
echo "alter table ods.cc_studied_lesson_log  add if not exists partition (PARTITION_TIME='$timeone');" >> /opt/add_partition.sql
echo "alter table ods.cc_studied_lesson_log  add if not exists partition (PARTITION_TIME='$timetwo');" >> /opt/add_partition.sql


/hive/apache-hive-2.3.3-bin/bin/hive -f /opt/add_partition.sql  && \

rm -f /opt/add_partition.sql

}


add_partition





