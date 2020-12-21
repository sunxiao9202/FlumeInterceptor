#!/bin/bash

HIVE_HOME=/hive/apache-hive-2.3.3-bin
$HIVE_HOME/bin/hive -e "
drop table if exists  ods.nginx_log;
CREATE TABLE if not exists ods.nginx_log(
msec string COMMENT '请求时间戳',
forwardedip String COMMENT '请求前端来源IP 多个逗号隔开',
status int COMMENT '请求状态',
hostname  String COMMENT '请求域名',
uri String COMMENT '请求URL路径',
request_id String COMMENT '请求ID',
upstream_http_host String COMMENT '请求到达服务器IP',
http_user_agent String COMMENT '请求设备',
request_time String COMMENT '请求响应时长(ms)',
requestUri  String COMMENT '请求URL路径带参数',
http_referer String COMMENT '请求上一个来源页面地址',
province String COMMENT '前端来源IP所在省份（多个IP取倒数第2个IP）',
city String COMMENT '前端来源IP所在城市（多个IP取倒数第2个IP）'
)PARTITIONED BY (day String,hour String)
row format delimited fields terminated by ',' lines terminated by '\n';
"

