#!/bin/bash

flume_status=$(netstat -nl | grep 9601 | grep -v grep | wc -l)
if [[ $flume_status = 1 ]];then

ls /flume/flumeFlu/logs/*  | xargs -I x -n 1 sh -c "echo '' > x"

fi  
