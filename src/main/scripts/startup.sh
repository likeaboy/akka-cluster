#!/bin/sh
suffix=`date "+%Y-%m-%d-%H-%M-%S"`
nohup java -Xms2048m -Xmx2048m -jar analysis-tool-raptor.jar >> ../logs/akka.log"_""$suffix" &
pid=$(ps -ef|grep 'analysis-tool-raptor.jar' | grep -v 'grep'|awk '{print $2}')
echo $pid > pid.txt