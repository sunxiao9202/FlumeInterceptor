#!/bin/bash

nowtime=`date '+%Y%m%d_%H%M%S'`


nohup /flume/flume/bin/flume-ng agent \
--conf  /flume/flume/conf \
--name a4 \
--conf-file  /flume/flumeFlu/conf/kafka2flume2hive.properties \
-Dflume.monitoring.type=http \
-Dflume.monitoring.port=9601 \
> /flume/flumeFlu/logs/kafka2flume2hive_$nowtime.log   & 

