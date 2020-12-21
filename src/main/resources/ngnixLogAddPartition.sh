#!/bin/bash

today=`date +%Y%m%d`
tomorrow=`date -d "+1 day" +%Y%m%d`

set -u
function add_partition(){
# flume搜集kafka数据到hive分区表
hour_format=''
for ((i=0;i<=23;i++))
do

if [[ $i -le 9 ]];then
  number="0$i"
  hour_format=`printf "%02d" $((10#$number))`
else
  hour_format=$i
fi
echo $hour_format

/hive/apache-hive-2.3.3-bin/bin/hive -e "
set hive.execution.engine=tez;
alter table ods.nginx_log add if not exists partition (day='$tomorrow',hour='$hour_format');

" > /flume/flumeFlu/logs/ngnixLogAddpartition.log 2>&1

done

}

function send_email(){
if grep -q "FAILED" /flume/flumeFlu/logs/nginxLogAddpartition.log
then
echo "nginxLogAddpartition $tomorrow failed !!!" | mail -s '[ERROR] Flume nginxLogAddpartition' lihua@able-elec.com
else
echo "nginxLogAddpartition $tomorrow success !!!" | mail -s '[INFO] Flume nginxLogAddpartition' lihua@able-elec.com
fi
}

add_partition
send_email

# 删除分区
# alter table ods.nginx_log drop if exists partition(day='2020907');

