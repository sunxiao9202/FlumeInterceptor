#!/bin/bash

nohup /flume/flumeMonitor/flume_log_monitor.sh /flume/flumeFlu/logs > /flume/flumeMonitor/monitor.log  2>&1 &

