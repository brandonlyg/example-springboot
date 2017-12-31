#!/bin/sh

APP_NAME="example-springboot"
APP_HOME=`cd $(dirname $0)/..;pwd`

echo $APP_HOME
cd $APP_HOME

JAVA="java"

LOG_DIR="$APP_HOME"/logs

DATE=`date "+%Y-%m-%d"`
STD_LOG_FILE="${LOG_DIR}"/stdout."${DATE}".log

PID_FILE="${LOG_DIR}"/"${APP_NAME}".pid
JAVA_PID=""
if [ -f "${PID_FILE}" ];then
    cd "${APP_HOME}" && sh "bin/shutdown.sh"
fi


JAVA_OPTS="-Xms128M -Xmx128M -Xmn96M -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=96M"
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_DIR}"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:${LOG_DIR}/gc.log"


DIST_JAR="${APP_HOME}"/build/libs/"${APP_NAME}".jar

nohup ${JAVA} -jar ${JAVA_OPTS} "${DIST_JAR}" >> ${STD_LOG_FILE} 2>&1 &
JAVA_PID=$!
echo "${JAVA_PID}" > "${PID_FILE}"

echo "${APP_NAME}"" started, pid: ""${JAVA_PID}"


