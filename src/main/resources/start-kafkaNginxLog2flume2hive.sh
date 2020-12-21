#!/bin/bash

nowtime=`date '+%Y%m%d_%H%M%S'`


nohup /flume/flume/bin/flume-ng agent \
--conf  /flume/flume/conf \
--name a5 \
--classpath /flume/flumeFlu/files/ip2region.db \
--conf-file  /flume/flumeFlu/conf/kafkaNginxLog2flume2hive.properties \
-Dflume.monitoring.type=http \
-Dflume.monitoring.port=9602 \
> /flume/flumeFlu/logs/kafkaNginxLog2flume2hive_$nowtime.log  2>&1  &

