#!/bin/bash  

########### 定义常量和方法 ############
# 配置文件地址
CONFIG_PATH=./conf.yaml
# 可执行 jar 路径
APP_NAME=""
# JVM 参数
ENV_JAVA_OPTS=""
# 端口
ENV_PORT=""
# 日志路径
LOG_PATH=""
# 日志级别
LOG_LEVEL=""

# 获取配置属性
# Looks up a config value by key from a simple YAML-style key-value map.
# $1: key to look up
# $2: default value to return if key does not exist
# $3: config file to read from
readFromConfig() {
    local key=$1
    local defaultValue=$2
    local configFile=$3
    # first extract the value with the given key (1st sed), then trim the result (2nd sed)
    # if a key exists multiple times, take the "last" one (tail)
    local value=`sed -n "s/^[ ]*${key}[ ]*: \([^#]*\).*$/\1/p" "${configFile}" | sed "s/^ *//;s/ *$//" | tail -n 1`
    [ -z "$value" ] && echo "$defaultValue" || echo "$value"
}
########### 定义常量和方法 ############

########### 读取配置文件属性 ##########
if [ -z "${APP_NAME}" ]; then
    APP_NAME=$(readFromConfig "env.app.path" "" "${CONFIG_PATH}")
    # Remove leading and ending double quotes (if present) of value
    APP_NAME="$( echo "${APP_NAME}" | sed -e 's/^"//'  -e 's/"$//' )"
fi

if [ -z "${ENV_JAVA_OPTS}" ]; then
    ENV_JAVA_OPTS=$(readFromConfig "env.java.opts" "" "${CONFIG_PATH}")
    # Remove leading and ending double quotes (if present) of value
    ENV_JAVA_OPTS="$( echo "${ENV_JAVA_OPTS}" | sed -e 's/^"//'  -e 's/"$//' )"
fi

if [ -z "${ENV_PORT}" ]; then
    ENV_PORT=$(readFromConfig "env.port" "8080" "${CONFIG_PATH}")
fi

if [ -z "${LOG_PATH}" ]; then
    LOG_PATH=$(readFromConfig "env.log.path" "../logs" "${CONFIG_PATH}")
fi

if [ -z "${LOG_LEVEL}" ]; then
    LOG_LEVEL=$(readFromConfig "env.log.level" "INFO" "${CONFIG_PATH}")
fi
########### 读取配置文件属性 ##########

############## 完善参数 #############
JVM_ENV_PORT="--server.port=${ENV_PORT}"
JVM_LOG_HONE="-DLOG_HOME=\"${LOG_PATH}\"";
JVM_LOG_LEVEL="-DLOG_LEVEL=\"${LOG_LEVEL}\"";
############## 完善参数 #############


# 使用说明，用来提示输入参数
usage() {
    echo "Usage: sh deploy.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
  #如果不存在返回1，存在返回0     
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq 0 ]; then
    echo "${APP_NAME} is already running. pid=${pid}"
  else
    echo "${JAVA_HOME}/bin/java -Djava.awt.headless=true ${ENV_JAVA_OPTS} ${JVM_LOG_HONE} ${JVM_LOG_LEVEL} -jar ${APP_NAME} ${JVM_ENV_PORT} >/dev/null 2>&1 &"
    nohup ${JAVA_HOME}/bin/java -Djava.awt.headless=true ${ENV_JAVA_OPTS} ${JVM_LOG_HONE} ${JVM_LOG_LEVEL} -jar ${APP_NAME} ${JVM_ENV_PORT} >/dev/null 2>&1 &
  fi
}

#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi  
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#重启
restart(){
  stop
  sleep 5
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac

