#!/bin/bash

now=`date '+%Y-%m-%d %H:%M:%S'`


# 传入要遍历的目录
root_dir="$1"

# 初始化监控文件,通过getdir方法得到
monitor_file=


# 错误记录到mysql
function error_storage(){
/usr/bin/mysql -uhadoop -phadoop_db_password -h mysql.data.center -P 3306 -e \
"use hadoop;
CREATE TABLE IF NOT EXISTS hadoop.FLUME_LOG_MONITOR(
    HOST_NAME VARCHAR(255) COMMENT '日志产生的HOST',
    ERROR_LOG_DIR VARCHAR(255) COMMENT 'ERROR日志目录',
    ERROR_TIMES INT(11) DEFAULT 0 COMMENT '日志中新增ERROR次数',
    CREATE_TIME DATETIME DEFAULT NULL COMMENT '日志时间',
    PRIMARY KEY (HOST_NAME,ERROR_LOG_DIR)
)ENGINE = INNODB  DEFAULT CHARSET = utf8 comment 'FLUME APPLICATION 的日志监控';

"
}

# 钉钉告警

function DingDing_Alarm(){
webhook='https://oapi.dingtalk.com/robot/send?access_token=a59cfbae264752f76851ffb4c9683ab5b8c92fae1f27192d2c7536dd2c67bd6d'
curl $webhook -H 'Content-Type: application/json' -d "
    {
        'msgtype': 'text',
        'text': {
            'content': 'FLUME Log Error Alarm\n
HOST: [$hostname]\n
ERROR_LOG_DIR: [$monitor_file] \n
ERROR_TIMES: [$error_increase]\n
CREATE_TIME: [$now]'
        }
    }"
}


# 错误日志监控
function error_monitor(){

error_times=$(cat $monitor_file | grep 'ERROR' | wc -l)
echo 'error_times' $error_times
 
if [[ $error_times -gt 0 ]];then
	  hostname=$HOSTNAME
	  
	  # 查询mysql中存储的error_times
	  error_times_mysql=`/usr/bin/mysql -uhadoop -phadoop_db_password -h mysql.data.center -P 3306 -Bse "
	  select ERROR_TIMES from hadoop.FLUME_LOG_MONITOR WHERE HOST_NAME='"$hostname"' AND ERROR_LOG_DIR='"$monitor_file"';
	  " | tr -cd '[0-9]' | sed -r 's/0*([0-9])/\1/'`

	  # error_times_mysql为空时，赋值为0
	  para=
	  if [[ $error_times_mysql == $para ]];then
		error_times_mysql=0
	  fi
	 
	  # 新增error次数
	  error_increase=$(($error_times - $error_times_mysql))
	  	  
	  echo 'hostname' $hostname
	  echo 'monitor_file' $monitor_file
	  echo 'error_times_mysql' $error_times_mysql
	  echo 'error_increase' $error_increase
	  
	  if [[ $error_increase -gt 0 ]];then
		 
		 echo 'error_increse 大于0'
		 # 错误日志情况	 
		  /usr/bin/mysql -uhadoop -phadoop_db_password -h mysql.data.center  -P 3306 -e \
		 "
		  replace into hadoop.FLUME_LOG_MONITOR(HOST_NAME,ERROR_LOG_DIR,ERROR_TIMES,CREATE_TIME) values('"$hostname"','"$monitor_file"','"$error_times"','$now');
		 "
		 
		 # 钉钉告警
		 DingDing_Alarm
		   

	   elif [[ $error_increase -lt 0 ]];then
	        echo 'error_increse 小于0'

		 /usr/bin/mysql -uhadoop -phadoop_db_password -h mysql.data.center  -P 3306 -e \
		 "
		  delete from  hadoop.FLUME_LOG_MONITOR WHERE HOST_NAME='"$hostname" AND ERROR_LOG_DIR='"$monitor_file"'';
		 "
	   fi 

fi 

}


# 遍历目录下的所有子目录及文件
function getdir(){
    for element in `ls $1`
    do  
        dir_or_file=$1"/"$element
        if [ -d $dir_or_file ]
        then 
            getdir $dir_or_file
        else
			monitor_file=$dir_or_file
			echo $monitor_file
			#对每个文件日志进行监控
            #error_monitor   
            			
        fi  
    done
}

# 初始话数据库
# error_storage

# 执行脚本  ./flume_log_monitor.sh /flume/flumeFlu/logs
getdir $root_dir

